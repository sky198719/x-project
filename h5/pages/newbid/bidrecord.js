import React, { Component } from 'react'
import Header from './header'
import { SegmentedControl, Card } from 'antd-mobile'
// import SegmentedControl from 'antd-mobile/lib/segmented-control'
// import Card from 'antd-mobile/lib/card'
import { cFetch } from '../../common/Promise'
import moment from 'moment'
import urlPath from './api'
import { formatDate, delCookie } from '../../common/Util'

class BidRecord extends Component {
    constructor() {
        super()
        this.state = {
            listLock: [],
            listCompent: [],
            messageAlert: false,
            isoverlay: false,
            tipMessage: '',
            modelStatus: '0',
            tabChange: '1'
        }
        this.clickGo = this.clickGo.bind(this)
    }
    async componentDidMount() {
        this.initData('1')
        this.initData('2')
    }
    async initData(type) {
        const res = await cFetch(urlPath.xsbJoinRecord + '/XSB', {
            data: {
                type: type,
                currentPage: 1,
                pageSize: 10
            }
        })
        if(res.code == '200000'){
            if(type == '1'){
                this.setState({
                    listLock: res.data.list
                })
            }else{
                this.setState({
                    listCompent: res.data.list
                })
            }
        }else if(parseInt(res.code) >= 200300 && parseInt(res.code) < 200400){
            this.setState({
                messageAlert: true,
                isoverlay: true,
                tipMessage: '请先登录'
            })
        }else{
            this.setState({
                messageAlert: true,
                isoverlay: true,
                tipMessage: res.message,
                modelStatus: '1'
            })
        }
    }
    onClose() {
        if(this.state.modelStatus == '1'){
            this.setState({
                messageAlert: false,
                isoverlay: false,
                tipMessage: '',
                modelStatus: '0'
            })
        }else{
            delCookie('XXD_webapp_SESSIONID')
            delCookie('userToken')
            window.location.href = "/m?isShowLogin=true"
        }
    }
    valueChange = async (val)=> {
        let typeId = val == '已完成' ? '2' : '1'
        this.setState({
            tabChange: typeId
        })
    }
    clickGo() {
        window.location.href = "/m/#!/static/html/trade/tradeList.html?joinId=" + this.state.listLock[0].joinId +"&isTender=true&pType=xinshoubiao"
    }
    render() {
        if(this.state.tabChange == '1' && this.state.listLock.length == 0){
            return (
                <div>
                    <Header title="新手标加入记录" />
                    <div className="record-top">
                        <SegmentedControl values={['锁定期', '已完成']} onValueChange={ this.valueChange } />
                    </div>
                    <div className="record-top">
                        <p className="text-center">暂无数据</p>
                    </div>
                </div>
            )
        }
        if(this.state.tabChange == '2' && this.state.listCompent.length == 0){
            return (
                <div>
                    <Header title="新手标加入记录" />
                    <div className="record-top">
                        <SegmentedControl values={['锁定期', '已完成']} onValueChange={ this.valueChange } />
                    </div>
                    <div className="record-top">
                        <p className="text-center">暂无数据</p>
                    </div>
                </div>
            )
        }
        var lockstatus = ''
        if(this.state.listLock.length != 0){
            switch (this.state.listLock[0].status){
                case 0:
                lockstatus = '加入中'
                    break;
                case 1:
                lockstatus = '加入成功'
                    break;
                case 2:
                lockstatus = '到期退出'
                    break;
                case 3:
                lockstatus = '提前退出'
                    break;
                case 4:
                lockstatus = '正在退出'
                    break;
                default:
                lockstatus = '未知'
            }
        }
        return (
            <div>
                <Header title="新手标加入记录" />
                <div className="record-top">
                    <SegmentedControl values={['锁定期', '已完成']} onValueChange={ this.valueChange.bind(this) } />
                </div>
                <div className={ this.state.tabChange == '1' ? 'record-top whitebg' :  'record-top dim'}>
                    {
                        this.state.listLock.map((item)=> {
                            const title = item.name + '-' + item.periodName + '期'
                            return (
                                <Card key={item.productId}>
                                    <Card.Header
                                        title={title}
                                        extra={<span>{lockstatus}</span>}
                                    />
                                    <Card.Body>
                                        <div>
                                            <p>出借金额：{item.account}元</p>
                                            <p>历史年化收益：{item.apr}%</p>
                                            <p>出借时间：{moment(item.addDate).format('YYYY-MM-DD HH:mm:ss')} <b>锁定期</b></p>
                                            <p>历史收益：{item.interest}元 <b className="red">{item.period + item.periodUnit}</b></p>
                                            <p>债权列表：<span onClick={this.clickGo} className="blue">点击查看</span></p>
                                            <p>于{moment(item.endDate).format('YYYY-MM-DD')}日到期 本金和收益将返回新新贷账户</p>
                                        </div>
                                    </Card.Body>
                                </Card>
                            )
                        })
                    }
                </div>
                
                <div  className={ this.state.tabChange == '2' ? 'record-top whitebg' :  'record-top dim'}>
                    {
                        this.state.listCompent.map((item)=> {
                            const title = item.name + '-' + item.periodName + '期'
                            return (
                                <Card key={item.productId}>
                                    <Card.Header
                                        title={title}
                                        extra={<span></span>}
                                    />
                                    <Card.Body>
                                        <div>
                                            <p>出借金额：{item.account}元</p>
                                            <p>历史年化收益：{item.apr}%</p>
                                            <p>出借时间：{moment(item.addDate).format('YYYY-MM-DD HH:mm:ss')} <b>锁定期</b></p>
                                            <p>历史收益：{item.interest}元 <b className="red">{item.period + item.periodUnit}</b></p>
                                            <p>于{moment(item.endDate).format('YYYY-MM-DD')}日到期 本金和收益已返回新新贷账户</p>
                                        </div>
                                    </Card.Body>
                                </Card>
                            )
                        })
                    }
                </div>
                <div className={ this.state.messageAlert ? 'modal modal-in modal-visibal' :  'modal modal-in'}>
                    <div className="modal-inner">
                        <div className="modal-title">提示</div>
                        <div className="modal-text">{ this.state.tipMessage }</div>
                    </div>
                    <div className="modal-buttons ">
                        <span className="modal-button modal-button-bold" onClick={ this.onClose.bind(this) }>确定</span>
                    </div>
                </div>
                <div className={ this.state.isoverlay ? 'modal-overlay modal-visibal' : 'modal-overlay' }  ></div>
                
            </div>
        )
    }
}

export default BidRecord
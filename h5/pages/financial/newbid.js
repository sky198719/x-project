import React, { Component } from 'react'
import Head from 'next/head'
import Ceil from '../../components/ceiling/index'
import Api from '../../components/api/financial'
import loginApi from '../../components/api/home'
import userApi from '../../components/api/purse'
import HomeIco from './components/homeico'
import ProBottom from './components/probottom'
import { Modal, Toast } from 'antd-mobile'
import Tab from './components/tab'
import Problem from './components/problem'
import Mask from './components/modal'
import bridge from "../../static/merge/xxd-jsBridge.esm"
import { localItem , removeLocalItem, getQueryString } from '../../common/Util'

const alert = Modal.alert
export default class extends Component {
    constructor(props) {
        super(props)
        this.state = {
            btnName: '马上加入',
            btnGray: false,
            isGoApp: false,
            isLogin: false,
            month: '',
            datas: {},
            totalCount: ''
        }
    }
    async componentDidMount() {
        Toast.loading('加载中……', 0)
        const month = getQueryString('query')
        const datas = await Api.getNewBid({month: month})
        const recordContext = {
            reglintstId: datas.id,
            currentPage: 1,
        }
        const recode = await Api.newBidRecord(recordContext)

        this.setState({
            month,
            datas,
            totalCount: recode.totalCount
        })


        let localItems = {}
        if(localItem('dataList')){
            localItems = JSON.parse(localItem('dataList'))
        }
        if(sessionStorage.getItem('riskSession')){
            sessionStorage.removeItem('riskSession')
        }
        if(localStorage.getItem('creditList')){
            localStorage.removeItem('creditList')
        }
        const data = await Api.getNewBid({month: month})
        const {isLogin, isPurchasedProduct, status} = data
        await this.setState({
            isLogin
        })
        if(isLogin){
            //  已登录
            if(isPurchasedProduct){
                //  已加入过了其他产品
                await this.setState({
                    btnName: '新手专享，更多优质产品尽在APP',
                    isGoApp: true
                })
            }else{
                this.nameChange(status)
            }
        }else{
            //  未登陆
            this.nameChange(status)
        }

        Toast.hide()
    }
    nameChange = (status) => {
        switch(status) {
            case '1':
                this.setState({
                    btnName: '等待发售',
                    btnGray: true
                })
                break;
            case '2':
                this.setState({
                    btnName: '马上加入'
                })
                break;
            case '3':
                this.setState({
                    btnName: '已售罄',
                    btnGray: true
                })
                break;
            default: 
        }
    }
    buyProduct = async () => {
        const isLogin = this.state.isLogin
        if(this.state.isGoApp){
            bridge.open({
                pagename: 'home'
            } , ()=>{
                var ua  = bridge.ua;
                if(ua.isWeChat()) {
                    alert("微信里面无法唤醒APP");
                }
                location = "http://m.xinxindai.com/m/static/html/download/app.html?model=auto";
            })
        }else{
            if(isLogin){
                const res = await userApi.userInfo()
                if(res.openAccountStatus == '1'){
                    //  已开户
                    if(res.riskExamStatus == '1'){
                        //  已评测
                        location = '/financial/neworderbuy?id=' + this.state.month
                    }else{
                        //  未评测
                        alert('提示', '您尚未进行风险评测，请先进行风险评测', [
                            { text: '取消', onPress: () => console.log('cancel') },
                            { text: '去评测', onPress: () => {
                                sessionStorage.setItem('riskSession', location.href)
                                location = '/mypurse/riskrating'
                            }},
                        ])
                    }
                }else{
                    //  未开户
                    if(res.mobile){
                        alert('提示', '请先开通存管账户', [
                            { text: '取消', onPress: () => console.log('cancel') },
                            { text: '存管开户', onPress: () => {
                                location = '/mypurse/openaccount'
                            }},
                        ])
                    }else{
                        alert('提示', '您尚未绑定手机号，为了您的账户安全，请前往APP进行绑定手机号后进行开户操作', [
                            { text: '取消', onPress: () => console.log('cancel') },
                            { text: '前往APP', onPress: () => {
                                bridge.open({
                                    pagename: 'home'
                                } , ()=>{
                                    var ua  = bridge.ua;
                                    if(ua.isWeChat()) {
                                        alert("微信里面无法唤醒APP");
                                    }
                                    location = "http://m.xinxindai.com/m/static/html/download/app.html?model=auto";
                                })
                            }},
                        ])
                    }
                }
            }else{
                Toast.info('请先登录', 2)
                sessionStorage.setItem('loginType', window.location.href)
                setTimeout(()=> {
                    location = '/login'
                }, 2000)
            }
        }
        
    }
    render() {
        const data = this.state.datas
        return (
            <div>
                <Head>
                    <link rel='stylesheet' type='text/css' href="/static/mods/financial/_.css" />
                </Head>
                <div className="product-box position-a">
                    <div className="product—detail-container newbie-container position-a">
                        <div className="detail-wrapper">
                            <h1 className="product-name">{data.name}</h1>
                            <div className="rate-wrapper-1"></div>
                            <div className="rate-wrapper-2"></div>
                            <div className="rate-wrapper">
                                <div className="wrapper">
                                    <p className="rate">{data.apr || 0}%</p>
                                    <p className="txt">历史年化收益</p>
                                </div>
                                <p className="require-tips"><span>限购一次</span></p>
                            </div>
                        </div>
                        <ul className="xxd-common-list list-wrapper">
                            <li className="list-item">
                                <span className="item-txt">起投金额</span>
                                <span className="item-right-txt">{data.lowestTender}元</span>
                            </li>
                            <li className="list-item">
                                <span className="item-txt">出借期限</span>
                                <span className="item-right-txt">{data.period}个月</span>
                            </li>
                            <li className="list-item">
                                <span className="item-txt">起息时间</span>
                                <span className="item-right-txt">募集完成后开始起息</span>
                            </li>
                        </ul>
                
                        <ul className="intro-list">
                            <Tab title="产品描述" dev_id="A8.1-2" eventtype="close_float_window" eventtypehide="open_float_window" show={ true }>
                                新手标是针对<span className="notice">尚未在新新贷出借过的新手用户</span>推出的自动投标出借计划，新手标
                                在用户任何的标的范围内，对符合要求的标的进行自动投标，切回款本金在相应期限内自动复投，期限结束后新手标会通过
                                新新贷债权转让平台进行转让推出，该计划一旦投标成功，不支持提前退出或债权转让，该计划所对应的标的均百分之百适
                                用于新新贷质保服务专款计划并由系统实现标的分散出借。
                            </Tab>
                            <Tab title="出借图示" show={ false }>
                                <div className="wrapper">
                                    <p>
                                        <span>计息期</span>
                                    </p>
                                    <div>
                                        <span className="line"></span>
                                        <span className="circle"></span>
                                        <span className="txt">加入新手标</span>
                                    </div>
                                    <div>
                                        <span className="line"></span>
                                        <span className="circle"></span>
                                        <span className="txt">起息日</span><i className="desc">次日开始计息</i>
                                    </div>
                                    <div>
                                        <span className="line"></span>
                                        <span className="circle"></span>
                                        <span className="txt">锁定期</span><i className="desc">锁定期内不可退出</i></div>
                                    <div>
                                        <span className="line"></span>
                                        <span className="circle"></span>
                                        <span className="txt">到期日</span><i className="desc">结束计息</i>
                                    </div>
                                    <div><span className="circle"></span>
                                        <span className="txt">退出日</span><i className="desc">本金和利息返还至新新贷账户</i>
                                    </div>
                                </div>
                            </Tab>
                            <Tab title="回款方式" show={ false }>
                                <p>1、锁定期结束后次日（含周末及节假日）系统自动将本金和收益返还到新新贷账户。</p>
                                <p>2、锁定期内不支持提前退出。</p>
                            </Tab>
                            <Tab title="费用说明" show={ false }>
                                <p>1、加入费用：0.00%</p>
                                <p>2、到期退出费用：0.00%</p>
                            </Tab>
                        </ul>
                
                        <Problem joinNum={ this.state.totalCount } proId={ this.state.datas.id } />
                
                        <ProBottom show={ true } type="newbid" />
                    </div>
                
                    <div className="div-bottom-btn">
                        <button className="xxd-xl-btn dmp-click" dev_id="A8.1-1" eventtype="jump" onClick={ this.buyProduct } disabled={ this.state.btnGray }>{ this.state.btnName }</button>
                    </div>

                    <HomeIco dev_id="A8.1-7" />
                
                    <Mask show={ this.state.isGoApp } />
                </div>
            </div>
        )
    }
}
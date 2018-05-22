import React, { Component } from 'react'

import Router from 'next/router'

import server from '../api/index'

import { Modal } from 'antd-mobile'


class TanderOther extends Component {
    constructor(props) {
        super(props)

        this.state = {
            modal: false
        }

        this.contract = ()=> {
            Router.push({pathname: '/consumes/contract', query: { bidCode: this.props.detailData.bidCode }})
        }

        this.goBid = this.goBid.bind(this)
    }

    componentDidMount() {
    }

    async goBid() {
        const res = await server.isLogin()
        if(res){
            Router.push({pathname: '/consumes/bid', query: { bidCode: this.props.detailData.bidCode }})
        }else{
            this.setState({
                modal: true,
            });
        }
    }
    onClose = key => () => {
        this.setState({
          [key]: false,
        });
        window.location.href = "/m?isShowLogin=true"
    }
    

    render() {
        if(!this.props.detailData){
            return (
                <div>
                </div>
            )
        }

        
        const detailData = this.props.detailData
        var allExpense = ''
        if(detailData.expenseExplanation){
            const expensePtag = detailData.expenseExplanation.substr(0,3)
            const expense = detailData.expenseExplanation.substr(8)
            allExpense = expensePtag + expense
        }
        return (
            <div>
                <div className="nw1e title-person">其它信息</div>
                <ul className="xui-list mh10x nh1e-z nl1e nr1e-z">
                    <li className="open no-active">
                        <h3 className="list_switch ew w100" onClick={ this.props.methods }>
                            <b className="this-title font17">费用说明</b>
                            <span className="surplus-a ml ar co-back"><i className="icon icon-right"></i></span>
                        </h3>
                        <div className="moneysay" dangerouslySetInnerHTML={{__html: `${allExpense}`}} />
                    </li>
                    <li id="safe_type" onClick={ this.props.safeGuard }>
                        <h3 className="ew w100">
                            <b className="this-title font17">还款保障措施</b>
                            <span className="surplus-a ml ar co-back"><i className="icon icon-right"></i></span>
                        </h3>
                    </li>
                    <li id="danger_type" onClick={ this.props.safeGuard }>
                        <h3 className="ew w100">
                            <b className="this-title font17">项目风险提示</b>
                            <span className="surplus-a ml ar co-back"><i className="icon icon-right"></i></span>
                        </h3>
                    </li>
                    <li>
                        <h3 className="ew w100" onClick={ this.contract }>
                            <b className="this-title font17">合同范本</b>
                            <span className="surplus-a ml ar co-back"><i className="icon icon-right"></i></span>
                        </h3>
                    </li>
                </ul>
                <div className="nw1e title-person">投标记录</div>
                <ul className="xui-list mh10x nh1e-z nl1e nr1e-z">
                        <li id="o_record" onClick={ this.props.toRecord }>
                            <h3 className="ew w100">
                                <b className="this-title font17">查看详情</b>
                                <span className="surplus-a ml ar co-back">
                                            <i className="icon icon-right"></i>
                                        </span>
                            </h3>
                        </li>
                </ul>

                <div className="nw12x nb2e">
                    <button id="to_bid" className="xui-btn w100 lm" data-co="main" onClick={ this.goBid } disabled={detailData.status.code == 'BIDDING' ? false:true}>{ detailData.status.code == 'BIDDING'?'马上投标': '已售完' }</button>
                </div>
                <Modal
                    visible={this.state.modal}
                    transparent
                    maskClosable={false}
                    onClose={this.onClose('modal')}
                    title="提示"
                    footer={[{ text: '确定', onPress: () => { console.log('ok'); this.onClose('modal')(); } }]}
                >
                    <div style={{ overflow: 'scroll' }}>
                        请先登录
                    </div>
                </Modal>
            </div>
        )
    }
}

export default TanderOther
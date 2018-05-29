import React, { Component } from 'react'
import Ceil from '../../components/ceiling/index'
import Head from 'next/head'
import HomeIco from './components/homeico'
import ProBottom from './components/probottom'
import Tab from './components/tab'
import { getQueryString } from '../../common/Util'

import Api from '../../components/api/financial'
import bridge from "../../static/merge/xxd-jsBridge.esm"
import Loading from '../../common/Loading'

export default class extends Component {
    constructor(props) {
        super(props)
        this.state = {
            data: {}
        }
    }
    openApp = () => {
        bridge.open({
            pagename: 'home'
        } , ()=>{
            var ua  = bridge.ua;
            if(ua.isWeChat()) {
                alert("微信里面无法唤醒APP");
            }
            location = "http://m.xinxindai.com/m/static/html/download/app.html?model=auto";
        })
    }
    async componentDidMount(){
        const yyp = await Api.hotFinancialOther({type: 6})
        const context = {
            id: yyp[0].items[0].productId,
            type: 6,
        }
        const data = await Api.financialDetail(context)
        this.setState({
            data
        })
    }
    compute = () => {
        const { productDetail } = this.state.data
        const num = productDetail.plannedAnnualRate * 0.01
        const float = productDetail.floatingRate * 0.01
        const month = productDetail.leastPeriod
        let startMoney, endMoney, startResult, endResult, allResult = 0
        startMoney = 10000 * num / month
        startResult = Math.floor(startMoney * 100) / 100
        endMoney = 10000 * float / month
        endResult = Math.floor(endMoney * 100) / 100
        allResult = (startResult + endResult).toFixed(2)
        return allResult
    }
    render() {
        const { productDetail } = this.state.data
        if(!productDetail){
            return (
                <div>
                    <Head>
                        <link rel='stylesheet' type='text/css' href="/static/mods/financial/_.css" />
                        <title>__每月付息_出借产品_网络借贷-_</title>
                        <meta name="keywords" content="_,出借，网络借贷" />
                        <meta name="description" content="_是_推出的便捷高效的自动投标工具。_在用户认可的标的范围内，对符合要求的标的进行自动投标，每月派息，到期通过债权转让退出。详情请登录_官网。" />
                    </Head>
                    <div className="product-box position-a">
                        <Ceil />
                        <Loading />
                    </div>
                </div>
            )
        }
        return (
            <div>
                <Head>
                    <link rel='stylesheet' type='text/css' href="/static/mods/financial/_.css" />
                    <title>__每月付息_出借产品_网络借贷-_</title>
                    <meta name="keywords" content="_,出借，网络借贷" />
                    <meta name="description" content="_是_推出的便捷高效的自动投标工具。_在用户认可的标的范围内，对符合要求的标的进行自动投标，每月派息，到期通过债权转让退出。详情请登录_官网。" />
                </Head>
                <div className="product-box position-a">
                    <Ceil />
                    <div className="product—detail-container position-a">
                        <div className="detail-wrapper">
                            <h1 className="product-name">{productDetail.name}</h1>
                            <div className="rate-wrapper-1"></div>
                            <div className="rate-wrapper-2"></div>
                            <div className="rate-wrapper">
                                <div className="wrapper">
                                    <p className="rate">{ productDetail.plannedAnnualRate + '%' }<span>{ productDetail.floatingRate > 0 ? '+' + productDetail.floatingRate : ''   }<i className={ productDetail.floatingRate > 0 ? 'toast' : '' }>{ productDetail.floatingRate > 0 ? '限时加息' : '' }</i></span></p>
                                    <p className="txt">历史年化收益可达</p>
                                </div>
                                <p className="require-tips">
                                    <span>APP专享</span>
                                    <span>每月收息</span>
                                </p>
                            </div>
                        </div>
                        <ul className="xxd-common-list list-wrapper">
                            <li className="list-item">
                                <span className="item-txt">起投金额</span>
                                <span className="item-right-txt">{ productDetail.leastInvestAmount }元</span>
                            </li>
                            <li className="list-item">
                                <span className="item-txt">锁定期限</span>
                                <span className="item-right-txt">{productDetail.leastPeriod}{productDetail.leastPeriodUnit}</span>
                            </li>
                            <li className="list-item">
                                <span className="item-txt">起息时间</span>
                                <span className="item-right-txt">募集完成后开始起息</span>
                            </li>
                        </ul>
                
                        <ul className="intro-list">
                            <Tab title="产品描述" show={ true }>
                                _是_推出的便捷高效的自动投标工具。_在用户认可的标的范围内，对符合要求的标的进行自动投标，根据借款用户回款日期收息，期限结束后经用户授权，通过_债权转让平台进行转让退出。该计划所对应的标的由系统实现标的分散出借。
                            </Tab>
                            <Tab title="收益计算" show={ false }>
                                <p>出借锁定期：<span className="notice">{productDetail.leastPeriod}{productDetail.leastPeriodUnit}</span></p>
                                <p>还款方式：每月结息，到期通过债权转让退出</p>
                                <p>每月万元收益：<span className="notice">{this.compute()}元</span>（相当于历史年化收益率<span className="notice">{ productDetail.plannedAnnualRate + '%' }{ productDetail.floatingRate > 0 ? '+' + productDetail.floatingRate : ''   }</span>)</p>
                                <p>每月收益=出借金额<span className="notice">×{ productDetail.plannedAnnualRate + '%' }</span>÷{productDetail.leastPeriod}<span className="notice">{ productDetail.floatingRate > 0 ? '+出借金额×' + productDetail.floatingRate + '÷' + productDetail.leastPeriod : '' }</span></p>
                            </Tab>
                            <Tab title="回款方式" show={ false }>
                                <p>1、产品锁定期结束后自动退出，不收取任何费用。</p>
                                <p>2、产品提前退出，收取<span className="notice">{productDetail.forfeitPercent + '%'}</span>违约金。</p>
                                <p>退出后本金不再复投，经用户授权退出以系统自动转让债权的方式完成。</p>
                            </Tab>
                        </ul>
                
                        <ProBottom />
                    </div>
                    <div className="div-bottom-btn">
                        <button className="xxd-xl-btn" onClick={ this.openApp }>心动了吗？赶紧前往_APP吧</button>
                    </div>
                    <HomeIco />
                </div>
            </div>
        )
    }
}
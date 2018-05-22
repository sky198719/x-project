import React, { Component } from 'react'
import Ceil from '../../components/ceiling/index'
import Head from 'next/head'
import HomeIco from './components/homeico'
import ProBottom from './components/probottom'
import Tab from './components/tab'
import { getQueryString } from '../../common/Util'
import Loading from '../../common/Loading'

import Api from '../../components/api/financial'
import bridge from "../../static/merge/xxd-jsBridge.esm"

export default class extends Component {
    constructor(props) {
        super(props)
        this.state = {
            data : {},
            eachNew: {}
        }
    }
    async componentDidMount() {
        const xyb = await Api.hotFinancialOther({type: 5})
        const context = {
            id: xyb[0].items[0].productId,
            type: 5,
        }
        const eachNew = await Api.getEachNew()
        const data = await Api.financialDetail(context)
        this.setState({
            data,
            eachNew
        })
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
    compute = ( month, apr, floatApr ) => {
        const num = apr * 0.01
        const float = floatApr * 0.01
        let startMoney, endMoney, startResult, endResult, allResult = 0
        startMoney = 10000 * num / 12 * month
        startResult = Math.floor(startMoney * 100) / 100
        endMoney = 10000 * float / 12 * month
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
                        <title>新元宝_出借产品_多种期限出借服务-新新贷</title>
                        <meta name="keywords" content="新元宝，出借，出借服务，定期理财，投资理财" />
                        <meta name="description" content="新元宝是新新贷推出的自动投标计划，所有加入新元宝的资金将由系统优先匹配优质债权，出借资金不站岗！详情请登录新新贷官网。" />
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
                    <title>新元宝_出借产品_多种期限出借服务-新新贷</title>
                    <meta name="keywords" content="新元宝，出借，出借服务，定期理财，投资理财" />
                    <meta name="description" content="新元宝是新新贷推出的自动投标计划，所有加入新元宝的资金将由系统优先匹配优质债权，出借资金不站岗！详情请登录新新贷官网。" />
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
                                    <p className="rate">{ productDetail.plannedAnnualRate + '%' }<span>{ productDetail.floatingRate > 0 ? '+' + productDetail.floatingRate + '%' : ''   }<i className={ productDetail.floatingRate > 0 ? 'toast' : '' }>{ productDetail.floatingRate > 0 ? '限时加息' : '' }</i></span></p>
                                    <p className="txt">历史年化收益可达</p>
                                </div>
                                <p className="require-tips">
                                    <span>APP专享</span>
                                    <span>多种期限选择</span>
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
                                <span className="item-right-txt">{ productDetail.leastPeriod } { productDetail.leastPeriodUnit }</span>
                            </li>
                            <li className="list-item">
                                <span className="item-txt">起息时间</span>
                                <span className="item-right-txt">募集完成后开始起息</span>
                            </li>
                        </ul>
                
                        <ul className="intro-list">
                            <Tab title="产品描述" show={ true }>
                                新元宝是新新贷推出的便捷高效的自动投标工具。新元宝在用户认可的标的范围内，对符合要求的标的进行自动投标，且回款本金在相应期限内自动复投，期限结束后新元宝会通过新新贷债权转让平台进行转让退出。该计划对应标的由系统实现标的分散出借。
                            </Tab>
                           <Tab title="收益计算" show={ false }>
                                <ul>
                                    <li>
                                        <span>出借锁定期</span>
                                        <span>历史年化收益</span>
                                        <span>每万元收益(元)</span>
                                    </li>
                                    {
                                        this.state.eachNew.map((item)=> {
                                            return (
                                                <li key={ item.period }>
                                                    <span>{ item.period }个月</span>
                                                    <span>{ item.apr + '%' }{ item.floatApr > 0 ? <i> + <i className='notice'>{ item.floatApr + '%' }</i> </i> : '' }</span>
                                                    <span>{ this.compute(item.period, item.apr, item.floatApr) }</span>
                                                </li>
                                            )
                                        })
                                    }
                                </ul>
                                <p className="right">收益=<span className="notice">出借金额×历史年化收益/12×锁定期</span></p>
                            </Tab>
                            <Tab title="回款方式" show={ false }>
                                <p>1、锁定期结束后次日（含周末及节假日）系统自动将本金和收益返回到新新贷账户，不收取任何费用。</p>
                                <p>2、产品提前退出，收取{productDetail.forfeitPercent}%的违约金。</p>
                            </Tab>
                        </ul>
                        <ProBottom />
                    </div>
                
                    <div className="div-bottom-btn">
                        <button className="xxd-xl-btn" onClick={ this.openApp }>心动了吗？赶紧前往新新贷APP吧</button>
                    </div>
                
                    <HomeIco />
                </div>
            </div>
        )
    }
}
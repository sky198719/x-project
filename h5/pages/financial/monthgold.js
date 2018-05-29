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
            data: {}
        }
    }
    async componentDidMount() {
        let id = ''
        const hotData = await Api.hotFinancial()
        for(let i=0; i<hotData.length; i++){
            if(hotData[i].productType == 4){
                id = hotData[i].productId
            }
        }
        const context = {
            id: id,
            type: 4,
        }
        const data = await Api.financialDetail(context)
        if(!data || JSON.stringify(data) == '{}'){
            await this.setState({
                data: {
                    productDetail:{},
                }
            })
        }else{
            await this.setState({
                data
            })
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
            location = "/m/static/html/download/app.html?model=auto";
        })
    }
    render() {
        const { productDetail } = this.state.data
        if(!productDetail){
            return (
                <div>
                    <Head>
                        <link rel='stylesheet' type='text/css' href="/static/mods/financial/_.css" />
                        <title>__出借产品_出借服务-_</title>
                        <meta name="keywords" content="_,出借产品,出借服务,P2P理财,投资理财" />
                        <meta name="description" content="_是_推出的31天期出借计划，历史年化收益12%，当日起息。出借产品可以在每天10点、20点限时发售，成功出借本产品之日起31天（含加入当日及节假日）后，系统通过债权转让退出的方式将资金返回到您的_账户，详情请登录_官网。" />
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
                    <title>__出借产品_出借服务-_</title>
                    <meta name="keywords" content="_,出借产品,出借服务,P2P理财,投资理财" />
                    <meta name="description" content="_是_推出的31天期出借计划，历史年化收益12%，当日起息。出借产品可以在每天10点、20点限时发售，成功出借本产品之日起31天（含加入当日及节假日）后，系统通过债权转让退出的方式将资金返回到您的_账户，详情请登录_官网。" />
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
                                    <p className="txt">历史年化收益</p>
                                </div>
                                <p className="require-tips">
                                    <span>APP专享</span>
                                    <span>限时秒杀</span>
                                </p>
                            </div>
                        </div>
                        <ul className="xxd-common-list list-wrapper">
                            <li className="list-item">
                                <span className="item-txt">起投金额</span>
                                <span className="item-right-txt">{productDetail.leastInvestAmount}元</span>
                            </li>
                            <li className="list-item">
                                <span className="item-txt">出借上限</span>
                                <span className="item-right-txt">{productDetail.mostInvestAmount}元</span>
                            </li>
                            <li className="list-item">
                                <span className="item-txt">起息时间</span>
                                <span className="item-right-txt">募集完成后开始起息</span>
                            </li>
                            <li className="list-item">
                                <span className="item-txt">锁定期限</span>
                                <span className="item-right-txt">{productDetail.leastPeriod}{productDetail.leastPeriodUnit}</span>
                            </li>
                        </ul>
                
                        <ul className="intro-list">
                            <Tab title="产品描述及加入说明" show={ true }>
                                <p>_是_推出的<span className="notice">{productDetail.leastPeriod}{productDetail.leastPeriodUnit}</span>期出借计划，每日<span className="notice">10:00、20:00</span>限时销售，售完即止。</p>
                                <p>该产品{productDetail.leastInvestAmount}元起投，<span className="notice">{productDetail.leastInvestAmount}元</span>为一个出借单位，每位用户出借额度为<span className="notice">{productDetail.mostInvestAmount}元</span>， 出借金额到期退出后释放出借额度，可以继续出借 。该产品一旦出借成功，不支持提前赎回。该产品债权对接平台的优质短期借款标的。</p>
                            </Tab>
                            <Tab title="收益如何计算" show={ false }>
                                本产品出借成功后当日开始计息，收益=<span className="notice">出借金额×{productDetail.plannedAnnualRate  }%/360×31</span>
                            </Tab>
                            <Tab title="如何回款" show={ false }>
                                成功出借本产品之日起{productDetail.leastPeriod}{productDetail.leastPeriodUnit}（含加入当日及节假日）后，系统自动将本金加利息返回到您的_账户。
                            </Tab>
                        </ul>
                
                        <ProBottom />
                    </div>
                
                    <div className="div-bottom-btn">
                        <button className="xxd-xl-btn dmp-click" dev_id="A8.3-1.1" eventtype="jump" onClick={ this.openApp }>心动了吗？赶紧前往_APP吧</button>
                    </div>
                
                    <HomeIco />
                </div>
            </div>
        )
    }
}
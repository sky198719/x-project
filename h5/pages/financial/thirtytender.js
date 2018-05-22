import React, { Component } from 'react'
import Ceil from '../../components/ceiling/index'
import Head from 'next/head'
import HomeIco from './components/homeico'
import ProBottom from './components/probottom'
import Tab from './components/tab'
import Problem from './components/problem'

import userApi from '../../components/api/home'
import Api from '../../components/api/financial'
import myApi from '../../components/api/purse'
import Mask from './components/modal'
import { Modal, Toast } from 'antd-mobile'

import Loading from '../../common/Loading'

import bridge from "../../static/merge/xxd-jsBridge.esm"

import { localItem , removeLocalItem } from '../../common/Util'

const alert = Modal.alert
export default class extends Component {
    constructor(props) {
        super(props)
        this.state = {
            btnName: '马上加入',
            btnGray: false,
            isGoApp: false,
            isBuy: false,
            data: {}
        }
    }
    async componentDidMount() {
        if(localItem('dataList')){
            localItems = JSON.parse(localItem('dataList'))
        }
        if(sessionStorage.getItem('riskSession')){
            sessionStorage.removeItem('riskSession')
        }
        if(sessionStorage.getItem('creditList')){
            sessionStorage.removeItem('creditList')
        }
        if(localStorage.getItem('creditList')){
            localStorage.removeItem('creditList')
        }
        let id = ''
        const hotData = await Api.hotFinancial()
        for(let i=0; i<hotData.length; i++){
            if(hotData[i].productType == 10){
                id = hotData[i].productId
            }
        }
        const context = {
            id: id,
            type: 10
        }
        const data = await Api.financialDetail(context)
        await this.setState({
            data
        })
        const userRes = await userApi.isLogin()
        const productDetail = data.productDetail
        const homeRes = await userApi.homeServer()
        if(homeRes.data.investStatus == '0'){
            // 没有加入过
            await this.setState({
                isBuy: false
            })
        }else{
            await this.setState({
                isBuy: true
            })
        }
        if(userRes){
            //  已登陆
            if(this.state.isBuy){
                this.setState({
                    btnName: '新手专享，更多优质产品尽在APP',
                    isGoApp: true
                })
            }else{
                this.btnChange(productDetail.status, productDetail.leftAmount, productDetail.mostInvestAmount)
            }
        }else{
            // 未登录
            this.btnChange(productDetail.status, productDetail.leftAmount, productDetail.mostInvestAmount)
        }
    }
    btnChange = (status, leftAmount, mostInvestAmount) => {
        if(status == '235' || status == '236'){
            this.setState({
                btnGray: true,
                btnName: '等待发售'
            })
        }else if(status == '237'){
            if(leftAmount < mostInvestAmount){
                this.setState({
                    btnName: '已售罄',
                    btnGray: true
                })
            }else{
                this.setState({
                    btnName: '马上加入'
                })
            }
        }else if(status == '238'){
            this.setState({
                btnName: '已售罄',
                btnGray: true
            })
        }
    }
    compute = ( money ) => {
        const { productDetail } = this.state.data
        const num = productDetail.plannedAnnualRate * 0.01
        const float = productDetail.floatingRate * 0.01
        let startMoney, endMoney, startResult, endResult, allResult = 0
        startMoney = money * num / 12
        startResult = Math.floor(startMoney * 100) / 100
        endMoney = money * float / 12
        endResult = Math.floor(endMoney * 100) / 100
        allResult = (startResult + endResult).toFixed(2)
        return allResult
    }
    buyProduct = async () => {
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
            const userRes = await userApi.isLogin()
            if(userRes){
                const res = await myApi.userInfo()
                if(res.openAccountStatus == '1'){
                    //  已开户
                    if(res.riskExamStatus == '1'){
                        //  已评测
                        location = '/financial/orderbuy?id=' + this.state.data.productDetail.productId
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
                setTimeout(() => {
                    location = '/login'
                }, 2000)
            }
        }
    }
    render() {
        const { totalCount, productDetail } = this.state.data
        if(!productDetail){
            return (
                <div>
                    <Head>
                        <link rel='stylesheet' type='text/css' href="/static/mods/financial/_.css" />
                        <title>新元宝（新手专享）-新手福利-出借服务-新新贷</title>
                        <meta name="keywords" content="新元宝，新手专享，新手福利，出借，理财，投资理财，新新贷" />
                        <meta name="description" content="新新贷（m.xinxindai.com）是中国顶尖P2P网络借款平台,为广大中小微企业以及民间个人提供无抵押小额贷款,企业项目融资,P2P网贷,P2P贷款,无抵押贷款信用贷款服务，成为P2P网贷平台和中小微金融服务的行业标杆品牌。新新贷以信相贷用新服务。" />
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
                    <title>新元宝（新手专享）-新手福利-出借服务-新新贷</title>
                    <meta name="keywords" content="新元宝，新手专享，新手福利，出借，理财，投资理财，新新贷" />
                    <meta name="description" content="新新贷（m.xinxindai.com）是中国顶尖P2P网络借款平台,为广大中小微企业以及民间个人提供无抵押小额贷款,企业项目融资,P2P网贷,P2P贷款,无抵押贷款信用贷款服务，成为P2P网贷平台和中小微金融服务的行业标杆品牌。新新贷以信相贷用新服务。" />
                </Head>
                <div className="product-box position-a">
                    <Ceil />
                    <div className="product—detail-container position-a">
                        <div className="detail-wrapper">
                            <h1 className="product-name">{ productDetail.name }</h1>
                            <div className="rate-wrapper-1"></div>
                            <div className="rate-wrapper-2"></div>
                            <div className="rate-wrapper">
                                <div className="wrapper">
                                    <p className="rate">{ productDetail.plannedAnnualRate + '%' }<span>{ productDetail.floatingRate > 0 ? '+' + productDetail.floatingRate + '%' : ''   }<i className={ productDetail.floatingRate > 0 ? 'toast' : '' }>{ productDetail.floatingRate > 0 ? '新手专享加息' : '' }</i></span></p>
                                    <p className="txt">历史年化收益</p>
                                </div>
                                <p className="require-tips"><span>限参与一次</span></p>
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
                            <Tab title="产品描述" dev_id="A8.2-3.8" eventtype="close_float_window" eventtypehide="open_float_window" show={ true }>
                                <p>新元宝（新手专享）是新元宝计划系列中专门针对新手推出的一种福利，仅限未曾在新新贷出借过的用户专享新手加息，每位新手用户仅限享受1次。新手用户的专享加息福利由平台贴息奖励。</p>
                                <p>该服务在用户认可的标的范围内，对符合要求的标的进行自动投标，期限结束后会通过新新贷债权转让平台进行转让退出。</p>
                            </Tab>
                            <Tab title="收益计算" dev_id="A8.2-3.10.1" eventtype="close_float_window" eventtypehide="open_float_window" show={ false }>
                                <p>历史收益=出借金额×{productDetail.plannedAnnualRate}%/12<span className="notice">{ productDetail.floatingRate > 0 ? '+出借金额×'+ productDetail.floatingRate +'%/12' : '' }</span></p>
                                <ul className="thirtytender-list">
                                    <li>
                                        <span>出借金额(元)</span>
                                        <span>历史年化收益</span>
                                        <span>锁定期限</span>
                                        <span>历史收益(元)</span>
                                    </li>
                                    <li>
                                        <span>100</span>
                                        <span>{ productDetail.plannedAnnualRate + '%' }{ productDetail.floatingRate > 0 ? <i> + <i className='notice'>{ productDetail.floatingRate + '%' }</i> </i> : '' }</span>
                                        <span>{productDetail.leastPeriod}{productDetail.leastPeriodUnit}</span>
                                        <span>{ this.compute(100) }</span>
                                    </li>
                                    <li>
                                        <span>1000</span>
                                        <span>{ productDetail.plannedAnnualRate + '%' }{ productDetail.floatingRate > 0 ? <i> + <i className='notice'>{ productDetail.floatingRate + '%' }</i> </i> : '' }</span>
                                        <span>{productDetail.leastPeriod}{productDetail.leastPeriodUnit}</span>
                                        <span>{ this.compute(1000) }</span>
                                    </li>
                                    <li>
                                        <span>5000</span>
                                        <span>{ productDetail.plannedAnnualRate + '%' }{ productDetail.floatingRate > 0 ? <i> + <i className='notice'>{ productDetail.floatingRate + '%' }</i> </i> : '' }</span>
                                        <span>{productDetail.leastPeriod}{productDetail.leastPeriodUnit}</span>
                                        <span>{ this.compute(5000) }</span>
                                    </li>
                                    <li>
                                        <span>10000</span>
                                        <span>{ productDetail.plannedAnnualRate + '%' }{ productDetail.floatingRate > 0 ? <i> + <i className='notice'>{ productDetail.floatingRate + '%' }</i> </i> : '' }</span>
                                        <span>{productDetail.leastPeriod}{productDetail.leastPeriodUnit}</span>
                                        <span>{ this.compute(10000) }</span>
                                    </li>
                                </ul>
                            </Tab>
                            <Tab title="回款方式" dev_id="A8.2-3.11.1" eventtype="close_float_window" eventtypehide="open_float_window" show={ false }>
                                锁定期结束后次日（含周末节假日）自动返还至新新贷账户。
                            </Tab>
                        </ul>
                        <Problem joinNum={ totalCount } proId={ this.state.data.productDetail.productId } type="thirty"/>
                        <ProBottom show={ true }/>
                    </div>
                
                    <div className="div-bottom-btn">
                        <button className="xxd-xl-btn dmp-click" dev_id="A8.2-3.7" eventtype="jump" disabled={ this.state.btnGray } onClick={ this.buyProduct }>{ this.state.btnName }</button>
                    </div>
                
                    <HomeIco />
                </div>
                <Mask show={ this.state.isGoApp }/>
            </div>
        )
    }
}
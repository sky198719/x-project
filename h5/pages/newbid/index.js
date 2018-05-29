import React, { Component } from 'react'
import { cFetch } from '../../common/Promise'
import urlPath from './api'
import { getQueryString, delCookie } from '../../common/Util'
import Router from 'next/router'
import Header from './header'
import { Button, Table } from "antd-mobile";
import moment from 'moment'
let page = 1
export default class Bids extends Component {
    constructor(props) {
        super(props)
        this.state = {
            loadText: '加载更多',
            titleModal: '提示',
            tipMessage: '',
            newJson: {},
            initTable: [],
            messageAlert: false,
            isoverlay: false,
            pops: false,
            shows: '1',
            onlyText: '确定',
            openAccount: false,
            disabled: true,
            btnTitle: ''
        }
        this.tabsLi = this.tabsLi.bind(this)
        this.creditorList = this.creditorList.bind(this)
    }
    async componentDidMount() {
        const term = getQueryString('term')
        if(term){
            const res = await cFetch(urlPath.investProduct, {
                data: {
                    prcode: 'XSB' + term
                }
            }) 
            if(res.code == '200000'){
                if(res.data.status == '1'){
                    this.setState({
                        btnTitle: '等待发售',
                        disabled: true
                    })
                }else if(res.data.status == '2'){
                    this.setState({
                        btnTitle: '立即加入',
                        disabled: false
                    })
                }else if(res.data.status == '3'){
                    this.setState({
                        btnTitle: '已售罄',
                        disabled: true
                    })
                }else{
                    this.setState({
                        btnTitle: '出借已结束',
                        disabled: true 
                    }) 
                }
                this.setState({
                    newJson: res.data
                })
            }else if(parseInt(res.code) >= 200300 && parseInt(res.code) < 200400){
                this.setState({
                    messageAlert: true,
                    isoverlay: true,
                    openAccount: false,
                    tipMessage: '请先登录',
                    onlyText: '确定'
                })
            }else{
                this.setState({
                    messageAlert: true,
                    isoverlay: true,
                    openAccount: false,
                    tipMessage: res.message,
                    onlyText: '好的',
                })
            }
        }
    }
    onClose() {
        if(this.state.onlyText == '确定'){
            delCookie('XXD_webapp_SESSIONID')
            delCookie('userToken')
            window.location.href = "/m?isShowLogin=true"
        }else if(this.state.onlyText == '好的'){
            this.setState({
                messageAlert: false,
                isoverlay: false, 
                openAccount: false,
            })
        }else{
            window.location.href = '/m/#!/static/html/popular/financesList.html?v=20170907'
        }
    }
    closeLay() {
        this.setState({
            messageAlert: false,
            isoverlay: false
        })
    }
    toOpenAccount() {
        window.location.href = '/m/#!/static/html/personal/personalInfo.html?isShowOpenAccount=0&v=20170907'
    }
    async changeTab(key) {
        if(key == '3'){
            page = 1
            this.setState({
                initTable: []
            })
            this.tableServer(page)
        }
    }
    async tableServer(index) {
        this.setState({
            loadText: '加载中'
        })
        const res = await cFetch(urlPath.investmentRecord, {
            data: {
                reglintstId: this.state.newJson.id,
                // reglintstId: 'RI20161010001',
                currentPage: index,
                pageSize: 10,
                productSign: 'XSB'
            }
        })
        if(res.code == '200000'){
            let upCount = Math.ceil(res.data.totalCount / res.data.pageSize)
            if(upCount >= index){
                this.setState({
                    initTable: this.state.initTable.concat(res.data.items),
                    loadText: '加载更多'
                })
            }else{
                this.setState({
                    loadText: '没有更多了'
                })
            }
            
        }
    }
    async rush() {
        const me = this
        const res = await cFetch(urlPath.isOpenAccount, {
            data: {}
        })
        if(res && res.code == '200000' && res.data.code == '0'){
            if(res.data.data.isopenaccount == '1'){
                if(this.state.newJson.isPurchasedProduct){
                    this.setState({
                        messageAlert: true,
                        isoverlay: true,
                        openAccount: false,
                        tipMessage: '新手标只针对新注册且未进行过出借的用户开放，感谢您的关注，请关注其他产品。',
                        onlyText: '好'
                    })
                }else{
                    const term = getQueryString('term')
                    if(term){
                        Router.push({pathname: '/newbid/bid', query:{ termId: term }})
                    }
                }
            }else{
                this.setState({
                    messageAlert: true,
                    isoverlay: true,
                    openAccount: true,
                    tipMessage: '为应监管需要，您需要先开通银行存管账户才能顺利出借哦～'
                })
            }
        }
    }
    loadMore() {
        if(this.state.loadText != '没有更多了'){
            this.tableServer(++page)
        }
    }
    tabsLi(num) {
        this.setState({
           shows: num.target.id 
        })
        if(num.target.id  == '3'){
            page = 1
            this.setState({
                initTable: []
            })
            this.tableServer(page)
        }
    }
    creditorList() {
        window.location.href = '/m/#!/static/html/trade/tradeList.html?pId=' + this.state.newJson.id
    }
    showProtocol() {
        this.setState({
            pops: true
        })
    }
    protocolBack() {
        this.setState({
            pops: false
        })
    }
    render() {
        const initData = this.state.newJson
        return (
            <div>
                <Header title="新手标详情页" />
                <div className="new-container">
                    <div className="new-container-clear">
                        <div className="new-conleft">
                            <div className="new-bgblue">
                                <p>{initData.apr}<span>%</span></p>
                                <span>历史年化收益</span>
                            </div>
                        </div>

                        <div className="new-conright">
                            <p>{initData.name} - {initData.releasePeriodCount} 期</p>
                            <p>剩余可投：{initData.remAccount}元</p>
                            <p>锁定期限：{initData.period} {initData.periodUnit == 'MONTH' ? '个月' : initData.periodUnit == 'DAY' ? '天' : ''}</p>
                            <p className="new-titleimportant">风险提示:<span onClick={ this.showProtocol.bind(this) }>《风险提示函》</span></p>
                            <p className="new-titleimportant">债权列表:<span onClick={ this.creditorList }>点击查看</span></p>
                        </div>
                    </div>
                    <p className="new-tip">*仅限未曾在_投资过的用户加入，每位用户限购1次</p>
                </div>
                <div className="btn-bottom">
                    <div className="new-li">
                        <ul>
                            <li id='1' className={ this.state.shows == '1' ? 'bottomline' : '' } onClick={ this.tabsLi }>产品介绍</li>
                            <li id='2' className={ this.state.shows == '2' ? 'bottomline' : '' } onClick={ this.tabsLi }>常见问题</li>
                            <li id='3' className={ this.state.shows == '3' ? 'bottomline' : '' } onClick={ this.tabsLi }>加入记录</li>
                        </ul>
                    </div>
                    <div className="new-tabs">
                        <ul>
                            <li className={ this.state.shows == '1' ? 'show' : 'hide' }>
                                <div style={{ alignItems: 'center', justifyContent: 'center', background: '#fff', paddingTop: '15px' }}>
                                    <h2>投资介绍</h2>
                                    <p>新手标是<span className="red">针对尚未在_投资过的新手用户</span>推出的自动投标理财计划，新手标在用户认可的标的范围内，对符合要求的标的进行自动投标，且回款本金在相应期限内自动复投，期限结束后新手标会通过_债权转让平台进行转让退出。该计划一旦投标成功，不支持提前退出或债权转让。该计划所对应的标的由系统实现标的分散投资。</p>
                                    <h2>投资图示</h2>
                                    <p><img src="../../static/mods/newbid/imgs/tushi.png" /></p>
                                    <h2>历史年化收益说明</h2>
                                    <p>历史年化收益率：{initData.apr}%<br />历史年化收益率是依据往期计划计算的收益率，最终收益由自动投资的借款项目收益决定</p>
                                    <h2>期限说明</h2>
                                    <p>投资锁定期：{initData.period}个月<br />锁定期内不支持提前退出</p>
                                    <h2>回款方式</h2>
                                    <p>锁定期结束后次日（含周末及节假日）系统自动将本金和收益返还到_账户</p>
                                    <h2>费用说明</h2>
                                    <p>加入费用：0.00% <br />到期退出费用：0.00%</p>
                                </div>
                            </li>
                            <li className={ this.state.shows == '2' ? 'show' : 'hide' }>
                                <div style={{ alignItems: 'center', justifyContent: 'center', background: '#fff', paddingTop: '15px' }} className="tabs-pro">
                                    <h2>1、我投资了新手标1个月，还能投新手标3个月吗？</h2>
                                    <p>不能，新手标每个账户限购1次</p>
                                    <h2>2、投资新手标可以使用新手红包吗？</h2>
                                    <p>不可以，新手红包仅限在投资步步高升、_、_产品及散标直投产品（票据贷除外）可用。</p>
                                    <h2>3、投资新手标锁定期内可以退出吗？</h2>
                                    <p>不可以，新手标1个月和3个月，锁定期限内均不支持提前退出。</p>
                                    <h2>4、新手标安全吗？</h2>
                                    <p>_以严谨负责的态度对每笔借款进行严格筛选。同时，交易资金由银行存管，杜绝挪用和隔离风险。</p>
                                </div>
                            </li>
                            <li className={ this.state.shows == '3' ? 'show' : 'hide' }>
                                <table className="con-table">
                                    <thead>
                                        <tr className="al">
                                            <th>出借人</th>
                                            <th>加入金额</th>
                                            <th>加入时间</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    {
                                        this.state.initTable.map((item)=> {
                                            var ractMoney = (item.account).toFixed(2)
                                            var changeMoney = ractMoney.split('.');
                                            changeMoney[0] = changeMoney[0].replace(/(\d)(?=(\d{3})+$)/g,'$1,');
                                            changeMoney = changeMoney.join('.');
                                            return(
                                                <tr key={item.addTime}>
                                                    <td>{item.userName}</td>
                                                    <td>{changeMoney}</td>
                                                    <td>{moment(item.addTime).format('YYYY-MM-DD HH:mm:ss')}</td>
                                                </tr>
                                            )
                                        })
                                    }
                                    </tbody>
                                </table>
                                <p className="loadmore" onClick={ this.loadMore.bind(this) }>{ this.state.loadText }</p>
                            </li>
                        </ul>
                    </div>
                </div>
                <Button className="btn none">&nbsp;</Button>
                <Button className="btn btnfixed" type="primary" disabled={ this.state.disabled } onClick={ this.rush.bind(this) }>{ this.state.btnTitle }</Button>
                <div className={ this.state.pops ? 'popup show' : 'popup' }>
                    <p className="protocol-back" onClick={ this.protocolBack.bind(this) }>返回</p>
                    <div className="static-class">
                        <h5>资金出借风险提示函</h5>
                        <p>出借人应认真阅读《_网站服务协议》、资金出借相关协议（《借款合同》、《债权转让协议》等）、本函内容及本网站（www.xinxindai.com）关于资金出借、资费介绍、标的说明等操作规则，充分了解在本网站上出借资金的法律意义及相关风险，并根据自身的出借经验、出借目的、出借期限、自身资产状况等判断所选择的借款标的是否与自身的风险承受能力相当。 
                        出借人的资金在出借过程中可能面临各种风险，包括但不限于市场风险、信用风险、利率风险、流动性风险以及战争、自然灾害等不可抗力导致的出借资金损失。</p>
                        <p>主要风险说明如下：</p>
                        <h6>一、政策风险</h6>
                        <p>国家宏观政策、财政政策、货币政策、行业政策、地区发展政策的变动可能会对出借方执行产生不利影响，对此_（上海）金融信息服务有限公司不承担责任。</p> 
                        <h6>二、借款方信用风险</h6>
                        <p>当借款方因突发事件或其他不可预见的事件，导致短期或者长期丧失还款能力 (包括但不限于借款人收入情况、财产状况发生变化、人身出现意外、发生疾病、死亡等情况)，或者借款人的还款意愿发生变化时，出借人的资金存在无法按时回收之风险。</p>
                        <h6>三、资金流动性风险</h6>
                        <p>出借人按照约定将资金出借给借款人使用，在借款人不主动提前还款的情况下，借款人将按照约定的期限分期偿还出借人的本金和利息，出借人的出借资金将分期回收，因此资金回收需要一定的周期；</p>
                        <p>若出借人需要于当期债权未到期时提前回收（至少持满一定期限后方可提前回收，以具体理财产品下标注的持有期限为准）出借资金的，应当以债权转让方式向第三人转让剩余债权。本网站将在出借人提出需要以及其他对出借人有利的时机，帮助出借人寻找、向出借人推荐愿意受让出借人债权资产的第三方。</p>
                        <p>出借人应当知晓在匹配债权受让人时，存在无法按其需求的时间或期限匹配到债权受让人的资金流动性风险。一旦发生风险，在完成本次债权转让匹配前，出借人仍将按照约定的利息持有该债权，直至债权期满或债权转让成功。</p>
                        <h6>四、不可抗力的风险</h6>
                        <p>由于战争、动乱、罢工、自然灾害等不可抗力因素的出现，可能导致出借人的出借资金受到损失，对此_（上海）金融信息服务有限公司不承担责任。 </p>
                        <h6>五、其他风险</h6>
                        <p>本风险提示函的揭示事项仅为列举性质，未能详尽列明出借人所面临的全部风险和可能导致出借人资产损失的所有因素。</p> 
                        <p>出借人在出借资金前，应认真阅读并理解相关业务规则、标的说明书、网站服务协议、电子借款合同及本风险提示函的全部内容，并确信自身已做好足够的风险评估与财务安排，避免因出借资金而遭受难以承受的损失。 </p>
                        <p>注：本函中 “出借人”是指在本网站注册，并以其自有合法资金通过本网站提供的信息服务获取收益的用户，包括网站各类借款标的投标人、债权受让人等。 </p>
                        <h5>出借人承诺</h5>
                        <p><b>_（上海）金融信息服务有限公司：</b></p>
                        <p>本人已在贵司运营的_平台（www.xinxindai.com）注册并有意实际出借自有资金。现本人基于出借行为作出承诺如下：</p>
                        <h6>一、本人系完全民事行为能力人。</h6>
                        <h6>二、本人承诺本人资金来源合法。</h6>
                        <h6>三、本人承诺所提供的信息和材料全部真实、准确，若有任何不实之处，本人自愿承担所有不利后果。</h6>
                        <h6>四、本人已认真阅读《_注册协议》、资金出借相关协议（《借款合同》、《债权转让协议》及_网站关于资金出借、资费介绍、标的说明等操作规则），充分了解在贵网站上出借资金的法律意义及相关风险，并根据自身的出借经验、出借目的、出借期限、自身资产状况等判断所选择的借款标的是否与自身的风险承受能力相当,避免因出借资金而遭受难以承受的损失。</h6>
                        <h6>五、本人知晓并自愿承担出借方的资金在出借过程中可能面临各种风险，包括但不限于政策风险、借款方信用风险、资金流动性风险、不可抗力风险及其他风险而导致的出借资金损失。</h6>
                        <h6>六、本人知晓任何投资行为均存在投资风险，风险涵盖投资本金及利息等全部款项，本人作为出借人将自愿承担相应风险所导致的一切损失。</h6>
                        <h6>七、本承诺书自签订之日起单独成立并生效。本人通过_平台签订《借款合同》、《服务协议》等一切文书是否生效、无效或存在效力瑕疵，均不影响本承诺书的效力。</h6>
                        <p>特此承诺！</p>
                        
                    </div>
                </div>
                <div className={ this.state.messageAlert ? 'modal modal-in modal-visibal' :  'modal modal-in'}>
                    <div className="modal-inner">
                        <div className="modal-title">{ this.state.titleModal }</div>
                        <div className="modal-text">{ this.state.tipMessage }</div>
                    </div>
                    <div className={ !this.state.openAccount ? 'modal-buttons' : 'modal-buttons  modal-none' }>
                        <span className='modal-button modal-button-bold' onClick={ this.onClose.bind(this) }>{ this.state.onlyText }</span>
                    </div>
                    <div className={ this.state.openAccount ? 'modal-buttons' : 'modal-buttons  modal-none' }>
                        <span className='modal-button modal-button-bold' onClick={ this.closeLay.bind(this) }>取消</span>
                        <span className='modal-button modal-button-bold' onClick={ this.toOpenAccount.bind(this) }>立即开户</span>
                    </div>
                </div>
                <div className={ this.state.isoverlay ? 'modal-overlay modal-visibal' : 'modal-overlay' }  ></div>
            </div>
        )
    }
}
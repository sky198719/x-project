import React, { Component } from 'react'

import Header from './header/index'

import Router from 'next/router'

import Loading from '../../common/Loading'

import server from './api/index'

import md5 from 'md5'

import { List, Switch, WingBlank, Modal, Toast } from 'antd-mobile'


class Bid extends Component {
    constructor(props) {
        super(props)
        this.state = {
            bidCode:'',
            baseData: {},
            agreenState: true,
            available:{},
            pops: false,
            ractMoney: '',
            modalTitle: '提示',
            modal1: false,
            modalCont: '',
            apiState: 0,
            isoverlay: false,
            messageAlert: false,
            enterpassword: false,
            tipMessage: '',
            titleModal: '提示',
            password: '',
            successGo: false,
            repeatPay: false
        }
        this.changes = this.changes.bind(this)
        this.showProtocol = this.showProtocol.bind(this)
        this.protocolBack = this.protocolBack.bind(this)
        this.entryMoney = this.entryMoney.bind(this)
        this.onClose = this.onClose.bind(this)
    }

    avalidate = async () => {
        const me = this
        const res = await server.validatePayPwdByToken(md5(this.state.password))
        console.log(res)
        if(res && res.code == '200000' && res.data.code != -3){
            const resPay = await server.investOrder(this.state.bidCode, parseFloat(this.state.ractMoney).toFixed(2))
            if(resPay.bizStatus.code == 'SUCCESS'){
                await me.setState({
                    isoverlay: true,
                    messageAlert: true,
                    enterpassword: false,
                    successGo: true,
                    titleModal: '恭喜',
                    tipMessage: '投标成功！'
                })
            }else{
                await me.setState({
                    isoverlay: true,
                    messageAlert: true,
                    enterpassword: false,
                    titleModal: '提示',
                    tipMessage: resPay.bizStatus.message
                })
            }
        }else{
            await me.setState({
                repeatPay: false,
                isoverlay: true,
                messageAlert: true,
                enterpassword: false,
                titleModal: '提示',
                tipMessage: res.data.message
            })
        }

    }

    changes(event) {
        this.setState({
            agreenState: !this.state.agreenState
        })
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

    entryMoney(event) {
        var reg = /^\d+\.?(\d{1,2})?$/
        if(!reg.test(event.target.value)){
            if(event.target.value != '') return
        }
        if(event.target.value > this.state.available.availableBalance && this.state.available.availableBalance < this.state.baseData.leftTenderAmount){
            this.setState({
                ractMoney: this.state.available.availableBalance
            })
        }else if(event.target.value > this.state.baseData.leftTenderAmount && this.state.baseData.leftTenderAmount <  this.state.available.availableBalance){
            this.setState({
                ractMoney: this.state.baseData.leftTenderAmount
            })
        }else if(this.state.baseData.tenderAmountUp && event.target.value > this.state.baseData.tenderAmountUp){
            this.setState({
                ractMoney: this.state.baseData.tenderAmountUp
            })
        }else{
            this.setState({
                ractMoney: event.target.value
            })
        }
    }

    bidPay = () => {
        var me = this;
        if(this.state.ractMoney == '' || this.state.ractMoney == 0){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                tipMessage: '必须填写投标金额',
                password: ''
            })
        }else if(this.state.ractMoney > this.state.baseData.leftTenderAmount){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                tipMessage: '加入金额大于可投金额',
                password: ''
            })
        }else if(this.state.ractMoney < this.state.baseData.tenderAmountDown && this.state.baseData.tenderAmountDown < this.state.baseData.leftTenderAmount){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                tipMessage: '加入金额小于标的最低投标限额',
                password: ''
            })
        }else if(this.state.baseData.tenderAmountDown > this.state.baseData.leftTenderAmount && this.state.ractMoney > this.state.baseData.leftTenderAmount){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                tipMessage: '加入金额大于可投金额',
                password: ''
            })
        }else if(this.state.baseData.tenderAmountDown == this.state.baseData.leftTenderAmount && this.state.ractMoney < this.state.baseData.tenderAmountDown){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                tipMessage: '加入金额小于标的最低投标限额',
                password: ''
            })
        }else{
            this.setState({
                isoverlay: true,
                enterpassword: true,
                tipMessage: '',
                password: ''
            })
        }
    }
    async nextPay() {
        if(!this.state.repeatPay){
            await this.setState({
                repeatPay: true
            })
            this.avalidate()
        }
    }

    onClose() {
        if(this.state.successGo){
            Router.push({pathname: '/consumes', query:{ refresh: true }})
        }else{
            this.setState({
                isoverlay: false,
                enterpassword: false,
                messageAlert: false
            });
        }
    }

    recharge() {
        window.location.href = window.location.origin + '/m/#!/static/html/account/topup.html?path=account&v=20170817'
    }

    passwordEntry(e) {
        this.setState({
            password: e.target.value
        })
    }

    componentWillMount() {
       this.setState({bidCode: this.props.url.query.bidCode})
    }

    async componentDidMount() {

        const res = await server.consumeInfo(this.state.bidCode, {})

        this.setState({baseData: res})

        const overview = await server.consumeOverview()

        this.setState({available: overview})

        console.log(overview)

        // this.bidDetail().then((res)=> {
        //     this.setState({baseData: res.data})
        //     return this.overView()
        // }).then(res=> {
        //     this.setState({available: res.data})
        // })
    }

    render() {
        if(!this.state.bidCode || JSON.stringify(this.state.available) == "{}"){
            return (
                <div>
                    <Header title="马上投标" />
                    <Loading />
                </div>
            )
        }

        return (
            <div>
                <Header title="马上投标" />
                <div className="xui-list-white border-dede">
                    <h1 className="title18">{this.state.baseData.bidName}</h1>
                    <p className="font15">剩余可投金额：<span>{this.state.baseData.leftTenderAmount.toFixed(2)}</span> 元</p>
                    <p className="font15">投标限额：{ this.state.baseData.tenderAmountUp ? this.state.baseData.tenderAmountDown.toFixed(2) + '元  - ' +  this.state.baseData.tenderAmountUp.toFixed(2) + '元' : this.state.baseData.tenderAmountDown.toFixed(2) + '元以上'}</p>
                </div>
                <div className="xui-list-white">
                    <div className="xui-payandlow">
                        <h1 className="font17">账户余额：<span>{this.state.available.availableBalance.toFixed(2)}</span> 元</h1>
                        <span onClick={ this.recharge }>充值 <i className="icon icon-right"></i></span>
                    </div>
                    <div className="xui-pay-input">
                        <input type="number" placeholder={`请输入投标金额，最低${this.state.baseData.tenderAmountDown}元`} value={this.state.ractMoney} onChange={ this.entryMoney }/> <span>元</span>
                    </div>
                    <p>
                        <span className="xui-react-pay">实际支付：<i>{this.state.ractMoney == '' ? '0.00' : this.state.ractMoney}</i>元</span>
                    </p>
                </div>
                <div className="xui-list-white">
                       <div className="ios-checkbox">
                            <input type="checkbox" defaultChecked= {this.state.agreenState} id="ios-checkbox" className="raw-checkbox" onClick={ this.changes }/>
                            <label htmlFor="ios-checkbox" className="emulate-ios-button"></label>
                        </div>
                        <span className="xui-right" onClick={ this.showProtocol }>我已同意《_资金出借风险提示函》</span>
                </div>
                <div className="nw12x nb2e">
                    <button id="to_bid" className="xui-btn w100 lm" data-co="main" onClick={ this.bidPay } disabled={ this.state.agreenState ? false:true}>确认投标</button>
                </div>
                <div className={ this.state.pops ? 'popup show' : 'popup' }>
                        <p className="protocol-back" onClick={ this.protocolBack }>返回</p>
                        <div className="static-class">
                        <h5>资金出借风险提示函</h5>
                        <p>出借人应认真阅读《_网站服务协议》、资金出借相关协议（《借款合同》、《债权转让协议》等）、本函内容及本网站（www.xinxindai.com）关于资金出借、资费介绍、标的说明等操作规则，充分了解在本网站上出借资金的法律意义及相关风险，并根据自身的出借经验、出借目的、出借期限、自身资产状况等判断所选择的借款标的是否与自身的风险承受能力相当。 </p>
                        <p>出借人的资金在出借过程中可能面临各种风险，包括但不限于市场风险、信用风险、利率风险、流动性风险以及战争、自然灾害等不可抗力导致的出借资金损失。</p>
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
                        <p>本风险提示函的揭示事项仅为列举性质，未能详尽列明出借人所面临的全部风险和可能导致出借人资产损失的所有因素。 </p>
                        <p>出借人在出借资金前，应认真阅读并理解相关业务规则、标的说明书、网站服务协议、电子借款合同及本风险提示函的全部内容，并确信自身已做好足够的风险评估与财务安排，避免因出借资金而遭受难以承受的损失。</p> 
                        <p>注：本函中 “出借人”是指在本网站注册，并以其自有合法资金通过本网站提供的信息服务获取收益的用户，包括网站各类借款标的投标人、债权受让人等。</p>
                        <h5>出借人承诺</h5>
                        <p>_（上海）金融信息服务有限公司：</p>
                        <p>本人已在贵司运营的_平台（www.xinxindai.com）注册并有意实际出借自有资金。现本人基于出借行为作出承诺如下：</p>
                        <h6>一、本人系完全民事行为能力人。</h6>
                        <h6>二、本人承诺本人资金来源合法。</h6>
                        <h6>三、本人承诺所提供的信息和材料全部真实、准确，若有任何不实之处，本人自愿承担所有不利后果。</h6>
                        <h6>四、本人已认真阅读《_注册协议》、资金出借相关协议（《借款合同》、《债权转让协议》及_网站关于资金出借、资费介绍、标的说明等操作规则），充分了解在贵网站上出借资金的法律意义及相关风险，并根据自身的出借经验、出借目的、出借期限、自身资产状况等判断所选择的借款标的是否与自身的风险承受能力相当,避免因出借资金而遭受难以承受的损失。</h6>
                        <h6>五、本人知晓并自愿承担出借方的资金在出借过程中可能面临各种风险，包括但不限于政策风险、借款方信用风险、资金流动性风险、不可抗力风险及其他风险而导致的出借资金损失。</h6>
                        <h6>六、本人知晓任何出借行为均存在出借风险，风险涵盖出借本金及利息等全部款项，本人作为出借人将自愿承担相应风险所导致的一切损失。</h6>
                        <h6>七、本承诺书自签订之日起单独成立并生效。本人通过_平台签订《借款合同》、《服务协议》等一切文书是否生效、无效或存在效力瑕疵，均不影响本承诺书的效力。</h6>
                        <p>特此承诺！</p>
                    </div>
                </div>
                <div className={ this.state.messageAlert ? 'modal modal-in modal-visibal' :  'modal modal-in'}>
                    <div className="modal-inner">
                        <div className="modal-title">{ this.state.titleModal }</div>
                        <div className="modal-text">{ this.state.tipMessage }</div>
                    </div>
                    <div className="modal-buttons ">
                        <span className="modal-button modal-button-bold" onClick={ this.onClose }>确定</span>
                    </div>
                </div>
                <div className={ this.state.enterpassword ? 'modal modal-in modal-visibal' :  'modal modal-in'}>
                    <div className="modal-inner">
                        <div className="modal-title">支付确认</div>
                        <div className="modal-text">确认从您的账户中扣除{this.state.ractMoney}元用以投标，请输入支付密码</div>
                        <input type="password" placeholder="请输入支付密码" className="passwordInput" value={ this.state.password } onChange={ this.passwordEntry.bind(this) }  />
                    </div>
                    <div className="modal-buttons ">
                        <span className="modal-button" onClick={ this.onClose }>取消</span>
                        <span className="modal-button modal-button-bold" onClick={ this.nextPay.bind(this) }>确定</span>
                    </div>
                </div>
                <div className={ this.state.isoverlay ? 'modal-overlay modal-visibal' : 'modal-overlay' }  ></div>
            </div>
        )
    }
}

export default Bid
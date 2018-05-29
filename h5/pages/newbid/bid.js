import React, { Component } from 'react'

import Header from './header'

import Router from 'next/router'

import Loading from '../../common/Loading'

import md5 from 'md5' 

import moment from 'moment' 

import { cFetch } from '../../common/Promise'
import urlPath from './api'

class Bid extends Component {
    constructor(props) {
        super(props)
        this.state = {
            ractMoney: '',
            newJson: {},
            isoverlay: false,
            messageAlert: false,
            tipMessage: '',
            titleModal: '提示',
            modalTitle: '提示',
            enterpassword: false,
            password: '',
            successGo: false,
            agreenState: true,
            pops: false,
            autoPros: false,
            min: 0,
            repeatPay: false,
            successData: {},
            lostable: false,
            licai: false,
            peibiao: {},
            protocol: {}
        }
    }

    async componentDidMount() {
        this.agreen()
        const term = this.props.url.query.termId
        if(term){
            const res = await cFetch(urlPath.investProduct, {
                data: {
                    prcode: 'XSB' + term
                }
            })
            if(res.code == '200000'){
                var num = []
                num.push(res.data.mostTender)
                num.push(res.data.remAccount)
                num.push(res.data.usable)
                for(let i=1; i< num.length; i++){
                    var minNum = num[0]
                    if(num[i] < minNum){
                        minNum = num[i]
                    }
                }
                this.serverProtocol(res.data.id)
                this.setState({
                    min: minNum,
                    newJson: res.data
                })
            }else if(parseInt(res.code) >= 200300 && parseInt(res.code) < 200400){
                this.setState({
                    messageAlert: true,
                    isoverlay: true,
                    licai: false,
                    openAccount: false,
                    tipMessage: '请先登录'
                })
            }else{
                this.setState({
                    messageAlert: true,
                    isoverlay: true,
                    licai: false,
                    openAccount: false,
                    tipMessage: res.message
                }) 
            }
        }
    }
    async agreen() {
        const res = await cFetch(urlPath.agreement, {
            data: {}
        })
        if(res.code == '200000'){
            this.setState({
                peibiao: res.data.userInfo
            })
        }
    }
    async serverProtocol(id) {
        const res = await cFetch(urlPath.agreement, {
            data: {
                productCode: 'XSB',
                productId: id,
                productJoinId: ''
            }
        })
        if(res.code == '200000'){
            this.setState({
                protocol: res.data
            })
        }
    }
    async payServer() {
        const res = await cFetch(urlPath.validatePayPwdByToken, {
            type: 'POST',
            data: {
                data: {
                    payPassword: md5(this.state.password)
                }
            }
        })

        if(res && res.code == '200000' && res.data.code == 0){
            const resJson = await cFetch(urlPath.InvestOrder, {
                type: 'PUT',
                data: {
                    data:{
                        "productCategory": 1,
                        "productId": this.state.newJson.id,
                        "productType": 16,
                        "redEnvelopeCode": "",
                        "tenderAmount": parseFloat(this.state.ractMoney).toFixed(2)
                    }
                }
            })
            if(resJson.code == '200000'){
                if(resJson.data.bizStatus.code == 'SUCCESS'){
                    this.setState({
                        isoverlay: true,
                        messageAlert: true,
                        enterpassword: false,
                        licai: false,
                        successGo: true,
                        titleModal: '恭喜',
                        tipMessage: '投标成功！',
                        successData: resJson.data
                    })
                }else if(resJson.data.bizStatus.code == 'NOT_MEET_THE_TERM'){
                    this.setState({
                        isoverlay: true,
                        messageAlert: true,
                        enterpassword: false,
                        titleModal: '提示',
                        licai: true,
                        tipMessage: '新手标每个用户限购1次'
                    })
                }else{
                    this.setState({
                        isoverlay: true,
                        messageAlert: true,
                        enterpassword: false,
                        licai: false,
                        titleModal: '提示',
                        tipMessage: resJson.data.bizStatus.message
                    })
                }
            }else{
                this.setState({
                    isoverlay: true,
                    messageAlert: true,
                    enterpassword: false,
                    licai: false,
                    titleModal: '提示',
                    tipMessage: resJson.data.message
                })
            }
        }else if(parseInt(res.code) >= 200300 && parseInt(res.code) < 200400){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                enterpassword: false, 
                titleModal: '提示',
                licai: false,
                openAccount: false,
                tipMessage: '用户登录异常，请重新登录'
            })
        }else{
            this.setState({
                isoverlay: true,
                messageAlert: true,
                licai: false,
                enterpassword: false,
                titleModal: '提示',
                tipMessage: res.data.message
            })
        }
    }
    changeMoney(val) {
        if(val){
            var changeMoney = 0
            const ractMoney = Number(val).toFixed(2)
            changeMoney = ractMoney.split('.');
            changeMoney[0] = changeMoney[0].replace(/(\d)(?=(\d{3})+$)/g,'$1,');
            changeMoney = changeMoney.join('.');
            return changeMoney
        }
    }

    recharge() {
        window.location.href = window.location.origin + '/m/#!/static/html/account/topup.html?path=account&v=20170817'
    }

    arrayMin(arrs){
        var min = arrs[0];
        for(var i = 1, ilen = arrs.length; i < ilen; i+=1) {
            if(arrs[i] < min) {
                min = arrs[i];
            }
        }
        return min;
    }
    
    async entryMoney(event) {
        var reg = /^\d+\.?(\d{1,2})?$/
        if(!reg.test(event.target.value)){
            if(event.target.value != '') return
        }
        this.setState({
            ractMoney: event.target.value
        })
        // if(event.target.value > this.state.min){
        //     this.setState({
        //         ractMoney: this.state.min
        //     })
        // }else{
        //     this.setState({
        //         ractMoney: event.target.value
        //     })
        // }
    }
    changes() {
        this.setState({
            agreenState: !this.state.agreenState
        })
    }
    bidPay() {
        var me = this;
        const lostNum = parseInt(this.state.ractMoney / this.state.newJson.step)
        const parseMenoy = parseInt(lostNum * this.state.newJson.step)
        if(this.state.ractMoney == '' || this.state.ractMoney == 0){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                tipMessage: '请输入加入金额',
                licai: false,
                lostable: false,
                password: ''
            })
        }else if(this.state.ractMoney > this.state.newJson.remAccount){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                lostable: false,
                licai: false,
                tipMessage: '加入金额大于可投金额',
                password: ''
            })
        }else if(this.state.ractMoney < this.state.newJson.lowestTender && this.state.newJson.lowestTender < this.state.newJson.remAccount){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                lostable: false,
                licai: false,
                tipMessage: '加入金额不能低于' + this.state.newJson.lowestTender + '元，请调整加入金额',
                password: ''
            })
        }else if(this.state.newJson.lowestTender > this.state.newJson.remAccount && this.state.ractMoney > this.state.newJson.remAccount){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                lostable: false,
                licai: false,
                tipMessage: '加入金额大于可投金额',
                password: ''
            })
        }else if(this.state.ractMoney > this.state.newJson.mostTender){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                lostable: false,
                licai: false,
                tipMessage: '加入金额大于最大出借额',
                password: ''
            })
        }else if(parseMenoy != parseInt(this.state.ractMoney)){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                lostable: false,
                licai: false,
                tipMessage: '加入金额需为' + this.state.newJson.step + '的整数倍，请调整加入金额',
                password: ''
            })
        }else if(this.state.ractMoney > this.state.newJson.usable){
            this.setState({
                isoverlay: true,
                messageAlert: true,
                tipMessage: '账户余额不足，请充值！',
                lostable: true,
                licai: false,
                password: ''
            })
        }else{
            this.setState({
                isoverlay: true,
                enterpassword: true,
                lostable: false,
                tipMessage: '',
                licai: false,
                password: ''
            })
        }
    }
    autoBook() {
        this.setState({
            autoPros: true
        })
    }
    newbidServer() {
        this.setState({
            pops: true
        })
    }
    onClose() {
        // debugger
        if(this.state.licai){
            window.location.href = window.location.origin + '/m/#!/static/html/popular/financesList.html?v=20170921'
        }
        if(this.state.lostable){
            window.location.href = window.location.origin + '/m/#!/static/html/account/topup.html?path=account&v=20170817'
        }
        if(this.state.tipMessage.indexOf('您的支付密码') != -1){
            window.location.href = '/m/#!/static/html/personal/setPassword.html?path=personal'
        }
        let name = ''
        if(this.props.url.query.termId == 1){
            name = '新手标1个月'
        }else if(this.props.url.query.termId == 3){
            name = '新手标3个月'
        }
        const { amount, createTime, startDate, endDate, expireDate, plannedInterest } = this.state.successData
        if(this.state.successGo){
            Router.push({pathname: '/newbid/orderSuccess', query:{name: name, amount: amount, createTime: createTime, startDate: startDate, endDate: endDate, expireDate: expireDate, plannedInterest: plannedInterest}})
        }else{
            this.setState({
                isoverlay: false,
                enterpassword: false,
                messageAlert: false,
                repeatPay: false
            });
        }
    }
    protocolBack() {
        this.setState({
            pops: false,
            autoPros: false
        })
    }
    passwordEntry(e) {
        this.setState({
            password: e.target.value
        })
    }
    async nextPay() {
        if(!this.state.repeatPay){
            await this.setState({
                repeatPay: true
            })
            this.payServer()
        }
    }
    render() {
        const initData = this.state.newJson
        const { userInfo, productInfo } = this.state.protocol
        if(JSON.stringify(this.state.protocol) == "{}"){
            return (
                <div>
                    <Header title="加入新手标" />
                    <Loading />
                </div>
            )
        }
        return (
            <div>
                <Header title="加入新手标" />
                <div className="xui-list-white">
                    <h1 className="fz20">{initData.name} - {initData.releasePeriodCount} 期</h1>
                    <p>历史年化收益：<span>{initData.apr}%</span></p>
                    <p>剩余可投：<span>{this.changeMoney(initData.remAccount)}</span>元</p>
                    <p>加入限额：{this.changeMoney(initData.lowestTender)} - {this.changeMoney(initData.mostTender)}元</p>
                    <p>解锁日期：{moment(initData.unLockTime).format('YYYY-MM-DD')}</p>
                </div>
                <div className="xui-list-white">
                    <div className="xui-payandlow">
                        <h1>账户余额：<span>{initData.usable}</span>元</h1>
                        <span onClick={ this.recharge }>充值 <i className="icon icon-right"></i></span>
                    </div>
                    <div className="xui-pay-input">
                        <input type="number" placeholder={`请输入投标金额，最低${initData.lowestTender}元`} value={this.state.ractMoney} onChange={ this.entryMoney.bind(this) }/> <span>元</span>
                    </div>
                    <p>
                        <span className="xui-react-pay">实际支付：<i>{this.state.ractMoney == '' ? '0.00' : this.state.ractMoney}</i>元</span>
                    </p>
                </div>
                <div className="xui-list-white">
                       <div className="ios-checkbox">
                            <input type="checkbox" defaultChecked= {this.state.agreenState} id="ios-checkbox" className="raw-checkbox" onClick={ this.changes.bind(this) }/>
                            <label htmlFor="ios-checkbox" className="emulate-ios-button"></label>
                        </div>
                        <span className="xui-right" onClick={ this.showProtocol }>我已同意<span onClick={ this.newbidServer.bind(this) }>《新手标服务协议》</span><span onClick={ this.autoBook.bind(this) }>《自动配标委托书》</span></span>
                </div>
                <div className="nw12x nb2e">
                    <button id="to_bid" className="xui-btn w100 lm" data-co="main" onClick={ this.bidPay.bind(this) } disabled={ this.state.agreenState ? false:true}>确认全额支付</button>
                </div>
                <div className={ this.state.pops ? 'popup show' : 'popup' }>
                    <p className="protocol-back" onClick={ this.protocolBack.bind(this) }>返回</p>
                    <div className="protocol-title">
                        <h3>新手标服务协议</h3>
                    </div>
                    <div className="newbid-cont">
                        <p className="indent-zero">合同编号：</p>                 
                        <p className="indent-zero">协议签订地： 上海市虹口区四川北路859号</p>
                        <p className="indent-zero">甲方：{ userInfo.realName }</p>
                        <p className="indent-zero">身份证号码：{ userInfo.idCardNo }</p>
                        <p className="indent-zero">联系电话：{ userInfo.mobile }</p>
                        <p className="indent-zero">_用户名：{ userInfo.userName }</p>
                        <br />
                        <p className="indent-zero">乙方：_（上海）金融信息服务有限公司</p>
                        <p className="indent-zero">地址：上海市虹口区四川北路859号中信广场28层</p>
                        <p className="indent-zero">邮编： 200085</p>
                        <p className="indent-zero">咨询电话：4000 169 521</p>
                        <br />
                        <p>乙方作为一家在上海市合法成立并有效存续的企业，拥有www.xinxindai.com网站（下称“_网站”）的经营权，主要通过_网站为互联网环境下自然人之间的借贷交易提供金融信息服务。</p>
                        <p>在互联网金融模式下，乙方推出"出借人优先自动投标及到期按相关规则退出"的新手标服务计划，为加入新手标服务计划的出借人提供更加贴心、便捷的服务，并将尽最大努力维护出借人的合法利益。</p>
                        <b>本协议使用说明及风险提示：</b>
                        <p>1、甲方的资金通过_网站对接优质短期借款标的,但在出借过程中仍可能面临各种风险，包括但不限于政策风险、借款方信用风险、资金流动性风险、不可抗力风险及其他风险而导致的出借资金损失。</p>
                        <p>2、甲方应认真阅读_网站上公示的有关本次服务计划的所有描述、说明。除此之外，甲方还应认真阅读《_使用协议》、资金出借相关协议（《借款合同》、《债权转让协议》等）及_网站关于资金出借、资费介绍、标的说明等操作规则，充分了解在本网站上出借资金的法律意义及相关风险，并根据自身情况判断加入本次服务计划是否与自身的风险承受能力相当,避免因出借资金而遭受难以承受的损失。</p>
                        <b>甲乙双方经友好协商，本着平等自愿、诚实信用的原则，达成如下协议：</b>
                        <b>一、乙方声明与保证</b>
                        <p>1、乙方为合法登记并有效存续的法人机构，拥有_网站的经营权，通过_平台为互联网环境下自然人之间的借贷交易提供金融信息服务。</p>
                        <p>2、乙方推出 “出借人优先自动投标及到期按相关规则退出”的新手标服务计划，为参加该计划客户提供金融信息服务。</p>
                        <b>二、甲方声明和保证</b>
                        <p>1、在签署本协议前，乙方已就本协议书有关交易文件的全部条款和内容向甲方进行了详细的说明和解释，甲方已认真阅读本协议有关条款，对有关条款不存在任何疑问或异议，并对协议双方的权利、义务、责任与风险有清楚和准确的理解。</p>
                        <p>2、甲方保证所使用的资金为合法取得，且甲方对该等资金拥有完全且无瑕疵的处分权。</p>
                        <b>甲方保证为履行本协议而向乙方提供的全部资料真实、有效。</b>
                        <p>3、甲方同意加入本次新手标服务计划，理解并遵守_网站的相关协议及规则。</p>
                        <p>4、甲方已知悉并同意：本协议所示历史收益率不代表甲方最终实际收益，甲方出借本金以及相应的利息存在不能够按期收回的风险；在实际收益率未达到历史收益率的情况下，甲方仅能收取实际收益。乙方不对甲方本金的收回、可获得收益金额作出任何承诺和保证。</p>
                        <p>5、甲方在开放加入阶段内完成支付该计划资金，即为加入本期新手标计划，甲方即可通过_平台系统在本协议约定的出借范围内进行优先自动投标。</p>
                        <b>三、本次新手标服务计划具体内容</b>
                        <b>本次服务计划的具体内容和要求如下：</b>
                        
                        <p>1、<strong>本次新手标计划内容</strong><br />
                        名称、历史年化收益率、甲方出借资金、锁定期、锁定期开始日、锁定期结束日</p>
                        <p>2、<strong>加入要求：</strong>甲方为首次在_平台出借的用户，首次定义为从未在_网站平台进行过任何出借；甲方有且仅有一次机会可以加入本次服务计划。</p>
                        <p>3、<strong>优先自动投标范围：</strong>_平台的优质借款标的。</p>
                        <p>4、<strong>加入资金要求：</strong>最低加入金额为人民币{this.state.newJson.lowestTender}元，并以人民币{this.state.newJson.step}元正整数倍递增，最高不超过当期可加入金额。</p>
                        <p>5、<strong>锁定期：</strong>锁定期内，甲方加入的服务计划本金不能转让或退出。</p>
                        <p>6、<strong>计息时间：</strong>锁定期开始次日起计息，直至锁定期结束。</p>
                        <p>7、<strong>结算日：</strong> {productInfo.settleDate}</p>
                        <p>8、<strong>历史年化收益：</strong>历史年化收益=甲方投入资金×历史年化收益率×锁定期（月数）/12</p>
                        <p>9、<strong>结算资金：</strong>甲方的出借资金及收益将在结算日自动归于甲方的_账户。</p>
                        <p>10、<strong>到期退出：</strong>锁定期满后，甲方自动退出本次服务计划，退出后甲方不得再次享受新手标服务。</p>
                        <b>四、授权</b>
                        <p>1、甲方在此无条件且不可撤销地同意并授权乙方自甲方加入本次服务计划起，有权对甲方加入资金在_网站进行自动投标。</p>
                        <p>2、甲方在此无条件且不可撤销地同意并授权乙方在甲方锁定期届满自动退出本次服务计划时，有权将甲方加入本次服务计划的资金及相应收益自动划扣至甲方_账户。</p>
                        <b>五、服务计划保障</b>
                        <p>1、为降低甲方因出借标的过于集中所带来的信用违约风险，乙方将对甲方加入本次服务计划的资金安排分散化的优先自动投标。</p>
                        <p>2、为保证本次服务计划的及时性，在甲方加入本次服务计划后即刻开始为甲方资金安排优先投标。</p>
                        <b>六、不可抗力</b>
                        <p>1、如因司法机关、行政机关对甲方采取强制措施导致其加入本次服务计划的资金被全部或部分划扣或冻结等，则视为甲方就全部本金进行了提前支取，本协议自动终止。甲方将不再享有相应收益。</p>
                        <p>2、由于地震、火灾或其他不可抗力原因导致交易中断、延误的，甲乙双方互不承担责任，但应采取一切必要措施以减小不可抗力造成的损失。</p>
                        <b>七、其他约定</b>
                        <p>1、甲方应自行承担并缴付其因加入本次服务计划所获收益的税额。</p>
                        <p>2、甲方应通过_网站平台或客服人员等方式了解相关信息，如因未及时了解，或由于通讯故障、系统故障以及其他不可抗力等因素影响使甲方无法及时了解相关信息，由此产生的责任和风险由甲方自行承担。</p>
                        <p>3、甲方有义务遵守本服务协议、双方签署的用户注册使用协议以及乙方在_网站不时公示的交易规则、说明、公告等涉及甲乙双方权利义务的法律文件，如有违反，应当自行承担相关法律后果。给乙方造成损失的，须全额赔偿。</p>
                        <p>4、本协议项下产生的纠纷，双方先行协商解决，协商不成的，任何一方可向本协议签订地的虹口区人民法院提起诉讼，本合同签订地为上海市虹口区四川北路859号。</p>
                        <p>5、本协议采用电子文本形式制成，不提供纸质协议及账单。本协议经甲方通过_网站在线点击确认后成立， 于甲方支付本协议3.1条约定的服务计划资金后生效。本协议保存在乙方，甲方均认可该形式的协议效力及本协议内容。甲方委托乙方保管所有与本协议有关的书面文件和电子信息，对各方均具有法律约束力。各方确认乙方保管的书面文件和电子信息作为本协议有关事项的终局证明。</p>
                        <p>6、本协议未尽事宜，以乙方官网_网站的产品介绍为准。</p>
                        <b>甲方（签名） { userInfo.realName }</b>
                        <b>乙方（签名）</b>
                        <p>_（上海）金融信息服务有限公司</p>
                        <b>签约日期：</b>
                    </div>
                </div>
                <div className={ this.state.autoPros ? 'popup show' : 'popup' }>
                    <p className="protocol-back" onClick={ this.protocolBack.bind(this) }>返回</p>
                    <div className="protocol-title">
                        <h3>自动配标委托书</h3>
                    </div>
                    <div className="newbid-cont">
                        <p className="indent-zero">委托人：{this.state.peibiao.realName}</p>
                        <p className="indent-zero">身份证号：{ this.state.peibiao.idCardNo }</p>
                        <p className="indent-zero">联系方式：{ this.state.peibiao.mobile }</p>
                        <p className="indent-zero">受托人：_（上海）金融信息服务有限公司（以下简称_）</p><br />
                        <p>鉴于委托人为在_平台上实名注册的用户，现拟参与出借_平台开发的自动配标工具（_、_等），为优化委托人的用户体验，委托人就自动投标工具操作的相关事项向受托人做出如下授权：:</p>
                        <b>一、委托内容</b>
                        <p>委托人委托受托人在其先行选择的标的范围内进行自动配标。</p>
                        <b>二、委托人承诺</b>
                        <p>（一）委托人承认并认可自动配标所形成的《借款合同》、《债权转让协议》等合同，并自愿承担由此产生的一切法律后果。</p>
                        <p>（二）委托人上述委托系其真实意思表达。</p>
                        <b>三、委托期限</b>
                        <p>本授权期限自委托人与受托人间签署《自动配标委托书》之日起至完全退出相应自动配标工具（或紧急退出）之日止。</p>
                        <b>四、本委托书经委托人在_平台以线上点击确认的方式签署。</b><br />
                        <b>委托人：{ this.state.peibiao.realName }</b>
                        <b>日期：</b>
                    </div>
                </div>
                <div className={ this.state.messageAlert ? 'modal modal-in modal-visibal' :  'modal modal-in'}>
                    <div className="modal-inner">
                        <div className="modal-title">{ this.state.titleModal }</div>
                        <div className="modal-text">{ this.state.tipMessage }</div>
                    </div>
                    <div className="modal-buttons ">
                        <span className="modal-button modal-button-bold" onClick={ this.onClose.bind(this) }>确定</span>
                    </div>
                </div>
                <div className={ this.state.enterpassword ? 'modal modal-in modal-visibal' :  'modal modal-in'}>
                    <div className="modal-inner">
                        <div className="modal-title">支付确认</div>
                        <div className="modal-text">确认从您的账户中扣除{this.state.ractMoney}元用以投标，请输入支付密码</div>
                        <input type="password" placeholder="请输入支付密码" className="passwordInput" value={ this.state.password } onChange={ this.passwordEntry.bind(this) }  />
                    </div>
                    <div className="modal-buttons ">
                        <span className="modal-button" onClick={ this.onClose.bind(this) }>取消</span>
                        <span className="modal-button modal-button-bold" onClick={ this.nextPay.bind(this) }>确定</span>
                    </div>
                </div>
                <div className={ this.state.isoverlay ? 'modal-overlay modal-visibal' : 'modal-overlay' }  ></div>
            </div>
        )
    }
}

export default Bid
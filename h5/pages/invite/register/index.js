import React,{Component} from 'react'
import Head from 'next/head'
import { Toast } from 'antd-mobile'
import Api from '../../../components/api/home'
import md5 from 'md5'
import { cFetch } from '../../../common/Promise';
import Api_invite from '../../../components/api/invite'


export default class record extends Component {
    constructor(props){
        super(props)
        this.state = {
            checkPage:false,
            passwordStatus: 'password',//密码显示状态
            password:'',//密码
            username:'',//手机号
            showInput1:true,//展示手机号输入框
            showInput2:false,//展示短信验证和密码输入框
            ableMsgTime:'获取验证码',//获取短信验证码显示框内容
            imgUrl: '/userCenter/kaptcha.jpg',
            imgCode: '',
            imgModal: false,
            messageState: 'sms',
            sendTextCode: '发送验证码',
            textDisabled: false,
            smsCode: '',
            inputType: 'password',
            passwordShow: true,
            password: '',
            protocol: true,
            performance:[
                            {img:'/static/mods/invite/imgs/register-9-4.png',name:'平台稳健运营',detail:'5年52天'},
                            {img:'/static/mods/invite/imgs/register-9-5.png',name:'平台成交金额',detail:'111.11亿元'},
                            {img:'/static/mods/invite/imgs/register-9-6.png',name:'累计注册人数',detail:'160.66万人'},
                            {img:'/static/mods/invite/imgs/register-9-7.png',name:'累计为投资人赚取',detail:'5.44亿元'}
                        ],
            xszcRate:{},//新手专享30天利率
            yjdjRate:'12',//_利率
            xybRate:[]//_利率
        }
    }
  
    componentDidMount(){
        this.getCompanyData()
        this.xszx()
        this.yjdj()
        this.getXybRate()
    }
    // 获取企业数据
    async getCompanyData(){
        let res = Api_invite.getCompanyData()
            
            res.then((r)=>{
                let data = r.data
                
                let time = data.time
                let dealMoney = data.items[1]['nvalue']
                let amount = data.items[6]['nvalue']
                let getMoney = data.items[5]['nvalue']
                time = time.replace('年','A') 
                time = time.replace('天','A') 
                time = time.replace('小时','')
                time = time.split('A')

                


                this.state.performance[0]['detail'] = time
                this.state.performance[1]['detail'] = (dealMoney/100000000).toFixed(2)
                this.state.performance[2]['detail'] = (amount/10000).toFixed(2)
                this.state.performance[3]['detail'] = (getMoney/100000000).toFixed(2)
                this.setState({
                    performance: this.state.performance
                })
            })
    }
    // 新手专享30天利率
    async xszx(){
        let res = Api_invite.xszx()
            res.then((r)=>{
                this.setState({
                    xszcRate: r.data.items
                })
            })
    }
    // _
    async yjdj(){
        let res = Api_invite.yjdj()
            res.then((r) => {
                console.log(r,'_')
                if(r.data.items){
                    this.setState({
                        yjdjRate: r.data.items.apr
                    })
                }
            })
    }
    // _利率
    async getXybRate(){
        let res = Api_invite.getXybRate()
            res.then((r) => {
                console.log(r,'_')
                let arry = []
                    arry.push(r.data[0]['apr'])
                    arry.push(r.data[5]['apr'])
                this.setState({
                    xybRate: arry
                })
            })
    }
    
    // 点击改变密码显示状态
    changePasswordStatus(){
        if(this.state.passwordStatus=='password'){
            this.setState({
                passwordStatus: 'text'
            })
        }
        else{
            this.setState({
                passwordStatus: 'password'
            })
        }
    }
    // 获取输入框中的值
    change = key => (e) => {
        if(key == 'username' && e.target.value.length > 11){
            return
        }
        if(key == 'smsCode' && e.target.value.length > 4){
            return
        }
        if(key == 'password'){
            var reg = /^[0-9a-zA_Z]+$/
            if(!reg.test(e.target.value)){
                if(e.target.value != '') return
            }
        }
        this.setState({
            [key]: e.target.value
        })
    }
    // 点击108元 提交手机号
    async submitPhone(){
        const phoneReg = /^134[0-8]\d{7}$|^13[^4]\d{8}$|^14[5-9]\d{8}$|^15[^4]\d{8}$|^16[6]\d{8}$|^17[0-8]\d{8}$|^18[\d]{9}$|^19[8,9]\d{8}$/
        if(!phoneReg.test(this.state.username)){
            Toast.info('手机号格式错误', 2)
            return
        }
        else{
            let GetQueryString = (name) => {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]); return null;
            }
            let param = {
                phone:this.state.username,
                // identity: GetQueryString('identity')
                identity: 'sadfas'
            }
            let res = await Api_invite.savePhone(param)
            if(res.code == 200000){
                this.setState({
                    showInput1:false,
                    showInput2:true,
                })
            }
        }
    }
    // 点击获取短信验证码
    getMsg(){
        if(this.state.ableMsgTime=='获取验证码'){
            let time = 60
            let timer = setInterval(
                ()=>{
                    time--
                    this.setState({
                        ableMsgTime: `${time}s重发`
                    })
                    if(time==0){
                        this.setState({
                            ableMsgTime:'获取验证码'
                        })
                        clearInterval(timer)
                    }
                }
            ,1000)
        }
    }
    // 出现或隐藏图片验证码弹出页
    showCheckNumber = () => {
        this.setState({
            checkPage: !this.state.checkPage
        })
    }
    // 验证图片验证码格式
    imgChange = (e) => {
        var reg = /^[0-9a-zA-Z]+$/
        if(!reg.test(e.target.value)){
            if(e.target.value != '') return
        }
        if(e.target.value.length > 4){
            return
        }
        this.setState({
            imgCode: e.target.value
        })
    }
    // 重置图片验证码
    reset = () => {
        this.setState({
            imgUrl: this.state.imgUrl + '?t=' + Math.random()
        })
    }
    //发送短信验证请求
    sendMsg = async () => {
        if(this.state.imgCode == ''){
            Toast.info('请输入图片验证码', 2)
            this.setState({
                imgUrl: this.state.imgUrl + '?t=' + Math.random()
            })
            return
        }else if(this.state.imgCode.length != 4){
            Toast.info('您的图片验证码不正确', 2)
            this.setState({
                imgUrl: this.state.imgUrl + '?t=' + Math.random()
            })
            return
        }else {
            const context = {
                imgcode: this.state.imgCode,
                phone: this.state.username,
                type: '0',
                scene: '',
                busiCode: 'BUSICODE_REGISTER'
            }
             let res = await Api.sendTextMessage(context)
            if(res.code != 0){
                Toast.info(res.message, 2)
                this.setState({
                    imgUrl: this.state.imgUrl + '?t=' + Math.random()
                })
            }else{
                this.showCheckNumber()//隐藏图片验证码弹出框
                Toast.info(res.message, 2)
                this.setState({
                    imgModal: false,
                    imgCode: ''
                })
                this.setState({
                    sendTextCode: '60s后重新发送',
                    textDisabled: true
                })
                let time = 60
                let timeInterval =  setInterval(()=> {
                    time--
                    this.setState({
                        sendTextCode: time + 's后重新发送',
                        textDisabled: true
                    })
                    if(time == 0){
                        window.clearInterval(timeInterval)
                        this.setState({
                            sendTextCode: '发送验证码',
                            textDisabled: false
                        })
                    }
                },1000)
            }
        }
    }
    submitRegister = async () => {
        const reg = /^[1][3,4,5,7,8,9][0-9]{9}$/
        const passwordReg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$/
        if(this.state.username == ''){
            Toast.info('请输入您的手机号', 2)
            return
        }else if(!reg.test(this.state.username)){
            Toast.info('手机号码格式错误', 2)
            return
        }else if(this.state.smsCode == ''){
            Toast.info('请输入验证码', 2)
            return
        }else if(this.state.smsCode.length < 4){
            Toast.info('您的验证码不正确', 2)
            return
        }else if(this.state.password == ''){
            Toast.info('请设置登录密码', 2)
            return
        }else if(!passwordReg.test(this.state.password)){
            Toast.info('密码应为6-16位数字与字母组合', 2)
            return
        }else if(!this.state.protocol){
            Toast.info('注册前需同意《_注册协议》、《资金出借风险提示函》', 2)
            return
        }
        let GetQueryString = (name) => {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
        const context = {
            password: md5(md5(this.state.password)),
            phone: this.state.username,
            smsCode: this.state.smsCode,
            identity:GetQueryString('identity')
        }
        const res = await Api.userRegister(context)
        if(res.code != 0){
            Toast.info(res.message, 2)
        }else{
            // Toast.info('注册成功', 2)
            window.location.href = '/home'
        }
    }


    render(){
        return(

            <div className="box">
                <Head>
                    <link rel="stylesheet" href="/static/mods/invite/register/_.css"/>
                </Head>
                <div className="register-area">
                    <div className="content">
                        {/* 用户信息 */}
                        <div className="userInfo">
                            <div className="inner">
                                <img src={'/static/mods/invite/imgs/register-5.png'}/>
                                <span>*恒</span>
                            </div>
                        </div>
                        {/* 利率 */}
                        <div className="rate">
                            我在_出借新手产品有14%年化收益，注册送的108元红包已撸，Oh Yeah！
                        </div>
                        {/* 输入电话 */}
                        {this.state.showInput1&&
                            <div className="input-phone">
                                <input type='tel' placeholder="请输入手机号" maxLength="11" onChange={this.change('username')}/>
                            </div>
                        }
                        {/* 短信验证码 */}
                        {this.state.showInput2&&
                            <div>
                                <div className="input-check-number">
                                    <input type='tel' placeholder="请输入短信验证码"  maxLength="6" value={ this.state.smsCode } onChange={ this.change('smsCode')}/>
                                    <button className="get-number" eventtype="to_inactive" disabled={ this.state.textDisabled } onClick={ this.showCheckNumber }>
                                        {this.state.sendTextCode}
                                    </button>
                                </div>
                                {/* 设置密码 */}
                                <div className="input-password">
                                    <input type={this.state.passwordStatus} placeholder="设置密码(6-16位字母+数字)" maxLength="16" onChange={this.change('password')}/>
                                    <div className="show-password" onClick={this.changePasswordStatus.bind(this)}>
                                        <img src={'/static/mods/invite/imgs/eye.png'}/>
                                    </div>
                                </div>
                            </div>
                        }
                        {/* 领取金额 */}
                        <div className="get-money">
                            {this.state.showInput1&&
                                <img src={'/static/mods/invite/imgs/register-1.png'} onClick={this.submitPhone.bind(this)}/>
                            }
                            {this.state.showInput2&&
                                <img src={'/static/mods/invite/imgs/register-3.png'} onClick={this.submitRegister}/>
                            }
                        </div>
                        {/* 一些点缀小纸片 */}
                        <div className="dec-1">
                            <img src={'/static/mods/invite/imgs/register-4-1.png'}/>
                        </div>
                        <div className="dec-2">
                            <img src={'/static/mods/invite/imgs/register-4-2.png'}/>
                        </div>
                        <div className="dec-3">
                            <img src={'/static/mods/invite/imgs/register-4-3.png'}/>
                        </div>
                        <div className="dec-4">
                            <img src={'/static/mods/invite/imgs/register-4-4.png'}/>
                        </div>
                        <div className="dec-5">
                            <img src={'/static/mods/invite/imgs/register-4-5.png'}/>
                        </div>
                    </div>
                </div>
                {/* 明星产品 */}
                <div className="star-product">
                    <div className="title">
                        <img src={'/static/mods/invite/imgs/register-7.png'}/>
                    </div>
                    <div className="content">
                        <ul>
                            <li>
                                <p>历史年化收益</p>
                                <div className="sbox">
                                    <div className="sbox-child"><i>{this.state.xszcRate.apr} </i>+<i>{this.state.xszcRate.floatApr}</i></div>%
                                </div>
                                <p>新手专享</p>
                            </li>
                            <li>
                                <p>历史年化收益</p>
                                <p><i>{this.state.yjdjRate}</i>%</p>
                                <p>_</p>
                            </li>
                            <li>
                                <p>历史年化收益</p>
                                    <div className="sbox">
                                        <div className="sbox-child"><i>{this.state.xybRate[0]} </i> - <i> {this.state.xybRate[1]}</i></div>%
                                    </div>
                                <p>_</p>
                            </li>
                        </ul>
                    </div>
                </div>
                {/* 用心服务 值得信赖 */}
                <div className="service">
                    <div className="title">
                        <img src={'/static/mods/invite/imgs/register-8.png'}/>
                    </div>
                    <div className="service-list">
                        <ul>
                            <li>
                                <img src={'/static/mods/invite/imgs/register-9-1.png'}/>
                                <span>国资背景</span>
                                <span>中核汇能</span>
                                <span>战略入股</span>
                            </li>
                            <li>
                                <img src={'/static/mods/invite/imgs/register-9-2.png'}/>
                                <span>银行存管</span>
                                <span>开通存管账户</span>
                                <span>平台与资金隔离</span>
                            </li>
                            <li>
                                <img src={'/static/mods/invite/imgs/register-9-3.png'}/>
                                <span>风险控制</span>
                                <span>百人风控团队</span>
                                <span>14重风控体系</span>
                            </li>
                        </ul>
                    </div>
                    <div className="performance">
                        <ul>
                            <li>
                                <div className="left">
                                    <img src={'/static/mods/invite/imgs/register-9-4.png'}/>
                                </div>
                                <div className="right">
                                    <span>平台稳健运营</span>
                                    <span><i>{this.state.performance[0]['detail'][0]}</i>年<i>{this.state.performance[0]['detail'][1]}</i>天</span>
                                </div>
                            </li>
                            <li>
                                <div className="left">
                                    <img src={'/static/mods/invite/imgs/register-9-5.png'}/>
                                </div>
                                <div className="right">
                                    <span>平台成交金额</span>
                                    <span><i>{this.state.performance[1]['detail']}</i>亿元</span>
                                </div>
                            </li>
                            <li>
                                <div className="left">
                                    <img src={'/static/mods/invite/imgs/register-9-6.png'}/>
                                </div>
                                <div className="right">
                                    <span>累计注册人数</span>
                                    <span><i>{this.state.performance[2]['detail']}</i>万人</span>
                                </div>
                            </li>
                            <li>
                                <div className="left">
                                    <img src={'/static/mods/invite/imgs/register-9-7.png'}/>
                                </div>
                                <div className="right">
                                    <span>累计为投资人赚取</span>
                                    <span><i>{this.state.performance[3]['detail']}</i>亿元</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                {/* 图片验证码 */}
                {this.state.checkPage&&
                    <div>
                        <div className="check-number">
                            <div className="title">请输入验证码</div>
                            <div className="content">
                                <input type="text" maxLength='4' onChange={ this.imgChange } placeholder="输入验证码"/>
                                <img src={this.state.imgUrl} onClick={ this.reset } />
                            </div>
                            <div className="btns">
                                <p onClick={this.showCheckNumber}>取消</p>
                                <p onClick={this.sendMsg}>确定</p>
                            </div>
                        </div>
                        <div className="register-mask"></div>
                    </div>
                }
            </div>
        )
    }
}
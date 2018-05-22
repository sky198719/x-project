import React,{Component} from 'react'
import Head from 'next/head'
import Api from '../../../components/api/invite'

export default class record extends Component {
    constructor(props){
        super(props)
        this.state = {
            linkUrl:''
            
        }
    }
    async getLink(){
        let res = await Api.inviteLink()
        this.setState({
            linkUrl: res.data.data
        })
    }
    async componentDidMount(){
        this.getLink()
        var qrcode = new QRCode(document.getElementById("qrcode"), {
            colorDark : "#902df0",
            colorLight : "#ffffff",
            correctLevel : QRCode.CorrectLevel.H,
            width : 200,
            height : 200
        });
        qrcode.makeCode(this.state.linkUrl)
    }
    render(){
        return(
            <div className="box">
                <Head>
                    <link rel="stylesheet" href="/static/mods/invite/rcode/_.css"/>
                    <script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
                    <script type="text/javascript" src="http://static.runoob.com/assets/qrcode/qrcode.min.js"></script>
                </Head>
                <div className="title">
                    <img src={'/static/mods/invite/imgs/invite-code-title.png'}/>
                </div>
                <div className="code-box">
                    <div id="qrcode">
                    </div>
                </div>
                <div className="tip-box">
                    <p>●  好友通过微信“扫一扫”或其他支持扫码的应用，扫描上方二维码。</p>
                    <p>●  好友在打开的页面中完成注册，即可完成邀请。</p>
                </div>
            </div>
        )
    }
}
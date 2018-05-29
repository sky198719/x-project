import React,{Component} from 'react'
import Head from 'next/head'
import Link from 'next/link'
import Api from '../../components/api/invite'
import bridge from "../../static/merge/xxd-jsBridge.esm";
import app from './app.js'
import Router from 'next/router'

export default class Invite extends Component {
    constructor(props){
        super(props)
        this.state = {
            showDetail:false,//展开细则标志
            opentext:'展开活动细则',//展开细则标签内容
            arrowUrl:'down',//箭头方向
            rewardStatus:null,//奖励详细的状态值
            prizesCarousel:[],//循环滚动中奖列表
            activityTime:'',//活动时间
            newUserRate:'',//新手专享利率
            inviteBrief:{amount:"0",registCount:"0.0"},//奖励数和邀请人数
            isLogined:false//是否登录，默认为false 未登录
        }
    }
    // 事件
    showDetail(){
        let text = null
        let status = null
        if(this.state.showDetail){
            text = '展开活动细则'
            status = 'down'
        }
        else{
            text = '收起活动细则'
            status = 'up'
        }
        this.setState({
            showDetail: !this.state.showDetail,
            opentext: text,
            arrowUrl:'down',
            arrowUrl: status
        })
    }
    // 展示奖励细则
    showReward(e){
        let tem = null
        this.setState({
            rewardStatus: e
        })
    }
    // 关闭奖励细则
    closeReward(){
        this.setState({
            rewardStatus:null
        })
    }
    // 跳转到record页面
    goRecord = () => {
        if(app.isLogin()){
            Router.push({pathname: '/invite/record'})
        }
        else{
            bridge.open({
                pagename: 'login'
            })
        }
    }
    // 分享弹框
    getInviteLink(){
        if(app.isLogin()){
            bridge.open({
                pagename: 'share'
            })
        }
        else{
            bridge.open({
                pagename: 'login'
            })
        }
    }
    //获取字幕轮播列表
    async getCarousel(){
        let res = await Api.prizesCarousel()
        this.setState({
            prizesCarousel: res.data
        })
    }
    //获取奖励数和邀请人数
    async inviteBrief(){
        let res = await Api.inviteBrief()
        this.setState({
            inviteBrief: res.data
        })
    }
    // 读取新手专享年利率
    async getNewUserRate(){
        let res = await Api.getNewUserRate()
                console.log(res)
            let data = res.data.wonderfulProduct
            let rate = Number(data.plannedAnnualRate) + Number(data.floatingRate)
            this.setState({
                newUserRate: rate
            })
    }
    // 获取活动时间
    async getActivityTime(){
        let res = Api.getActivityTime()
            res.then(r => {
                let timeArr = r.data.split('-')

                let s_year = timeArr[0].substring(0,4)
                let s_mouth = timeArr[0].substring(4,6)
                let s_day = timeArr[0].substring(6,8)

                let e_year = timeArr[1].substring(0,4)
                let e_mouth = timeArr[1].substring(4,6)
                let e_day = timeArr[1].substring(6,8)
                
                let strTime =`活动时间：${s_year}.${s_mouth}.${s_day}-${e_year}.${e_mouth}.${e_day}`
                this.setState({
                    activityTime: strTime
                })

            })
    }
    async componentDidMount(){
        
        this.setState({
            // isLogined: app.isLogin()
            isLogined: false
        })
       if(!this.state.isLogined){
            await this.getNewUserRate()
            await this.getActivityTime()
            await this.getCarousel()
            await this.inviteBrief()
            /**
             * 顶部滚动文字
             * 当块元素的位置到最左边多一点时，然后立即把该元素位置拖到最后面，循环如此
             */
            let parent = document.getElementsByClassName('running')[0]
            let target1 = document.getElementsByClassName('scroll-text-1')[0]
            let target2 = document.getElementsByClassName('scroll-text-2')[0]
            let end = target1.offsetWidth + 40//滚动目标宽度
            let data1 = 0//模块1的初始位置
            let data2 = end//..2......
            let index = 1//标号,判断现在是哪个模块处于屏幕中心位置
            let timer = setInterval(
                () => {
                    if(index==1){
                        //模块1
                        data1 ++
                        target1.style.left = `-${data1}px`
                        if(data1 >= end){
                            data1 = end
                            index = 2
                        }
                        //模块2
                        data2 --
                        target2.style.left = `${data2}px`
                    }
                    else{
                        //模块1
                        data1 --
                        target1.style.left = `${data1}px`
                        //模块2
                        data2 ++
                        target2.style.left = `-${data2}px`
                        if(data2 >= end){
                            data2 = end
                            index = 1
                        }
                    }
                },30)
            }
    }

    render(){
        return(
            <div className="box">
            <Head>
                <link rel="stylesheet" href="/static/mods/invite/_.css"/>
            </Head>
                {/* 顶部滚动 */}
                <div className="running">
                    <div className="bg"></div>
                    <div className = 'scroll-text scroll-text-1'>
                        {this.state.prizesCarousel.map((item,index)=>(
                            <div className="slide" key={index}>{item}</div>
                        ))}
                    </div>
                    <div className = 'scroll-text scroll-text-2'>
                        {this.state.prizesCarousel.map((item,index)=>(
                            <div className="slide" key={index}>{item}</div>
                        ))}
                    </div>
                </div>
                {/* 主图 */}
                <div className="banner">
                    <img src={'../../static/mods/invite/imgs/1.png'}/>
                    <p>{this.state.activityTime}</p>
                </div>
                {/* 记录 */}
                <div className="record">
                    <ul>
                        <li>
                            <span><i>{this.state.inviteBrief.registCount}</i>人</span>
                            <span>已邀请</span>
                            
                        </li>
                        <img src={'../../static/mods/invite/imgs/space.png'}/>
                        <li>
                            <span><i>{this.state.inviteBrief.amount}</i>元</span>
                            <span>已获得奖励金额</span>
                            
                        </li>
                        <img src={'../../static/mods/invite/imgs/space.png'}/>
                            <a>
                                <span onClick={this.goRecord}>
                                    查看明细 >>
                                </span>
                            </a>
                    </ul>
                </div>
                {/* 4种奖励 */}
                <div className="content">
                    <div className="reward-1">
                        <img src={'../../static/mods/invite/imgs/2.png'}/>
                        <div className="words">
                            <p>每成功邀请好友首投可得</p>
                            <p>10元红包</p>
                            <p onClick={this.showReward.bind(this,1)}>规则详情>></p>
                        </div>
                    </div>
                    <div className="reward-2">
                        <img src={'../../static/mods/invite/imgs/3.png'}/>
                        <div className="words">
                            <p>每月邀请2-3人，最高可再得</p>
                            <p>130元红包</p>
                            <p onClick={this.showReward.bind(this,2)}>规则详情>></p>
                        </div>
                    </div>
                    <div className="reward-3">
                        <img src={'../../static/mods/invite/imgs/4.png'}/>
                        <div className="words">
                            <p>单个好友出借最高可得58元</p>
                            <p>现金返佣，多邀多得</p>
                            <p onClick={this.showReward.bind(this,3)}>规则详情>></p>
                        </div>
                    </div>
                    <div className="reward-4">
                        <img src={'../../static/mods/invite/imgs/5.png'}/>
                        <div className="words">
                            <p>好友注册即送108元</p>
                            <p>新手大礼包</p>
                            <p onClick={this.showReward.bind(this,4)}>规则详情>></p>
                        </div>
                    </div>
                </div>
                {/* 活动细则 */}
                <div className="active-detail">
                    <div className="title">
                        <img src={'../../static/mods/invite/imgs/6.png'}/>
                    </div>
                    <div className="steps">
                        <div className="steps-img-container">
                            <img className="step-img" src={'../../static/mods/invite/imgs/7.png'}/>
                            <ul>
                                <li>
                                    分享给好友
                                </li>
                                <li>
                                    <span>好友在邀请</span>
                                    <span>界面注册</span>
                                </li>
                                <li>
                                    好友进行出借
                                </li>
                                <li>
                                    发放奖励
                                </li>
                            </ul>
                            <div className="open-btn" onClick={this.showDetail.bind(this)}>
                                <span>
                                    {this.state.opentext}
                                </span>
                                <img src={'../../static/mods/invite/imgs/db-'+this.state.arrowUrl+'.png'}/>
                            </div>
                        </div>
                        {/* 控制 */}
                        {this.state.showDetail &&
                            <div className="active-detail">
                                <div className="invite-qualification">
                                    <ul>
                                        <li className="title">
                                            <span>邀请条件：</span>
                                        </li>
                                        <li>
                                            ① 参与资格：邀请人（本人）须在_有过出借记录，不满足资格的，不能参加本次活动。
                                        </li>
                                        <li>
                                            ② 适用平台：APP端（Android/iOS）
                                        </li>
                                        <li>
                                            ③ 适用产品（统计好友出借额的产品）：_（包括所有期限及新手专享）、_及_；散标及债权转让除外
                                        </li>
                                        <li>
                                            ④ 好友注册时间在活动期间内，才能获得对应奖励。
                                        </li>
                                        <li>
                                            ⑤ 好友必须通过邀请人分享的链接进行注册，否则邀请人无法获得该好友的邀请奖励。
                                        </li>
                                    </ul>
                                </div>
                                <div className="reward-rules">
                                    <ul>
                                        <li className="title">
                                            <span>奖励规则：</span>
                                        </li>
                                        <li>
                                            ① 请点击相应奖励进行查看。
                                        </li>
                                        <li>
                                            ② 奖励一、二、三相互独立，可叠加享有，所有奖励达到条件后实时发放。<br/>
                                            举例，如果邀请4个好友，且4个好友都出借满1万元，那么邀请人可以获得：奖励一：10元红包*4个=40元；奖励二：50元红包+80元红包=130元；奖励三：30元现金奖励*4=120元；合计可获得290元奖励
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        }
                        {this.state.showDetail &&
                            <div className="warn">
                                <ul>
                                    <li>※ 注意事项</li>
                                    <li>
                                        <span>① 若被邀请人为本司工作人员，则邀请人无法获得对应的邀请奖励</span>
                                    </li>
                                    <li>
                                        <span>② 若活动中发现存在作弊等不合规行为，_有权取消该用户的奖励，并保留相关法律权利。</span>
                                    </li>
                                </ul>
                            </div>
                        }
                    </div>
                    <div className="tips">
                        <p>更多活动请关注_微信订阅号：_服务号</p>
                        <p>客服电话：4000-169-521（9:00-18:00 工作日）</p>
                        <p>
                            <span>
                                提示
                            </span>
                            市场有风险，出借需谨慎
                        </p>
                    </div>
                </div>
                {/* 邀请按钮 */}
                <div className="invite-btn" onClick={this.getInviteLink.bind(this)}>
                    <i>立即邀请好友</i>
                </div>
                {/* 弹框部分********************************************************************************* */}
                {this.state.rewardStatus&&
                    <div className="page-container">
                        
                        {/* 第一个弹框......... */}
                        {this.state.rewardStatus==1&&
                            <div className="page-1">
                                <div className="page-bg">
                                    <img className="step-img" src={'../../static/mods/invite/imgs/bg.png'}/>
                                </div>
                                <div className="page-1-box">
                                    <div className="title">
                                        奖励一
                                    </div>
                                    <div className="content">
                                        <ul>
                                            <li>
                                                邀请的好友自注册之日起<i>15天</i>内出借<i>金额≥100</i>元，您即可获得<i>10元</i>红包。
                                            </li>
                                            <li>
                                                <i>多邀多得</i>，邀请N个人，即可以得到<i>N个</i>10元红包，最多50个红包。
                                            </li>
                                            <li>
                                                <p> ● 单笔出借金额≥1000元，出借期限≥1个月，即可使用 10元红包。</p>
                                                <p> ● 红包使用仅限_，有效期30天，过期失效。</p>
                                            </li>
                                        </ul>
                                    </div>
                                    <img className="del-img" src={'../../static/mods/invite/imgs/delete.png'} onClick={this.closeReward.bind(this)}/>
                                </div>
                            </div>
                        }
                        {/* 第二个弹框......... */}
                        {this.state.rewardStatus==2&&
                            <div className="page-2">
                                <div className="page-bg">
                                    <img className="step-img" src={'../../static/mods/invite/imgs/bg.png'}/>
                                </div>
                                <div className="page-2-box">
                                    <div className="title">
                                        奖励二
                                    </div>
                                    <div className="content">
                                        <ul>
                                            <li>
                                                在同一个自然月内，邀请<i>2位</i>好友且每位好友在当月单笔出借金额<i>≥5000</i>元，您即可获得<i>50</i>元红包。
                                            </li>
                                            <li>
                                                当月邀请<i>3位</i>好友且每位好友在当月单笔出借金额<i>≥5000</i>元，可再获得<i>80元</i>红包。
                                            </li>
                                            <li>
                                                <p> ● 每人每月最高可获得130元红包（红包分阶段发放，当月成功邀请2人可得50元红包，若再邀请1人，将再得80元红包）</p>
                                                <p> ● 单笔出借金额≥10000元，出借期限≥1个月，即可使用50元红包；单笔出借金额≥10000元，出借期限≥3个月，即可使用80元红包。</p>
                                                <p> ● 红包使用仅限_，有效期30天，过期失效。</p>
                                                <p> ● 下个自然月再邀请好友符合本奖励条件，可再获得本奖。</p>
                                            </li>
                                        </ul>
                                    </div>
                                    <img className="del-img" src={'../../static/mods/invite/imgs/delete.png'} onClick={this.closeReward.bind(this)}/>
                                </div>
                            </div>
                        }
                        {/* 第三个弹框......... */}
                        {this.state.rewardStatus==3&&
                            <div className="page-3">
                                <div className="page-bg">
                                    <img className="step-img" src={'../../static/mods/invite/imgs/bg.png'}/>
                                </div>
                                <div className="page-3-box">
                                    <div className="title">
                                        奖励三
                                    </div>
                                    <div className="content">
                                        <ul>
                                            <li>
                                                您邀请的单个好友自注册之日起<i>15天</i>内累积出借达到相应金额，您即可获得相应现金奖励，详见下表：
                                            </li>
                                            <li>
                                                <table border="1" bordercolor="#fce998">
                                                    <tbody>
                                                        <tr>
                                                            <th align="cener">好友累计出借金额</th>
                                                            <th>累计现金奖励</th>
                                                        </tr>
                                                        <tr>
                                                            <td>1000≤X＜5000</td>
                                                            <td>8元</td>
                                                        </tr>
                                                        <tr>
                                                            <td>5000≤X＜10000</td>
                                                            <td>8元+10元</td>
                                                        </tr>
                                                        <tr>
                                                            <td>10000≤X＜20000</td>
                                                            <td>8元+10元+12元</td>
                                                        </tr>
                                                        <tr>
                                                            <td>X≥20000</td>
                                                            <td>8元+10元+12元+28元</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </li>
                                            <li>
                                                <p> ● X即注册之日起15天内的累计出借额。</p>
                                                <p> ● 此奖励为分阶段奖励，可叠加获得，例如：同一个好友A投标出借1000元，获得8元；有效期内A再投标出借4000元，额外再获得10元，即累计出借6000元，总计获得18元。</p>
                                                <p> ● 奖励以新新币形式发放，与人民币兑换比例50：1。如获得8元现金，则发放400个新新币。</p>
                                                <p> ● 需用户到_App→我的新钱袋→我的奖励→我的新新币，自行兑换成现金。兑换无门槛。</p>
                                                <p> ● 邀请多个好友，可叠加获得奖励（举例：邀请2个好友，A出借5000元，B出借20000元，此项奖励您可获得18元+58元=76元）。</p>
                                            </li>
                                        </ul>
                                    </div>
                                    <img className="del-img" src={'../../static/mods/invite/imgs/delete.png'} onClick={this.closeReward.bind(this)}/>
                                </div>
                            </div>
                        }
                        {/* 第四个弹框......... */}
                        {this.state.rewardStatus==4&&
                            <div className="page-4">
                                <div className="page-bg">
                                    <img className="step-img" src={'../../static/mods/invite/imgs/bg.png'}/>
                                </div>
                                <div className="page-4-box">
                                    <div className="title">
                                        好友礼
                                    </div>
                                    <div className="content">
                                        <ul>
                                            <li>
                                                您邀请的<i>好友</i>在_<i>注册成功</i>后，即可获得<i>108元</i>新人红包大礼
                                            </li>
                                            <li>
                                                <p> ● 108元共含4个红包，分别为5元、10元、25元、68元。</p>
                                                <p> ● 享受历史年化收益率{this.state.newUserRate}%的产品--_（新手专享）的购买权。</p>
                                            </li>
                                        </ul>
                                    </div>
                                    <img className="del-img" src={'../../static/mods/invite/imgs/delete.png'} onClick={this.closeReward.bind(this)}/>
                                </div>
                            </div>
                        }
                        {/* 无资格弹框。。。。。。 */}
                        {this.state.rewardStatus==5&&
                            <div className="page-5">
                                <div className="page-5-box">
                                    <p>只有出借过的用户才有资格获得邀请奖励</p>
                                    <p>您可以先进行出借获得邀请资格。</p>
                                    <p>去出借获得资格</p>
                                    <img className="del-img" src={'../../static/mods/invite/imgs/delete.png'} onClick={this.closeReward.bind(this)}/>
                                </div>
                            </div>
                        }
                        <div className="mask">
                                
                        </div>
                    </div>
                }
            </div>
        )
    }
}
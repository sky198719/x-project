import React,{Component} from 'react'
import Head from 'next/head'
import Api from '../../../components/api/invite'
import bridge from "../../../static/merge/xxd-jsBridge.esm";

export default class record extends Component {
    constructor(props){
        super(props)
        this.state = {
            invite_person_page: 1,
            invite_person_pageSize: 7,
            invite_reward_page: 1,
            invite_reward_pageSize: 7,
            reward_total:0,
            person_total:0,
            recordInfoList:[],
            rewardInfoList:[],
            inviteBrief:{}
        }
    }
    //获取记录列表 人
    async inviteRecord(){
        let timeFormat = (createTime) => {
            let date = new Date(Number(createTime))
            let year = date.getFullYear() 
            let mouth = date.getMonth()+ 1 
            let day   = date.getDate()
            let strTime = `${year}.${mouth}.${day}`
            return strTime
        }
        let param = {
            page:this.state.invite_person_page,
            pageSize:this.state.invite_person_pageSize
        }
        let res = await Api.inviteRecord(param)
        res.data.data.list.forEach(item => {
            item.registtime = timeFormat(item.registtime)
        });
        this.setState({
            recordInfoList: res.data.data.list,
            record_total: res.data.data.total
        })
    }

    //获取记录列表 奖品
    async inviteReward(){
        let timeFormat = (createTime) => {
            let date = new Date(Number(createTime))
            let year = date.getFullYear() 
            let mouth = date.getMonth()+ 1 
            let day   = date.getDate()
            let strTime = `${year}.${mouth}.${day}`
            return strTime
        }
        let param = {
            page:this.state.invite_reward_page,
            pageSize:this.state.invite_reward_pageSize
        }
        let res = await Api.inviteReward(param)
        res.data.data.list.forEach(item => {
            item.createtime = timeFormat(item.createtime)
        });
        this.setState({
            rewardInfoList: res.data.data.list,
            reward_total: res.data.data.total
        })
    }
    async inviteBrief(){
        let res = await Api.inviteBrief()
        this.setState({
            inviteBrief: res.data
        })
    }
    //点击在家更多
    async loadMore(e){
        switch(e){
            case 'person':  
                this.setState({
                    invite_person_pageSize:this.state.invite_person_pageSize +=7
                },()=>{
                    this.inviteRecord();
                })
                
            break;
            case 'reward': 
                this.setState({
                    invite_reward_pageSize:this.state.invite_reward_pageSize +=7
                },()=>{
                    this.inviteReward()
                })
                
            break;
        }
    }
    //点击兑换按钮
    convertMoney(){
        console.log('1')
        bridge.open({
            //跳转至App→我的新钱袋→我的奖励→我的新新币，新新币兑换
            pagename: 'myreward?filter=1'
        })
    }
    //立即查看红包
    viewRredPacket(){
        console.log('2')
        bridge.open({
            //跳转至App→我的新钱袋→我的奖励→我的优惠券，查看红包页面
            pagename: 'myreward?filter=0'
        })
    }
    async componentDidMount(){
        await this.inviteRecord()
        await this.inviteReward()
        await this.inviteBrief()
    }


    render(){
        return(
            <div className="box">
                <Head>
                    <link rel="stylesheet" href="/static/mods/invite/record/_.css"/>
                </Head>
                {/* 邀请记录 */}
                <div className="invite-record">
                    <div className="record-number">
                        <span><i>{this.state.inviteBrief.registCount}</i>人</span>
                        <span>我的邀请记录</span>
                    </div>
                    {this.state.recordInfoList.length>0&&
                        <div className="record-info">
                            <div className="decorate">
                                <div className="inner">
                            
                                </div>
                            </div>
                            <div className="info-list">
                                <ul>
                                    <li>
                                        <span>已邀请好友</span>
                                        <span>注册时间</span>
                                        <span>首投时间</span>
                                    </li>
                                    {this.state.recordInfoList.map((item,index) => (
                                        <li key={index}>
                                            <span>{item.mobile}</span>
                                            <span>{item.registtime}</span>
                                            <span>{item.firstinvestementtime}</span>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>
                    }
                    {this.state.recordInfoList.length<=0&&
                        <div className="record-info-none">
                            <div className="decorate">
                                <div className="inner">
                            
                                </div>
                            </div>
                            <div className="info-list">
                                <ul>
                                    <li>
                                        <span>已邀请好友</span>
                                        <span>注册时间</span>
                                        <span>首投时间</span>
                                    </li>
                                    <li>
                                        暂无记录
                                    </li>
                                </ul>
                            </div>
                        </div>
                    }
                    <div className="bgimg">
                        <img src="/static/mods/invite/imgs/record-1.png"/>
                    </div>
                    {this.state.recordInfoList.length>6&&this.state.invite_person_pageSize<this.state.person_total&&
                        <div className="load-more">
                            <span onClick={this.loadMore.bind(this,'person')}>加载更多</span>
                        </div>
                    }
                </div>
                {/* 奖励记录 */}
                <div className="reward-record">
                    <div className="reward-number">
                        <span><i>{this.state.inviteBrief.amount}</i>元</span>
                        <span>我的奖励明细</span>
                    </div>
                    {this.state.rewardInfoList.length>0&&
                        <div className="reward-info">
                            <div className="decorate">
                                <div className="inner">
                            
                                </div>
                            </div>
                            <div className="info-list">
                                <ul>
                                    <li>
                                        <span>发放时间</span>
                                        <span>奖    励</span>
                                        <span>奖励来源</span>
                                    </li>
                                    
                                    {this.state.rewardInfoList.map((item,index) => (
                                        <li key={index}>
                                            <span>{item.createtime}</span>
                                            <span>{item.rewardName}</span>
                                            <span>{item.rewarddesc}</span>
                                        </li>
                                    ))}
                                </ul>
                            </div>
                        </div>  
                    }
                    {this.state.recordInfoList.length<1&&
                        <div className="reward-info-none">
                            <div className="decorate">
                                <div className="inner">
                            
                                </div>
                            </div>
                            <div className="info-list">
                                <ul>
                                    <li>
                                        <span>发放时间</span>
                                        <span>奖    励</span>
                                        <span>奖励来源</span>
                                    </li>
                                    <li>
                                        暂无记录
                                    </li>
                                </ul>
                            </div>
                        </div>
                    }
                    <div className="bgimg">
                        <img src="/static/mods/invite/imgs/record-1.png"/>
                    </div>
                    {this.state.rewardInfoList.length>6&&this.state.invite_reward_pageSize<this.state.reward_total&&
                        <div className="load-more">
                            <span onClick={this.loadMore.bind(this,'reward')}>加载更多</span>
                        </div>
                    }
                </div>
                {/* 说明 */}
                <div className="tips">
                    <ul>
                        <li className="title">
                            ※奖励兑换说明：
                        </li>
                        <li>
                            ●  50个新新币可兑换1元；<i onClick={this.convertMoney.bind(this)}>立即兑换>></i>
                        </li>
                        <li>
                            ●  <i onClick={this.viewRredPacket.bind(this)}>立即查看红包>></i>
                        </li>
                        <li className="title">
                            ※已邀请但没奖励？
                        </li>
                        <li>
                            ● 邀请人（本人）需在新新贷有过出借记录，才有资格邀请。
                        </li>
                        <li>
                            ● 好友不是通过您分享的链接注册的，邀请无效。
                        </li>
                    </ul>
                </div>
            </div>
        )
    }
}
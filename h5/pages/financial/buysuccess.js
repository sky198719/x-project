import React, { Component, PureComponent } from 'react'
import Head from 'next/head'
import moment from 'moment'
import bridge from "../../static/merge/xxd-jsBridge.esm"
import track from '../../static/merge/track-base'
import { Modal, Toast } from 'antd-mobile'

const alert = Modal.alert
export default class extends PureComponent {
    constructor(props) {
        super(props)
        this.state = {
            productName: '',
            createTime: '',
            startDate: '',
            plannedInterest: '',
            expireDate: ''
        }
    }
    componentDidMount() {
        let successInfo = ''
        track.init()
        if(sessionStorage.getItem('successInfo')){
            successInfo = JSON.parse(sessionStorage.getItem('successInfo'))
            const nowDate = new Date()
            // debugger
            // if(successInfo.productName.indexOf('新手标') == -1 && nowDate.getFullYear() == '2018' && nowDate.getMonth() + 1 == '3' && nowDate.getDate() >= 7 && nowDate.getDate() <= 23){
            //     // console.log('111')
            //     alert('加入成功！', '参加周年庆活动，赢普吉岛浪漫双人游！', [
            //         { text: '知道了', onPress: () => console.log('cancel') },
            //         { text: '立即参加', onPress: () => {
            //             location = '/html/anniversaryActivity/index.html'
            //         }},
            //     ])
            // }
            this.setState({
                productName: successInfo.productName,
                createTime: successInfo.createTime,
                startDate: successInfo.startDate,
                plannedInterest: successInfo.plannedInterest,
                expireDate: successInfo.expireDate
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
    investRecord = () =>{
        location = '/mypurse/investmenthistory'
    }
    // <p>{ this.state.plannedInterest }元</p>
    render() {
        return (
            <div>
                <Head>
                    <link rel='stylesheet' type='text/css' href="/static/mods/financial/_.css" />
                </Head>
                <div className="product—detail-container purchase-success-container position-a">
                    <div className="wrapper">
                        <div className="content-li congratulate-content">
                            <p className="bold-font">恭喜您成功加入{this.state.productName}</p>
                            <p>{ moment(Number(this.state.createTime)).format('YYYY/MM/DD HH:mm:ss') }</p>
                            <span className="line-bg"></span>
                        </div>
                        <div className="content-li calculate-content">
                            <p className="bold-font">开始计算收益</p>
                            <p>{ moment(Number(this.state.startDate)).format('YYYY/MM/DD') }</p>
                            <span className="line-bg line-bg-reverse"></span>
                        </div>
                        <div className="content-li income-content">
                            <p className="bold-font">预计回款日期</p>
                            <p>{ moment(Number(this.state.expireDate)).format('YYYY/MM/DD') } 到期通过债权转让退出</p>
                        </div>
                    </div>
                    <div className="div-btn">
                        <button className="xxd-xl-btn dmp-click" dev_id="A11.1-1" eventtype="jump" onClick={ this.openApp }>更多优质产品尽在APP</button>
                        <button className="xxd-xl-btn detail-btn dmp-click" dev_id="A11.1-1.1" eventtype="jump" onClick={ this.investRecord }>查看出借详情</button>
                    </div>
                </div>
            </div>
        )
    }
}
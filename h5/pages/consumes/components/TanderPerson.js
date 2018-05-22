import React, { Component } from 'react'

import Loading from '../../../common/Loading'

class TanderPerson extends Component {
    constructor() {
        super()
    }
    changeMoney(val) {
        var changeMoney = 0
        const ractMoney = val.toFixed(2)
        changeMoney = ractMoney.split('.');
        changeMoney[0] = changeMoney[0].replace(/(\d)(?=(\d{3})+$)/g,'$1,');
        changeMoney = changeMoney.join('.');
        return changeMoney
    }
    render() {

        if(!this.props.data || !this.props.loadRecord){
            return (
                <div>
                    <Loading />
                </div>
            )
        }

        const data = this.props.data
        const loadRecord = this.props.loadRecord
        // const dataPhone = data.username.substr(0,3) + '****' + data.username.substr(7)
        return (
            <div>
                <div className="nw1e title-person">借款人信息</div>
                <ul className="xui-list mh10x nh1e-z nl1e nr1e-z open">
                    <li className="open no-active">
                        <h3 className="list_switch ew w100" onClick={ this.props.methods }>
                            <b className="this-title font17">发布者 {data.nickname}</b>
                            <span className="surplus-a ml ar co-back"><i className="icon icon-right"></i></span>
                        </h3>
                        <ul className="more_info nt1e w100 v2e fontweight3">
                            <li>
                                <em>借款人姓名：</em>
                                <em>{data.realname}</em>
                            </li>
                            <li>
                                <em>身份证号码：</em>
                                <em>{data.idCardNo}</em>
                            </li>
                            <li>
                                <em>性别：</em>
                                <em>{data.gender}</em>
                            </li>
                            <li>
                                <em>逾期次数：</em>
                                <em>{data.overdueCount}次</em>
                            </li>
                            <li>
                                <em>所在地：</em>
                                <em>{data.location}</em>
                            </li>
                        </ul>
                    </li>
                    <li className="open no-active">
                        <h3 className="list_switch ew w100" onClick={ this.props.methods }>
                            <b className="this-title font17">贷款记录</b>
                            <span className="surplus-a ml ar co-back"><i className="icon icon-right"></i></span>
                        </h3>
                        <ul className="more_info nt1e w100 v2e fontweight3">
                            <li>
                                <em>累计借贷：</em>
                                <em>{this.changeMoney(loadRecord.sumAmount ? loadRecord.sumAmount : 0)}元</em>
                            </li>
                            <li>
                                <em>待还金额：</em>
                                <em>{this.changeMoney(loadRecord.dueRepaymentAmount ? loadRecord.dueRepaymentAmount : 0)}元</em>
                            </li>
                            <li>
                                <em>正常还清：</em>
                                <em>{loadRecord.normalLoan ? loadRecord.normalLoan : 0}次</em>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        )
    }
}

export default TanderPerson
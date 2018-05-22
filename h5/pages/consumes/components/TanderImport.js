import React, { Component } from 'react'

import { formatDate } from '../../../common/Util'


class TanderImport extends Component {
    constructor(props) {
        super(props)
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
        if(!this.props.data){
            return (
                <div>
                </div>
            )
        }

        const data = this.props.data 

        if(data && JSON.stringify(data.repaymentType) != "{}"){
            // switch(data.repaymentType)
            data.repaymentTypes = data.repaymentType.message
        }

        const percentageTotle = parseInt(100 - ((data.leftTenderAmount / data.bidAmount) * 100))+ '%'

        return (
            <ul id="consumerloans-detail" className="xui-list-back">
                <li>
                    <h3 className="list_switch ew w100 mb10x">
                        <b className="this-title title18">{data.bidName}</b>
                        <span className="surplus-a ml ar co-back backico"><i className="xui-bubble" data-theme="arrow" data-pos="none"
                                                                data-co="main">{data.status.message}</i></span>
                    </h3>
                    <div className="xui-line mb1e"></div>
                    <ul className="w100 v2e font15">
                        <li>
                            <em>剩余可投金额：</em>
                            <em className="import-money">{this.changeMoney(data.leftTenderAmount)} <i>元</i></em>
                        </li>
                        <li className="ew w100">
                            <em className="w1 bold">进度：</em>
                            <em className="w9">
                                <ul className="progress-bar ew w100 nh8x">
                                    <li className="col-10"><span className="xui-progress-bar2" data-theme="default" data-co="main"><b
                                            style={{width:percentageTotle}}></b></span></li>
                                    <li className="nl1e"><span className="xui-bubble" data-theme="arrow" data-pos="left" data-co="main">{percentageTotle}</span>
                                    </li>
                                </ul>
                            </em>
                        </li>
                        <li>
                            <em>年化收益：</em>
                            <em className="red persetbai">{data.plannedAnnualRate} <i>%</i></em>
                        </li>
                        <li>
                            <em>标的期限：</em>
                            <em>{data.leastPeriodValue} { data.leastPeriodType == 'MONTH' ? '个月' : '' } { data.leastPeriodType == 'YEAR' ? '年' : '' } { data.leastPeriodType == 'DAY' ? '天' : '' }</em>
                        </li>
                        <li>
                            <em>资金用途：</em>
                            <em>{data ? data.loanPurpose.message : ''}</em>
                        </li>
                        <li>
                            <em>起投金额：</em>
                            <em>{data.tenderAmountDown} 元</em>
                        </li>
                        <li>
                            <em>还款方式：</em>
                            <em>{data.repaymentTypes}</em>
                        </li>
                        <li>
                            <em>预计起息日：</em>
                            <em>{data.plannedValueDate}</em>
                        </li>
                    </ul>
                </li>
            </ul>
        )
    }
}

export default TanderImport
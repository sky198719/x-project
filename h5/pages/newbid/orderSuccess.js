import React, { Component } from 'react'
import Header from './header'
import { Button } from 'antd-mobile'
import moment from 'moment'

export default class Success extends Component {
    moreProduct() {
        window.location.href = '/m/#!/static/html/popular/financesList.html?path=popular&v=20170921'
    }
    myXXDDetail() {
        window.location.href = '/m/#!/static/html/account/account.html?v=20170921&v=20170921'
    }
    render() {
        const { name, amount, createTime, startDate, endDate, expireDate, plannedInterest } = this.props.url.query
        return (
            <div>
                <Header title="出借成功" />
                <div className="content-block account_tit">
                    <div className="content-block">
                        <div className="row no-gutter width100 mb10 ">
                            <div className="col-25">
                                <img src="../../static/newbid/imgs/day-succeed.png" width="30" className=" mt15" />
                            </div>
                            <div className="col-75 ">
                                <h5 className="font-black"><span>出借</span><span className="tenderItem">{name}</span>成功</h5>
                                <h5 className="font-grey"><span className="font-red tenderAmount">{amount}</span><span>元</span></h5>
                                <h6 className="font-grey tenderTime">{moment(parseInt(createTime)).format('YYYY-MM-DD HH:mm:ss')}</h6>
                            </div>
                        </div>

                        <div className="row width100 mb10 mt10">
                            <div className="col-25 ">
                                <img src="../../static/newbid/imgs/arrows-ok.png" width="20" height="40" style={{marginLeft: '5px'}} />
                            </div>
                            <div className="col-75">
                            </div>
                        </div>

                        <div className="row width100 mb10 mt10">
                            <div className="col-25 " >
                                <img src="../../static/newbid/imgs/calculate-show.png" width="27" className=" mt5" />
                            </div>
                            <div className="col-75 ">
                                <h5 className="font-black">开始计算收益</h5>
                                <h6 className="font-grey startDate">{moment(parseInt(startDate)).format('YYYY-MM-DD')}</h6>
                            </div>
                        </div>


                        <div className="row width100 mb10 mt10">
                            <div className="col-25 ">
                                <img src="../../static/newbid/imgs/arrows-ok.png" width="20" height="40" style={{marginLeft: '5px'}}  />
                            </div>

                        </div>
                        <div className="row width100 mb20 mt10">
                            <div className="col-25">
                                <img src="../../static/newbid/imgs/earnings-grey.png" width="30" className=" mt10" />
                            </div>
                            <div className="col-75 leftxxd">
                                <h5 className="font-grey">预计收益到账</h5>
                                <h5 className="font-grey"><span className="font-red profitAmount">{plannedInterest}</span><span>元</span></h5>
                                <h6 className="font-grey arrivalDate">{moment(parseInt(expireDate)).format('YYYY-MM-DD')}</h6>
                            </div>
                        </div>
                    </div>
                    <div className="btn-ordersuccess">
                        <Button className="btn" type="primary" onClick={ this.moreProduct }>更多理财产品</Button>
                        <Button className="btn" type="primary" onClick={ this.myXXDDetail }>去“我的新新贷”查看</Button>
                    </div>
                </div>
            </div>
        )
    }
}
import React, { Component } from 'react'

import { Toast } from 'antd-mobile'

import Header from './header/index'

import server from './api/index'

import { getLocalItem } from '../../common/Util'

let pages = 1

class TanderRecode extends Component {
    constructor() {
        super()
        this.state = {
            recodeList: [],
            bidCode: '',
            pageTotle: 0,
            loadText: '点击加载更多'
        }
    }

    componentWillMount() {
        this.setState({
            bidCode:  this.props.url.query.typeId
        })
    }

    async componentDidMount() {
        pages = 1
        const res = await server.consumeInfo(this.state.bidCode + '/investments', {
            data: {
                bidCode: this.state.bidCode,
                currentPage: pages,
                pageSize: 10
            }
        })

        let resTotle = res.totalCount
        
        let upCount = Math.ceil(resTotle / res.pageSize)

        if(upCount !== 0){
            this.setState({
                recodeList: this.state.recodeList.concat(res.items),
                pageTotle: upCount
            })
        }else{
            this.setState({
                loadText: '没有更多数据了哦'
            })
        }
    }

    loadMore = async () => {
        pages++
        if(pages <= this.state.pageTotle){
            const res = await server.consumeInfo(this.state.bidCode + '/investments', {
                data: {
                    bidCode: this.state.bidCode,
                    currentPage: pages,
                    pageSize: 10
                }
            })
            this.setState({
                recodeList: this.state.recodeList.concat(res.items)
            })
        } else{
            this.setState({
                loadText: '没有更多数据了哦'
            })
        }
    }
    render() {
        return (
            <div>
                <Header title='投标记录' />
                <div className="tables">
                    <div className="nw1e">
                        <table className="xui-table" data-theme="002" data-sz="">
                            <thead>
                                <tr className="al">
                                    <th>投标人</th>
                                    <th>投标金额</th>
                                    <th>状态</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.recodeList.map((stat)=> {

                                        var tenderTime = stat.tenderDate

                                        var baseTime =  new Date(tenderTime).toLocaleString()

                                        var rectTime = baseTime.substr(0,11)

                                        var ractMoney = (stat.tenderAmount).toFixed(2)
                                        var changeMoney = ractMoney.split('.');
                                        changeMoney[0] = changeMoney[0].replace(/(\d)(?=(\d{3})+$)/g,'$1,');
                                        changeMoney = changeMoney.join('.');
                                        return (
                                            <tr key={stat.ROW_ID}>
                                                <td>{stat.borrower}</td>
                                                <td>{changeMoney}</td>
                                                <td>{stat.status.message}</td>
                                            </tr>
                                        )
                                    })
                                }
                            </tbody>
                        </table>
                        <p className="loadmore" onClick={ this.loadMore }>{ this.state.loadText }</p>
                    </div>
                </div>
             </div>
        )
    }
}

export default TanderRecode
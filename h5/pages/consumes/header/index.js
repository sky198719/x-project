import React, { Component } from 'react'
import Head from 'next/head'

class Header extends Component {
    constructor() {
        super()
        this.state = {
            menuList:[
                {
                    id:1,
                    menu:'散标直投'
                },{
                    id:2,
                    menu:'债权转让'
                },{
                    id:3,
                    menu:'新宜贷'
                },{
                    id:4,
                    menu:'热门理财'
                }
            ]
        }
        this.goBack = ()=> {
            window.history.back()
        }

        this.showMenu = ()=> {
           if(this.props.icodown) {
               if(this.refs.mask.style.display == 'none'){
                    this.refs.mask.style.display = 'block'
                    this.refs.menuList.style.display = 'block'
                }else{
                    this.refs.mask.style.display = 'none'
                    this.refs.menuList.style.display = 'none'
                }
           }
        }
        this.filterMenu = (id)=> {
            switch(id) {
                case 1:
                    window.location.href = '/m/#!/static/html/borrow/borrowListV2.html?v=20170817'
                    break
                case 2:
                    window.location.href = '/m/#!/static/html/trade/tradeRequestListV2.html?v=20170817'
                    break
                case 3:
                    this.refs.mask.style.display = 'none'
                    this.refs.menuList.style.display = 'none'
                    break
                case 4:
                    window.location.href = '/m/#!/static/html/popular/financesList.html?v=20170817'
                    break
                default:
            }
        } 
    }
    componentDidMount() {
        this.refs.mask.style.display = 'none'
    }
    render() {
        const { title } = this.props

        const menuAll = this.state.menuList.map((item)=> {
            return (
                <li key={item.id} onClick={this.filterMenu.bind(this, item.id)}>{item.menu}</li>
            )
        })

        return (
            <div>
                <Head>
                    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui" />
                    <link rel='stylesheet' type='text/css' href="/static/mods/consumes/_.css" />
                </Head>
                <div className="tobar ml ban_move" style={{position: 'fixed'}}>
                    <div className="tonav nw1e">
                        <em className="co-main lm"><span onClick={ this.goBack }><b className="icon icon-left"></b>返回</span></em>
                        <div className="page_title xui-menu ll nl1e" data-theme="down-ac" data-pos="bottom">
                            <p onClick={ this.showMenu }>{title}<i className={this.props.icodown ? 'icon icon-down' : 'hide'}></i></p>
                            <ul className="this-menu ls" ref="menuList" style={{display: 'none'}}>
                                {menuAll}
                            </ul>
                        </div>
                    </div>
                </div>
                <div className="dim" ref="mask" onClick={this.showMenu}></div>
            </div>
        )
    }
}


export default Header
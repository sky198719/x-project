import React, { Component } from 'react'
import Head from 'next/head'

class Header extends Component {
    constructor() {
        super()
        this.goBack = ()=> {
            window.history.back()
        }
    }
    render() {
        const { title } = this.props
        return (
            <div>
                <Head>
                    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui" />
                    <link rel='stylesheet' type='text/css' href="/static/mods/newbid/_.css" />
                </Head>
                <div className="tobar ml ban_move" style={{position: 'fixed'}}>
                    <div className="tonav nw1e">
                        <em className="co-main lm"><span onClick={ this.goBack }><b className="icon icon-left"></b>返回</span></em>
                        <div className="page_title xui-menu ll">
                            <p>{title}</p>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}


export default Header
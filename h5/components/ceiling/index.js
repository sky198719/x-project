import React, { Component } from 'react';
import bridge from "../../static/merge/xxd-jsBridge.esm";
class ceiling extends Component {
    componentDidMount () {
        console.log ("... ok ...");
    }
    fnOpenApp = () =>{
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


    render() {
        var me = this;
        return (
            <div className="home-header header">
                <div className="logo-wrapper">
                    <div className="logo">
                        <img src="/static/html/home/imgs/app-logo@3x.png" alt="app-logo" />
                    </div>
                    <div className="header-tit">
                        <p className="app-name">新新贷金融</p>
                        <p className="app-slogan">以信相贷，用新服务</p>
                    </div>
                </div>
                <div className="header-right">
                    <button className="open-app-btn dmp-click" dev_id="A2.1-1" eventtype="jump" onClick={this.fnOpenApp}>打开App</button>
                </div>
            </div>
        )
    }
}
export default ceiling;
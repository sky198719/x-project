import React, { Component } from 'react'
import bridge from "../../../static/merge/xxd-jsBridge.esm"

export default class extends Component {
    constructor(props) {
        super(props)
        this.state = {
            shows: false
        }
    }
    async componentDidMount() {
        console.log(this.props.show)
        this.setState({
            shows: this.props.show
        })
    }
    close = (e) => {
        e.currentTarget.parentElement.parentElement.parentElement.className = 'purchased-alert position-a'
        // this.setState({
        //     show: false
        // })
    }
    goApp = () => {
        bridge.open({
            pagename: 'home'
        } , ()=>{
            var ua  = bridge.ua;
            if(ua.isWeChat()) {
                alert("微信里面无法唤醒APP");
            }
            location = "http://m.xinxindai.com/m/static/html/download/app.html?model=auto";
        })
    }
    render() {
        return (
            <div className={ this.props.show ? 'purchased-alert position-a block' : 'purchased-alert position-a' }>
                <div className="alert-content-wrapper">
                    <div className="alert-content">
                        <div className="close-btn" onClick={ this.close }></div>
                        <div className="alert-bg"></div>
                        <p>新手专享，更多优质产品等您临幸!</p>
                    </div>
                    <div className="div-btn"><button className="xxd-xl-btn" onClick={ this.goApp }>立即前往_APP</button></div>
                </div>
            </div>
        )
    }
}
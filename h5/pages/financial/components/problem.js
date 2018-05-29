import React, { Component } from 'react'

export default class extends Component {
    constructor(props) {
        super(props)
        this.state = {
            isShow: false
        }
    }
    change = () => {
        this.setState({
            isShow: !this.state.isShow
        })
    }
    joinRecord = () => {
        if(this.props.type == 'thirty'){
            location = '/financial/joinrecord?id=' + this.props.proId + '&type=10'
        }else{
            location = '/financial/joinrecordnew?id=' + this.props.proId
        }
    }
    render() {
        return (
            <ul className="main-list xxd-common-list">
                <li className="list-item faq-item">
                    <p onClick={ this.change } className="dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.12.1' : 'A8.1-3' } eventtype={ this.state.isShow ? 'close_float_window' : 'open_float_window' }>
                        <span className="item-txt dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.12.1' : 'A8.1-3' } eventtype={ this.state.isShow ? 'close_float_window' : 'open_float_window' }>常见问题</span>
                        <span className="arrow-down-icon dmp-click" className={ this.state.isShow ? 'arrow-down-icon up-icon' : 'arrow-down-icon' }  dev_id={ this.props.type == 'thirty' ? 'A8.2-3.12.1' : 'A8.1-3' } eventtype={ this.state.isShow ? 'close_float_window' : 'open_float_window' }></span>
                    </p>
                    {
                        this.props.type == 'thirty' ? 
                        <ul className="content" className={ this.state.isShow ? 'content show' : 'content' }>
                        <li>
                            <p className="question">1. _（新手专享）安全吗？</p>
                            <div className="answer">
                            本产品是新用户专享的一种福利。同_计划一样，匹配_平台优质的借款标的，均经过严格的标准筛选，同享安全保障。
                            </div>
                        </li>
                        <li>
                            <p className="question">2. 我为什么无法出借_（新手专享）？</p>
                            <div className="answer">
                            _（新手专享）仅针对未在_平台出借过的新用户，且每位用户只能参与一次。
                            </div>
                        </li>
                        <li>
                            <p className="question">3. _（新手专享）产品可以使用红包吗？</p>
                            <div className="answer">
                            不可以。红包仅限用于_等普通产品，请详见红包使用说明。
                            </div>
                        </li>
                        <li>
                            <p className="question">4. _（新手专享）可以提前退出吗？</p>
                            <div className="answer">
                            不可以，锁定期限内不支持提前退出。
                            </div>
                        </li>
                        <li>
                            <p className="question">5. _（新手专享）什么时候回款？</p>
                            <div className="answer">
                            本产品在锁定期结束后，次日一次性还本付息，到时可至您的银行存管账户查询。
                            </div>
                        </li>
                    </ul> : 
                    <ul className="content" className={ this.state.isShow ? 'content show' : 'content' }>
                        <li>
                            <p className="question">1、我出借了新手标1个月，还能投新手标3个月吗？</p>
                            <div className="answer">
                                不能，新手标每个账户限购1次。
                            </div>
                        </li>
                        <li>
                            <p className="question">2、出借新手标可以使用新手红包吗？</p>
                            <div className="answer">
                                不可以，新手红包仅限在出借_、_产品及散标直投产品（票据贷除外）可用。
                            </div>
                        </li>
                        <li>
                            <p className="question">3、出借新手标锁定期内可以退出吗？</p>
                            <div className="answer">
                                不可以，新手标1个月和3个月，锁定期限内均不支持提前退出。
                            </div>
                        </li>
                        <li>
                            <p className="question">4、新手标安全吗？</p>
                            <div className="answer">
                                _以严谨负责的态度对每笔借款进行严格筛选。
                            </div>
                        </li>
                    </ul>
                    }
                    
                </li>
                <li className="list-item dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.13.1' : 'A8.1-4' } eventtype="jump" onClick={ () => location = '/financial/credit' }>
                    <span className="item-txt dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.13.1' : 'A8.1-4' } eventtype="jump">债权列表</span>
                    <span className="arrow-right-icon dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.13.1' : 'A8.1-4' } eventtype="jump"></span>
                </li>
                <li className="list-item dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.14.1' : 'A8.1-5' } eventtype="jump" onClick={ this.joinRecord }>
                    <span className="item-txt dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.14.1' : 'A8.1-5' } eventtype="jump">加入记录</span>
                    <span className="item-right-txt dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.14.1' : 'A8.1-5' } eventtype="jump">{ this.props.joinNum }人</span>
                    <span className="arrow-right-icon dmp-click" dev_id={ this.props.type == 'thirty' ? 'A8.2-3.14.1' : 'A8.1-5' } eventtype="jump"></span>
                </li>
            </ul>
        )
    }
}
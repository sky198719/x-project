import React, { Component } from 'react'

import Header from './header/index'

import server from './api/index'

import { getLocalItem } from '../../common/Util'

class TanderRecode extends Component {
    constructor() {
        super()
        this.state = {
            content: ''
        }
        this.goBack = ()=> {
            window.history.back()
        }
    }

    async componentDidMount() {
        const paramType = this.props.url.query.typeId

        if(paramType == 'safe_type'){
            this.typeTxt = 'REPAYMENT_GUARANTEE'
        }else{
            this.typeTxt = 'PROJECT_RISK'
        }

        const res = await server.consumeStatic(this.typeTxt)
        this.setState({content: res.content})

    }

    render() {
        const titleName = this.props.url.query.typeId == 'safe_type'? '还款保障措施':'项目风险提示'
        return (
            <div>
                <Header title={titleName} />
                <div>
                    <div className="static-class" dangerouslySetInnerHTML={{__html: `${this.state.content}`}} />
                </div>
            </div>
        )
    }
}

export default TanderRecode
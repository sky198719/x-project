import React, { Component } from 'react'
import Header from './header/index'
import server from './api/index'

class Contract extends Component {
    constructor(props) {
        super(props)
        this.state = {
            bidCode: '',
            article: ''
        }
    }

    componentWillMount() {
        this.setState({bidCode: this.props.url.query.bidCode})
    }
    async componentDidMount() {
        const that = this
        const res = await server.consumeInfo(this.state.bidCode, {})
        that.setState({
            article: res.contractModelURL
        })
    }
    render() {
        return (
            <div>
                <Header title="承诺书" />
                <div className="contract-class" dangerouslySetInnerHTML={{__html: `${this.state.article}`}} />
            </div>
        )
    }
}

export default Contract
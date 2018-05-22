import React, { Component } from 'react'

import Router from 'next/router'

import Header from './header/index'

import ProImport from './components/TanderImport'

import ProPerson from './components/TanderPerson'

import ProOther from './components/TanderOther'

import server from './api/index'

class TanderDetail extends Component {
    constructor() {
        super()

        this.state = {
            bidCode: '',
            bidDetail: '',
            bidUser: '',
            loadRecord: ''
        }

        this.visibile = (e)=> {
            
            const parentClass = e.currentTarget.parentNode
            const nextElement = e.currentTarget.nextSibling

            if(parentClass.className.indexOf('open') != -1){
                parentClass.classList.remove('open')
                nextElement.style.display = 'none'
            }else{
                parentClass.classList.add('open')
                nextElement.style.display = ''
            }
        }

        this.safeGuard = (e)=> {
            Router.push({pathname: '/consumes/static', query: {typeId: e.currentTarget.id}})
        }

        this.toRecord = ()=> {
            Router.push({pathname: '/consumes/record', query: {typeId: this.props.url.query.bidCode}})
        }

    }

    componentWillMount() {
        this.setState({bidCode: this.props.url.query.bidCode})
    }

    //  查询标的基本信息
    bidDetail = async () => {
        const res = await server.consumeInfo(this.state.bidCode, {})
        this.setState({bidDetail: res})
    }

    //  查看借款人详情
    bidUser = async () => {
        const res = await server.consumeInfo(this.state.bidCode + '/borrower', {
            data: {}
        })
        this.setState({bidUser: res})
    }

    //  贷款记录
    loadRecord = async () => {
        const res = await server.consumeInfo(this.state.bidCode + '/loans', {
            data: {
                currentPage:1,
                pageSize:10
            }
        })
        this.setState({loadRecord: res})
    }


    async componentDidMount() {
        await this.bidDetail()
        await this.bidUser()
        await this.loadRecord()
    }
    

    render() {
        if(!this.state.bidCode){
            return (
                <div>
                    <Header title="标的详情" />
                </div>
            )
        }
        return (
            <div>
            <Header title="标的详情" />
            <div id="view1" className="view" style={{
                overflow: 'auto'
            }}>
                <ProImport data={ this.state.bidDetail } />
                <ProPerson methods={ this.visibile } data={ this.state.bidUser } loadRecord={ this.state.loadRecord } />
                <ProOther  
                    methods={ this.visibile } 
                    detailData = { this.state.bidDetail } 
                    safeGuard = { this.safeGuard }
                    toRecord = { this.toRecord } />
            </div>
        </div>
        )
    }
}


export default TanderDetail
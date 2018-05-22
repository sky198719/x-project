import React, { Component } from 'react'
import { bindActionCreators } from 'redux'
import withRedux from 'next-redux-wrapper'
import { initStore, getAllData, getPageTotle, clearData } from '../../store'
import Head from 'next/head';
import Router from 'next/router'
import { PullToRefresh, ListView, Button, Modal } from 'antd-mobile'
import Header from './header/index'
import { localItem , removeLocalItem, getQueryString } from '../../common/Util'
import server from './api/index'
let pageIndex = 1
// 滚动到记录的位置方法
const returnTop = (con)=> {
    if (localItem('scrollPosition')) {
        if (!con.refs.lv) return;
        try {
            con.refs.lv.listviewRef.ListViewRef.ScrollViewRef.scrollTop = localItem('scrollPosition');
        }
        catch (e) {
            // console.log(e)
        }
        removeLocalItem('scrollPosition');
    }
}

class Home extends Component {
  constructor(props) {
    super(props);
    const dataSource = new ListView.DataSource({
      rowHasChanged: (row1, row2) => row1 !== row2,
    });

    this.state = {
      dataSource,
      refreshing: false,
      isLoading: true,
      pageTotle: 0,
      useBodyScroll: false,
      modal: false
    }; 
    this.goingDetail = this.goingDetail.bind(this)
  }

  componentWillMount() {
        // if(this.props.url.query.refresh){
        //     Router.push({pathname: '/consumes'})
        // }
        if(pageIndex == this.props.pages){
            this.setState({
                isLoading: false,
                hasMore: false
            })
        }
    }
  async componentDidMount() {

    if(this.props.dataList.length == 0){
        const res = await server.consumeList(pageIndex)
        const pagesTotle = Math.ceil(res.totalCount / res.pageSize)
        this.props.getPageTotle(pagesTotle)
        this.props.getAllData(res.items)
        this.setState({
            isLoading: false,
            refreshing: false,
            pageTotle: pagesTotle
        })
    }else{
        if(pageIndex >= this.props.pages){
            this.setState({
                isLoading: false,
                hasMore: false
            })
        }else{
            this.setState({
                isLoading: false,
                hasMore: true
            })
        }
    }
    returnTop(this);
  }
   //返回记录滚动位置三件套2-针对浏览器返回按钮情况：
   componentDidUpdate() {
    returnTop(this);
  }
  //返回记录滚动位置三件套3-记录离开时的滚动条位置：
  componentWillUnmount() {
    localItem('scrollPosition', this.refs.lv.listviewRef.scrollProperties.offset);
  }

  onRefresh = async () => {
    this.setState({ refreshing: true, isLoading: true });
    // simulate initial Ajax
    pageIndex = 1
    setTimeout( async()=> {
        const res = await server.consumeList(pageIndex)
        const pagesTotle = Math.ceil(res.totalCount / res.pageSize)
        await this.props.getPageTotle(pagesTotle)
        await this.props.clearData()
        await this.props.getAllData(res.items)
        this.setState({
            hasMore: true,
            isLoading: false,
            refreshing:false,
        })
    },600)
     
  };

onEndReached  = async (event) => {
    if(pageIndex >= this.props.pages){
        this.setState({
            hasMore: false,
            isLoading: false 
        })
        return
    }
    if (this.state.isLoading && this.state.hasMore) {
      return;
    }
    
    this.setState({ isLoading: true });

    const res = await server.consumeList(++pageIndex)

    await this.props.getAllData(res.items)
    this.setState({
        hasMore: true,
        isLoading: false,
        refreshing:false,
    })
  }
  transMoney(val) {
    var changeMoney = 0
    if(val > 9999){
        var ractMoney = (val/1e4)
        ractMoney = ractMoney + ''
        if(ractMoney.indexOf('.') != -1){
            if(ractMoney.split(".")[1].length == 1){
                ractMoney = ractMoney + 0.00 + '万'
            }else{
                ractMoney = ractMoney.substr(0, ractMoney.indexOf('.') + 3) + '万'
            }
        }else{
            ractMoney = Number(ractMoney).toFixed(2) + '万'
        }
        changeMoney = ractMoney.split('.');
        changeMoney[0] = changeMoney[0].replace(/(\d)(?=(\d{3})+$)/g,'$1,');
        changeMoney = changeMoney.join('.');
    }else{
        val = val + ''
        if(val.indexOf('.') != -1){
            if(val.split(".")[1].length == 1){
                //51000
                changeMoney = val + 0.00 + '元'
            }else{
                //53550.21
                changeMoney = val.substr(0, val.indexOf('.') + 3) + '元'
            }
        }else{
            //50000
            changeMoney = Number(val).toFixed(2) + '元'
        }
    }

    return changeMoney
  }
  goingDetail = (id) => async () => {
        const res = await server.isLogin()
        if(res){
            Router.push({pathname: '/consumes/detail', query: {bidCode:id}})
        }else{
            this.setState({
                modal: true
            })
        }
  }
  onClose = key => () => {
    this.setState({
      [key]: false,
    });
    window.location.href = "/m?isShowLogin=true"
  }

  render() {
    if(!this.props.dataList.length){
        return (
            <div>
                <Header title="新宜贷" icodown="true"/>
            </div>
        )
    }
    const separator = (sectionID, rowID) => (
        <div key={`${sectionID}-${rowID}`}
          style={{
            backgroundColor: '#F5F5F9',
            height: 10,
            borderTop: '1px solid #e1e1e1',
            borderBottom: '1px solid #e1e1e1',
          }}
        />
    )
    const row = (rowData, sectionID, rowID) => {
        const { history, percentage } = this.props
        const percentageTotle = parseInt(100 - ((rowData.leftTenderAmount / rowData.bidAmount) * 100)) + '%'
        
        return (
            <div className="view" key={rowID}>
                <ul id="consumerloans-list" className="xui-list-back marginnone">
                    <li className={rowData.status.code == 'BIDDING' ? '' : 'stoped'}  onClick={this.goingDetail(rowData.bidCode)}>
                        <h3 className="ew ew-fix">
                            <b className="this-title"><i className="xui-bubble" data-theme="default" data-co="main">宜</i>{rowData.bidName}</b>
                            <span className="surplus-a ml ar co-back">剩余可投<span>{this.transMoney(rowData.leftTenderAmount)}</span></span>
                        </h3>
                        <ul className="progress-bar ew nh8x">
                            <li className="col-10"><span className="xui-progress-bar" data-theme="default" data-co="main"><b style={{width: percentageTotle }}></b></span></li>
                            <li className="nl1e nr1x"><span className="xui-bubble" data-theme="arrow" data-pos="left" data-co="main">{percentageTotle}</span></li>
                        </ul>
                        <ul className="this-info ew ew-fix w100 vb-z">
                            <li>
                                <h1 className="co-danger h2e5"><em className="s2e">{rowData.plannedAnnualRate}</em>%</h1>
                                <p className="co-back">年化收益</p>
                            </li>
                            <li>
                                <h1 className="h2e5 vb">{rowData.leastPeriodValue } { rowData.leastPeriodType == 'MONTH' ? '个月' : '' } { rowData.leastPeriodType == 'YEAR' ? '年' : '' } { rowData.leastPeriodType == 'DAY' ? '天' : '' }</h1>
                                <p className="co-back">期限</p>
                            </li>
                            <li className="ar">
                                <button className="tobuy xui-btn lm mb2x" data-co="warn" data-sz="s">立即加入</button>
                                <div className="surplus-b al">
                                    <h1 className="h2e5 vb">0.00元</h1>
                                    <p className="co-back">剩余可投</p>
                                </div>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        );
    };
    return ( 
        <div>
            <Header title="新宜贷" icodown="true" />  
            <ListView
                ref="lv"
                dataSource={this.state.dataSource.cloneWithRows(this.props.dataList)}
                renderFooter={() => (<div style={{ padding: 10, textAlign: 'center' }}>
                {this.state.isLoading ? '加载中...' : '加载完成'}
                </div>)}    
                renderRow={row}  
                renderSeparator={separator}
                initialListSize={this.props.dataList.length}
                style={{
                height: (document.documentElement.clientHeight - 48) + 'px',
                overflow: 'auto',
                }}
                pullToRefresh={<PullToRefresh
                refreshing={this.state.refreshing}
                onRefresh={this.onRefresh}
                />} 
                onEndReached={this.onEndReached}
                onEndReachedThreshold={1000}
                pageSize={10} 
                scrollRenderAheadDistance={2000}
                scrollEventThrottle={30}
            /> 
            <Modal 
                visible={this.state.modal}
                transparent
                maskClosable={false}
                onClose={this.onClose('modal')}
                title="提示"
                footer={[{ text: '确定', onPress: () => { console.log('ok'); this.onClose('modal')(); } }]}
            >
                <div style={{ overflow: 'scroll' }}>
                    请先登录
                </div>
            </Modal>
    </div>);
  }
}

const mapStateToProps = ({ pages, dataList  }) => ({ pages, dataList })

const mapDispatchToProps = (dispatch) => {
  return {
    getAllData: bindActionCreators(getAllData, dispatch),
    getPageTotle: bindActionCreators(getPageTotle, dispatch),
    clearData: bindActionCreators(clearData, dispatch)
  }
}

export default withRedux(initStore, mapStateToProps, mapDispatchToProps)(Home)
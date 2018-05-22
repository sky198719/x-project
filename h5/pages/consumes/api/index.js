import { cFetch } from '../../../common/Promise'
import urlPath from './url'
import { getCookie, delCookie } from '../../../common/Util'
import { Toast } from 'antd-mobile'


//  验证登陆
const isLogin = async ()=> {
    const res = await cFetch(urlPath.beInLogging,{
        data: {}
    })
    if(res.code !== '200000'){
        delCookie('XXD_webapp_SESSIONID')
        delCookie('userToken')
        return false
    }else{
        return true
    }
}
//  散标列表
const consumeList = async (index)=> {
    const res = await cFetch(urlPath.consumeList, {
        data:{
            keyType: 3,
            keyValue: '',
            status: '["BIDDING","REPAYING","SATISFIED_BID","REPAY_OVER"]',
            productCategory: 'P001',
            pageSize: 10,
            currentPage: index
        }
    })
    if(res.code == '200000'){
        return res.data
    }
}
//  散标详情
const consumeInfo = async (code, json)=> {
    const res = await cFetch(urlPath.consumeList + '/' + code, json)
    if(res.code == '200000'){
        return res.data
    }
}
//  散标详情静态资源
const consumeStatic = async (code)=> {
    const res = await cFetch(urlPath.consumeStatic, {
        data: {
            source: 'PC',
            typeKey: code
        }
    })
    if(res.code == '200000'){
        return res.data
    }
}

//  散标详情获取用户资产信息
const consumeOverview = async (code)=> {
    const res = await cFetch(urlPath.overview, {
        data:{}
    })
    if(res.code == '200000'){
        return res.data
    }
}

//  散标支付密码验证
const validatePayPwdByToken = async (code)=> {
    const res = await cFetch(urlPath.validatePayPwdByToken, {
        type: 'POST',
        data: {
            data:{
                payPassword: code
            }
        }
    })
    return res
}

//  散标支付接口
const investOrder = async (code, money)=> {
    const res = await cFetch(urlPath.investOrder, {
        type: 'PUT',
        data: {
            data:{
                productId: code,
                tenderAmount: money,
                "productCategory": 1,
                "productType": 20,
                "redEnvelopeCode": "",
            }
        }
    })
    if(res.code == '200000'){
        return res.data
    }
}

export default { consumeList, consumeInfo, consumeStatic, consumeOverview, validatePayPwdByToken, investOrder, isLogin } 
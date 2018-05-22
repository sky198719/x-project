import { cFetch } from '../../common/Promise'
import { Toast, Modal } from 'antd-mobile'
import { setTimeout } from 'timers';
import urlPath from './url'
import fetch from 'isomorphic-fetch'
import md5 from 'md5'

// getFormToken: '/feapi/users/formToken',//获取formToken
// getImgCode: '/feapi/randCode/createVerifyCode',//获取图片验证码
// getMsgCode: '/feapi/users/sendSMS',//获取短信验证码



//  轮播
const prizesCarousel = async (context) => {
    // debugger
    const res = await cFetch(`${urlPath}/apih5/api/app/invitation/invitation-reward-show`, {
        data: {
        },
        isServerTime: false
    })
    if(res.code == '200000'){
        console.log(res,'@@@')
        return res
    }else{
        Toast.info(res.message, 2)
    }
}
// 邀请链接二维码 
const inviteLink = async (context) => {
    const res = await cFetch(`${urlPath}/apih5/api/app/invitation/generate-link`, {
        data: {},
        isServerTime: false
    })
    if(res.code == '200000'){
        console.log(res,'@@@')
        return res
    }else{
        Toast.info(res.message, 2)
    }
}
// 查询邀请好友总数及获取奖励总额（包括新新币）
const inviteBrief = async (context) => {
    const res = await cFetch(`${urlPath}/apih5/api/app/invitation/invitation-brief`, {
        data: {},
        isServerTime: false
    })
    if(res.code == '200000'){
        console.log(res,'邀请概括')
        return res
    }else{
        Toast.info(res.message, 2)
    }
}
// 查询邀请好友记录
const inviteRecord = async (context) => {
    const res = await cFetch(`${urlPath}/apih5/api/app/invitation/invitation-detail`, {
        data: {
            pages:context.page,
            pageSize:context.pageSize
        },
        isServerTime: false
    })
    if(res.code == '200000'){
        console.log(res,'邀请好友记录')
        return res
    }else{
        Toast.info(res.message, 2)
    }
}
// 查询邀请好友奖励记录
const inviteReward = async (context) => {
    const res = await cFetch(`${urlPath}/apih5/api/app/invitation/invitation-reward`, {
        data: {
            pages:context.page,
            pageSize:context.pageSize
        },
        isServerTime: false
    })
    if(res.code == '200000'){
        console.log(res,'邀请概括')
        return res
    }else{
        Toast.info(res.message, 2)
    }
}

//保存用户手机号
const savePhone = async (context) => {
    let temData = {
        mobile:context.phone,
        identity:context.identity
    }
    const res = await cFetch(`${urlPath}/apih5/api/app/invitation/save-invited-info`, {
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: temData,
        isServerTime: false
    })
    if(res.code == '200000'){
        console.log(res,'邀请概括')
        return res
    }else{
        Toast.info(res.message, 2)
    }
}

//提交注册表单
const userRegister = async (context) => {
    Toast.loading('请稍后……', 0)
    const res = await cFetch(`${urlPath}/apih5/api/users/register`, {
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: {
            password: context.password,
            phone: context.phone,
            smsCode: context.smsCode,
            identity: context.identity
        },
        isServerTime: false
    })
    if(res.code == '200000'){
        Toast.hide()
        return res.data
    }else{
        Toast.hide()
        setTimeout(()=>{
            Toast.info(res.message, 2)
        }, 200)
    }
}
let token = "POuPAdvKCNrawHVgF283DBgqLrbun3la8zPVDrGas_F2m8HZcNM_wMmlS08ALuBRTAr-HFKakOOY6f2draqxtCk5F5vi0QBTzWbPMXej-tA"
// 获取活动时间
const getActivityTime = async (context) => {
    let param = {
            method : 'GET',
            headers:{
                // "token": getCookie('userToken') || '',
                "token": token,
                "Accept": "application/json",
                "clientTime": new Date().getTime(),
                "s":md5(`xxdTest${new Date().getTime()}defaultKey`),
                "contentType": 'application/x-www-form-urlencoded',
                "clientId" : "xxdTest"
            },
            credentials: 'include'
    }
    const res = await fetch(`${urlPath}/configurationAPI/sysconfig?key=INVITATE_ACTIVE_TIME`, param)
            return res.json()
}


// 获取企业数据
const getCompanyData = async (context) => {
    let param = {
            method : 'GET',
            headers:{
                // "token": getCookie('userToken') || '',
                "token": token,
                "Accept": "application/json",
                "s":'wwww',
                "clientId": "001",
                "clientTime": '001',
                "contentType": 'application/x-www-form-urlencoded',
            },
            credentials: 'include'
    }
    const res = await fetch(`${urlPath}/biz/bulletin/operationData`, param)
            return res.json()
}
// 新元宝新手专享
const xszx = async (context) => {
    let param = {
            method : 'GET',
            headers:{
                // "token": getCookie('userToken') || '',
                "token": token,
                "Accept": "application/json",
                "s":'wwww',
                "clientId": "001",
                "clientTime": '001',
                "contentType": 'application/x-www-form-urlencoded',
            },
            credentials: 'include'
    }
    const res = await fetch(`${urlPath}/m/product/getNewProduct.do?pCode=XSCP30T`, param)
            return res.json()
}
// 月进斗金
const yjdj = async (context) => {
    let param = {
            method : 'GET',
            headers:{
                // "token": getCookie('userToken') || '',
                "token": token,
                "Accept": "application/json",
                "s":'wwww',
                "clientId": "001",
                "clientTime": '001',
                "contentType": 'application/x-www-form-urlencoded',
            },
            credentials: 'include'
    }
    const res = await fetch(`${urlPath}/m/product/getNewProduct.do?pCode=YJDJ`, param)
            return res.json()
}
// 为了获取新手专享的年利率,而调用homes接口
const getNewUserRate = async (context) => {
    const res = await cFetch(`${urlPath}/apih5/api/homes`, {
        data: {},
        isServerTime: false
    })
    if(res.code == '200000'){
        return res
    }else{
        Toast.info(res.message, 2)
    }
}
// 新元宝利率
const getXybRate = async (context) => {
    const res = await cFetch(`${urlPath}/apih5/api/finances/max-apr-xyb`, {
        data: {},
        isServerTime: false
    })
    if(res.code == '200000'){
        return res
    }else{
        Toast.info(res.message, 2)
    }
}
export default {
    inviteLink,
    inviteBrief,
    prizesCarousel,
    inviteRecord,
    inviteReward,
    savePhone,
    getActivityTime,
    getCompanyData,
    xszx,
    yjdj,
    getNewUserRate,
    getXybRate
}
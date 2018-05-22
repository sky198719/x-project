var devHttp = 'http://localhost:3000/'
if(process.env.NODE_ENV != 'development'){
    devHttp = '/'
}
const urlParams = {
    beInLogging: 'userCenter/user/beInLogging',                                        //..验证登陆
    consumeList: 'integrationPlatform/bids',                                           //..新宜贷散标列表
    consumeStatic: 'integrationPlatform/bids/staticResources/P001',                    //..新宜贷静态资源
    overview: 'investmentAPI/asset/overview',                                          //..新宜贷支付-获取用户资产信息 
    validatePayPwdByToken: 'userCenter/user/validatePayPwdByToken',                    //..新宜贷密码验证  
    investOrder: 'tradeCenter/InvestOrder',                                            //..新宜贷支付接口
}
const repeatUrl = {}
for(let i in urlParams){
    urlParams[i] = devHttp + urlParams[i]
}
export default urlParams
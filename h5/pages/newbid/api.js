var devHttp = 'http://localhost:3000/'
if(process.env.NODE_ENV != 'development'){
    devHttp = '/'
}
const urlParams = {
    investProduct: 'tradeCenter/investProduct/XSB/mine',                       //..新手标1-3个月详情（已登陆）
    investmentRecord: 'tradeCenter/investProduct/XSB/investmentRecord',       //..新手标加入记录
    isOpenAccount: 'userCenter/user/userInfoByToken',                          //..判断用户是否开通存管账户
    validatePayPwdByToken: 'userCenter/user/validatePayPwdByToken',            //..验证支付密码
    InvestOrder: 'tradeCenter/InvestOrder',                                    //..支付接口
    xsbJoinRecord: 'tradeCenter/investBiz/myInvestmentProductsByToken',
    agreement: 'tradeCenter/investBiz/agreement'
}
// const repeatUrl = {}
for(let i in urlParams){
    urlParams[i] = devHttp + urlParams[i]
}
export default urlParams


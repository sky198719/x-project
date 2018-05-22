import React, { Component } from 'react'
import Head from 'next/head'
import Header from '../../components/header/index'
import HomeIco from './components/homeico'
export default class extends Component {
    render() {
        return (
            <div>
                <Head>
                    <link rel='stylesheet' type='text/css' href="/static/mods/financial/_.css" />
                </Head>
                <div className="hint-box position-a">
                    <Header title="风险提示函" />
                    <div className="hint-container position-a">
                        <p className="center bold">资金出借风险提示函</p>
                        <p>出借人应认真阅读《新新贷网站服务协议》、资金出借相关协议（《借款合同》、《债权转让协议》等）、本函内容及本网站（www.xinxindai.com）关于资金出借、资费介绍、标的说明等操作规则，充分了解在本网站上出借资金的法律意义及相关风险，并根据自身的出借经验、出借目的、出借期限、自身资产状况等判断所选择的借款标的是否与自身的风险承受能力相当。</p>
                        <p>出借人的资金在出借过程中可能面临各种风险，包括但不限于市场风险、信用风险、利率风险、流动性风险以及战争、自然灾害等不可抗力导致的出借资金损失。</p>
                        <p>主要风险说明如下：</p>
                        <p className="bold">一、政策风险</p>
                        <p>国家宏观政策、财政政策、货币政策、行业政策、地区发展政策的变动可能会对出借方执行产生不利影响，对此新新贷（上海）金融信息服务有限公司不承担责任。</p>
                        <p className="bold">二、借款方信用风险</p>
                        <p>当借款方因突发事件或其他不可预见的事件，导致短期或者长期丧失还款能力 (包括但不限于借款人收入情况、财产状况发生变化、人身出现意外、发生疾病、死亡等情况)，或者借款人的还款意愿发生变化时，出借人的资金存在无法按时回收之风险。</p>
                        <p className="bold">三、资金流动性风险</p>
                        <p>出借人按照约定将资金出借给借款人使用，在借款人不主动提前还款的情况下，借款人将按照约定的期限分期偿还出借人的本金和利息，出借人的出借资金将分期回收，因此资金回收需要一定的周期；</p>
                        <p><span className="bold indent">若出借人需要于当期债权未到期时提前回收（至少持满一定期限后方可提前回收，以具体出借产品下标注的持有期限为准）出借资金的，应当以债权转让方式向第三人转让剩余债权。</span>本网站将在出借人提出需要以及其他对出借人有利的时机，帮助出借人寻找、向出借人推荐愿意受让出借人债权资产的第三方。</p>
                        <p className="bold indent">出借人应当知晓在匹配债权受让人时，存在无法按其需求的时间或期限匹配到债权受让人的资金流动性风险。一旦发生风险，在完成本次债权转让匹配前，出借人仍将按照约定的利息持有该债权，直至债权期满或债权转让成功。</p>
                        <p className="bold">四、不可抗力的风险</p>
                        <p>由于战争、动乱、罢工、自然灾害等不可抗力因素的出现，可能导致出借人的出借资金受到损失，对此新新贷（上海）金融信息服务有限公司不承担责任。</p>
                        <p className="bold">五、其他风险</p>
                        <p>本风险提示函的揭示事项仅为列举性质，未能详尽列明出借人所面临的全部风险和可能导致出借人资产损失的所有因素。</p>
                        <p>出借人在出借资金前，应认真阅读并理解相关业务规则、标的说明书、网站服务协议、电子借款合同及本风险提示函的全部内容，并确信自身已做好足够的风险评估与财务安排，避免因出借资金而遭受难以承受的损失。</p>
                        <p>注：本函中 “出借人”是指在本网站注册，并以其自有合法资金通过本网站提供的信息服务获取收益的用户，包括网站各类借款标的投标人、债权受让人等。</p>
                         
                        <p className="center bold">出借人承诺</p>
                        <p>新新贷（上海）金融信息服务有限公司：</p>
                        <p>本人已在贵司运营的新新贷平台（www.xinxindai.com）注册并有意实际出借自有资金。现本人基于出借行为作出承诺如下：</p>
                        <p>一、本人系完全民事行为能力人。</p>
                        <p>二、本人承诺本人资金来源合法。</p>
                        <p>三、本人承诺所提供的信息和材料全部真实、准确，若有任何不实之处，本人自愿承担所有不利后果。</p>
                        <p>四、本人已认真阅读《新新贷注册协议》、资金出借相关协议（《借款合同》、《债权转让协议》及新新贷网站关于资金出借、资费介绍、标的说明等操作规则），充分了解在贵网站上出借资金的法律意义及相关风险，并根据自身的出借经验、出借目的、出借期限、自身资产状况等判断所选择的借款标的是否与自身的风险承受能力相当,避免因出借资金而遭受难以承受的损失。</p>
                        <p className="bold indent">五、本人知晓并自愿承担出借方的资金在出借过程中可能面临各种风险，包括但不限于政策风险、借款方信用风险、资金流动性风险、不可抗力风险及其他风险而导致的出借资金损失。</p>
                        <p className="bold indent">六、本人知晓任何出借行为均存在出借风险，风险涵盖出借本金及利息等全部款项，本人作为出借人将自愿承担相应风险所导致的一切损失。</p>
                        <p>七、本承诺书自签订之日起单独成立并生效。本人通过新新贷平台签订《借款合同》、《服务协议》等一切文书是否生效、无效或存在效力瑕疵，均不影响本承诺书的效力。</p>
                        <p className="bold indent">特此承诺！</p>
                    </div>
                </div>
                <HomeIco />
            </div>
        )
    }
}
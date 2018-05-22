/**
 * Created by chaihuangqi on 2015/6/29.
 */
/**
 * data analysis
 */
define(['chart','js/utils/date','js/utils/animateNumber'], function (Chart,DateHandle,animateNumber) {
    var dataAnalysisCtrl = {
        init: function () {
            dataAnalysisCtrl.loadDataAnalysis();
        },
        loadDataAnalysis:function() {
            req.callGet({
                url: GC.getDataPath() + 'dataAnalysis.json?' + DateHandle.formatDate('yyyyMMdd',new Date()) ,
                dataType: 'json',
                indicator: true,
                success:function(result){
                    //$$("#totalDealAmount").html(result.totalDealAmount);
                    animateNumber.animate({
                        element:'#totalDealAmount',
                        to:result.totalDealAmount,
                        precision:2,
                        valueType:'money'
                    });
                    //$$("#totalProfit").html(result.totalProfit);
                    animateNumber.animate({
                        element:'#totalProfit',
                        to:result.totalProfit,
                        precision:2,
                        valueType:'money'
                    });
                   // $$("#benefitedBusiness").html(result.benefitedBusiness);
                    animateNumber.animate({
                        element:'#benefitedBusiness',
                        to:result.benefitedBusiness
                    });
                    dataAnalysisCtrl.chartA(result.quarterAmount);
                    dataAnalysisCtrl.chartB(result.riskReserve);
                }
            });
        },
        chartA: function (data) {
            var canvasA = document.getElementById("DataAnalysisA");
            canvasA.width = $(document.body).width();
            var ctxA = document.getElementById("DataAnalysisA").getContext("2d");
            new Chart(ctxA).Line(data);
        },
        chartB: function (data) {
            var canvasB = document.getElementById("DataAnalysisB");
            canvasB.width = $(document.body).width();
            var ctxB = document.getElementById("DataAnalysisB").getContext("2d");
            new Chart(ctxB).Bar(data);
        }
    };
    return dataAnalysisCtrl;
});

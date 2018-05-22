define(['js/utils/xxd_dmp'], function (xxd_dmp) {
    var router =
    {
        /**
         * Init router, that handle page events
         */
        init: function () {
            $$(document).on("pageBeforeInit", function (event) {
                try{xxd_dmp.init();}catch (e){}
                var page = event.detail.page;
                if (page.name != null) {
                    var path = page.query.path;
                    if (path == null || path == undefined) {
                        path = page.name;
                    }

                    if ("neglect" === path) {
                        return;
                    }

                    var directory = $$(page.container).attr("directory");

                    if (directory != null && directory != undefined) {
                        path = directory;
                    }
                    router.load(path, page.name, event);
                }
            });
        },
        /**
         * Load (or reload) controller from js code (another controller) - call it's init function
         * @param controllerName
            * @param query
            */
           load: function (path, controllerName, event) {
            require(['js/' + path + '/' + controllerName + 'Controller'], function (controller) {
                controller.init(event);

                //XXD_DMP布码
               try{

                   //GA基础代码、虚拟页面部署
                   var GAUserId = $$("body").attr("data-userId");
                   gaInits(GAUserId);

                       $(document).off("click", ".dmp-click").on("click", '.dmp-click', function () {
                       var $this = $(this),

                       //dmp新规则
                           eventType = $this.attr('eventType'),
                           dev_id = $this.attr('dev_id'),
                           target_id = $this.attr("target_id"),
                           textHref = $this.attr("textHref"),
                           dmp_text =  $this.attr("dmp_text"),
                           dmp_money_input = $(".dmp_content_input").val(),
                           dmp_money_attr =  $this.attr("dmp_money"),
                           dmp_redBag = $this.attr("dmp_redBag"),
                           dmp_money = "",
                           xxd_utm_source = xxd_dmp.getDmpUrlParam("xxd_utm_source") || "";
                           if(dmp_money_input){
                               dmp_money = dmp_money_input;
                           }else if(dmp_money_attr){
                               dmp_money = dmp_money_attr;
                           }
                       xxd_dmp.clickDmpEvent(dmp_data_obj("click",eventType,dev_id,target_id,textHref,dmp_text,dmp_money,dmp_redBag,xxd_utm_source));
                       function  dmp_data_obj(action,event,dev_id,target_id,textHref,dmp_text,dmp_money,dmp_redBag,xxd_utm_source) {
                           var dmp_data_obj = {};
                           dmp_data_obj.action = action;
                           dmp_data_obj.event = event;
                           dmp_data_obj.dev_id = dev_id;
                           dmp_data_obj.target_id = {};
                           if(target_id != null && target_id!= "" && target_id != undefined){
                               dmp_data_obj.target_id.id = target_id;
                           }
                           if(textHref != null && textHref!= "" && textHref != undefined){
                               dmp_data_obj.target_id.textHref = textHref;
                           }
                           if(dmp_text != null && dmp_text!= "" && dmp_text != undefined){
                               dmp_data_obj.target_id.text = dmp_text;
                           }
                           if(dmp_money != null && dmp_money!= "" && dmp_money != undefined){
                               dmp_data_obj.target_id.money = dmp_money;
                           }
                           if(dmp_redBag != null && dmp_redBag!= "" && dmp_redBag != undefined){
                               dmp_data_obj.target_id.redBag = dmp_redBag;
                           }
                           if(xxd_utm_source != null && xxd_utm_source!= "" && xxd_utm_source != undefined){
                               dmp_data_obj.xxd_utm_source = xxd_utm_source;
                           }
                           //console.log(dmp_data);
                           return dmp_data_obj;
                       }
                    });
               } catch (e){}
            });
        }
    };
    return router;
});
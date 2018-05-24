define(["base"],function(e){var t=e(window);e.fn.lazyload=function(i){function o(){var t=0;r.each(function(){var i=e(this);if(!f.skip_invisible||i.is(":visible"))if(e.abovethetop(this,f)||e.leftofbegin(this,f));else if(e.belowthefold(this,f)||e.rightoffold(this,f)){if(++t>f.failure_limit)return!1}else i.trigger("appear"),t=0})}var n,r=this,f={threshold:0,failure_limit:0,event:"scroll",effect:"show",container:window,data_attribute:"original",skip_invisible:!0,appear:null,load:null,placeholder:"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsQAAA7EAZUrDhsAAAANSURBVBhXYzh8+PB/AAffA0nNPuCLAAAAAElFTkSuQmCC"};return i&&(void 0!==i.failurelimit&&(i.failure_limit=i.failurelimit,delete i.failurelimit),void 0!==i.effectspeed&&(i.effect_speed=i.effectspeed,delete i.effectspeed),e.extend(f,i)),n=void 0===f.container||f.container===window?t:e(f.container),0===f.event.indexOf("scroll")&&n.bind(f.event,function(){return o()}),this.each(function(){var t=this,i=e(t);t.loaded=!1,void 0!==i.attr("src")&&!1!==i.attr("src")||i.is("img")&&i.attr("src",f.placeholder),i.one("appear",function(){if(!this.loaded){if(f.appear){var o=r.length;f.appear.call(t,o,f)}e("<img />").bind("load",function(){var o=i.attr("data-"+f.data_attribute);i.hide(),i.is("img")?i.attr("src",o):i.css("background-image","url('"+o+"')"),i[f.effect](f.effect_speed),t.loaded=!0;var n=e.grep(r,function(e){return!e.loaded});if(r=e(n),f.load){var l=r.length;f.load.call(t,l,f)}}).attr("src",i.attr("data-"+f.data_attribute))}}),0!==f.event.indexOf("scroll")&&i.bind(f.event,function(){t.loaded||i.trigger("appear")})}),t.bind("resize",function(){o()}),/(?:iphone|ipod|ipad).*os 5/gi.test(navigator.appVersion)&&t.bind("pageshow",function(t){t.originalEvent&&t.originalEvent.persisted&&r.each(function(){e(this).trigger("appear")})}),e(document).ready(function(){o()}),this},e.belowthefold=function(i,o){return(void 0===o.container||o.container===window?(window.innerHeight?window.innerHeight:t.height())+t.scrollTop():e(o.container).offset().top+e(o.container).height())<=e(i).offset().top-o.threshold},e.rightoffold=function(i,o){return(void 0===o.container||o.container===window?t.width()+t.scrollLeft():e(o.container).offset().left+e(o.container).width())<=e(i).offset().left-o.threshold},e.abovethetop=function(i,o){return(void 0===o.container||o.container===window?t.scrollTop():e(o.container).offset().top)>=e(i).offset().top+o.threshold+e(i).height()},e.leftofbegin=function(i,o){return(void 0===o.container||o.container===window?t.scrollLeft():e(o.container).offset().left)>=e(i).offset().left+o.threshold+e(i).width()},e.inviewport=function(t,i){return!(e.rightoffold(t,i)||e.leftofbegin(t,i)||e.belowthefold(t,i)||e.abovethetop(t,i))},e.extend(e.expr[":"],{"below-the-fold":function(t){return e.belowthefold(t,{threshold:0})},"above-the-top":function(t){return!e.belowthefold(t,{threshold:0})},"right-of-screen":function(t){return e.rightoffold(t,{threshold:0})},"left-of-screen":function(t){return!e.rightoffold(t,{threshold:0})},"in-viewport":function(t){return e.inviewport(t,{threshold:0})},"above-the-fold":function(t){return!e.belowthefold(t,{threshold:0})},"right-of-fold":function(t){return e.rightoffold(t,{threshold:0})},"left-of-fold":function(t){return!e.rightoffold(t,{threshold:0})}})});
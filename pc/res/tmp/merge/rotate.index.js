define(["base"],function(n){return{sliderBox:function(e){var i,t,l,d,a,o=n(e.object),r=e.sliderType,c=e.sliderSpeed,s=e.animateSpeed,f=e.title,u=e.info,p=e.arrow,m=e.dataType,v=e.data,h=e.minWidth,g=e.callback,I=0,x=0;o.append("<ul></ul>"),o.append("<ol></ol>"),o.css("min-width",parseInt(h)+"px"),"json"==m?(n.each(v,function(n,e){var i="banner"+n;o.find("ul").append('<li><a target="_blank" class="ga-click dmp-click" eventType = "jump" dev_id="'+i+'" target_id=" '+e.imgId+'" textHref="'+e.linkurl+'" dmp_text="'+e.title+'" ga-commnd="send,ec:addPromo,ec:setAction" ga-ec-addPromo="{\'name\':\' '+e.title+'\'}" ga-ec-setAction-command="promo_click" ga-hitType="event" ga-category="增强电商" ga-action="站内广告点击" ga-label=" '+e.title+'" href="'+e.linkurl+'"><img src="'+e.imgurl+'" /></a><h2>'+e.title+"</h2><span>"+e.content+"</span></li>")}),null!=g&&g()):"html"==m&&(o.find("ul").append(v),null!=g&&g()),1==f?o.find("ul li").find("h2").fadeIn(0):o.find("ul li").find("h2").remove(),1==u?o.find("ul li").find("span").fadeIn(0):o.find("ul li").find("span").remove(),1==p?o.append('<div class="controlBox"><div class="pre"></div><div class="next"></div></div>'):o.remove(".controlBox"),n.each(o.find("ul li"),function(){o.find("ol").append("<li></li>")}),o.find("ol").find("li").eq(0).addClass("current"),l=function(){x--,x==-1&&(x=o.find("ul li").length-1)},d=function(){x++,x==o.find("ul li").length&&(x=0)},1==r?(o.addClass("sliderType1"),o.find("ul li").eq(0).fadeIn(0),t=function(){o.find("ul li").stop().fadeOut(parseInt(s)).eq(x).stop().fadeIn(parseInt(s)),o.find("ol li").removeClass("current").eq(x).addClass("current")}):2==r?(o.addClass("sliderType2"),o.find("ul").css("width",100*o.find("ul li").length+"%"),o.find("ul li").css("width",1/o.find("ul li").length*100+"%"),t=function(){o.find("ul").stop().animate({left:-100*parseInt(x)+"%"},parseInt(s)),o.find("ol li").removeClass("current").eq(x).addClass("current")}):3==r&&(o.addClass("sliderType3"),t=function(){o.find("ul").stop().animate({top:-o.height()*parseInt(x)},parseInt(s)),o.find("ol li").removeClass("current").eq(x).addClass("current")}),i=function(){clearInterval(a),a=setInterval(function(){I=1,d(),t(),setTimeout(function(){I=0},s)},c)},i(),o.mouseover(function(){clearInterval(a),o.find(".controlBox").fadeIn(0)}).hover(function(){clearInterval(a),o.find(".controlBox").fadeIn(0)}).mouseout(function(){i(),o.find(".controlBox").fadeOut(0)}),o.find("ol").find("li").click(function(){return 0==I&&(I=1,x=n(this).index(),t(),setTimeout(function(){I=0},s),void 0)}),o.find(".controlBox .pre").click(function(){return 0==I&&(I=1,l(),t(),setTimeout(function(){I=0},s),void 0)}),o.find(".controlBox .next").click(function(){return 0==I&&(I=1,d(),t(),setTimeout(function(){I=0},s),void 0)})}}});
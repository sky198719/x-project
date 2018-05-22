require.config({
	paths:{
		'common':'../common/common'
	}
});
require(['common'],function(common){
	/* 通用变量 */
	var timer; //日期
	var myTime; //毫秒
	var downloadPage = 1; //下载列表当前页
	var downloadTotal; //下载页总页数
	var rotate = 0; //旋转图片角度
	var uploadfileId; //上传文件id
	var uploadfileType; //上传文件类型
	var navId; //栏目id
	var listPage = 30; //每页条数
	var listCurrent = 1; //当前页数
	var urlStr = window.location.href;
	var urlIndex1 = urlStr.indexOf('applyCode=');
	var urlIndex2 = urlStr.indexOf('&userId=');
	var urlIndex3 = urlStr.indexOf('&idCode=');
	$('#orderId').html(urlStr.substring(urlIndex1 + 10,urlIndex2)); //进件单号
	var userId = urlStr.substring(urlIndex2 + 8,urlIndex3); //userid
	var idCode = urlStr.substr(urlIndex3 + 8,urlStr.length); //身份证号码

	/* 方法配置 */
	var init = {
		/* 异常报错 */
		errorBox:function(){
			alert('网络异常，请刷新页面后重试！');
			init.loadingOut();
		},
		/* 加载动画开始 */
		loadingIn:function(){
			$('.loading').fadeIn(0);
			// init.myDate();
		},
		/* 加载动画结束 */
		loadingOut:function(){
			$('.loading').fadeOut(0);
		},
		/* 上传中动画 */
		loadingAnimate:function(){
			var index = 0;
			setInterval(function(){
				index++;
				if(index >= 12){
					index = 0;
				}
				$('#upload b').removeClass('hasUpload').eq(index).addClass('hasUpload');
			},100);
		},
		/* 列表加载下一页&左栏固定 */
		listLoad:function(){
			$(window).scroll(function(){
				if($(window).scrollTop() + document.body.clientHeight >= $(document).height()){
					if($('#navList').find('.current').attr('value') != 'RECEIPT_IDCARDNO' && $('#navList').find('.current').attr('value') != 'RECEIPT_FACE_RECOGNITION'){
						listCurrent++;
						dataInit.listData(navId);
					}else{
						return false;
					}
				}
				if($(window).scrollTop() >= 100){
					$('.leftBar').css({'position':'fixed','top':'0px'});
				}else{
					$('.leftBar').css({'position':'absolute','top':'100px'});
				}
			});
		},
		/* 当前客户端时间 */
		myDate:function(){
			timer = new Date();
			myTime = timer.getTime();
		},
		/* 通用关闭-初始化弹出层数据 */
		closeBox:function(){
			$('.close').click(function(){
				$('.alerts').fadeOut(0);
				$('.layer').fadeOut(0);
				/* 大图信息初始化 */
				$('.picReview .m').removeAttr('style');
				$('.picReview .m img').removeAttr('style').attr('src','');
				$('.picReview .t em a').attr('href','');
				/* 视频信息初始化 */
				$('.videoReview video').attr('src','');
				/* 音频信息初始化 */
				$('.audioReview audio').attr('src','');
			});
		},
		/* 左栏切换效果 */
		navControl:function(){
			$(document).on('click','#navList li',function(){
				var index = $(this).index();
				listCurrent = 1;
				$('#navList li').removeClass('current').eq(index).addClass('current');
				// init.loadingIn();
				navId = $(this).attr('value');
				$('#fileList').empty();
				$('#fileList').append('<li id="upload"><span><input type="file" id="uploadFile" name="file" /></span><strong>上传图片</strong></li>');
				dataInit.listData(navId);
			});
		},
		/* 列表图片居中 */
		listImg:function(){
			$.each($('#fileList li span img'),function(){
				$(this).load(function(){
					var _this = $(this);
					if(_this.width() >= _this.height()){
						_this.css({'width':'145px','top':(145 - _this.height()*145/_this.width())/2 + 'px','left':'0px'});
					}else{
						_this.css({'height':'145px','top':'0px','left':(145 - _this.width()*145/_this.height())/2 + 'px'});
					}
				})
			});
		},
		/* 列表菜单浮层效果 */
		listControl:function(){
			$('#fileList').children('li').hover(function(){
				var _this = $(this);
				var index = $(this).index();
				_this.find('div').stop().animate({'top':'0px'},200);
			},function(){
				var _this = $(this);
				_this.find('div').stop().animate({'top':'-145px'},200);
			});
		},
		/* 视频预览 */
		videoReview:function(){
			$('.bigVideo').click(function(){
				$('.videoReview video').attr('src',$(this).attr('value'));
				$('.videoReview').fadeIn(0);
				$('.layer').fadeIn(0);
			});
		},
		/* 音频预览 */
		audioReview:function(){
			$('.bigAudio').click(function(){
				$('.audioReview audio').attr('src',$(this).attr('value'));
				$('.audioReview').fadeIn(0);
				$('.layer').fadeIn(0);
			});
		},
		/* 打开全图预览 */
		imgReview:function(){
			$('.bigPic').click(function(){
				rotate = 0;
				$('.loading').fadeIn(0);
				$('.picReview .m img').attr('src',$(this).attr('value'));
				$('.picReview .t em a').attr('href',$(this).attr('value') + '&attname=');
			});
		},
		/* 全图预览强行居中 */
		boxInit:function(){
			$('.picReview').css({'margin-top':-$('.picReview').height()/2 + 'px','margin-left':-$('.picReview').width()/2 + 'px'});
		},
		/* 大图旋转 */
		boxRotate:function(){
			$('.picReview .t strong').click(function(){
				var imgWidth = $('.picReview .m').width();
				var imgHeight = $('.picReview .m').height();
				$('.picReview .m').css({'width':imgWidth +'px','height':imgHeight + 'px'});
				rotate++;
				if(rotate == 4){
					rotate = 0;
				}
				if(imgWidth >= imgHeight){
					if(rotate%2 != 0){
						$('.picReview .m img').css({'width':imgHeight + 'px','margin-left':(imgWidth - imgHeight)/2 + 'px','margin-top':(imgHeight - imgHeight/imgWidth*imgHeight)/2 + 'px'});
						$('.picReview .m img').css({'transform':'rotate(' + rotate * 90 + 'deg)','-ms-transform':'rotate(' + rotate * 90 + 'deg)','-moz-transform':'rotate(' + rotate * 90 + 'deg)','-webkit-transform':'rotate(' + rotate * 90 + 'deg)','-o-transform':'rotate(' + rotate * 90 + 'deg)'});
					}else{
						$('.picReview .m img').css({'width':imgWidth + 'px','margin-left':'0','margin-top':'0'});
						$('.picReview .m img').css({'transform':'rotate(' + rotate * 90 + 'deg)','-ms-transform':'rotate(' + rotate * 90 + 'deg)','-moz-transform':'rotate(' + rotate * 90 + 'deg)','-webkit-transform':'rotate(' + rotate * 90 + 'deg)','-o-transform':'rotate(' + rotate * 90 + 'deg)','width':imgWidth + 'px'});
					}
				}else{
					if(rotate%2 != 0){
						$('.picReview .m img').css({'height':imgWidth + 'px','margin-left':(imgWidth - imgWidth/imgHeight*imgWidth)/2 + 'px','margin-top':(imgHeight - imgWidth)/2 + 'px'});
						$('.picReview .m img').css({'transform':'rotate(' + rotate * 90 + 'deg)','-ms-transform':'rotate(' + rotate * 90 + 'deg)','-moz-transform':'rotate(' + rotate * 90 + 'deg)','-webkit-transform':'rotate(' + rotate * 90 + 'deg)','-o-transform':'rotate(' + rotate * 90 + 'deg)'});
					}else{
						$('.picReview .m img').css({'height':imgHeight + 'px','margin-left':'0','margin-top':'0'});
						$('.picReview .m img').css({'transform':'rotate(' + rotate * 90 + 'deg)','-ms-transform':'rotate(' + rotate * 90 + 'deg)','-moz-transform':'rotate(' + rotate * 90 + 'deg)','-webkit-transform':'rotate(' + rotate * 90 + 'deg)','-o-transform':'rotate(' + rotate * 90 + 'deg)','height':imgHeight + 'px'});
					}
				}
			});
		},
		/* 查看打包下载列表 */
		zipList:function(){
			$('#downloadList').click(function(){
				downloadPage = 1;
				dataInit.downloadlistData(downloadPage);
				$('.zipList').fadeIn(0);
				$('.layer').fadeIn(0);	
			});
		},
		/* 日期转换 */
		dataUpdate:function(obj){
			if(obj.toString().length <= 1){
				return '0' + obj;
			}else{
				return obj;
			}
		},
		/* 下载列表上一页 */
		downloadPre:function(){
			$('#downloadPre').click(function(){
				if(downloadPage <= 1){
					alert('已经是第一页！');
					return false;
				}else{
					downloadPage--;
					dataInit.downloadlistData(downloadPage);
				}
			});
		},
		/* 下载列表下一页 */
		downloadNext:function(){
			$('#downloadNext').click(function(){
				if(downloadPage >= downloadTotal){
					alert('已经是最后一页！');
					return false;
				}else{
					downloadPage++;
					dataInit.downloadlistData(downloadPage);
				}
			});
		},
		/* 下载列表刷新 */
		downloadReflash:function(){
			$('#ziplistReflash').click(function(){
				downloadPage = 1;
				dataInit.downloadlistData(downloadPage);
			});
		},
		/* 删除打包文件 */
		zipDelete:function(){
			$('.zipDelete').click(function(){
				if(confirm('打包文件删除后不可恢复！是否确认删除？')){
					dataInit.deleteData($(this).parent('em').parent('li').attr('value'));
				}else{
					return false;
				}
			});
		},
		/* 文件打包 */
		toZip:function(){
			$('#toZip').click(function(){
				if(confirm('是否确认该页面所有文件打包？')){
					dataInit.zipData();
				}else{
					return false;
				}
			});
		}
	}

	/* 数据请求 */
	var dataInit = {
		/* 分类信息 */
		categoryDate:function(){
			$.ajax({
				url:ajaxlink + '/receipt/categories',
				type:'get',
				cache:false,
				data:{code:'RECEIPT_FILE'},
				beforeSend:function(request){
					init.myDate();
                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
                	request.setRequestHeader('clientTime',myTime);
                },
				success:function(data){
					if(data.code != '200000'){
						alert(data.message);
						init.loadingOut();
						return false;
					}else{
						$('#navList').empty();
						$.each(data.data.category.items,function(index,item){
							$('#navList').append('<li value="' + item.code + '">' + item.message + '</li>');
						});
						$('#navList').children('li').eq(0).addClass('current');
						navId = $('#navList').children('li').eq(0).attr('value');
						$('#fileList').empty();
						$('#fileList').append('<li id="upload"><span><input type="file" id="uploadFile" name="file" /></span><strong>上传图片</strong></li>');
						dataInit.listData(navId);
					}
				},
				error:function(){
					init.errorBox();
				}
			});
		},
		/* 列表数据 */
		listData:function(navId){
			// 身份证单独处理
			if(navId == 'RECEIPT_IDCARDNO'){
				$.ajax({
					url:ajaxlink2 + '/user/appro/realNamePic',
					type:'get',
					cache:false,
					data:{idCardNumber:idCode},
					beforeSend:function(request){
						init.myDate();
	                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
	                	request.setRequestHeader('clientTime',myTime);
	                },
	                success:function(data){
	                	if(data.code != '200000'){
							alert(data.message);
							init.loadingOut();
							return false;
						}else{
							if(data.data.code == 0){
								var idCardPicP = data.data.data.positivePicUrl;
								var idCardPicN = data.data.data.negativePicUrl;
								var PicPurl = idCardPicP.substring(0,4);
								var PicNurl = idCardPicN.substring(0,4);
								if(PicPurl == 'http'){
									$('#fileList').append('<li class="image"><span><img src="' + data.data.data.positivePicUrl + '" /><div><ul><li value="' + data.data.data.positivePicUrl + '" class="bigPic">查看大图</li><li><a href="' + data.data.data.positivePicUrl + '?&attname=" target="_blank">下载</a></li></ul></div></span><strong>身份证正面</strong></li>');
									$('#fileList').append('<li class="image"><span><img src="' + data.data.data.negativePicUrl + '" /><div><ul><li value="' + data.data.data.negativePicUrl + '" class="bigPic">查看大图</li><li><a href="' + data.data.data.negativePicUrl + '?&attname=" target="_blank">下载</a></li></ul></div></span><strong>身份证反面</strong></li>');
								}else{
									$('#fileList').append('<li class="image"><span><img src="../../../../' + data.data.data.positivePicUrl + '" /><div><ul><li value="../../../../' + data.data.data.positivePicUrl + '" class="bigPic">查看大图</li><li><a href="../../../../' + data.data.data.positivePicUrl + '&attname=" target="_blank">下载</a></li></ul></div></span><strong>身份证正面</strong></li>');
									$('#fileList').append('<li class="image"><span><img src="../../../..' + data.data.data.negativePicUrl + '" /><div><ul><li value="../../../..' + data.data.data.negativePicUrl + '" class="bigPic">查看大图</li><li><a href="../../../..' + data.data.data.negativePicUrl + '&attname=" target="_blank">下载</a></li></ul></div></span><strong>身份证反面</strong></li>');
								}
								// 除身份证外其他信息
								$.ajax({
									url:ajaxlink + '/receipt/files',
									type:'get',
									cache:false,
									data:{applyCode:$('#orderId').html(),sourceType:'BACKEND',categoryCode:navId,watermarkFlag:'WATERMARK_YES',thumbnailFlag:'THUMBNAIL_YES',currentPage:listCurrent,pageSize:listPage},
									beforeSend:function(request){
										init.myDate();
					                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
					                	request.setRequestHeader('clientTime',myTime);
					                },
									success:function(data){
										if(data.code != '200000'){
											alert(data.message);
											init.loadingOut();
											return false;
										}else{
											if(data.data.status.code != 'DATA_NOT_FOUND'){
												$.each(data.data.items,function(index,item){
													if(item.fileType == 'IMAGE'){
														$('#fileList').append('<li class="image"><span><img src="' + item.thumbnailURL + '" /><div><ul><li value="' + item.downLoadURL + '" class="bigPic">查看大图</li><li><a href="' + item.downLoadURL + '&attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
													}else if(item.fileType == 'TXT'){
														$('#fileList').append('<li class="doc"><span><div><ul><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
													}else if(item.fileType == 'VIDEO'){
														$('#fileList').append('<li class="media"><span><div><ul><li value="' + item.downLoadURL + '" class="bigVideo">预览文件</li><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
													}else if(item.fileType == 'AUDIO'){
														$('#fileList').append('<li class="media"><span><div><ul><li value="' + item.downLoadURL + '" class="bigAudio">预览文件</li><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
													}
												});
											}
											init.listControl();
											init.listImg();
											init.imgReview();
											$('.picReview .m img').load(function(){
												init.boxInit();
												$('.picReview').fadeIn(0);
												$('.layer').fadeIn(0);
												$('.loading').fadeOut(0);
											});
											init.videoReview();
											init.audioReview();
											// init.loadingOut();
											dataInit.uploadFile();
										}
									}
								});
							}
							init.listControl();
							init.listImg();
							init.imgReview();
							$('.picReview .m img').load(function(){
								init.boxInit();
								$('.picReview').fadeIn(0);
								$('.layer').fadeIn(0);
								$('.loading').fadeOut(0);
							});
							init.videoReview();
							init.audioReview();
							// init.loadingOut();
							dataInit.uploadFile();
						}
	                },
	                error:function(){
	                	init.errorBox();
	                }
				});
			}else if(navId == 'RECEIPT_FACE_RECOGNITION'){
				$.ajax({
					url:ajaxlink2 + '/user/appro/facePics',
					type:'get',
					cache:false,
					data:{idCardNumber:idCode},
					beforeSend:function(request){
						init.myDate();
	                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
	                	request.setRequestHeader('clientTime',myTime);
	                },
	                success:function(data){
	                	if(data.code != '200000'){
							alert(data.message);
							init.loadingOut();
							return false;
						}else{
							if(data.data.code == 0){
								$.each(data.data.data,function(index,item){
									$('#fileList').append('<li class="image"><span><img src="' + item.downLoadURL + '" /><div><ul><li value="' + item.downLoadURL + '" class="bigPic">查看大图</li><li><a href="' + item.downLoadURL + '?&attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
								});
								// 除身份证外其他信息
								$.ajax({
									url:ajaxlink + '/receipt/files',
									type:'get',
									cache:false,
									data:{applyCode:$('#orderId').html(),sourceType:'BACKEND',categoryCode:navId,watermarkFlag:'WATERMARK_YES',thumbnailFlag:'THUMBNAIL_YES',currentPage:listCurrent,pageSize:listPage},
									beforeSend:function(request){
										init.myDate();
					                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
					                	request.setRequestHeader('clientTime',myTime);
					                },
									success:function(data){
										if(data.code != '200000'){
											alert(data.message);
											init.loadingOut();
											return false;
										}else{
											if(data.data.status.code != 'DATA_NOT_FOUND'){
												$.each(data.data.items,function(index,item){
													if(item.fileType == 'IMAGE'){
														$('#fileList').append('<li class="image"><span><img src="' + item.thumbnailURL + '" /><div><ul><li value="' + item.downLoadURL + '" class="bigPic">查看大图</li><li><a href="' + item.downLoadURL + '&attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
													}else if(item.fileType == 'TXT'){
														$('#fileList').append('<li class="doc"><span><div><ul><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
													}else if(item.fileType == 'VIDEO'){
														$('#fileList').append('<li class="media"><span><div><ul><li value="' + item.downLoadURL + '" class="bigVideo">预览文件</li><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
													}else if(item.fileType == 'AUDIO'){
														$('#fileList').append('<li class="media"><span><div><ul><li value="' + item.downLoadURL + '" class="bigAudio">预览文件</li><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
													}
												});
											}
											init.listControl();
											init.listImg();
											init.imgReview();
											$('.picReview .m img').load(function(){
												init.boxInit();
												$('.picReview').fadeIn(0);
												$('.layer').fadeIn(0);
												$('.loading').fadeOut(0);
											});
											init.videoReview();
											init.audioReview();
											// init.loadingOut();
											dataInit.uploadFile();
										}
									}
								});
							}
							init.listControl();
							init.listImg();
							init.imgReview();
							$('.picReview .m img').load(function(){
								init.boxInit();
								$('.picReview').fadeIn(0);
								$('.layer').fadeIn(0);
								$('.loading').fadeOut(0);
							});
							init.videoReview();
							init.audioReview();
							// init.loadingOut();
							dataInit.uploadFile();
						}
	                },
	                error:function(){
	                	init.errorBox();
	                }
				});
			}else{
				// 除身份证外其他信息
				$.ajax({
					url:ajaxlink + '/receipt/files',
					type:'get',
					cache:false,
					data:{applyCode:$('#orderId').html(),sourceType:'BACKEND',categoryCode:navId,watermarkFlag:'WATERMARK_YES',thumbnailFlag:'THUMBNAIL_YES',currentPage:listCurrent,pageSize:listPage},
					beforeSend:function(request){
						init.myDate();
	                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
	                	request.setRequestHeader('clientTime',myTime);
	                },
					success:function(data){
						if(data.code != '200000'){
							alert(data.message);
							init.loadingOut();
							return false;
						}else{
							if(data.data.status.code != 'DATA_NOT_FOUND'){
								$.each(data.data.items,function(index,item){
									if(item.fileType == 'IMAGE'){
										$('#fileList').append('<li class="image"><span><img src="' + item.thumbnailURL + '" /><div><ul><li value="' + item.downLoadURL + '" class="bigPic">查看大图</li><li><a href="' + item.downLoadURL + '&attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
									}else if(item.fileType == 'TXT'){
										$('#fileList').append('<li class="doc"><span><div><ul><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
									}else if(item.fileType == 'VIDEO'){
										$('#fileList').append('<li class="media"><span><div><ul><li value="' + item.downLoadURL + '" class="bigVideo">预览文件</li><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
									}else if(item.fileType == 'AUDIO'){
										$('#fileList').append('<li class="media"><span><div><ul><li value="' + item.downLoadURL + '" class="bigAudio">预览文件</li><li><a href="' + item.downLoadURL + '?attname=" target="_blank">下载</a></li></ul></div></span><strong>' + item.fileName + '</strong></li>');
									}
								});
							}
							init.listControl();
							init.listImg();
							init.imgReview();
							$('.picReview .m img').load(function(){
								init.boxInit();
								$('.picReview').fadeIn(0);
								$('.layer').fadeIn(0);
								$('.loading').fadeOut(0);
							});
							init.videoReview();
							init.audioReview();
							// init.loadingOut();
							dataInit.uploadFile();
						}
					},
					error:function(){
						init.errorBox();
					}
				});
			}
		},
		/* 下载列表数据 */
		downloadlistData:function(downloadPage){
			$.ajax({
				url:ajaxlink + '/receipt/packages',
				type:'get',
				cache:false,
				data:{applyCode:$('#orderId').html(),currentPage:downloadPage,pageSize:6},
				beforeSend:function(request){
					init.myDate();
                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
                	request.setRequestHeader('clientTime',myTime);
                },
				success:function(data){
					if(data.code != '200000'){
						alert(data.message);
						init.loadingOut();
						return false;
					}else{
						$('#zipList').empty();
						$('#zipList').append('<li><span>状态</span><strong>时间</strong><em>操作</em></li>');
						if(data.data.status.code != 'DATA_NOT_FOUND'){
							if(data.data.totalCount%6 == 0){
								downloadTotal = parseInt(data.data.totalCount/6);
							}else{
								downloadTotal = parseInt(data.data.totalCount/6 + 1);
							}
							$.each(data.data.items,function(index,item){
								if(item.status == 'PACKAGING'){
									$('#zipList').append('<li value="' + item.packageId + '"><span>打包中</span><strong>' + new Date(item.updateTime).getFullYear() + '/' + init.dataUpdate(new Date(item.updateTime).getMonth() + 1) + '/' + init.dataUpdate(new Date(item.updateTime).getDate()) + ' ' + init.dataUpdate(new Date(item.updateTime).getHours()) + ':' + init.dataUpdate(new Date(item.updateTime).getMinutes()) + ':' + init.dataUpdate(new Date(item.updateTime).getSeconds()) + '</strong><em><p></p><b></b></em></li>');
								}else{
									$('#zipList').append('<li class="zipOk" value="' + item.packageId + '"><span>可下载</span><strong>' + new Date(item.updateTime).getFullYear() + '/' + init.dataUpdate(new Date(item.updateTime).getMonth() + 1) + '/' + init.dataUpdate(new Date(item.updateTime).getDate()) + ' ' + init.dataUpdate(new Date(item.updateTime).getHours()) + ':' + init.dataUpdate(new Date(item.updateTime).getMinutes()) + ':' + init.dataUpdate(new Date(item.updateTime).getSeconds()) + '</strong><em><p><a href="' + item.downLoadURL + '?attname=" target="_blank"></a></p><b class="zipDelete"></b></em></li>');
								}
							});
							$('#downloadPage').html(downloadPage + '/' + downloadTotal);
						}
						// init.loadingOut();
						init.zipDelete();
					}
				},
				error:function(){
					init.errorBox();
				}
			});
		},
		/* 删除打包数据 */
		deleteData:function(packageId){
			init.loadingIn();
			$.ajax({
				url:ajaxlink + '/receipt/packages?packageId=' + packageId,
				type:'delete',
				cache:false,
				beforeSend:function(request){
					init.myDate();
                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
                	request.setRequestHeader('clientTime',myTime);
                },
				success:function(data){
					if(data.code != '200000'){
						alert(data.message);
						init.loadingOut();
						return false;
					}else{
						alert(data.data.status.message);
						init.loadingOut();
						downloadPage = 1;
						dataInit.downloadlistData(downloadPage);
					}
				},
				error:function(){
					init.errorBox();
				}
			});
		},
		/* 打包文件数据 */
		zipData:function(){
			init.loadingIn();
			$.ajax({
				url:ajaxlink + '/receipt/packages',
				type:'post',
				cache:false,
				data:{applyCode:$('#orderId').html()},
				beforeSend:function(request){
					init.myDate();
                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
                	request.setRequestHeader('clientTime',myTime);
                },
				success:function(data){
					if(data.code != '200000'){
						alert(data.message);
						init.loadingOut();
						return false;
					}else{
						alert(data.data.status.message);
						init.loadingOut();
						downloadPage = 1;
						dataInit.downloadlistData(downloadPage);
						$('.zipList').fadeIn(0);
						$('.layer').fadeIn(0);
					}
				},
				error:function(){
					init.errorBox();
				}
			});
		},
		/* 上传文件 */
		uploadFile:function(){
			$('#uploadFile').fileupload({
				url:ajaxlink + '/files',
				autoUpload:true,
				type:'post',
				cache:false,
		        formData:{bizCode:'RECEIPT_APPLY_FILE'},
		        acceptFileTypes:/\.(jpg|jpeg|bmp|png|doc|docx|xls|xlsx|ppt|pptx|pdf|avi|rmvb|mp4|mp3)$/i,
				maxFileSize:524288000,
		        beforeSend:function(request){
		        	init.myDate();
                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
                	request.setRequestHeader('clientTime',myTime);
                },
                processfail:function(){
                	$('.layer').fadeIn(0);
                	$('.error').fadeIn(0);
                },
		        progressall:function(e,data){
			        var progress = parseInt(data.loaded / data.total * 100);
			       	$('#uploadProgress').html(progress + '%');
			       	$('.layer').fadeIn(0);
		        	$('.upload').fadeIn(0);
			    },
			    success:function(data){
			    	if(data.code != '200000'){
		        		alert(data.message);
		        		return false;
		        	}else{
		        		uploadfileId = data.data.fileId;
		        		uploadfileType = data.data.fileType;
		        		dataInit.uploadParma();
		        	}
			    },
		        done:function(e,data){
		        	
		        },
		        fail:function(){
		        	init.errorBox();
		        }
		    });
		},
		/* 上传文件参数 */
		uploadParma:function(){
			$.ajax({
				url:ajaxlink + '/receipt/files',
				type:'post',
				cache:false,
				headers:{'Content-Type':'application/json'},
				data:'{"data":{"applyCode":"' + $('#orderId').html() + '","categoryCode":"' + navId + '","sourceType":"BACKEND","fileId":"' + uploadfileId + '","fileType":"' + uploadfileType + '","userId":"' + userId + '"}}',
				beforeSend:function(request){
					init.myDate();
                	request.setRequestHeader('clientId','SHENZHOURONG_FRONT_END');
                	request.setRequestHeader('clientTime',myTime);
                },
				success:function(data){
					if(data.code != '200000'){
						alert('上传失败！');
						$('.layer').fadeOut(0);
		        		$('.upload').fadeOut(0);
						return false;
					}else{
						alert('上传成功！');
						$('.layer').fadeOut(0);
		        		$('.upload').fadeOut(0);
		        		$('#fileList').empty();
						$('#fileList').append('<li id="upload"><span><input type="file" id="uploadFile" name="file" /></span><strong>上传图片</strong></li>');
						listCurrent = 1;
						dataInit.listData(navId);
					}
				},
				error:function(){
					init.errorBox();
				}
			});
		}
	}

	init.loadingAnimate();
	init.boxRotate();
	init.closeBox();
	init.listLoad();
	dataInit.categoryDate();
	init.downloadPre();
	init.downloadNext();
	init.downloadReflash();
	init.navControl();
	init.zipList();
	init.toZip();

	/*
	if(userId == '' || userId == undefined || userId == null){
		alert('获取用户相关信息失败！');
		return false;
	}else{
		init.loadingAnimate();
		init.boxRotate();
		init.closeBox();
		init.listLoad();
		dataInit.categoryDate();
		init.downloadPre();
		init.downloadNext();
		init.downloadReflash();
		init.navControl();
		init.zipList();
		init.toZip();
	}
	*/
});
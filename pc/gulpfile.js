//  gulp desc http://www.cnblogs.com/2050/p/4198792.html

var gulp = require('gulp'),
    minifycss = require('gulp-minify-css'),
    uglify = require('gulp-uglify'),
    rename = require('gulp-rename'),
    del = require('del'),
    less = require('gulp-less'),
    gulpif = require('gulp-if'),
    imagemin = require('gulp-imagemin'),
    pngquant = require('imagemin-pngquant'),
    //确保本地已安装gulp-cache [cnpm install gulp-cache --save-dev]
    cache = require('gulp-cache'),
    gulptpl = require('gulp-tpl2mod'),
    //juicer = require('gulp-juicer'),
    //gulptpl = require('gulp-tpl2mod'),
    through = require('through2'),
    //amdOptimize = require('amd-optimize'),
    amdOptimize = require('gulp-requirejs-optimize'),
    plumber = require('gulp-plumber'),
    concatCss = require('gulp-concat-css'),
    concat = require('gulp-concat'),
    base64 = require('gulp-base64'),
    isDebug = false, //是否压缩代码
    runTimestamp = Math.round(Date.now()/1000);
// gulp.task("tpl" , function (){
//     return gulp.src("res/**/*.tpl.html")
//         .pipe(juicer({
//             simple:false,
//             amd:true,
//             juicerPath:""
//         }))
//         .pipe(rename({
//             extname: '.html.js'
//         })).pipe(gulp.dest('./pages/build/'));
// });

gulp.task("tpl" , function (res)  {
    return gulp.src("res/**/*.tpl.html")
        .pipe(gulptpl({
            prefix: 'define(function(){return ',
            suffix: '});'
        }))
        .pipe(rename({
            extname:".js"
        }))
        .pipe(gulp.dest('./pages/build'));
});



gulp.task('less',["mergeRemoteCss"], function () {
    return gulp.src([
        './res/**/index.less','./res/**/header.less','./res/**/footer.less',
        './res/**/reset.less','./res/**/laydate.less','./res/**/cropper.min.css'])
        .pipe(less())
        .pipe(gulpif(!isDebug, minifycss()))
        .pipe(gulpif(!isDebug,base64({
            extensions: ['png','jpg'],
            maxImageSize: 20*1024, // bytes
            debug: false
        })))
        /*.on('error', function(err) {
            gutil.log('Less Error!', err.message);
            this.end();
        })*/
        .pipe(gulpif(!isDebug, minifycss()))
        .pipe(rename(function (path){
            //console.log ("basename:"+path.basename)
            if (path.basename=="index"){
                path.basename="_";
            }
        }))
        .pipe(gulp.dest('./pages/build/'));
});

gulp.task('js' , function (){
    gulp.src(['./res/**/*.min.js']).pipe( gulp.dest('./pages/build/') );
    return gulp.src(['./res/**/*.js','!./res/**/*.min.js'])
        .pipe(gulpif( !isDebug , uglify()))
        .pipe(gulp.dest('./pages/build/'));
});

function err(error) {
    console.error('[ERROR]'.red + error.message);
    this.emit('end');
}

//var muiHost = "localhost" , versionPath="/mui/pages/build/";
var muiHost = "test-static.xxd.com" , versionPath="/mui/1.0.21/build/";
var mergeFile = require("./gulp-merge-files.js");
gulp.task('mergeRemoteJs',function(cb){
    //"use strict";
    //if (!isDebug) del(['./res/tmp/merge/*.js'], cb);
    var mergeList = [{
        hostname:muiHost,
        modName:"backTop",
        path:versionPath+"backTop/feedback.tpl.html.js"
    },{
        hostname:muiHost,
        modName:"dialog",
        path:versionPath+"dialog/index.js"
    },{
        hostname:muiHost,
        modName:"dialog-float",
        path:versionPath+"dialog-float/index.js"
    },{
        hostname:muiHost,
        modName:"dialog-float",
        path:versionPath+"dialog-float/float.tpl.html.js"
    },{
        hostname:muiHost,
        modName:"dialog-float",
        path:versionPath+"dialog-float/preview.tpl.html.js"
    },{
        hostname:muiHost,
        modName:"rotate",
        path:versionPath+"rotate/index.js"
    },{
        hostname:muiHost,
        modName:"backTop",
        path:versionPath+"backTop/index-1.js"
    },{
        hostname:muiHost,
        modName:'tip',
        path:versionPath+"tip/index.js"
    },{
        hostname:muiHost,
        modName:'paging',
        path:versionPath+"paging/index.js"
    },{
        hostname:muiHost,
        modName:'paging',
        path:versionPath+"paging/index.tpl.html.js"
    }];
    return gulp.src(['./res/tmp/tmp.js'])
        //.pipe( mergeFile(mergeList));
    .pipe(gulpif(!isDebug, mergeFile(mergeList)));

});

gulp.task("mergeRemoteCss" , function (cb) {
    //del(['./res/tmp/merge/*.css','./res/tmp/merge/*.less'], cb);
    "use strict";
    //del(['./res/tmp/merge/*.css'], cb);
    var mergeList = [{
        hostname:muiHost,
        modName:"rotate",
        path:versionPath+"rotate/index.css"
    },{
        hostname:muiHost,
        modName:"backTop",
        path:versionPath+"backTop/index-1.css"
    },{
        hostname:muiHost,
        modName:"tip",
        path:versionPath+"tip/index.css"
    },{
        hostname:muiHost,
        modName:"dialog",
        path:versionPath+"dialog/index.css"
    },{
        hostname:muiHost,
        modName:"dialog-float",
        path:versionPath+"dialog-float/index.css"
    }];
    return gulp.src(['./res/tmp/tmp.js'])
        //.pipe(mergeFile(mergeList));
    .pipe(gulpif(!isDebug, mergeFile(mergeList)));
});




gulp.task('rjs', ["mergeRemoteJs","js",'tpl'],function (){
    "use strict";
    //console.log ("path:"+__dirname);
    var abspath = __dirname;
    return gulp.src(['./pages/**/*/index.js'])
        .pipe(amdOptimize(function (file){
            return {
                exclude: [
                    //'jquery'
                ],
                //out:"_2"+new Date().getTime()+".js",
                paths: {
                    "jquery": abspath + "/pages/build/js/common/jquery.min",
                    "requirejs": abspath + "/pages/build/js/common/require.min",
                    "juicer": abspath + "/pages/build/js/common/juicer.min",
                    "base": abspath + "/pages/build/js/common/base",
                    "json": abspath + "/pages/build/js/common/json2",
                    "echarts":abspath +"/pages/build/js/common/echarts.min",
                    "md":abspath + "/pages/build/js/common/md5",
                    "laydate":abspath + "/pages/build/js/common/laydate",
                    "cropper":abspath + "/pages/build/js/common/cropper",
                    "static":abspath + "/pages/build/mods/const",
                    "header":abspath + "/pages/build/mods/header",
                    "footer":abspath + "/pages/build/mods/footer",
                    "store":abspath + "/pages/build/mods/store",
                    "companyHeader":abspath+"/pages/build/mods/company/header",

                    //detail common
                    "detail":abspath + "/pages/build/mods/detail/detail",
                    "side":abspath + "/pages/build/mods/user/side",


                    "track":abspath + "/pages/build/js/tracker/track",
                    'sbztTpl':abspath+"/pages/build/mods/home/sbzt.tpl.html",
                    'zqzrTpl':abspath+"/pages/build/mods/home/zqzr.tpl.html",
                    'bankListTpl':abspath+"/pages/build/tpl/bank-list-dialog.tpl.html",
                    'addCardTpl':abspath + '/pages/build/mods/user/bundled/add-card.tpl.html',
                    "trackBase":abspath + "/pages/build/js/tracker/track-base",
                    "dialog":abspath+"/res/tmp/merge/dialog.index",
                    "float":abspath+"/res/tmp/merge/dialog-float.index",
                    "feedbackTpl":abspath+"/res/tmp/merge/backTop.feedback.tpl.html",
                    "floatTpl":abspath+"/res/tmp/merge/dialog-float.float.tpl.html",
                    "previewTpl":abspath+"/res/tmp/merge/dialog-float.preview.tpl.html",
                    "rotate":abspath+"/res/tmp/merge/rotate.index",
                    "backTop":abspath+"/res/tmp/merge/backTop.index-1",
                    "tip":abspath+"/res/tmp/merge/tip.index",
                    "paging":abspath+"/res/tmp/merge/paging.index",
                    "pagingTpl":abspath+"/res/tmp/merge/paging.index.tpl.html",
                    "formValidate":abspath+"/pages/build/js/common/formValidate",
                },
                shim: {},
                optimize: "none" //default uglify
            };
        }))
        // .pipe(rename(function (path) {
        //     console.log (path.dirname);
        // }))
        .pipe(rename({
            basename: "_"
        }))
        .pipe(gulp.dest('./pages/'));
});

gulp.task('image', function () {
    return gulp.src('./res/**/*.{png,jpg,gif,ico}')
        .pipe(cache(
            imagemin({
                optimizationLevel: 5, //类型：Number  默认：3  取值范围：0-7（优化等级）
                progressive: true, //类型：Boolean 默认：false 无损压缩jpg图片
                interlaced: true, //类型：Boolean 默认：false 隔行扫描gif进行渲染
                multipass: true //类型：Boolean 默认：false 多次优化svg直到完全优化
            })
        )).pipe(gulp.dest('./pages/build/'));
});
/*//添加iconfont压缩
gulp.task('Iconfont', function(){
    return gulp.src('./res/!**!/!*.svg')
        .pipe(iconfont({
            fontName: 'myfont', // required
            prependUnicode: true, // recommended option
            formats: ['ttf', 'eot', 'woff'], // default, 'woff2' and 'svg' are available
            timestamp: runTimestamp, // recommended to get consistent builds when watching files
        }))
        .on('glyphs', function(glyphs, options) {
            // CSS templating, e.g.
            console.log(glyphs, options);
        })
        .pipe(gulp.dest('./pages/build/'));
});*/
// 图标字体作务
gulp.task('iconfont', function () {
    return gulp.src('./res/**/*.{eot,svg,ttf,woff}')                  //挑选需要的iconfont文件（demo等不需要的不选择）
        .pipe(gulp.dest('./pages/build/'));                             //从开发区（dev）移到打包区（test）文件夹
});
//git动画作务
gulp.task('gif', function () {
    return gulp.src('./res/**/*.{gif}')
        .pipe(gulp.dest('./pages/build/'));
});

gulp.task("watch",function (){
    isDebug = true;
    gulp.watch('./res/**/*.html', ['tpl']);
    //gulp.watch('./res/**/*.less', ['less']);
    //gulp.watch('./res/**/*.js', ['js']);
    gulp.watch (['./res/**/*.css','./res/**/*.less'],['less']);
    gulp.watch ('./res/**/*.js',['rjs']);
    gulp.watch('./res/**/*.{png,jpg,gif,ico}', ['image']);
});

gulp.task('clean', function(cb) {
    return del(['./pages/build/','./res/tmp/merge/*.css','./res/tmp/merge/*.js'], cb)
});

gulp.task('default', ['clean'], function() {
    gulp.start('clean','less', 'tpl','image','iconfont','gif','rjs');
});
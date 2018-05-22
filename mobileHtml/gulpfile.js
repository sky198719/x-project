var gulp        = require('gulp'),
    minifycss   = require('gulp-minify-css'),
    uglify      = require('gulp-uglify'),
    rename      = require('gulp-rename'),
    del         = require('del'),
    less        = require('gulp-less'),
    sass        = require('gulp-sass'),
    mincss      = require('gulp-clean-css'),
    gulpif      = require('gulp-if'),
    imagemin    = require('gulp-imagemin'),
    pngquant    = require('imagemin-pngquant'),
    //确保本地已安装gulp-cache [cnpm install gulp-cache --save-dev]
    cache       = require('gulp-cache'),
    gulptpl     = require('gulp-tpl2mod'),
    //juicer = require('gulp-juicer'),
    //gulptpl = require('gulp-tpl2mod'),
    through     = require('through2'),
    //amdOptimize = require('amd-optimize'),
    amdOptimize = require('gulp-requirejs-optimize'),
    plumber     = require('gulp-plumber'),
    concatCss   = require('gulp-concat-css'),
    //concat = require('gulp-concat'),
    base64      = require('gulp-base64'),
    htmlmin     = require('gulp-htmlmin'),
    shell       = require('gulp-shell'),
    isDebug     = false; //是否压缩代码


gulp.task("tpl", function (res) {
    return gulp.src("res/**/*.tpl.html")
        .pipe(gulptpl({
            prefix: 'define(function(){return ',
            suffix: '});'
        }))
        .pipe(rename({
            extname: ".js"
        }))
        .pipe(gulp.dest('./html/'));
});


gulp.task('js', function () {
    gulp.src(['./res/**/*.min.js']).pipe(gulp.dest('./html/'));
    return gulp.src(['./res/**/*.js', '!./res/**/*.min.js'])
        .pipe(gulpif(!isDebug, uglify()))
        .pipe(gulp.dest('./html/'));
});


var host = "test-static.xxd.com", versionMuiPath = "/mui/1.0.14/build/", versionPcPath = "/pc/1.1.1/build/";

var mergeFile = require("./gulp-merge-files.js");
gulp.task('mergeRemoteJs', function (cb) {
    "use strict";
    del(['./res/tmp/merge/*.js'], cb);
    var mergeList = [{
        hostname: host,
        modName : "dialog",
        path    : versionMuiPath + "dialog/index.js"
    }, {
        hostname: host,
        modName : "login",
        path    : versionMuiPath + "login/h5.js"
    }, {
        hostname: host,
        modName : "login",
        path    : versionMuiPath + "login/index.tpl.html.js"
    }, {
        hostname: host,
        modName : "register",
        path    : versionMuiPath + "register/h5.js"
    }, {
        hostname: host,
        modName : "register",
        path    : versionMuiPath + "register/index.tpl.html.js"
    }, {
        hostname: host,
        modName : "tracker",
        path    : versionPcPath + "js/tracker/track.js"
    }, {
        hostname: host,
        modName : "tracker",
        path    : versionPcPath + "js/tracker/track-base.js"
    }, {
        hostname: host,
        modName : "base",
        path    : versionPcPath + "js/common/base.js"
    }];
    return gulp.src(['./res/tmp/tmp.js'])
        .pipe(mergeFile(mergeList));
});

gulp.task("mergeRemoteCss", function (cb) {
    del(['./res/tmp/merge/*.css', './res/tmp/merge/*.less'], cb);
    var mergeList = [{
        hostname: host,
        modName : "reset",
        path    : versionPcPath + "css/reset.css"
    }, {
        hostname: host,
        modName : "dialog",
        path    : versionMuiPath + "dialog/index.css"
    }, {
        hostname: host,
        modName : "login",
        path    : versionMuiPath + "login/index.css"
    }, {
        hostname: host,
        modName : "register",
        path    : versionMuiPath + "register/index.css"
    }];
    return gulp.src(['./res/tmp/tmp.js'])
        .pipe(mergeFile(mergeList));
});


gulp.task('rjs', ["mergeRemoteJs", "js", 'tpl'], function () {
    "use strict";
    var abspath = __dirname;
    return gulp.src(['./html/**/*/*.js'])
        .pipe(amdOptimize(function (file) {
            return {
                paths   : {
                    "requirejs"     : abspath + "/res/js/require.min",
                    "jquery"        : abspath + "/res/js/jquery-3.2.0.min",
                    "md5"           : abspath + "/res/js/jQuery.md5",
                    "IScroll"       : abspath + "/res/js/iscroll-probe.min",
                    "Swiper"        : abspath + "/res/js/swiper.jquery.min",
                    "xxdBridge"     : abspath + "/res/js/xxd-jsBridge",
                    "echarts"       : abspath + "/res/js/echarts.min",
                    "echarts_simple": abspath + "/res/js/echarts.simple.min",
                    "echarts_common": abspath + "/res/js/echarts.common.min",
                    "com"           : abspath + "/res/js/xui-common-v1.0.0.min",
                    "xui_app_v100"  : abspath + "/res/js/xui-app-v1.0.0.min",
                    "xui_user_v101" : abspath + "/res/js/xui-user-v1.0.1.min",
                    "xui_user_v102" : abspath + "/res/js/xui-user-v1.0.2.min",
                    "mcLayer_v100"  : abspath + "/res/js/mc-layer-v1.0.0.min",
                    "mcPrompt_v300" : abspath + "/res/js/mc-prompt-v3.0.0.min",
                    "juicer"        : abspath + "/res/js/juicer.min",
                    "base"          : abspath + "/res/tmp/merge/base.base",
                    "json"          : abspath + "/res/js/json2",
                    "vTicker"       : abspath + "/res/js/vticker",
                    "dialog"        : abspath + "/res/tmp/merge/dialog.index",
                    "login"         : abspath + "/res/tmp/merge/login.h5",
                    "register"      : abspath + "/res/tmp/merge/register.h5",
                    "loginTpl"      : abspath + "/res/tmp/merge/login.index.tpl.html",
                    "registerTpl"   : abspath + "/res/tmp/merge/register.index.tpl.html",
                    "track"         : abspath + "/res/tmp/merge/tracker.track",
                    "trackBase"     : abspath + "/res/tmp/merge/tracker.track-base",
                    "china"         : abspath + "/html/js/china",
                    "countUp"       : abspath + "/html/js/countUp",
                    "store"         : abspath + "/html/js/store.store",
                    "vticker"       : abspath + "/res/js/vticker"
                },
                shim    : {},
                optimize: "none" //default uglify
            };
        }))
        .pipe(rename(function (path) {
            console.log(path.dirname);
        }))
        .pipe(gulp.dest('./html/'));
});


gulp.task('html', function () {
    var options = {
        collapseWhitespace           : true,
        collapseBooleanAttributes    : true,
        removeComments               : true,
        removeEmptyAttributes        : true,
        removeScriptTypeAttributes   : true,
        removeStyleLinkTypeAttributes: true,
        minifyJS                     : true,
        minifyCSS                    : true
    };
    gulp.src('res/**/*.html')
        .pipe(htmlmin(options))
        .pipe(gulp.dest('./html/'));
});


// 编译压缩SCSS任务
gulp.task('sass', ["mergeRemoteCss"], function () {
    // 全部样式合并打包
    gulp.src('./res/**/css/main.scss')                                //需要编译的scss文件夹路经
        .pipe(sass())                                                        //编译scss
        .pipe(mincss())                                                      //压缩编译好的css
        .pipe(rename({suffix: '.min'}))                                      //压缩的css重命名为*.min.css
        .pipe(gulp.dest('./html/'));                              //编译压缩好的css文件输出到测试区（test）
});
gulp.task('less', ["mergeRemoteCss"], function () {
    // 全部样式合并打包
    gulp.src('./res/**/css/main.less')                                //需要编译的scss文件夹路经
        .pipe(less())                                                        //编译scss
        .pipe(mincss())                                                      //压缩编译好的css
        .pipe(rename({suffix: '.min'}))                                      //压缩的css重命名为*.min.css
        .pipe(gulp.dest('./html/'));                              //编译压缩好的css文件输出到测试区（test）
});
// 图标字体作务
gulp.task('fonts', function () {
    return gulp.src('./res/**/fonts/*.{eot,svg,ttf,woff}')                  //挑选需要的iconfont文件（demo等不需要的不选择）
        .pipe(gulp.dest('./html/'));                             //从开发区（dev）移到打包区（test）文件夹
});


gulp.task("mergeCss", ["mergeRemoteCss"], function () {
    console.log("css merge is done!");
    return gulp.src(["./res/**/*combo*.less"]).pipe(less()).pipe(gulpif(!isDebug, minifycss())).pipe(gulp.dest('./html/'));
});


gulp.task('image', function () {
    return gulp.src('./res/**/*.{png,jpg,gif,ico}')
    // .pipe(cache(
    //     imagemin({
    //         optimizationLevel: 5, //类型：Number  默认：3  取值范围：0-7（优化等级）
    //         progressive: true, //类型：Boolean 默认：false 无损压缩jpg图片
    //         interlaced: true, //类型：Boolean 默认：false 隔行扫描gif进行渲染
    //         multipass: true, //类型：Boolean 默认：false 多次优化svg直到完全优化
    //         verbose: 1   //logger
    //     })
    // ))
        .pipe(gulp.dest('./html/'));
});


var rev          = require('gulp-rev'),
    revCollector = require('gulp-rev-collector');
//gulp.src(['./res/**/*.min.js']).pipe( gulp.dest('./html/') );
//CSS生成文件hash编码并生成 rev-manifest.json文件名对照映射
gulp.task('revFile', ["rjs", "mergeCss", "html"], function () {
    return gulp.src(["./html/**/*.css", "./html/**/*comb*.js"])
        .pipe(rev())
        .pipe(gulp.dest('./html/'))
        .pipe(rename(function (path) {
            var dirname  = path.dirname;
            path.dirname = dirname.replace("", '');
        }))
        .pipe(rev.manifest({
            path : 'rev.json',
            merge: false
        }))
        .pipe(gulp.dest('./html/'));
});


gulp.task('md5', ["revFile"], function () {
    return gulp.src(['./html/**/*.json', './html/**/*.html'])
        .pipe(
            revCollector(
                {
                    replaceReved   : true,
                    dirReplacements: {}
                }
            )
        )
        .pipe(gulp.dest('./html'));
});

gulp.task("watch", function () {
    isDebug = true;
    gulp.watch('./res/**/*.tpl.html', ['tpl']);
    gulp.watch('./res/**/*.less', ['mergeCss']);
    gulp.watch('./res/**/*.js', ['rjs']);
    gulp.watch('./res/**/*.{png,jpg,gif,ico}', ['image']);
});

gulp.task('clean', function (cb) {
    return del(['./html/'], cb)
});


gulp.task('default', ['clean'], function () {
    return gulp.start('clean', 'js', 'tpl', 'image', 'rjs', 'sass', 'fonts', 'html', 'md5', 'less');
});

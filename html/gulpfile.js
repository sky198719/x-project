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
    htmlmin = require('gulp-htmlmin'),
    isDebug = false,//是否压缩代码
    svgSymbols = require('gulp-svg-symbols');  //svg文件压缩

gulp.task('svgsprites', function () {
    return gulp.src('./res/**/*.svg')
    //.pipe(svgSymbols())
        .pipe(gulp.dest('./html/'));
});

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

gulp.task('less', function () {
    return gulp.src([
        './res/**/*.css', './res/**/*.less'])
        .pipe(less())
        .pipe(gulpif(!isDebug, minifycss()))
        .pipe(gulp.dest('./html/'));
});

gulp.task('js', function () {
    gulp.src(['./res/**/*.min.js']).pipe(gulp.dest('./html/'));
    return gulp.src(['./res/**/*.js', '!./res/**/*.min.js'])
        .pipe(gulpif(!isDebug, uglify()))
        .pipe(gulp.dest('./html/'));
});


gulp.task('html', function () {
    var options = {
        collapseWhitespace: true,
        collapseBooleanAttributes: true,
        removeComments: true,
        removeEmptyAttributes: true,
        removeScriptTypeAttributes: true,
        removeStyleLinkTypeAttributes: true,
        minifyJS: true,
        minifyCSS: true
    };
    gulp.src('res/**/*.html')
        .pipe(htmlmin(options))
        .pipe(gulp.dest('./html/'));
});


var commonCss = [
    './res/tmp/merge/reset.reset.css',
    './res/tmp/merge/header.header.css',
    './res/tmp/merge/footer.footer.css',
    './res/tmp/merge/tip.index.css',
    './res/tmp/merge/dialog.index.css',
    './res/tmp/merge/dialog-float.index.css',
    './res/tmp/merge/backTop.index-1.css'
];

var host = "test-static.xxd.com", versionMuiPath = "/mui/1.0.21/build/", versionPcPath = "/pc/1.4.3/build/";
var mergeFile = require("./gulp-merge-files.js");
gulp.task('mergeRemoteJs', function (cb) {
    "use strict";
    del(['./res/tmp/merge/*.js'], cb);
    var mergeList = [{
        hostname: host,
        modName: "header",
        path: versionMuiPath + "header/index.tpl.html.js"
    }, {
        hostname: host,
        modName: "header",
        path: versionMuiPath + "header/index.js"
    }, {
        hostname: host,
        modName: "footer",
        path: versionMuiPath + "footer/index.js"
    }, {
        hostname: host,
        modName: "footer",
        path: versionMuiPath + "footer/index.tpl.html.js"
    }, {
        hostname: host,
        modName: "backTop",
        path: versionMuiPath + "backTop/index-1.js"
    }, {
        hostname: host,
        modName: "dialog",
        path: versionMuiPath + "dialog/index.js"
    }, {
        hostname: host,
        modName: "dialog-float",
        path: versionMuiPath + "dialog-float/index.js"
    }, {
        hostname: host,
        modName: "dialog-float",
        path: versionMuiPath + "dialog-float/preview.tpl.html.js"
    }, {
        hostname: host,
        modName: "backTop",
        path: versionMuiPath + "backTop/feedback.tpl.html.js"
    }, {
        hostname: host,
        modName: "dialog-float",
        path: versionMuiPath + "dialog-float/float.tpl.html.js"
    }, {
        hostname: host,
        modName: 'tip',
        path: versionMuiPath + "tip/index.js"
    }, {
        hostname: host,
        modName: "base",
        path: versionPcPath + "js/common/base.js"
    }, {
        hostname: host,
        modName: "pcHeader",
        path: versionPcPath + "mods/header.js"
    }, {
        hostname: host,
        modName: "pcFooter",
        path: versionPcPath + "mods/footer.js"
    }, {
        hostname: host,
        modName: "tracker",
        path: versionPcPath + "js/tracker/track.js"
    }, {
        hostname: host,
        modName: "tracker",
        path: versionPcPath + "js/tracker/track-base.js"
    }, {
        hostname: host,
        modName: "store",
        path: versionPcPath + "mods/store.js"
    }];
    return gulp.src(['./res/tmp/tmp.js'])
        .pipe(mergeFile(mergeList));
});

gulp.task("mergeRemoteCss", function (cb) {
    del(['./res/tmp/merge/*.css', './res/tmp/merge/*.less'], cb);
    del(['./res/tmp/merge/*.css'], cb);
    var mergeList = [{
        hostname: host,
        modName: "header",
        path: versionPcPath + "mods/header.css"
    }, {
        hostname: host,
        modName: "footer",
        path: versionPcPath + "mods/footer.css"
    }, {
        hostname: host,
        modName: "reset",
        path: versionPcPath + "css/reset.css"
    }, {
        hostname: host,
        modName: "tip",
        path: versionMuiPath + "tip/index.css"
    }, {
        hostname: host,
        modName: "backTop",
        path: versionMuiPath + "backTop/index-1.css"
    }, {
        hostname: host,
        modName: "dialog",
        path: versionMuiPath + "dialog/index.css"
    }, {
        hostname: host,
        modName: "dialog-float",
        path: versionMuiPath + "dialog-float/index.css"
    }];
    return gulp.src(['./res/tmp/tmp.js'])
        .pipe(mergeFile(mergeList));
});

//
gulp.task('cssCompintr', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/compintr.css'))
        .pipe(concatCss("compintr.css"))
        .pipe(gulp.dest('./html/help/'))
});


gulp.task('cssEmplprofile', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/emplprofile.css'))
        .pipe(concatCss("emplprofile.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssFinalinfo', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/finalinfo.css'))
        .pipe(concatCss("finalinfo.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssRiskmanager', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/riskmanager.css'))
        .pipe(concatCss("riskmanager.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssRunreports', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/runreports.css'))
        .pipe(concatCss("runreports.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssProdintr', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/prodintr.css'))
        .pipe(concatCss("prodintr.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssSafesecurity', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/safesecurity.css'))
        .pipe(concatCss("safesecurity.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssPlatform', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/platform.css'))
        .pipe(concatCss("platform.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssCompprofile', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/compprofile.css'))
        .pipe(concatCss("compprofile.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssDatadisclose', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/datadisclose.css'))
        .pipe(concatCss("datadisclose.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssEnterculture', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/enterculture.css'))
        .pipe(concatCss("enterculture.css"))
        .pipe(gulp.dest('./html/help/'))
});
// NEW cssTestquarterly
gulp.task('cssTestquarterly', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/testquarterly.css'))
        .pipe(concatCss("testquarterly.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssNewsreport', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/newsreport.css'))
        .pipe(concatCss("newsreport.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssJoinus', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/joinus.css'))
        .pipe(concatCss("joinus.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssQualihonor', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/qualihonor.css'))
        .pipe(concatCss("qualihonor.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssTeammanager', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/teammanager.css'))
        .pipe(concatCss("teammanager.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssPartner', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/partner.css'))
        .pipe(concatCss("partner.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssDevelphistory', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/develphistory.css'))
        .pipe(concatCss("develphistory.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssContactus', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/contactus.css'))
        .pipe(concatCss("contactus.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssComplirun', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/complirun.css'))
        .pipe(concatCss("complirun.css"))
        .pipe(gulp.dest('./html/help/'))
});

gulp.task('cssHappyw', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/wednesday/wednesday.css'))
        .pipe(concatCss("wednesday.css"))
        .pipe(gulp.dest('./html/wednesday/'))
});

gulp.task('cssFinancing', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/financing/financing.css'))
        .pipe(concatCss("financing.css"))
        .pipe(gulp.dest('./html/financing/'))
});

gulp.task('cssFifth', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/fifthyears/fifthyears.css'))
        .pipe(concatCss("fifthyears.css"))
        .pipe(gulp.dest('./html/fifthyears/'))
});

gulp.task('cssRisk', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/riskcontrol/riskcontrol.css'))
        .pipe(concatCss("riskcontrol.css"))
        .pipe(gulp.dest('./html/riskcontrol/'))
});

gulp.task('cssSeven', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/sevendays/sevendays.css'))
        .pipe(concatCss("sevendays.css"))
        .pipe(gulp.dest('./html/sevendays/'))
});

gulp.task('cssAbest', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/abest/best.css'))
        .pipe(concatCss("best.css"))
        .pipe(gulp.dest('./html/abest/'))
});

gulp.task('cssAprilfools', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/aprilfools/aprilfools.css'))
        .pipe(concatCss("aprilfools.css"))
        .pipe(gulp.dest('./html/aprilfools/'))
});
gulp.task('cssBankDeposition', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/bankDeposition/bankDeposition.css'))
        .pipe(concatCss("bankDeposition.css"))
        .pipe(gulp.dest('./html/bankDeposition/'))
});

gulp.task('cssAprilMemeber', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/aprilMember/aprilMember.css'))
        .pipe(concatCss("aprilMember.css"))
        .pipe(gulp.dest('./html/aprilMember/'))
});
gulp.task('cssJuneMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/juneMember/juneMember.css'))
        .pipe(concatCss("juneMember.css"))
        .pipe(gulp.dest('./html/juneMember/'))
});
gulp.task('cssMaintain', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/maintain/maintain.css'))
        .pipe(concatCss("maintain.css"))
        .pipe(gulp.dest('./html/maintain/'))
});
gulp.task('cssJulyMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/julyMember/julyMember.css'))
        .pipe(concatCss("julyMember.css"))
        .pipe(gulp.dest('./html/julyMember/'))
});
gulp.task('cssAugustMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/augustMember/augustMember.css'))
        .pipe(concatCss("augustMember.css"))
        .pipe(gulp.dest('./html/augustMember/'))
});
gulp.task('cssSeptemberMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/septemberMember/septemberMember.css'))
        .pipe(concatCss("septemberMember.css"))
        .pipe(gulp.dest('./html/septemberMember/'))
});

gulp.task('cssSeptemberPromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/septemberMember/septemberPromotion.css'))
        .pipe(concatCss("septemberPromotion.css"))
        .pipe(gulp.dest('./html/septemberMember/'))
});
gulp.task('cssOctoberMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/octoberMember/octoberMember.css'))
        .pipe(concatCss("octoberMember.css"))
        .pipe(gulp.dest('./html/octoberMember/'))
});
gulp.task('cssOctoberPromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/octoberMember/octoberPromotion.css'))
        .pipe(concatCss("octoberPromotion.css"))
        .pipe(gulp.dest('./html/octoberMember/'))
});
//双十一
gulp.task('cssDoubleEleven', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/doubleEleven/doubleEleven.css'))
        .pipe(concatCss("doubleEleven.css"))
        .pipe(gulp.dest('./html/doubleEleven/'))
});
gulp.task('cssDoublePromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/doubleFestival/doublePromotion.css'))
        .pipe(concatCss("doublePromotion.css"))
        .pipe(gulp.dest('./html/doubleFestival/'))
});
gulp.task('cssDecemberMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/decemberMember/decemberMember.css'))
        .pipe(concatCss("decemberMember.css"))
        .pipe(gulp.dest('./html/decemberMember/'))
});
gulp.task('cssDecemberPromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/decemberMember/decemberPromotion.css'))
        .pipe(concatCss("decemberPromotion.css"))
        .pipe(gulp.dest('./html/decemberMember/'))
});
gulp.task('cssDecemberMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/decemberMember/decemberMember.css'))
        .pipe(concatCss("decemberMember.css"))
        .pipe(gulp.dest('./html/decemberMember/'))
});
gulp.task('csshappyWed', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/happyWed/happyWed.css'))
        .pipe(concatCss("happyWed.css"))
        .pipe(gulp.dest('./html/happyWed/'))
});
gulp.task('cssFocusing', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/focusing/focusing.css'))
        .pipe(concatCss("focusing.css"))
        .pipe(gulp.dest('./html/focusing/'))
});
gulp.task('cssReportingApril', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportingApril.css'))
        .pipe(concatCss("reportingApril.css"))
        .pipe(gulp.dest('./html/help/'))
});
gulp.task('cssReportingJanuary', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportingJanuary.css'))
        .pipe(concatCss("reportingJanuary.css"))
        .pipe(gulp.dest('./html/help/'))
});
gulp.task('cssBreaktenbilion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/breaktenbilion/breaktenbilion.css'))
        .pipe(concatCss("breaktenbilion.css"))
        .pipe(gulp.dest('./html/breaktenbilion/'))
});
gulp.task('cssReportingMay', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportingMay.css'))
        .pipe(concatCss("reportingMay.css"))
        .pipe(gulp.dest('./html/help/'))
});
gulp.task('cssStoragepage', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/storagepage/storagepage.css'))
        .pipe(concatCss("storagepage.css"))
        .pipe(gulp.dest('./html/storagepage/'))
});
gulp.task('cssAnnouncement', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/announcement/announcement.css'))
        .pipe(concatCss("announcement.css"))
        .pipe(gulp.dest('./html/announcement/'))
});

gulp.task('cssCommitment', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/commitment/commitment.css'))
        .pipe(concatCss("commitment.css"))
        .pipe(gulp.dest('./html/commitment/'))
});
// landPages
gulp.task('cssRedpacket', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/landPages/redpacket.css'))
        .pipe(concatCss("redpacket.css"))
        .pipe(gulp.dest('./html/landPages/'));
});
gulp.task('cssSevengold', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/landPages/sevengold.css'))
        .pipe(concatCss("sevengold.css"))
        .pipe(gulp.dest('./html/landPages/'));
});
gulp.task('cssNewhand', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/landPages/newhand.css'))
        .pipe(concatCss("newhand.css"))
        .pipe(gulp.dest('./html/landPages/'));
});
gulp.task('cssMonthgold', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/landPages/monthgold.css'))
        .pipe(concatCss("monthgold.css"))
        .pipe(gulp.dest('./html/landPages/'));
});
gulp.task('cssGivedata', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/landPages/givedata.css'))
        .pipe(concatCss("givedata.css"))
        .pipe(gulp.dest('./html/landPages/'));
});
gulp.task('cssWednesdayLand', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/landPages/happy-wednesday.css'))
        .pipe(concatCss("happy-wednesday.css"))
        .pipe(gulp.dest('./html/landPages/'));
});
gulp.task('cssReportingJune', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportingJune.css'))
        .pipe(concatCss("reportingJune.css"))
        .pipe(gulp.dest('./html/help/'))
});
//新手引导页
gulp.task('cssGuide', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/introduce/guide.css'))
        .pipe(concatCss("guide.css"))
        .pipe(gulp.dest('./html/introduce/'))
});
//新新理财全面升级大改版
gulp.task('cssPromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/promotion/promotion.css'))
        .pipe(concatCss("promotion.css"))
        .pipe(gulp.dest('./html/promotion/'))
});
gulp.task('cssReportingJuly', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportingJuly.css'))
        .pipe(concatCss("reportingJuly.css"))
        .pipe(gulp.dest('./html/help/'))
});
gulp.task('cssRedAugust', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/redAugust/redAugust.css'))
        .pipe(concatCss("redAugust.css"))
        .pipe(gulp.dest('./html/redAugust/'));
});

gulp.task('cssBorrowScan', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/borrowScan/borrowScan.css'))
        .pipe(concatCss("borrowScan.css"))
        .pipe(gulp.dest('./html/borrowScan/'));
});
gulp.task('cssReportAugust', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportAugust.css'))
        .pipe(concatCss("reportAugust.css"))
        .pipe(gulp.dest('./html/help/'));
});
//九月运营报告
gulp.task('cssReportSept',["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportSept.css'))
        .pipe(concatCss("reportSept.css"))
        .pipe(gulp.dest('./html/help/'))
});
//十月运营报告
gulp.task('cssReportOctober',["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportOctober.css'))
        .pipe(concatCss("reportOctober.css"))
        .pipe(gulp.dest('./html/help/'))
});
//十一月运营报告
gulp.task('cssReportNov',["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/reportNov.css'))
        .pipe(concatCss("reportNov.css"))
        .pipe(gulp.dest('./html/help/'))
});
//欢乐星期三
gulp.task('cssHappyWednesday', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/happyWednesday/happyWednesday.css'))
        .pipe(concatCss("happyWednesday.css"))
        .pipe(gulp.dest('./html/happyWednesday/'));
});
gulp.task('cssHappyWednesdayV2', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/happy-wednesdayV2/happy-wednesdayV2.css'))
        .pipe(concatCss("happy-wednesdayV2.css"))
        .pipe(gulp.dest('./html/happy-wednesdayV2/'));
});
gulp.task('cssHappyWednesdayV3', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/happy-wednesdayV3/happy-wednesdayV3.css'))
        .pipe(concatCss("happy-wednesdayV3.css"))
        .pipe(gulp.dest('./html/happy-wednesdayV3/'));
});
gulp.task('cssHappyWedV3Promotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/happy-wednesdayV3/happy-wedPromotion.css'))
        .pipe(concatCss("happy-wedPromotion.css"))
        .pipe(gulp.dest('./html/happy-wednesdayV3/'));
});
gulp.task('cssHappyWednesdayV4', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/happy-wednesdayV4/happy-wednesdayV4.css'))
        .pipe(concatCss("happy-wednesdayV4.css"))
        .pipe(gulp.dest('./html/happy-wednesdayV4/'));
});
gulp.task('cssHappyWedV4Promotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/happy-wednesdayV4/happy-wedPromotion.css'))
        .pipe(concatCss("happy-wedPromotion.css"))
        .pipe(gulp.dest('./html/happy-wednesdayV4/'));
});
//机构信息
gulp.task('cssOrganization', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/organization.css'))
        .pipe(concatCss("organization.css"))
        .pipe(gulp.dest('./html/help/'));
});

//11月会员日
gulp.task('cssNovemberMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/novemberMember/novemberMember.css'))
        .pipe(concatCss("novemberMember.css"))
        .pipe(gulp.dest('./html/novemberMember/'))
});
gulp.task('cssNovemberPromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/novemberMember/novemberPromotion.css'))
        .pipe(concatCss("novemberPromotion.css"))
        .pipe(gulp.dest('./html/novemberMember/'))
});
gulp.task('cssSevengoldv2', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/landPages/sevengoldv2.css'))
        .pipe(concatCss("sevengoldv2.css"))
        .pipe(gulp.dest('./html/landPages/'))
});
gulp.task('cssDoubleFestival', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/doubleFestival/doubleFestival.css'))
        .pipe(concatCss("doubleFestival.css"))
        .pipe(gulp.dest('./html/doubleFestival/'));
});
//嗨翻星期五
gulp.task('cssFridayRedpacket', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/friday-redpacket/fridayRedpacket.css'))
        .pipe(concatCss("fridayRedpacket.css"))
        .pipe(gulp.dest('./html/friday-redpacket/'));
});
//嗨翻星期五推广页
gulp.task('cssFridayFri', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/friday-redpacket/fridayFri.css'))
        .pipe(concatCss("fridayFri.css"))
        .pipe(gulp.dest('./html/friday-redpacket/'));

});
//会员日（2018）
gulp.task('cssJanMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/vipDay/JanMember.css'))
        .pipe(concatCss("JanMember.css"))
        .pipe(gulp.dest('./html/vipDay/'))
});
gulp.task('cssJanPromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/vipDay/JanPromotion.css'))
        .pipe(concatCss("JanPromotion.css"))
        .pipe(gulp.dest('./html/vipDay/'))
});
gulp.task('cssFebMember', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/vipDay/FebMember.css'))
        .pipe(concatCss("FebMember.css"))
        .pipe(gulp.dest('./html/vipDay/'))
});
gulp.task('cssFebPromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/vipDay/FebPromotion.css'))
        .pipe(concatCss("FebPromotion.css"))
        .pipe(gulp.dest('./html/vipDay/'))
});
//帮助中心信息披露
gulp.task('cssRecord', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/record.css'))
        .pipe(concatCss("record.css"))
        .pipe(gulp.dest('./html/help/'));
});
gulp.task('cssMajor', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/major.css'))
        .pipe(concatCss("major.css"))
        .pipe(gulp.dest('./html/help/'));
});
gulp.task('cssInformation', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/information.css'))
        .pipe(concatCss("information.css"))
        .pipe(gulp.dest('./html/help/'));
});
gulp.task('cssCommit', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/help/commitment.css'))
        .pipe(concatCss("commitment.css"))
        .pipe(gulp.dest('./html/help/'));
});
//六周年活动
gulp.task('cssSixPromotion', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/sixYears/promotion.css'))
        .pipe(concatCss("promotion.css"))
        .pipe(gulp.dest('./html/sixYears/'));
});
gulp.task('cssSixIndex', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/sixYears/index.css'))
        .pipe(concatCss("index.css"))
        .pipe(gulp.dest('./html/sixYears/'));
});
gulp.task('cssSixIndex', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/sixYearsV2/index.css'))
        .pipe(concatCss("index.css"))
        .pipe(gulp.dest('./html/sixYearsV2/'));
});

//运营报告（2018）
gulp.task('cssJanReport', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/operationReport/reportJan.css'))
        .pipe(concatCss("reportJan.css"))
        .pipe(gulp.dest('./html/operationReport/'))
});
//运营报告（2018-02）
gulp.task('cssFebReport', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/operationReport/reportFeb.css'))
        .pipe(concatCss("reportFeb.css"))
        .pipe(gulp.dest('./html/operationReport/'))
});
//安全征信
gulp.task('cssSafetyCredit', ["mergeRemoteCss", 'less'], function (cb) {
    return gulp.src(commonCss.concat('./html/safety-creditInfo/index.css'))
        .pipe(concatCss("index.css"))
        .pipe(gulp.dest('./html/safety-creditInfo/'))
});
gulp.task("mergeCss", ["cssCompintr",
        "cssSixIndex",
        "cssSixPromotion",
        "cssRecord",
        "cssMajor",
        "cssInformation",
        "cssCommit",
        "cssEmplprofile",
        "cssFinalinfo",
        "cssRiskmanager",
        "cssRunreports",
        "cssProdintr",
        "cssSafesecurity",
        "cssPlatform",
        "cssCompprofile",
        "cssDatadisclose",
        "cssEnterculture",
        "cssNewsreport",
        "cssJoinus",
        "cssQualihonor",
        "cssTeammanager",
        "cssPartner",
        "cssDevelphistory",
        "cssContactus",
        "cssComplirun",
        "cssHappyw",
        "cssFinancing",
        "cssFifth",
        "cssRisk",
        "cssSeven",
        "cssAbest",
        "cssBankDeposition",
        "cssAprilfools",
        "cssAprilMemeber",
        "cssFocusing",
        "cssReportingApril",
        "cssBreaktenbilion",
        "cssTestquarterly",
        "cssJuneMember",
        "cssMaintain",
        "cssReportingMay",
        "cssStoragepage",
        "cssReportingJanuary",
        "cssAnnouncement",
        "cssCommitment",
        "cssJulyMember",
        "cssAugustMember",
        "cssSeptemberMember",
        "cssSeptemberPromotion",
        "cssOctoberMember",
        "cssOctoberPromotion",
        "cssDoubleEleven",
        "cssDoublePromotion",
        "cssDecemberMember",
        "cssDecemberPromotion",
        "csshappyWed",
        "cssReportingMay",
        "cssAnnouncement",
        "cssRedpacket",
        "cssSevengold",
        "cssNewhand",
        "cssMonthgold",
        "cssGivedata",
        "cssWednesdayLand",
        "cssReportingJune",
        "cssGuide",
        "cssPromotion",
        "cssReportingJuly",
        "cssRedAugust",
        "cssBorrowScan",
        "cssReportAugust",
        "cssReportSept",
        "cssReportOctober",
        "cssReportNov",
        "cssHappyWednesday",
        "cssHappyWednesdayV2",
        "cssOrganization",
        "cssNovemberMember",
        "cssNovemberPromotion",
        "cssSevengoldv2",
        "cssHappyWednesdayV3",
        "cssHappyWedV3Promotion",
        "cssHappyWednesdayV4",
        "cssHappyWedV4Promotion",
        "cssDoubleFestival",
        "cssFridayRedpacket",
        "cssFridayFri",
        "cssJanMember",
        "cssJanPromotion",
        "cssFebMember",
        "cssFebPromotion",
        "cssJanReport",
        "cssFebReport",
        "cssSafetyCredit"
    ],
    function () {
        "use strict";
        console.log("css merge is done!");
        return gulp.src(["./html/**/*.css"]).pipe(gulpif(!isDebug, minifycss())).pipe(gulp.dest('./html/'));
        ;

    }
);


gulp.task('rjs', ["mergeRemoteJs", "js", 'tpl'], function () {
    "use strict";
    console.log("path:" + __dirname);
    var abspath = __dirname;
    return gulp.src(['./html/**/*/combo*.js'])
        .pipe(amdOptimize(function (file) {
            return {
                paths: {
                    "requirejs": abspath + "/html/js/require.min",
                    "jquery": abspath + "/html/js/jquery.min",
                    "juicer": abspath + "/html/js/juicer.min",
                    "md5": abspath + "/html/js/jquery.md5",
                    "base": abspath + "/res/tmp/merge/base.base",
                    "json": abspath + "/html/js/json2",
                    "echarts": abspath + "/html/js/echarts.min",
                    "echartsAll": abspath + "/html/js/echarts",
                    "chinaMap": abspath + "/html/js/china",
                    "countUp": abspath + "/html/js/countUp",
                    "paging": abspath + "/html/js/paging",
                    "jqueryui": abspath + "/html/js/jquery-ui-1.10.3.min",
                    "fullPage": abspath + "/html/js/fullPage.min",
                    "header": abspath + "/res/tmp/merge/header.index",
                    "headerTpl": abspath + "/res/tmp/merge/header.index.tpl.html",
                    "footerTpl": abspath + "/res/tmp/merge/footer.index.tpl.html",
                    "footer": abspath + "/res/tmp/merge/footer.index",
                    "pcHeader": abspath + "/res/tmp/merge/pcHeader.header",
                    "pcFooter": abspath + "/res/tmp/merge/pcFooter.footer",
                    "track": abspath + "/res/tmp/merge/tracker.track",
                    "trackBase": abspath + "/res/tmp/merge/tracker.track-base",
                    "store": abspath + "/res/tmp/merge/store.store",
                    "tip": abspath + "/res/tmp/merge/tip.index",
                    "backTop": abspath + "/res/tmp/merge/backTop.index-1",
                    "dialog": abspath + "/res/tmp/merge/dialog.index",
                    "float": abspath + "/res/tmp/merge/dialog-float.index",
                    "previewTpl": abspath + "/res/tmp/merge/dialog-float.preview.tpl.html",
                    "feedbackTpl": abspath + "/res/tmp/merge/backTop.feedback.tpl.html",
                    "floatTpl": abspath + "/res/tmp/merge/dialog-float.float.tpl.html",
                    "swiper": abspath + "/html/js/swiper.jquery.umd.min",
                    "swiper2": abspath + "/html/js/swiper2.min",
                    "vRoll": abspath+"/html/js/vRoll-v1",
                    "allRoll": abspath+"/html/js/allRoll"
                },
                shim: {},
                optimize: "none" //default uglify
            };
        }))
        .pipe(rename(function (path) {
            console.log(path.dirname);
        }))
        //.pipe(rev())
        .pipe(gulp.dest('./html/'));
});


gulp.task('image', function () {
    return gulp.src('./res/**/*.{png,jpg,gif,ico}')
        .pipe(cache(
            imagemin({
                optimizationLevel: 5, //类型：Number  默认：3  取值范围：0-7（优化等级）
                progressive: true, //类型：Boolean 默认：false 无损压缩jpg图片
                interlaced: true, //类型：Boolean 默认：false 隔行扫描gif进行渲染
                multipass: true, //类型：Boolean 默认：false 多次优化svg直到完全优化
                verbose: 1   //logger
            })
        ))
        .pipe(gulp.dest('./html/'));
});


var rev = require('gulp-rev'),
    revCollector = require('gulp-rev-collector');
//gulp.src(['./res/**/*.min.js']).pipe( gulp.dest('./html/') );
//CSS生成文件hash编码并生成 rev-manifest.json文件名对照映射
gulp.task('revFile', ["rjs", "mergeCss", "html"], function () {
    return gulp.src(["./html/**/*.css", "./html/**/*comb*.js"])
        .pipe(rev())
        .pipe(gulp.dest('./html/'))
        .pipe(rename(function (path) {
            var dirname = path.dirname;
            //path.dirname = ""+dirname;
            // if (dirname.indexOf("/js") > 0  ) {
            //     path.dirname =  "./js";
            // } else if (dirname.indexOf("/css") > 0  ){
            //     path.dirname =  "./css";
            // }else {
            //     path.dirname = "";
            // }
        }))
        .pipe(rev.manifest({
            path: 'rev.json',
            merge: false,
        }))
        .pipe(gulp.dest('./html/'));
});


gulp.task('md5', ["revFile"], function () {
    return gulp.src(['./html/**/*.json', './html/**/*.html'])
        .pipe(
            revCollector(
                {
                    replaceReved: true,
                    dirReplacements: {
                        // 'all':function (value) {
                        //     console.log (value);
                        //     return value;
                        // }
                    }
                }
            )
        )
        .pipe(gulp.dest('./html'));
});


gulp.task("watch", function () {
    isDebug = true;
    gulp.watch('./res/**/*.tpl.html', ['tpl']);
    //gulp.watch('./res/**/*.less', ['less']);
    //gulp.watch('./res/**/*.js', ['js']);
    gulp.watch('./res/**/*.less', ['mergeCss']);
    gulp.watch('./res/**/*.js', ['rjs']);
    // gulp.watch('./res/**/*.{png,jpg,gif,ico}', ['image']);
    gulp.watch('./res/**/*.html', ['html']);
});

gulp.task('clean', function (cb) {
    return del(['./html/'], cb)
});


gulp.task('default', ['clean'], function () {
    return gulp.start('clean', 'less', 'js', 'tpl', 'image', 'mergeCss', 'rjs', 'html', 'md5', 'svgsprites');
});

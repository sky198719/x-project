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
    juicer = require('gulp-juicer'),
    base64 = require('gulp-base64'),
    isDebug = false; //是否压缩代码

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




gulp.task('less', function () {
    return gulp.src([
        '!./res/*/demo/**/*','./res/**/*.css','./res/**/*.less'])
        .pipe(less())
        .pipe(gulpif(!isDebug, minifycss()))
        .pipe(base64({
            extensions: ['png','jpg'],
            maxImageSize: 28*1024, // bytes
            debug: true
        }))
        .pipe(gulp.dest('./pages/build/'));
});

gulp.task('js' , function (){
    return gulp.src(['!./res/*/demo/**/*','./res/**/*.js'])
        .pipe(gulpif( !isDebug , uglify()))
        .pipe(gulp.dest('./pages/build/'));
});

gulp.task('image', function () {
    return gulp.src(['!./res/*/demo/**/*','./res/**/*.{png,jpg,gif,ico}'])
        .pipe(cache(
            imagemin({
                optimizationLevel: 5, //类型：Number  默认：3  取值范围：0-7（优化等级）
                progressive: true, //类型：Boolean 默认：false 无损压缩jpg图片
                interlaced: true, //类型：Boolean 默认：false 隔行扫描gif进行渲染
                multipass: true //类型：Boolean 默认：false 多次优化svg直到完全优化
            })
        ))
        .pipe(gulp.dest('./pages/build/'));
});

gulp.task("watch",function (){
    isDebug = true;
    gulp.watch('./res/**/*.html', ['tpl']);
    gulp.watch('./res/**/*.less', ['less']);
    gulp.watch('./res/**/*.js', ['js']);
    gulp.watch('./res/**/*.{png,jpg,gif,ico}', ['image']);
});

gulp.task('clean', function(cb) {
    return del(['./pages/build/'], cb)
});

gulp.task('default', ['clean'], function() {
    gulp.start('clean','less','js', 'tpl',  'image');
});
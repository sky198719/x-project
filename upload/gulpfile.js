var gulp = require('gulp'),
	jshint = require('gulp-jshint'),
	minifycss = require('gulp-minify-css'),
	uglify = require('gulp-uglify')

gulp.task('jshint',function(){
	return gulp.src(['res/javascripts/**/*'])
		.pipe(jshint())
		.pipe(jshint.reporter('default'));
});

gulp.task('uglify',function(){
	return gulp.src(['res/javascripts/**/*'])
		.pipe(uglify())
		.pipe(gulp.dest('pages/javascripts/'));
});

gulp.task('minifycss',function(){
    return gulp.src(['res/styles/**/*'])
		.pipe(minifycss())
        .pipe(gulp.dest('pages/styles/'));
});

gulp.task('template',function(){
    return gulp.src(['res/template/*'])
        .pipe(gulp.dest('pages/template/'));
});

gulp.task('html',function(){
    return gulp.src(['res/*'])
        .pipe(gulp.dest('pages/'));
});

gulp.task('imagemin',function(){
    return gulp.src(['res/images/**/**/*'])
		.pipe(gulp.dest('pages/images/'));
});

gulp.task('default',['jshint'],function(){
    gulp.start('uglify','minifycss','html','template','imagemin');
});
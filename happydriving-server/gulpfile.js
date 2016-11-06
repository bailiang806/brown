/**
 * Created by gaoying on 15/10/28.
 */
var gulp = require('gulp');
var uglify = require("gulp-uglify");
var minifyCss = require("gulp-minify-css");
var imagemin = require('gulp-imagemin');
var minifyHtml = require("gulp-minify-html");


//主要压缩controller，router下得js下的文件，effect/css 下css文件，所有img,所有的html文件
gulp.task('controllerjs',function(){
    gulp.src('src/main/webapp/Restructures/controller/*.js').pipe(uglify()).pipe(gulp.dest('src/main/webapp/temp/controller'));
    console.log('compress is ok');
});

gulp.task('routerjs',function(){
    gulp.src('src/main/webapp/Restructures/router/*.js').pipe(uglify()).pipe(gulp.dest('src/main/webapp/temp/router'));
    console.log('compress is ok');
});

gulp.task('effectcss',function(){
    gulp.src('src/main/webapp/Restructures/effect/css/*.css').pipe(minifyCss()).pipe(gulp.dest('src/main/webapp/temp/effect/css'));
    console.log('compress is ok');
});

gulp.task('imgscompress', function () {
    return gulp.src('src/main/webapp/Restructures/imgs/*/*.*')
        .pipe(imagemin())
        .pipe(gulp.dest('src/main/webapp/temp/imgs'));
});

gulp.task('htmlprogress', function () {
    gulp.src('src/main/webapp/Restructures/templates/*/*.html') // 要压缩的html文件
        .pipe(minifyHtml()) //压缩
        .pipe(gulp.dest('src/main/webapp/temp/templates'));
});
















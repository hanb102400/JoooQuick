const { series, parallel, watch } = require('gulp');
const { src, dest } = require('gulp');
const webserver = require('gulp-webserver');
const del = require('delete');

function copyHtml() {

    return src('src/**/*.html')
        .pipe(dest('dist/'));
}

function copyVue() {
    return src('src/**/*.vue')
        .pipe(dest('dist/'));
}

function copyCss() {
    return src('src/**/*.css')
        .pipe(dest('dist/'));
}

function copyJs() {
    return src('src/**/*.js')
        .pipe(dest('dist/'));
}

function copyRes() {
    src('src/**/*.ttf')
        .pipe(dest('dist/'));

    src('src/**/*.woff')
        .pipe(dest('dist/'));

    src('src/**/*.woff2')
        .pipe(dest('dist/'));
}


function clean(cb) {
    del(['dist/*'], cb);
}

function build(cb) {
    copyHtml();
    copyVue();
    copyCss();
    copyJs();
    copyRes();
    cb();
}

watch('src/**/*.html', copyHtml);
watch('src/**/*.vue', copyVue);
watch('src/**/*.js', copyJs);
watch('src/**/*.css', copyCss);

function server(cb) {
    // body omitted
    src('dist').pipe(webserver({ //该任务调用的模块
        path: '/',
        host: '127.0.0.1',
        port: '1357',
        livereload: true, //自动刷新
        direactoryListing: true, //指定目录找到
        open: true, //自动打开
        proxies: [{
            source: '/api/',
            target: 'http://127.0.0.1:8003/' //要请求的接口
        }]
    }));
    cb();
}

exports.build = build;
exports.default = series(clean, build, server);
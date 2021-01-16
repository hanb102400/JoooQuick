/** Vue.import **/
/*Vue.import = function(path, func) {
    if (func == undefined || func == null) {
        return httpVueLoader(path);
    } else {
        console.log("httpVueLoader(path)()", httpVueLoader(path)())
        return httpVueLoader(path)().then(data => func(data));
    }

}*/
Vue.import = function(paths, func) {
    if (typeof paths === "string") {
        if (!func) {
            return httpVueLoader(paths);
        } else {
            httpVueLoader(paths)().then((data) => func(data));
        }
    } else if (Array.isArray(paths)) {
        const promises = paths.map((item) => httpVueLoader(item)());
        if (!func) {
            Promise.all(promises).then((data) => {
                return data;
            });
        } else {
            Promise.all(promises).then((data) => {
                func(...data);
            });
        }
    }
};


/** net **/
//设置baseurl
//axios.defaults.baseURL = 'http://127.0.0.1/';
//设置超时时间
axios.defaults.timeout = 30000;
axios.defaults.withCredentials = true;
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8';

axios.interceptors.response.use(
    (response) => {
        // 如果返回的状态码为200，说明接口请求成功，可以正常拿到数据
        if (response.status === 200) {
            return Promise.resolve(response);
        } else {
            return Promise.reject(response);
        }
    },
    (error) => {
        if (error.response.status) {
            switch (error.response.status) {
                case 401:
                    console.log(error.response);
                    if (error.response.data.loginUrl) {
                        window.document.location = error.response.data.loginUrl;
                    } else {

                        router.push({
                            path: "/login",
                        });
                    };
                    break;
                case 403:
                    console.log('errr', error);
                    break;
                case 404:
                    // 404请求不存在
                    ELEMENT.Message.error({ message: '您访问的地址不存在!' });
                    break;

                default:
                    // 其他错误，直接抛出错误提示
                    ELEMENT.Message.error({ message: '系统错误!' });
            }
        }
        return Promise.reject(error.response);
    }
);

var Net = {};
Net.getUrl = function(url) {
    if (url.startsWith("https://") || url.startsWith("http://")) {
        return url;
    } else if (url.startsWith("/")) {
        return "/api" + url;
    } else {
        return "/api/" + url;
    }
};

//封装get方法,
Net.get = function(url, data) {
    return new Promise((resolve, reject) => {
        axios
            .get(Net.getUrl(url), { params: data })
            .then((resp) => {
                resolve(resp.data);
            })
            .catch((err) => {
                reject(err.data);
            });
    });
};

// 封装post方法,发送json请求
Net.post = function(url, data) {
    return new Promise((resolve, reject) => {
        axios
            .post(Net.getUrl(url), data)
            .then((resp) => {
                resolve(resp.data);
            })
            .catch((err) => {
                reject(err.data);
            });
    });
};

// 封装post方法,发送Form请求
Net.postForm = function(url, data) {
    return new Promise((resolve, reject) => {
        axios
            .post(Net.getUrl(url), Qs.stringify(data), {
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
            })
            .then((resp) => {
                resolve(resp.data);
            })
            .catch((err) => {
                reject(err.data);
            });
    });
};

//loadScript
Net.loadScript = function(src, callback) {
    var script = document.createElement("script"),
        head = document.getElementsByTagName("head")[0];
    script.type = "text/javascript";
    script.async = false;
    script.src = src;
    if (script.addEventListener) {
        script.addEventListener(
            "load",
            function() {
                callback && callback();
            },
            false
        );
    } else if (script.attachEvent) {
        script.attachEvent("onreadystatechange", function() {
            var target = window.event.srcElement;
            if (target.readyState == "loaded") {
                callback && callback();
            }
        });
    }
    head.appendChild(script);
};
/*
var App = {}

App.getCookie = function() {
    return document.cookie;
};

App.getToken = function() {
    return '';
};*/
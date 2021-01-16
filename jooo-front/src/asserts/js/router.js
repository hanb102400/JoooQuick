// 可以从其他文件 import 进来
const Login = { template: '<div>Login</div>' }
const Bar = { template: '<div>bar</div>' }

// 2. 定义路由
// 每个路由应该映射一个组件。 其中"component" 可以是
// 通过 Vue.extend() 创建的组件构造器，
// 或者，只是一个组件配置对象。
// 我们晚点再讨论嵌套路由。



var ExHeader = Vue.import("/layout/component/ExHeader.vue")

const routes = [{
        path: '/login',
        component: Vue.import('/login.vue')
    }, {
        path: '/',
        redirect: '/component'
    }, {
        path: '/component',
        component: Vue.import('/module/component.vue'),
        children: [{
            path: '/component/table',
            component: Vue.import('/module/component/table.vue')
        }, {
            path: '/component/form',
            component: Vue.import('/module/component/form.vue')
        }]
    },
    {
        path: '/setting',
        component: Vue.import('/module/setting.vue'),
        children: [{
            path: '/rbac/user',
            component: Vue.import('/module/rbac/user.vue')
        }, {
            path: '/rbac/role',
            component: Vue.import('/module/rbac/role.vue')
        }, {
            path: '/rbac/menu',
            component: Vue.import('/module/rbac/menu.vue')
        }, {
            path: '/rbac/depart',
            component: Vue.import('/module/rbac/depart.vue')
        }]

    },
    {
        path: '/form',
        component: Vue.import('/module/table.vue')
    }
]



// 3. 创建 router 实例，然后传 `routes` 配置
// 你还可以传别的配置参数, 不过先这么简单着吧。
const router = new VueRouter({
    routes // (缩写) 相当于 routes: routes
})

Vue.component('treeselect', VueTreeselect.Treeselect)

// 4. 创建和挂载根实例。
// 记得要通过 router 配置参数注入路由，
// 从而让整个应用都有路由功能
Vue.import('App.vue', c => {

    const app = new Vue({
        router,
        render: h => h(c)
    }).$mount('#app')

})
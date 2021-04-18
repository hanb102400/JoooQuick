<template>
<div>
    <div class="header">

        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="navbar-wrapper">
                <div class="navbar-brand" logo-theme="theme1">管理后台系统</div>
            </div>
            <div class="container-fluid">

                <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleOpen" router>
                    <el-menu-item index="/setting" >
                        <span slot="title">系统配置</span>
                    </el-menu-item>
                    <el-menu-item index="/component">
                        <span slot="title">常用组件</span>
                    </el-menu-item>
                    <el-menu-item @click="logout"> 登出</el-menu-item>
                </el-menu>

                <!-- <div class="collapse navbar-collapse">
                    <ul class="navbar-nav nav-left">
                        <li class="nav-item">
                            <router-link to="/setting">
                                系统配置
                            </router-link>
                        </li>
                        <li class="nav-item ">
                            <router-link to="/component">
                                组件示例
                            </router-link>
                        </li>
                        <li class="nav-item">
                            <a @click="logout">
                                登出
                            </a>
                        </li>
                    </ul>
                </div> -->
            </div>
        </nav>
    </div>
</div>
</template>

<script>
module.exports = {
    data() {
        return {
            active:'',
        }
    },
    computed: {
        activeIndex() {
            return  sessionStorage.getItem("admin-menu-active");
        }
    },
    methods: {
        handleOpen(key, keyPath) {
            this.active = key;
            sessionStorage.setItem("admin-menu-active", this.active);
        },
        async logout() {
	    //const resp = await Net.post('/logout/cas');
            const resp = await Net.post('/logout');
            if (resp.code == 0) {
                if (resp.logoutUrl) {
                    window.document.location = resp.logoutUrl;
                } else {
                    this.$router.push({
                        path: "/login",
                    });
                }

            }

        }
    },
    mounted() {

    }

}
</script>

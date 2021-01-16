<template>
<div>
    <div class="header">
        <nav class="navbar navbar-expand-lg navbar-light">
            <div class="navbar-wrapper">
                <div class="navbar-brand" logo-theme="theme1">管理后台系统</div>
            </div>
            <div class="container-fluid">
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav nav-left">
                        <li class="nav-item">
                            <router-link to="/setting">
                                系统配置
                            </router-link>
                        </li>
                        <li class="nav-item active">
                            <router-link to="/component">
                                常用组件
                            </router-link>
                        </li>
                        <li class="nav-item">
                            <a @click="logout">
                                登出
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
</div>
</template>

<script>
module.exports = {
    data() {
        return {}
    },
    methods: {
        async logout() {
            const resp = await Net.post('/logout/cas');
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

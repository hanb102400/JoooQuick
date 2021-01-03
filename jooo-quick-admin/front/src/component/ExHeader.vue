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
                        <li class="nav-item active">
                            <router-link to="/component">
                                常用组件
                            </router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/setting">
                                系统配置
                            </router-link>
                        </li>
                        <li class="nav-item">
                              <router-link to="/login">
                                登录
                              </router-link>
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
    }
}
</script>

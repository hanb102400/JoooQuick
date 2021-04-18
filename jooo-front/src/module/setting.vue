<template>
<div>
    <ex-header></ex-header>

    <div class="main-container-wrapper">

        <div class="sidebar">
            <!-- Start .sidebar-inner -->
            <div class="sidebar-inner">
                <!-- Start #sideNav -->
                <div class="sidebar-navigation">导航菜单</div>
                <ul id="sideNav" class="nav nav-pills nav-stacked main-menu">
                    <li class="active">
                        <a href="#">
                            <i class="fa fa-home"></i>
                            <span> 权限配置</span>
                        </a>
                        <ul class="nav sub-menu">
                            <li class="">
                                <router-link to="/rbac/depart">
                                    <span>部门管理</span>
                                </router-link>
                            </li>
                        </ul>
                        <ul class="nav sub-menu">
                            <li class="">
                                <router-link to="/rbac/user">
                                    <span>用户管理</span>
                                </router-link>
                            </li>
                        </ul>
                        <ul class="nav sub-menu">
                            <li class="">
                                <router-link to="/rbac/role">
                                    <span>角色管理</span>
                                </router-link>
                            </li>
                        </ul>
                        <ul class="nav sub-menu">
                            <li class="">
                                <router-link to="/rbac/menu">
                                    <span>菜单管理</span>
                                </router-link>
                            </li>
                        </ul>
                      
                    </li>
                    <li class="active expand">
                        <a href="#">
                            <i class="fa fa-bars"></i>
                            <span>常用组件</span>
                        </a>
                        <ul class="nav sub-menu">
                            <li class="">
                                <router-link to="/component/table">
                                    <span>表格</span>
                                </router-link>
                            </li>
                            <li class="sub-menu">
                                <router-link to="/component/form">
                                    <span>表单</span>
                                </router-link>
                            </li>
                        </ul>
                    </li>
                </ul>
                <!-- End #sideNav -->
                <!-- Start .sidebar-panel -->
                <div class="sidebar-panel"></div>
                <!-- End .sidebar-panel -->
            </div>
            <!-- End .sidebar-inner -->
        </div>

        <div class="content-wrapper">
            <div class="content-inner">
                <transition name="fade-transform" mode="out-in">
                    <router-view :key="key"></router-view>
                </transition>
            </div>
        </div>
    </div>
</div>
</template>

<script>
module.exports = {
    components: {
        exHeader: ExHeader
    },
    computed: {
        key() {
            console.log("this.$route.path", this.$route.path);
            return this.$route.path
        }
    },
    data() {
        return {

        }
    }

}
</script>

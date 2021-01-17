<template>
<div>

    <ex-header></ex-header>

    <div class="main-container-wrapper">

        <div>
            <div class="sidebar">
                <!-- Start .sidebar-inner -->
                <div class="sidebar-inner">
                    <!-- Start #sideNav -->
                    <div class="sidebar-navigation">导航菜单1</div>

                    <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" router>
                        <el-submenu index="1">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>引导页</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="1-1">引导页1</el-menu-item>
                                <el-menu-item index="1-2">引导页2</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                          <el-submenu index="2">
                            <template slot="title">
                                <i class="el-icon-location"></i>
                                <span>常用组件</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="/component/table">表格</el-menu-item>
                                <el-menu-item index="/component/form">表单</el-menu-item>
                            </el-menu-item-group>
                        </el-submenu>
                    </el-menu>

                    <ul id="sideNav" class="nav nav-pills nav-stacked main-menu">
                        <li class="active">
                            <a href="#">
                                <i class="fa fa-home"></i>
                                <span> 引导页</span>
                            </a>
                            <ul class="nav sub-menu">
                                <li class="active">
                                    <a href="#app">
                                        <span> 引导页</span>
                                    </a>
                                </li>
                            </ul>
                        </li>
                        <li class="active expand">
                            <a href="#">
                                <i class="fa fa-bars"></i>
                                <span> 常用组件</span>
                            </a>
                            <ul class="nav sub-menu">
                                <li class="">
                                    <router-link to="/component/table">
                                        <span> 表格</span>
                                    </router-link>
                                </li>
                                <li class="sub-menu">
                                    <router-link to="/component/form">
                                        <span> 表单</span>
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
            return this.$route.path
        }
    },
    data() {
        return {

        }
    }
}
</script>

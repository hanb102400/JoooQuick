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
                            <a class="nav-link" aria-current="page" href="#">常用组件</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">系统配置</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#" tabindex="-1">Disabled</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>

    <div class="main-container-wrapper">

        <div class="sidebar">
            <!-- Start .sidebar-inner -->
            <div class="sidebar-inner">
                <!-- Start #sideNav -->
                <div class="sidebar-navigation">导航菜单1</div>
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
                                <router-link to="/table">
                                    <span> 表格</span>
                                </router-link>
                            </li>
                            <li class="sub-menu">
                                <router-link to="/form">
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
                <router-view></router-view>
            </div>
        </div>
    </div>
</div>
</template>

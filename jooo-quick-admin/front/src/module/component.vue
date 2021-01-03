<template>
<div>

    <ex-header></ex-header>

    <div class="main-container-wrapper">

        <ex-sidebar></ex-sidebar>

        <div class="content-wrapper">
            <div class="content-inner">
                <router-view></router-view>
            </div>
        </div>

    </div>
</div>
</template>

<script>
module.exports = {
    components: {
        exHeader: Vue.import("/component/ExHeader.vue"),
        exSidebar: Vue.import("/component/Exsidebar.vue"),
    },
    data() {
        return {

        }
    }

}
</script>

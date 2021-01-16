<template>
<div>
    <div id="app">
        <router-view></router-view>
    </div>
</div>
</template>

<script>
module.exports = {
    name: 'app',
    data: function () {
        return {
            clientHeight: ''
        }
    },
    mounted: function () {
        /*window.onfocus = async function () {
            const res = await Net.get('/login/status')
            console.log("激活页面。请求状态：" + res);
        }*/
    }
}
</script>

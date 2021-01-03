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

    },
}
</script>

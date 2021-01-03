<template>
<div>
    <el-table :data="tableData" style="width: 100%;margin-bottom: 20px;" row-key="id" border default-expand-all :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column prop="departName" label="部门名称" sortable width="180">
        </el-table-column>
        <el-table-column prop="status" label="状态" sortable width="180">
        </el-table-column>
        <el-table-column prop="sort" label="排序">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间">
        </el-table-column>
    </el-table>
</div>
</template>

<script>
module.exports = {
    data() {
        return {
            tableData: [{
                id: 1,
                departName: '梁山总部',
                status: '启用',
                sort: 1,
                createTime: '2016-05-02',
                children: [{
                    id: 2,
                    departName: '忠义堂',
                    status: '启用',
                    sort: 1,
                    createTime: '2016-05-02',
                }, {
                    id: 3,
                    departName: '断金亭',
                    status: '启用',
                    sort: 1,
                    createTime: '2016-05-02',
                }, {
                    id: 4,
                    departName: '机密军师',
                    status: '启用',
                    sort: 1,
                    createTime: '2016-05-02',
                }, {
                    id: 5,
                    departName: '四寨四店',
                    status: '启用',
                    sort: 1,
                    createTime: '2016-05-02',
                }]
            }, {
                id: 1,
                departName: '山外山',
                status: '启用',
                sort: 1,
                createTime: '2016-05-02',
                children: [{
                        id: 1,
                        departName: '五虎八骠',
                        status: '启用',
                        sort: 1,
                        createTime: '2016-05-02'
                    },
                    {
                        id: 5,
                        departName: '四寨四店',
                        status: '启用',
                        sort: 1,
                        createTime: '2016-05-02',
                    }
                ]
            }]
        }
    },
    methods: {
        load(tree, treeNode, resolve) {
            setTimeout(() => {
                resolve([{
                    id: 31,
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄'
                }, {
                    id: 32,
                    date: '2016-05-01',
                    name: '王小虎',
                    address: '上海市普陀区金沙江路 1519 弄'
                }])
            }, 1000)
        }
    },
}
</script>

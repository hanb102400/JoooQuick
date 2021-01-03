<template>
<div>
    <el-breadcrumb class="breadcrumb" separator-class="el-icon-arrow-right">
        <el-breadcrumb-item> 组件</el-breadcrumb-item>
        <el-breadcrumb-item> 表格 </el-breadcrumb-item>
    </el-breadcrumb>
    <el-form inline :model="query" label-position="right" label-width="60px" class="query-form">
        <el-form-item label="姓名">
            <el-input v-model="query.name" placeholder="输入查询"></el-input>
        </el-form-item>
        <el-form-item label="日期">
            <el-date-picker v-model="query.date" type="daterange" placeholder="输入查询">
            </el-date-picker>
        </el-form-item>
        <el-form-item>
            <el-button type="primary">查询</el-button>
        </el-form-item>
    </el-form>
    <el-table :data="tableData" style="width: 100%" class="table" stripe border>
        <el-table-column prop="date" label="日期" width="180">
        </el-table-column>
        <el-table-column prop="name" label="姓名" width="180">
        </el-table-column>
        <el-table-column prop="address" label="地址">
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination background class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-sizes="[15, 20, 30, 50]" :page-size="10" layout="total, sizes, prev, pager, next, jumper" :current-page="pageNo" :total="totalCount">
    </el-pagination>
</div>
</template>

<script>
module.exports = {
    methods: {
        handleSizeChange: function (val) {
            this.pagesize = val;
        },
        handleCurrentChange: function (currentPage) {
            this.currentPage = currentPage;
            this.handleTableData(currentPage);
        },
        async handleTableData(pageNo) {
            var that = this;
            var params = {
                "page": pageNo
            };
            const resp = await axios.get('/user/list?page=' + pageNo) //"url"处填写后台的接口
            this.tableData = resp.data.data;
        }
    },
    data() {
        return {
            query: {

            },
            totalCount: 100,
            totalPage: 10,
            pageNo: 1,
            tableData: [{
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1517 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1519 弄'
            }, {
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1516 弄'
            }]
        }
    },
    mounted() {
        console.log("/user/list")
        this.handleTableData(1)
    }

}
</script>

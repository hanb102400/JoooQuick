<template>
<div>
    <el-breadcrumb class="breadcrumb" separator-class="el-icon-arrow-right">
        <el-breadcrumb-item> 权限管理</el-breadcrumb-item>
        <el-breadcrumb-item> 用户管理 </el-breadcrumb-item>
    </el-breadcrumb>
    <el-form inline :model="query" label-position="right" label-width="60px" class="query-form">
        <el-form-item label="用户ID">
            <el-input v-model="query.userId" placeholder="输入查询"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
            <el-input v-model="query.nickname" placeholder="输入查询">
            </el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="handleSearch()">查询</el-button>
            <el-button type="info" icon="el-icon-edit" @click="handleReset()">重置</el-button>
        </el-form-item>
    </el-form>
    <div>
        <el-button type="primary" size="mini" plain @click="handleAdd()">新增</el-button>
        <el-button type="success" size="mini" plain>导出</el-button>
    </div>
    <el-table :data="tableData" style="width: 100%" class="table" stripe border>
        <el-table-column prop="userId" label="用户ID" width="180">
        </el-table-column>
        <el-table-column prop="username" label="账号" width="180">
        </el-table-column>
        <el-table-column prop="nickname" label="姓名">
        </el-table-column>
        <el-table-column prop="mobile" label="手机号">
        </el-table-column>
        <el-table-column prop="status" label="状态">
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button size="mini" type="danger" @click="handleRemove(scope.$index, scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination background class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-sizes="[10, 15, 20, 50]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :current-page="pageNo" :total="totalCount">
    </el-pagination>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="showDialog" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="用户昵称" prop="nickName">
                        <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="归属部门" prop="deptId">
                        <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="手机号码" prop="phonenumber">
                        <el-input v-model="form.phonenumber" placeholder="请输入手机号码" maxlength="11" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item v-if="form.userId == undefined" label="用户名称" prop="userName">
                        <el-input v-model="form.userName" placeholder="请输入用户名称" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item v-if="form.userId == undefined" label="用户密码" prop="password">
                        <el-input v-model="form.password" placeholder="请输入用户密码" type="password" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="用户性别">
                        <el-select v-model="form.sex" placeholder="请选择">
                            <el-option v-for="dict in sexOptions" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="状态">
                        <el-radio-group v-model="form.status">
                            <el-radio v-for="dict in statusOptions" :key="dict.dictValue" :label="dict.dictValue">{{dict.dictLabel}}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="岗位">
                        <el-select v-model="form.postIds" multiple placeholder="请选择">
                            <el-option v-for="item in postOptions" :key="item.postId" :label="item.postName" :value="item.postId" :disabled="item.status == 1"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="角色">
                        <el-select v-model="form.roleIds" multiple placeholder="请选择">
                            <el-option v-for="item in roleOptions" :key="item.roleId" :label="item.roleName" :value="item.roleId" :disabled="item.status == 1"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="24">
                    <el-form-item label="备注">
                        <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
        </div>
    </el-dialog>
</div>
</template>

<script>
module.exports = {
    methods: {
        cancel: function () {
            this.showDialog = false;
        },
        handleSearch: async function () {
            this.pageNo = 1;
            this.loadingData();
        },
        handleReset: function () {
            this.query = {}
        },
        handleAdd: function () {
            this.form = {};
            this.showDialog = true;
        },
        handleEdit:function(){
            this.showDialog = true;
        },
        handleRemove: async function (index, row) {

            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                const resp =await Net.post('/sysUser/remove', {
                    userId: row.userId
                });
                if (resp.code == 0) {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.loadingData();
                }

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });

        },
        handleSizeChange: function (val) {
            this.pagesize = val;
        },
        handleCurrentChange: function (pageNo) {
            this.pageNo = pageNo;
            this.loadingData();
        },
        loadingData: async function () {
            var that = this;
            //请求后台数据
            const resp = await Net.post('/sysUser/list?pageNo=' + this.pageNo + "&pageSize=" + this.pageSize, this.query)
            this.tableData = resp.data.content;
            this.pageNo = resp.data.pageNo
            this.pageSize = resp.data.pageSize
            this.totalCount = resp.data.totalCount
        }
    },
    data() {
        return {
            showDialog: false,
            query: {

            },
            form: {

            },
            totalCount: 100,
            totalPage: 10,
            pageNo: 1,
            pageSize: 10,
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
        this.pageNo = 1;
        this.loadingData()
    }
}
</script>

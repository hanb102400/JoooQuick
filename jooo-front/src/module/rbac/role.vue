<template>
<div>
    <el-breadcrumb class="breadcrumb" separator-class="el-icon-arrow-right">
        <el-breadcrumb-item> 权限管理</el-breadcrumb-item>
        <el-breadcrumb-item> 角色管理 </el-breadcrumb-item>
    </el-breadcrumb>
    <el-form inline :model="query" label-position="right" label-width="160px" class="query-form" size="mini">
        <el-form-item label="角色ID">
            <el-input v-model="query.roleId" placeholder="输入查询"></el-input>
        </el-form-item>
        <el-form-item label="角色名称">
            <el-input v-model="query.roleName" placeholder="输入查询">
            </el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" icon="el-icon-search"  @click="handleSearch()">查询</el-button>
            <el-button type="info" icon="el-icon-refresh" @click="handleReset()">重置</el-button>
        </el-form-item>
    </el-form>
  <div class="tool-bar">
        <el-button type="primary" size="mini" plain @click="handleAdd()">新增</el-button>
        <el-button type="success" size="mini" plain>导出</el-button>
    </div>
    <el-table v-loading="loading" :data="tableData" style="width: 100%" class="table" stripe border size="mini">
        <el-table-column prop="roleId" label="角色ID" width="180">
        </el-table-column>
        <el-table-column prop="roleName" label="角色名称" width="220">
        </el-table-column>
        <el-table-column prop="roleCode" label="角色编码">
        </el-table-column>
        <el-table-column prop="scope" label="授权范围">
        </el-table-column>
        <el-table-column label="状态">
            <template slot-scope="scope">
                <el-switch v-model="scope.row.status" active-color="#13ce66" :active-value="0" :inactive-value="1"></el-switch>
            </template>
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button type="text" icon="el-icon-delete" size="mini" @click="handleRemove(scope.$index, scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination background class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 15, 20, 50]" :page-size="pageSize" :current-page="pageNo" :total="totalCount">
    </el-pagination>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="showDialog" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px" size="mini">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="角色名称" prop="roleName">
                        <el-input v-model="form.roleName" placeholder="请输入用户昵称" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="角色编码" prop="roleCode">
                        <el-input v-model="form.roleCode" placeholder="请输入用户昵称" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="授权范围" prop="scope">
                        <el-input v-model="form.scope" placeholder="请输入手机号码" maxlength="11" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="状态">
                        <el-radio-group v-model="form.status">
                            <el-radio v-for="opt in statusOptions" :key="opt.key" :label="opt.val">{{opt.key}}</el-radio>
                        </el-radio-group>
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
            <el-button type="primary" @click="handleSubmit">确 定</el-button>
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
            if (this.$refs['query']) {
                this.$refs['query'].resetFields();
            }
        },
        handleAdd: function () {
            if (this.$refs['form']) {
                this.$refs['form'].resetFields();
            }
            this.showDialog = true;
        },
        handleEdit: async function (index, row) {
            if (this.$refs['form']) {
                this.$refs['form'].resetFields();
            }
            this.showDialog = true;
            const resp = await Net.get('/sysRole/detail', {
                roleId: row.roleId
            });
            this.form = resp.data;
        },
        handleSubmit: function () {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    if (this.form.roleId != undefined) {
                        this.submitEdit();
                    } else {
                        this.submitAdd();
                    }
                } else {
                    console.log('error validate!!');
                    return false;
                }
            });

        },
        handleRemove: function (index, row) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                const resp = await Net.get('/sysRole/remove', {
                    roleId: row.roleId
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
        submitAdd: async function () {
            const resp = await Net.post('/sysRole/add', this.form);
            if (resp.code == 0) {
                this.$message({
                    type: 'success',
                    message: '添加成功!'
                });
                this.showDialog = false;
                this.loadingData();
                this.$refs['form'].resetFields();
            } else {
                this.$message({
                    type: 'error',
                    message: resp.message
                });
            }
        },
        submitEdit: async function () {
            const resp = await Net.post('/sysRole/edit', this.form);
            if (resp.code == 0) {
                this.$message({
                    type: 'success',
                    message: '添加成功!'
                });
                this.showDialog = false;
                this.loadingData();
                this.$refs['form'].resetFields();
            } else {
                this.$message({
                    type: 'error',
                    message: resp.message
                });
            }
        },
        handleSizeChange: function (pageSize) {
            this.pageSize = pageSize;
            this.loadingData();
        },
        handleCurrentChange: function (pageNo) {
            this.pageNo = pageNo;
            this.loadingData();
        },
        loadingData: async function () {
            var that = this;
            //请求后台数据
            const resp = await Net.post('/sysRole/list?pageNo=' + this.pageNo + "&pageSize=" + this.pageSize, this.query)
            this.tableData = resp.data.content;
            this.pageNo = resp.data.pageNo
            this.pageSize = resp.data.pageSize
            this.totalCount = resp.data.totalCount
        }
    },
    data() {
        return {
            showDialog: false,
            query: {},
            form: {},
            rules: {
                roleName: [{
                    required: true,
                    message: '请输入用户名',
                    trigger: 'blur'
                }],
                roleCode: [{
                    required: true,
                    message: '请输入昵称',
                    trigger: 'blur'
                }]
            },
            totalCount: 0,
            totalPage: 0,
            pageNo: 1,
            pageSize: 15,
            tableData: [],
            statusOptions: [{
                    key: '正常',
                    val: 0
                },
                {
                    key: '停用',
                    val: 1
                }
            ]
        }
    },
    mounted() {
        this.pageNo = 1;
        this.loadingData()
    }
}
</script>

<template>
<div>
    <el-breadcrumb class="breadcrumb" separator-class="el-icon-arrow-right">
        <el-breadcrumb-item> 权限管理</el-breadcrumb-item>
        <el-breadcrumb-item> 用户管理 </el-breadcrumb-item>
    </el-breadcrumb>
    <el-form inline :model="query" label-position="right" label-width="100px" class="query-form" size="mini">
        <el-form-item label="用户ID">
            <el-input v-model="query.userId" placeholder="输入查询"></el-input>
        </el-form-item>
        <el-form-item label="昵称">
            <el-input v-model="query.nickname" placeholder="输入查询">
            </el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="handleSearch()">查询</el-button>
            <el-button type="info" icon="el-icon-refresh" @click="handleReset()">重置</el-button>
        </el-form-item>
    </el-form>
    <div class="tool-bar">
        <el-button type="primary" size="mini" plain icon="el-icon-plus" @click="handleAdd()">新增</el-button>
        <el-button type="success" size="mini" plain icon="el-icon-edit" @click="handleEdit()">修改</el-button>
        <el-button type="danger" size="mini" plain icon="el-icon-delete" @click="handleRemove()">删除</el-button>
        <el-button type="info" size="mini" plain icon="el-icon-upload" @click="handleImport()">导入</el-button>
        <el-button type="info" size="mini" plain icon="el-icon-download" @click="handleExport()">导出</el-button>
    </div>
    <el-table v-loading="loading" :data="tableData" style="width: 100%" class="table" stripe border size="mini" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55">
        </el-table-column>
        <el-table-column prop="userId" label="用户ID" width="180">
        </el-table-column>
        <el-table-column prop="username" label="账号" width="180">
        </el-table-column>
        <el-table-column prop="nickname" label="姓名">
        </el-table-column>
        <el-table-column prop="mobile" label="手机号">
        </el-table-column>
        <el-table-column label="状态">
            <template slot-scope="scope">
                <el-tag type="success" size="mini" v-if="scope.row.status==0">正常</el-tag>
                <el-tag type="info" size="mini" v-if="scope.row.status==1">停用</el-tag>
            </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间">
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-button type="text" icon="el-icon-edit" size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                <el-button type="text" icon="el-icon-delete" size="mini" @click="handleRemove(scope.$index, scope.row)">删除</el-button>
                 <el-button type="text" icon="el-icon-edit" size="mini" @click="handlePass(scope.$index, scope.row)">重置密码</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination background class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 15, 20, 50]" :page-size="pageSize" :current-page="pageNo" :total="totalCount">
    </el-pagination>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="showDialog" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
            <el-row>
                <el-col :span="12">
                    <el-form-item v-if="form.userId == undefined" label="用户账号" prop="username">
                        <el-input v-model="form.username" placeholder="请输入用户账号" />
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
                    <el-form-item label="用户昵称" prop="nickname">
                        <el-input v-model="form.nickname" placeholder="请输入用户昵称" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="手机号码" prop="mobile">
                        <el-input v-model="form.mobile" placeholder="请输入手机号码" maxlength="11" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="归属部门" prop="departId">
                        <treeselect v-model="form.departId" :options="departOptions" :show-count="true" placeholder="选择归属部门" />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="用户性别">
                        <el-select v-model="form.sex" placeholder="请选择">
                            <el-option v-for="item  in sexOptions" :key="item.value" :value="item.value" :label="item.label"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="角色">
                        <el-select v-model="form.roleIds" multiple placeholder="请选择">
                            <el-option v-for="item in roleOptions" :key="item.value" :value="item.value" :label="item.label" ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="12">
                    <el-form-item label="状态">
                        <el-radio-group v-model="form.status">
                            <el-radio v-for="item  in statusOptions" :key="item .value" :label="item .value">{{item .label}}</el-radio>
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
            <el-button @click="handleCancel">取 消</el-button>
        </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.showDialog" width="500px" append-to-body>
        <el-upload class="upload-demo" ref="upload" action="/api/user/import" :limit="1" :data="uploadData" :on-exceed="handleExceed" :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitImport">开始导入</el-button>

            <div slot="tip" class="el-upload__tip">只能上传Excel文件，且不超过100MB</div>
        </el-upload>
        <el-button size="small" type="warning" style="margin-top: 40px;">下载模板</el-button>

    </el-dialog>
</div>
</template>

<script>
module.exports = {
    data() {
        return {
            title: '',
            showDialog: false,
            upload: {
                title: '导入用户',
                showDialog: false

            },
            uploadData: {

            },
            loading: true,
            singleSelected: false,
            multipleSelected: false,
            multipleSelection: [],
            query: {},
            form: {},
            rules: {
                username: [{
                        required: true,
                        message: '请输入用户名, 必须为字母数字下划线',
                        pattern: /^[a-zA-Z0-9_]+$/,
                        trigger: 'blur'
                    },
                    {
                        min: 4,
                        max: 20,
                        message: '长度在 4 到 20 个字符',
                        trigger: 'blur'
                    }
                ],
                nickname: [{
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
            departOptions:[],
            roleOptions:[],
            statusOptions: [{
                    label: '正常',
                    value: 0
                },
                {
                    label: '停用',
                    value: 1
                }
            ],
            sexOptions: [{
                    label: '未知',
                    value: 0
                }, {
                    label: '男',
                    value: 1
                },
                {
                    label: '女',
                    value: 2
                }
            ]
        }
    },
    methods: {
        handleExceed(files, fileList) {
            if (files.length > 1) {
                this.$message.warning(`当前限制选择1个文件`);
                return false;
            }
        },
        submitImport() {
            this.$refs.upload.submit();
        },
        handleImport() {
            this.upload.showDialog = true;
        },
        handleCancel: function () {
            this.showDialog = false;
        },
        handleSearch: async function () {
            this.pageNo = 1;
            this.loadingData();
        },
        handleReset: function () {
            this.query = {};
        },
        handlePass: function (index, row){
            this.$prompt('请输入"' + row.nickname + '"的新密码', "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消"
            }).then(async ({ value }) => {
                 const resp = await Net.post('/sysUser/resetPass', {
                    userId: row.userId,
                    password: value
                });
           }).catch(() => {});
        },
        handleAdd: function () {
            if (this.$refs['form']) {
                this.$refs['form'].resetFields();
            }
            this.form = {}
            this.title = '新增用户';
            this.showDialog = true;
        },
        handleEdit: async function (index, row) {
            if (!row && !this.singleSelected) {
                this.$message.error('请选择一项!');
                return;
            }
            if (this.$refs['form']) {
                this.$refs['form'].resetFields();
            }
            this.title = '修改用户';
            this.showDialog = true;
            const resp = await Net.get('/sysUser/detail', {
               userId: row.userId
            });
            if (resp.code == 0) {
                this.form = resp.data;
                  console.log("xxxx",this.form)
            }
        },
        handleRemove: function (index, row) {
            if (!row && !this.singleSelected && !this.multipleSelected) {
                this.$message.error('请选择一项!');
                return;
            }
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                const resp = await Net.get('/sysUser/remove', {
                    userId: this.getSelectId(row)
                });
                if (resp.code == 0) {
                    this.$message.success('删除成功!');
                    this.loadingData();
                }
            }).catch((e) => {
                console.log(e)
                this.$message.info('已取消删除')
            });
        },
        handleSubmit: function () {
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    if (this.form.userId != undefined) {
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
        submitAdd: async function () {
            const resp = await Net.post('/sysUser/add', this.form);
            if (resp.code == 0) {
                this.$message.success('添加成功!')
                this.showDialog = false;
                this.loadingData();
                this.$refs['form'].resetFields();
            } else {
                this.$message.error(resp.message);
            }
        },
        submitEdit: async function () {
            const resp = await Net.post('/sysUser/edit', this.form);
            if (resp.code == 0) {
                this.$message.success('添加成功!')
                this.showDialog = false;
                this.loadingData();
                this.$refs['form'].resetFields();
            } else {
                this.$message.error(resp.message);
            }
        },
        handleSelectionChange(selection) {
            this.singleSelected = selection.length == 1;
            this.multipleSelected = selection.length > 1;
            this.ids = selection.map(item => item.userId);
        },
        handleSizeChange: function (pageSize) {
            this.pageSize = pageSize;
            this.loadingData();
        },
        handleCurrentChange: function (pageNo) {
            this.pageNo = pageNo;
            this.loadingData();
        },
        getSelectId: function (row) {
            if (row) {
                return row.userId;
            } else {
                return this.ids.join(',')
            }
        },
        loadingData: async function () {
            var that = this;
            //请求后台数据
            const resp = await Net.post('/sysUser/list?pageNo=' + this.pageNo + "&pageSize=" + this.pageSize, this.query)
            this.tableData = resp.data.content;
            this.pageNo = resp.data.pageNo
            this.pageSize = resp.data.pageSize
            this.totalCount = resp.data.totalCount
        },
        loadingRoleOptions:async function () {
            const resp = await Net.post('/sysRole/options')
            if (resp.code == 0) {
                 this.roleOptions = resp.data;
            }
           
        },
        async refreshTreeSelect() {
            const resp = await Net.post('/sysDepart/treeSelect', this.query)
            if (resp.code == 0) {
                this.departOptions = resp.data;
            }
        },

    },
    mounted() {
        this.pageNo = 1;
        this.loadingRoleOptions();
        this.refreshTreeSelect();
        this.loadingData();
        this.loading = false;
    }
}
</script>

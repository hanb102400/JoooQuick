<template>
<div>

    <el-breadcrumb class="breadcrumb" separator-class="el-icon-arrow-right">
        <el-breadcrumb-item> 权限管理</el-breadcrumb-item>
        <el-breadcrumb-item> 部门管理 </el-breadcrumb-item>
    </el-breadcrumb>

    <el-form inline :model="query" label-position="right" label-width="100px" class="query-form" size="mini">
        <el-form-item label="部门名称">
            <el-input v-model="query.departName" placeholder="输入查询"></el-input>
        </el-form-item>
        <el-form-item label="部门状态">
            <el-input v-model="query.status" placeholder="输入查询">
            </el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="handleSearch()">查询</el-button>
            <el-button type="info" icon="el-icon-refresh" @click="handleReset()">重置</el-button>
        </el-form-item>
    </el-form>
    <div class="tool-bar">
        <el-button type="primary" size="mini" plain icon="el-icon-plus" @click="handleAdd()">新增</el-button>
    </div>
    <el-table v-loading="loading" :data="treeData" size="mini" row-key="departId" stripe default-expand-all :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column prop="departName" label="部门名称" width="300">
        </el-table-column>
        <el-table-column prop="departCode" label="部门编码" width="180">
        </el-table-column>
        <el-table-column prop="manager" label="负责人" width="180">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
        </el-table-column>
        <el-table-column label="状态" sortable width="180">
            <template slot-scope="scope">
                <el-switch v-model="scope.row.status" active-color="#13ce66" :active-value="0" :inactive-value="1"></el-switch>
            </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.$index, scope.row)">新增</el-button>
                <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete" @click="handleRemove(scope.$index, scope.row)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog :title="title" :visible.sync="showDialog" width="600px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px" >
            <el-row>
                <el-col :span="24" v-if="form.parentId !== 0">
                    <el-form-item label="上级部门" prop="parentId">
                        <treeselect v-model="form.parentId" :options="departOptions" placeholder="选择上级部门" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="部门名称" prop="departName">
                        <el-input v-model="form.departName" placeholder="请输入部门名称" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="部门编码" prop="departCode">
                        <el-input v-model="form.departCode" placeholder="请输入部门编码" />
                    </el-form-item>
                </el-col>
               
                <el-col :span="12">
                    <el-form-item label="负责人" prop="manager">
                        <el-input v-model="form.manager" placeholder="请输入负责人" maxlength="20" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="联系电话" prop="mobile">
                        <el-input v-model="form.mobile" placeholder="请输入联系电话" maxlength="11" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
                    </el-form-item>
                </el-col>
                 <el-col :span="12">
                    <el-form-item label="显示排序" prop="sort">
                        <el-input-number v-model="form.sort" controls-position="right" :min="0" />
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="部门状态">
                        <el-radio-group v-model="form.status">
                            <el-radio v-for="item in statusOptions" :key="item.value" :label="item.value">{{item.label}}</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="handleSubmit">确 定</el-button>
            <el-button @click="handleCancel">取 消</el-button>
        </div>
    </el-dialog>

</div>
</template>

<script>
module.exports = {
    data() {
        return {
            title: '选择部门',
            showDialog: false,
            loading: true,
            treeData: [],
            query: {},
            form: {},
            rules: {
                parentId: [{
                    required: true,
                    message: "上级部门不能为空",
                    trigger: "blur"
                }],
                departName: [{
                    required: true,
                    message: "部门名称不能为空",
                    trigger: "blur"
                }],
                departCode: [{
                   pattern: /^[A-Za-z1-9]{0,20}$/,
                    message: "只能输入20位以内的字母或数字",
                    trigger: "blur"
                }],
                sort: [{
                    required: true,
                    message: "排序不能为空",
                    trigger: "blur"
                }],
                mobile: [{
                    pattern: /^1[3-9][0-9]\d{8}$/,
                    message: "请输入正确的手机号码",
                    trigger: "blur"
                }],
                email: [{
                    type: "email",
                    message: "'请输入正确的邮箱地址",
                    trigger: "blur"
                }]

            },
            statusOptions: [{
                value: 0,
                label: '正常'
            }, {
                value: 1,
                label: '停止'
            }],
            departOptions: []
        }
    },
    methods: {
        handleCancel: function () {
            if (this.$refs['form']) {
                this.$refs['form'].resetFields();
            }
            this.form = {}
            this.showDialog = false;
        },
        handleAdd: function (index, row) {
            if (this.$refs['form']) {
                this.$refs['form'].resetFields();
            }
            this.form = {}
            if(row){
                this.form.parentId = row.departId;
            }
            console.log("aadd",this.form);
            this.showDialog = true;
        },
        handleEdit: async function (index, row) {
            if (this.$refs['form']) {
                this.$refs['form'].resetFields();
            }
            this.showDialog = true;
            const resp = await Net.get('/sysDepart/detail', {
                departId: row.departId
            });
            this.form = resp.data;
        },
         handleSubmit() {
             console.log("this.formthis.formthis.form",this.form)
            this.$refs['form'].validate((valid) => {
                if (valid) {
                    if (this.form.departId != undefined) {
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
        async submitAdd() {
            const resp = await Net.post('/sysDepart/add', this.form);
            if (resp.code == 0) {
                this.$message.success('添加成功!') 
                this.showDialog = false;
                this.loadTreeData();
                this.refreshTreeSelect();
                this.$refs['form'].resetFields();
            } else {
               this.$message.error(resp.message);
            }
        },
        async submitEdit() {
            const resp = await Net.post('/sysDepart/edit', this.form);
            if (resp.code == 0) {
                this.$message.success('修改成功!')
                this.showDialog = false;
                this.loadTreeData();
                this.refreshTreeSelect();
                this.$refs['form'].resetFields();
            } else {
                this.$message.error(resp.message);
            }
        },
        handleRemove: function (index, row) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                const resp = await Net.get('/sysDepart/remove', {
                    departId: row.departId
                });
                if (resp.code == 0) {
                    this.$message.success('删除成功!');
                    this.loadTreeData();
                }

            }).catch((e) => {
                this.$message.info('已取消删除'+ e)
            });
        },
        async handleSearch() {
            const resp = await Net.post('/sysDepart/tree', this.query)
            if (resp.code == 0) {
                this.treeData = resp.data.content;
            } else {
                this.$message.error('查询失败!');
            }

        },
        async loadTreeData() {
            const resp = await Net.post('/sysDepart/tree', this.query)
            console.log("resp.data.content", resp.data.content);
            this.treeData = resp.data.content;
        },
        async refreshTreeSelect(){
            const resp = await Net.post('/sysDepart/treeSelect', this.query)
            if (resp.code == 0) {
                this.departOptions = resp.data;
            }
        }
    },
    mounted() {
        this.pageNo = 1;
        this.loadTreeData()
        this.refreshTreeSelect();
        this.loading = false;
    }
}
</script>

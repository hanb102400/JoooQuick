<template>
<div>

    <el-breadcrumb class="breadcrumb" separator-class="el-icon-arrow-right">
        <el-breadcrumb-item> 权限管理</el-breadcrumb-item>
        <el-breadcrumb-item> 菜单管理 </el-breadcrumb-item>
    </el-breadcrumb>

    <el-row :gutter="20">
        <el-col :span="6">
            <div class="tool-bar">
                <el-button type="primary" size="mini" plain @click="handleAdd">新增</el-button>
                <el-button type="danger" size="mini" plain @click="handleRemove">删除</el-button>
            </div>
            <el-tree :data="treeData" :props="defaultProps" default-expand-all @node-click="handleNodeClick"></el-tree>
        </el-col>

        <el-col :span="16">
            <div class="tool-bar">
                <el-button type="primary" size="mini" plain @click="handleAddSibling">新增同级菜单</el-button>
                <el-button type="primary" size="mini" plain @click="handleAddChildren">新增子菜单</el-button>
                <el-button type="danger" size="mini" plain @click="handleRemove">删除</el-button>
            </div>

            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-row>
                    <el-col :span="6" v-if="form.parentId !== 0">

                        <el-form-item label="上级菜单" prop="parentId">
                            <el-input placeholder="选择上级菜单" v-model="input3" class="input-with-select">
                                <el-button slot="append" icon="el-icon-search" @click="handleSelect"></el-button>
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="菜单类型" prop="departType">
                            <el-input v-model="form.departType" placeholder="请输入菜单类型" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="菜单名称" prop="menuName">
                            <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="菜单编码" prop="menuCode">
                            <el-input v-model="form.menuCode" placeholder="请输入菜单CODE" />
                        </el-form-item>
                    </el-col>

                    <el-col :span="18">
                        <el-form-item label="链接地址" prop="url">
                            <el-input v-model="form.url" placeholder="请输入链接地址" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="显示排序" prop="sort">
                            <el-input-number v-model="form.sort" controls-position="right" :min="0" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="状态">
                            <el-radio-group v-model="form.status">
                                <el-radio v-for="item  in statusOptions" :key="item .value" :label="item .value">{{item .label}}</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div>
                <el-button type="primary" @click="handleSubmit">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-col>
    </el-row>
    <el-dialog :title="title" :visible.sync="showDialog" width="600px" append-to-body>
        <el-tree :data="treeData" :props="defaultProps" default-expand-all @node-click="handleNodeSelect"></el-tree>
    </el-dialog>

</div>
</template>

<script>
module.exports = {
    name: "Menu",
    data() {
        return {
            title: '选择菜单',
            showDialog: false,
            loading: true,
            treeData: [],
            selectId: null,
            selectParentId: null,
            defaultProps: {
                children: 'children',
                label: 'menuName'
            },
            form: {},
            statusOptions: [{
                value: 0,
                label: '正常'
            }, {
                value: 1,
                label: '停止'
            }],

        };
    },
    methods: {
        async handleSelect() {
            this.showDialog = true;
        },
        handleNodeSelect() {
            this.showDialog = false;
        },
        async handleSubmit() {
            const resp = await Net.post('/sysMenu/add', this.form);
            if (resp.code == 0) {
                this.$message.success('保存成功!')
                this.showDialog = false;
                this.loadTreeData();
                this.refreshTreeSelect();
                this.$refs['form'].resetFields();
            } else {
                this.$message.error(resp.message);
            }
        },
        handleAddSibling() {

        },
        handleAddChildren() {

        },
        handleAdd() {
            if (this.$refs['form']) {
                this.$refs['form'].resetFields();
            }
        },
        handleRemove() {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                const resp = await Net.get('/sysMenu/remove', {
                    menuId: this.selectId
                });
                if (resp.code == 0) {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.loadTreeData();
                }
            }).catch(() => {
                this.$message.info('已取消删除');
            });
        },
        async handleNodeClick(data) {
            this.selectId = data.menuId;
            this.selectParentId = data.parentId;
            const resp = await Net.get('/sysMenu/detail', {
                menuId: this.selectId
            });
            if (resp.code == 0) {
                this.form = resp.data;
            }
        },
        async loadTreeData() {
            const resp = await Net.post('/sysMenu/tree', this.query)
            console.log("resp.data.content", resp.data.content);
            this.treeData = resp.data.content;
        },
    },
    mounted() {
        this.loadTreeData()
        this.loading = false;
    }
};
</script>

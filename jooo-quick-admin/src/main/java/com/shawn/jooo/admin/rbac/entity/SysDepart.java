package com.shawn.jooo.admin.rbac.entity;
import com.shawn.jooo.framework.core.tree.TreeNode;
import com.shawn.jooo.framework.json.annotation.JsonDate;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 部门表
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_depart")
public class SysDepart extends TreeNode<SysDepart> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: departId  部门ID
     */
    @Id
    @Column(name="depart_id")
    private Integer departId;

    /**
     * parentId 父部门ID
     */
    @Column(name="parent_id")
    private Integer parentId;

    /**
     * parentIds 祖级列表
     */
    @Column(name="parent_ids")
    private String parentIds;

    /**
     * name 部门名称
     */
    @Column(name="depart_name")
    private String departName;

    /**
     * name 部门代码
     */
    @Column(name="depart_code")
    private String departCode;

    /**
     * sort 顺序
     */
    @Column(name="sort")
    private Integer sort;

    /**
     * manager 负责人
     */
    @Column(name="manager")
    private String manager;

    /**
     * mobile 联系电话
     */
    @Column(name="mobile")
    private String mobile;

    /**
     * email 邮箱
     */
    @Column(name="email")
    private String email;


    /**
     * status 部门状态：0正常，1停用 -1删除
     */
    @Column(name="status")
    private Integer status;

    /**
     * remark 备注
     */
    @Column(name="remark")
    private String remark;

    /**
     * createUser
     */
    @Column(name="create_user")
    private Integer createUser;

    /**
     * createTime
     */

    @Column(name = "create_time")
    @JsonDate
    private Long createTime;

    /**
     * updateUser
     */
    @Column(name="update_user")
    private Integer updateUser;

    /**
     * updateTime
     */
    @Column(name = "update_time")
    @JsonDate
    private Long updateTime;

    public SysDepart(){}

    public void setDepartId( Integer departId ) {
            this.departId = departId;
    }

    public Integer getDepartId() {
        return this.departId;
    }

    public void setParentId( Integer parentId ) {
            this.parentId = parentId;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentIds( String parentIds ) {
            this.parentIds = parentIds;
    }

    public String getParentIds() {
        return this.parentIds;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }

    public void setSort(Integer sort ) {
            this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setManager( String manager ) {
            this.manager = manager;
    }

    public String getManager() {
        return this.manager;
    }

    public void setMobile( String mobile ) {
            this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setEmail( String email ) {
            this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setRemark(String remark ) {
            this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setCreateUser( Integer createUser ) {
            this.createUser = createUser;
    }

    public Integer getCreateUser() {
        return this.createUser;
    }

    public void setCreateTime( Long createTime ) {
            this.createTime = createTime;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setUpdateUser( Integer updateUser ) {
            this.updateUser = updateUser;
    }

    public Integer getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateTime( Long updateTime ) {
            this.updateTime = updateTime;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }


}


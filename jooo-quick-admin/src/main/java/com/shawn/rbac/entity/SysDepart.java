package com.shawn.rbac.entity;
import com.shawn.jooo.framework.base.BaseBean;

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
public class SysDepart extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: departId  部门id
     */
    @Id
    @Column(name="depart_id")
    private Integer departId;

    /**
     * parentId 父部门id
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
    @Column(name="name")
    private String name;

    /**
     * sort 显示顺序
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
    private Short status;

    /**
     * remark 备注
     */
    @Column(name="remark")
    private String remark;

    /**
     * createUser 创建者
     */
    @Column(name="create_user")
    private Integer createUser;

    /**
     * createTime 创建时间
     */
    @Column(name="create_time")
    private Long createTime;

    /**
     * updateUser 更新者
     */
    @Column(name="update_user")
    private Integer updateUser;

    /**
     * updateTime 更新时间
     */
    @Column(name="update_time")
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

    public void setName( String name ) {
            this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSort( Integer sort ) {
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

    public void setStatus( Short status ) {
            this.status = status;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setRemark( String remark ) {
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


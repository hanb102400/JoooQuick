package com.shawn.admin.rbac.entity;
import com.shawn.jooo.framework.base.BaseBean;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 角色信息表
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_role")
public class SysRole extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: roleId  角色ID
     */
    @Id
    @Column(name="role_id")
    private Long roleId;

    /**
     * roleName 角色名称
     */
    @Column(name="role_name")
    private String roleName;

    /**
     * roleCode 角色权限字符串
     */
    @Column(name="role_code")
    private String roleCode;

    /**
     * scope 数据范围
     */
    @Column(name="scope")
    private String scope;

    /**
     * status 角色状态状态（0正常 1停用，-1删除）
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

    public SysRole(){}

    public void setRoleId( Long roleId ) {
            this.roleId = roleId;
    }

    public Long getRoleId() {
        return this.roleId;
    }

    public void setRoleName( String roleName ) {
            this.roleName = roleName;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleCode( String roleCode ) {
            this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setStatus(Short status ) {
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


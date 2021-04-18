package com.shawn.jooo.admin.rbac.entity;

import com.shawn.jooo.framework.base.BaseBean;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 用户角色关联表
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_user_role")
public class SysUserRole extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: id  ID
     */
    @Id
    @Column(name="id")
    private Integer id;

    /**
     * userId 用户ID
     */
    @Column(name="user_id")
    private Integer userId;

    /**
     * roleId 角色ID
     */
    @Column(name="role_id")
    private Integer roleId;

    public SysUserRole(){}

    public void setId( Integer id ) {
            this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setUserId( Integer userId ) {
            this.userId = userId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setRoleId( Integer roleId ) {
            this.roleId = roleId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }


}


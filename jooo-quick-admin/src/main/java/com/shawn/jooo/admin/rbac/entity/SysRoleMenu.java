package com.shawn.jooo.admin.rbac.entity;

import com.shawn.jooo.framework.base.BaseBean;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 角色和菜单关联表
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_role_menu")
public class SysRoleMenu extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: id  ID
     */
    @Id
    @Column(name="id")
    private Integer id;

    /**
     * roleId 角色ID
     */
    @Column(name="role_id")
    private Integer roleId;

    /**
     * menuId 菜单ID
     */
    @Column(name="menu_id")
    private Integer menuId;

    public SysRoleMenu(){}

    public void setId( Integer id ) {
            this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setRoleId( Integer roleId ) {
            this.roleId = roleId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setMenuId( Integer menuId ) {
            this.menuId = menuId;
    }

    public Integer getMenuId() {
        return this.menuId;
    }


}


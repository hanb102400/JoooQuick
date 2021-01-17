package com.shawn.admin.rbac.entity;
import com.shawn.jooo.framework.base.BaseBean;
import com.shawn.jooo.framework.core.tree.TreeNode;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 菜单权限表
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_menu")
public class SysMenu extends TreeNode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: menuId  菜单ID
     */
    @Id
    @Column(name="menu_id")
    private Integer menuId;

    /**
     * parentId 父菜单ID
     */
    @Column(name="parent_id")
    private Long parentId;

    /**
     * menuName 菜单名称
     */
    @Column(name="menu_name")
    private String menuName;

    /**
     * menuType 菜单类型
     */
    @Column(name="menu_type")
    private Short menuType;

    /**
     * menuCode 菜单权限字符串
     */
    @Column(name="menu_code")
    private String menuCode;

    /**
     * sort 显示顺序
     */
    @Column(name="sort")
    private Integer sort;

    /**
     * url 请求地址
     */
    @Column(name="url")
    private String url;

    /**
     * target 打开方式）
     */
    @Column(name="target")
    private String target;

    /**
     * status 菜单状态：0正常，1停用 -1删除
     */
    @Column(name="status")
    private Short status;

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

    /**
     * remark 备注
     */
    @Column(name="remark")
    private String remark;

    public SysMenu(){}

    public void setMenuId( Integer menuId ) {
            this.menuId = menuId;
    }

    public Integer getMenuId() {
        return this.menuId;
    }

    public void setParentId( Long parentId ) {
            this.parentId = parentId;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setMenuName( String menuName ) {
            this.menuName = menuName;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuType( Short menuType ) {
            this.menuType = menuType;
    }

    public Short getMenuType() {
        return this.menuType;
    }

    public void setMenuCode( String menuCode ) {
            this.menuCode = menuCode;
    }

    public String getMenuCode() {
        return this.menuCode;
    }

    public void setSort( Integer sort ) {
            this.sort = sort;
    }

    public Integer getSort() {
        return this.sort;
    }

    public void setUrl( String url ) {
            this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    public void setTarget( String target ) {
            this.target = target;
    }

    public String getTarget() {
        return this.target;
    }

    public void setStatus( Short status ) {
            this.status = status;
    }

    public Short getStatus() {
        return this.status;
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

    public void setRemark( String remark ) {
            this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }


}


package com.shawn.rbac.vo;

import com.shawn.jooo.framework.mybatis.annotation.OrderBy;
import com.shawn.jooo.framework.mybatis.annotation.Query;
import com.shawn.jooo.framework.mybatis.condition.Direction;
import com.shawn.jooo.framework.mybatis.condition.QueryMode;

public class SysRoleVo {

    @Query(query = QueryMode.EQ)
    @OrderBy( direction =  Direction.DESC)
    private Long roleId;

    @Query(query = QueryMode.LIKE)
    private String roleName;

    @Query(query = QueryMode.EQ)
    private String roleCode;

    @Query(query = QueryMode.LIKE)
    private String scope;

    @Query(query = QueryMode.EQ)
    private Short status;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}

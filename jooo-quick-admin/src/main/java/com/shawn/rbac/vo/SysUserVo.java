package com.shawn.rbac.vo;

import com.shawn.jooo.framework.mybatis.annotation.OrderBy;
import com.shawn.jooo.framework.mybatis.condition.Direction;
import com.shawn.jooo.framework.mybatis.condition.MatchMode;
import com.shawn.jooo.framework.mybatis.annotation.Query;
import com.shawn.jooo.framework.mybatis.condition.QueryMode;

public class SysUserVo {

    @Query(query = QueryMode.EQ)
    private Long userId;

    @Query(query = QueryMode.EQ)
    @OrderBy(direction = Direction.ASC)
    private String username;

    @Query(query = QueryMode.LIKE, match = MatchMode.ANY)
    private String nickname;

    @Query(query = QueryMode.EQ)
    private Short status;

    @Query(query = QueryMode.LIKE, match = MatchMode.ANY)
    private String mobile;

    @Query(query = QueryMode.EQ)
    @OrderBy(direction = Direction.DESC)
    private Long createTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}

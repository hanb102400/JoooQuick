package com.shawn.admin.rbac.entity;

import com.shawn.jooo.framework.base.BaseBean;
import com.shawn.jooo.framework.json.annotation.Timestamp;
import com.shawn.jooo.framework.mybatis.annotation.LogicDelete;
import com.shawn.jooo.framework.mybatis.annotation.OrderBy;
import com.shawn.jooo.framework.mybatis.annotation.QueryField;
import com.shawn.jooo.framework.mybatis.annotation.QueryForm;
import com.shawn.jooo.framework.mybatis.condition.Criteria;
import com.shawn.jooo.framework.mybatis.condition.Direction;
import com.shawn.jooo.framework.mybatis.condition.LikeMatch;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 * 用户信息表
 *
 * @author jooo.gen
 */
@Entity
@Table(name = "sys_user")
@QueryForm
public class SysUser extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID: userId  用户ID
     */
    @Id
    @Column(name = "user_id")
    @QueryField
    private Long userId;

    /**
     * departId 部门ID
     */
    @Column(name = "depart_id")
    private Long departId;

    /**
     * username 登录账号
     */
    @Column(name = "username")
    private String username;

    /**
     * password 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * nickname 昵称
     */
    @Column(name = "nickname")
    @QueryField(value = Criteria.LIKE, match = LikeMatch.ANY)
    private String nickname;

    /**
     * userType 用户类型
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * email 用户邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * mobile 手机号码
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * sex 用户性别（0未知 1男 2女 ）
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * avatar 头像路径
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * loginIp 最后登录IP
     */
    @Column(name = "login_ip")
    private String loginIp;

    /**
     * loginDate 最后登录时间
     */
    @Column(name = "login_date")
    private Long loginDate;

    /**
     * pwdUpdateDate 密码最后更新时间
     */
    @Column(name = "pwd_update_date")
    private Long pwdUpdateDate;

    /**
     * status 帐号状态（0正常 1停用，-1删除）
     */
    @Column(name = "status")
    @LogicDelete(-1)
    private Integer status;

    /**
     * remark 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * createUser 创建者
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * createTime 创建时间
     */
    @Column(name = "create_time")
    @Timestamp
    private Long createTime;

    /**
     * updateUser 更新者
     */
    @Column(name = "update_user")
    private String updateUser;

    /**
     * updateTime 更新时间
     */
    @Column(name = "update_time")
    @Timestamp
    @OrderBy(direction = Direction.DESC)
    private Long updateTime;

    public SysUser() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDepartId() {
        return departId;
    }

    public void setDepartId(Long departId) {
        this.departId = departId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Long getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Long loginDate) {
        this.loginDate = loginDate;
    }

    public Long getPwdUpdateDate() {
        return pwdUpdateDate;
    }

    public void setPwdUpdateDate(Long pwdUpdateDate) {
        this.pwdUpdateDate = pwdUpdateDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}


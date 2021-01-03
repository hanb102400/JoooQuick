package com.shawn.rbac.entity;
import com.shawn.jooo.framework.base.BaseBean;
import com.shawn.jooo.framework.response.annotation.TimeToJson;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 用户信息表
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_user")
public class SysUser extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: userId  用户ID
     */
    @Id
    @Column(name="user_id")
    private Long userId;

    /**
     * departId 部门ID
     */
    @Column(name="depart_id")
    private Long departId;

    /**
     * username 登录账号
     */
    @Column(name="username")
    private String username;

    /**
     * password 密码
     */
    @Column(name="password")
    private String password;

    /**
     * nickname 昵称
     */
    @Column(name="nickname")
    private String nickname;

    /**
     * userType 用户类型
     */
    @Column(name="user_type")
    private Short userType;

    /**
     * email 用户邮箱
     */
    @Column(name="email")
    private String email;

    /**
     * mobile 手机号码
     */
    @Column(name="mobile")
    private String mobile;

    /**
     * sex 用户性别（0未知 1男 2女 ）
     */
    @Column(name="sex")
    private Short sex;

    /**
     * avatar 头像路径
     */
    @Column(name="avatar")
    private String avatar;

    /**
     * loginIp 最后登录IP
     */
    @Column(name="login_ip")
    private String loginIp;

    /**
     * loginDate 最后登录时间
     */
    @Column(name="login_date")
    private Long loginDate;

    /**
     * pwdUpdateDate 密码最后更新时间
     */
    @Column(name="pwd_update_date")
    private Long pwdUpdateDate;

    /**
     * status 帐号状态（0正常 1停用，-1删除）
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
    @TimeToJson
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

    public SysUser(){}

    public void setUserId( Long userId ) {
            this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setDepartId( Long departId ) {
            this.departId = departId;
    }

    public Long getDepartId() {
        return this.departId;
    }

    public void setUsername( String username ) {
            this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword( String password ) {
            this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setNickname( String nickname ) {
            this.nickname = nickname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setUserType( Short userType ) {
            this.userType = userType;
    }

    public Short getUserType() {
        return this.userType;
    }

    public void setEmail( String email ) {
            this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setMobile( String mobile ) {
            this.mobile = mobile;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setSex( Short sex ) {
            this.sex = sex;
    }

    public Short getSex() {
        return this.sex;
    }

    public void setAvatar( String avatar ) {
            this.avatar = avatar;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setLoginIp( String loginIp ) {
            this.loginIp = loginIp;
    }

    public String getLoginIp() {
        return this.loginIp;
    }

    public void setLoginDate( Long loginDate ) {
            this.loginDate = loginDate;
    }

    public Long getLoginDate() {
        return this.loginDate;
    }

    public void setPwdUpdateDate( Long pwdUpdateDate ) {
            this.pwdUpdateDate = pwdUpdateDate;
    }

    public Long getPwdUpdateDate() {
        return this.pwdUpdateDate;
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


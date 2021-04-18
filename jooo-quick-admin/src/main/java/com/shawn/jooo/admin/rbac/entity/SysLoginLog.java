package com.shawn.jooo.admin.rbac.entity;

import com.shawn.jooo.framework.base.BaseBean;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 系统访问记录
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_login_log")
public class SysLoginLog extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: loginId  访问ID
     */
    @Id
    @Column(name="login_id")
    private Integer loginId;

    /**
     * loginName 登录账号
     */
    @Column(name="login_name")
    private String loginName;

    /**
     * ipAddress 登录IP地址
     */
    @Column(name="ip_address")
    private String ipAddress;

    /**
     * loginLocation 登录地点
     */
    @Column(name="login_location")
    private String loginLocation;

    /**
     * browser 浏览器类型
     */
    @Column(name="browser")
    private String browser;

    /**
     * os 操作系统
     */
    @Column(name="os")
    private String os;

    /**
     * status 登录状态（0成功 1失败）
     */
    @Column(name="status")
    private Short status;

    /**
     * msg 提示消息
     */
    @Column(name="msg")
    private String msg;

    /**
     * loginTime 访问时间
     */
    @Column(name="login_time")
    private Long loginTime;

    public SysLoginLog(){}

    public void setLoginId( Integer loginId ) {
            this.loginId = loginId;
    }

    public Integer getLoginId() {
        return this.loginId;
    }

    public void setLoginName( String loginName ) {
            this.loginName = loginName;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setIpAddress( String ipAddress ) {
            this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public void setLoginLocation( String loginLocation ) {
            this.loginLocation = loginLocation;
    }

    public String getLoginLocation() {
        return this.loginLocation;
    }

    public void setBrowser( String browser ) {
            this.browser = browser;
    }

    public String getBrowser() {
        return this.browser;
    }

    public void setOs( String os ) {
            this.os = os;
    }

    public String getOs() {
        return this.os;
    }

    public void setStatus( Short status ) {
            this.status = status;
    }

    public Short getStatus() {
        return this.status;
    }

    public void setMsg( String msg ) {
            this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setLoginTime( Long loginTime ) {
            this.loginTime = loginTime;
    }

    public Long getLoginTime() {
        return this.loginTime;
    }


}


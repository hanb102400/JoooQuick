package com.shawn.rbac.entity;
import com.shawn.jooo.framework.base.BaseBean;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 操作日志记录
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_operate_log")
public class SysOperateLog extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: operId  日志主键
     */
    @Id
    @Column(name="oper_id")
    private Integer operId;

    /**
     * title 模块标题
     */
    @Column(name="title")
    private String title;

    /**
     * businessType 业务类型（0其它 1新增 2修改 3删除）
     */
    @Column(name="business_type")
    private Integer businessType;

    /**
     * method 方法名称
     */
    @Column(name="method")
    private String method;

    /**
     * requestMethod 请求方式
     */
    @Column(name="request_method")
    private String requestMethod;

    /**
     * operatorType 操作类别（0其它 1后台用户 2手机端用户）
     */
    @Column(name="operator_type")
    private Integer operatorType;

    /**
     * operName 操作人员
     */
    @Column(name="oper_name")
    private String operName;

    /**
     * deptName 部门名称
     */
    @Column(name="dept_name")
    private String deptName;

    /**
     * operUrl 请求URL
     */
    @Column(name="oper_url")
    private String operUrl;

    /**
     * operIp 主机地址
     */
    @Column(name="oper_ip")
    private String operIp;

    /**
     * operLocation 操作地点
     */
    @Column(name="oper_location")
    private String operLocation;

    /**
     * operParam 请求参数
     */
    @Column(name="oper_param")
    private String operParam;

    /**
     * jsonResult 返回参数
     */
    @Column(name="json_result")
    private String jsonResult;

    /**
     * status 操作状态（0正常 1异常）
     */
    @Column(name="status")
    private Integer status;

    /**
     * errorMsg 错误消息
     */
    @Column(name="error_msg")
    private String errorMsg;

    /**
     * operTime 鎿嶄綔鏃堕棿
     */
    @Column(name="oper_time")
    private Long operTime;

    public SysOperateLog(){}

    public void setOperId( Integer operId ) {
            this.operId = operId;
    }

    public Integer getOperId() {
        return this.operId;
    }

    public void setTitle( String title ) {
            this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setBusinessType( Integer businessType ) {
            this.businessType = businessType;
    }

    public Integer getBusinessType() {
        return this.businessType;
    }

    public void setMethod( String method ) {
            this.method = method;
    }

    public String getMethod() {
        return this.method;
    }

    public void setRequestMethod( String requestMethod ) {
            this.requestMethod = requestMethod;
    }

    public String getRequestMethod() {
        return this.requestMethod;
    }

    public void setOperatorType( Integer operatorType ) {
            this.operatorType = operatorType;
    }

    public Integer getOperatorType() {
        return this.operatorType;
    }

    public void setOperName( String operName ) {
            this.operName = operName;
    }

    public String getOperName() {
        return this.operName;
    }

    public void setDeptName( String deptName ) {
            this.deptName = deptName;
    }

    public String getDeptName() {
        return this.deptName;
    }

    public void setOperUrl( String operUrl ) {
            this.operUrl = operUrl;
    }

    public String getOperUrl() {
        return this.operUrl;
    }

    public void setOperIp( String operIp ) {
            this.operIp = operIp;
    }

    public String getOperIp() {
        return this.operIp;
    }

    public void setOperLocation( String operLocation ) {
            this.operLocation = operLocation;
    }

    public String getOperLocation() {
        return this.operLocation;
    }

    public void setOperParam( String operParam ) {
            this.operParam = operParam;
    }

    public String getOperParam() {
        return this.operParam;
    }

    public void setJsonResult( String jsonResult ) {
            this.jsonResult = jsonResult;
    }

    public String getJsonResult() {
        return this.jsonResult;
    }

    public void setStatus( Integer status ) {
            this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setErrorMsg( String errorMsg ) {
            this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setOperTime( Long operTime ) {
            this.operTime = operTime;
    }

    public Long getOperTime() {
        return this.operTime;
    }


}


package com.shawn.jooo.admin.rbac.entity;

import com.shawn.jooo.framework.base.BaseBean;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 * 操作日志记录
 *
 * @author jooo.gen
 */
@Entity
@Table(name = "sys_operate_log")
public class SysOperateLog extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "operate_id")
    private Integer operateId;

    @Column(name = "title")
    private String title;

    @Column(name = "business_type")
    private int businessType;

    @Column(name = "request_method")
    private String requestMethod;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "operate_type")
    private int operateType;

    @Column(name = "operate_name")
    private String operateName;

    @Column(name = "operate_path")
    private String operatePath;

    @Column(name = "request_param")
    private String requestParam;

    @Column(name = "json_result")
    private String jsonResult;

    @Column(name = "status")
    private String status;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "operate_user")
    private String operateUser;

    @Column(name = "operate_ip")
    private String operateIp;

    @Column(name = "operate_time")
    private Long operateTime;

    public Integer getOperateId() {
        return operateId;
    }

    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBusinessType() {
        return businessType;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getOperatePath() {
        return operatePath;
    }

    public void setOperatePath(String operatePath) {
        this.operatePath = operatePath;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public Long getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Long operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(String operateUser) {
        this.operateUser = operateUser;
    }
}


package com.shawn.jooo.module.dict.entity;

import com.shawn.jooo.framework.base.BaseBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * 
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_dict_type")
public class SysDictType extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: dictId  字典主键
     */
    @Id
    @Column(name="dict_id")
    private Integer dictId;

    /**
     * dictName 字典名称
     */
    @Column(name="dict_name")
    private String dictName;

    /**
     * dictType 字典类型
     */
    @Column(name="dict_type")
    private String dictType;

    /**
     * status 状态：0正常，1停用 -1删除
     */
    @Column(name="status")
    private Short status;

    /**
     * remark 
     */
    @Column(name="remark")
    private String remark;

    /**
     * createTime 
     */
    @Column(name="create_time")
    private Long createTime;

    /**
     * createUser 
     */
    @Column(name="create_user")
    private String createUser;

    /**
     * updateTime 
     */
    @Column(name="update_time")
    private Long updateTime;

    /**
     * updateUser 
     */
    @Column(name="update_user")
    private String updateUser;

    public SysDictType(){}

    public void setDictId( Integer dictId ) {
            this.dictId = dictId;
    }

    public Integer getDictId() {
        return this.dictId;
    }

    public void setDictName( String dictName ) {
            this.dictName = dictName;
    }

    public String getDictName() {
        return this.dictName;
    }

    public void setDictType( String dictType ) {
            this.dictType = dictType;
    }

    public String getDictType() {
        return this.dictType;
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

    public void setCreateTime( Long createTime ) {
            this.createTime = createTime;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateUser( String createUser ) {
            this.createUser = createUser;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setUpdateTime( Long updateTime ) {
            this.updateTime = updateTime;
    }

    public Long getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateUser( String updateUser ) {
            this.updateUser = updateUser;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }


}


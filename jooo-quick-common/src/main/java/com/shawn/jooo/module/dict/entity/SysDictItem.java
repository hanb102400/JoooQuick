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
@Table(name="sys_dict_item")
public class SysDictItem extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: itemId  字典编码
     */
    @Id
    @Column(name="item_id")
    private Integer itemId;

    /**
     * dictType 字典类型
     */
    @Column(name="dict_type")
    private String dictType;

    /**
     * itemLabel 字典标签
     */
    @Column(name="item_label")
    private String itemLabel;

    /**
     * itemValue 字典键值
     */
    @Column(name="item_value")
    private String itemValue;

    /**
     * sort 字典排序
     */
    @Column(name="sort")
    private Short sort;

    /**
     * isDefault 是否默认（1是 0否）
     */
    @Column(name="is_default")
    private Short isDefault;

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

    public SysDictItem(){}

    public void setItemId( Integer itemId ) {
            this.itemId = itemId;
    }

    public Integer getItemId() {
        return this.itemId;
    }

    public void setDictType( String dictType ) {
            this.dictType = dictType;
    }

    public String getDictType() {
        return this.dictType;
    }

    public void setItemLabel( String itemLabel ) {
            this.itemLabel = itemLabel;
    }

    public String getItemLabel() {
        return this.itemLabel;
    }

    public void setItemValue( String itemValue ) {
            this.itemValue = itemValue;
    }

    public String getItemValue() {
        return this.itemValue;
    }

    public void setSort( Short sort ) {
            this.sort = sort;
    }

    public Short getSort() {
        return this.sort;
    }

    public void setIsDefault( Short isDefault ) {
            this.isDefault = isDefault;
    }

    public Short getIsDefault() {
        return this.isDefault;
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


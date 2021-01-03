package com.shawn.rbac.entity;
import com.shawn.jooo.framework.base.BaseBean;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 *
 * 字典数据表
 *
 * @author jooo.gen
 */
@Entity
@Table(name="sys_dict_data")
public class SysDictData extends BaseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *  ID: dictCode  字典编码
     */
    @Id
    @Column(name="dict_code")
    private Integer dictCode;

    /**
     * dictLabel 字典标签
     */
    @Column(name="dict_label")
    private String dictLabel;

    /**
     * dictValue 字典键值
     */
    @Column(name="dict_value")
    private String dictValue;

    /**
     * dictType 字典类型
     */
    @Column(name="dict_type")
    private String dictType;

    /**
     * sort 字典排序
     */
    @Column(name="sort")
    private Short sort;

    /**
     * isDefault 是否默认（Y是 N否）
     */
    @Column(name="is_default")
    private String isDefault;

    /**
     * status 状态：0正常，1停用 -1删除
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

    public SysDictData(){}

    public void setDictCode( Integer dictCode ) {
            this.dictCode = dictCode;
    }

    public Integer getDictCode() {
        return this.dictCode;
    }

    public void setDictLabel( String dictLabel ) {
            this.dictLabel = dictLabel;
    }

    public String getDictLabel() {
        return this.dictLabel;
    }

    public void setDictValue( String dictValue ) {
            this.dictValue = dictValue;
    }

    public String getDictValue() {
        return this.dictValue;
    }

    public void setDictType( String dictType ) {
            this.dictType = dictType;
    }

    public String getDictType() {
        return this.dictType;
    }

    public void setSort( Short sort ) {
            this.sort = sort;
    }

    public Short getSort() {
        return this.sort;
    }

    public void setIsDefault( String isDefault ) {
            this.isDefault = isDefault;
    }

    public String getIsDefault() {
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


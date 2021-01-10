package com.shawn.jooo.generator.xstream;

import com.sun.org.apache.xerces.internal.impl.dv.xs.FullDVFactory;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 *
 * @author shawn
 */
@XStreamAlias("type")
public class Type {

    @XStreamAsAttribute
    @XStreamAlias("jdbcType")
    private String jdbcType;

    @XStreamAsAttribute
    private String javaType;

    @XStreamAsAttribute
    private String javaTypeFull;

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJavaTypeFull() {
        return javaTypeFull;
    }

    public void setJavaTypeFull(String javaTypeFull) {
        this.javaTypeFull = javaTypeFull;
    }
}

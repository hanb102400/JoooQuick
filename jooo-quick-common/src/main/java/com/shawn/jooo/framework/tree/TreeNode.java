package com.shawn.jooo.framework.tree;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shawn.jooo.framework.base.BaseBean;

import java.util.List;

public abstract class TreeNode<T> extends BaseBean {

    private List<T> children;

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}

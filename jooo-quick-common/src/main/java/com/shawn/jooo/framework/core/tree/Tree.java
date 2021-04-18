package com.shawn.jooo.framework.core.tree;


import java.util.List;

public class Tree<T extends TreeNode>  {

    private String idKey;

    private String parentIdKey;

    private List<T> content;

    public Tree() {

    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public String getParentIdKey() {
        return parentIdKey;
    }

    public void setParentIdKey(String parentIdKey) {
        this.parentIdKey = parentIdKey;
    }
}

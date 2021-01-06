package com.shawn.jooo.framework.tree;


import java.util.List;

public class Tree<T extends TreeNode> {

    private List<T> content;

    private String idKey;

    private String parentIdKey;

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

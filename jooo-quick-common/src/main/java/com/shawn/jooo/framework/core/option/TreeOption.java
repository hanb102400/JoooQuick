package com.shawn.jooo.framework.core.option;


import java.util.List;

public class TreeOption  {

    private String id;

    private String parentId;

    private String label;

    private List<TreeOption> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeOption> getChildren() {
        return children;
    }

    public void setChildren(List<TreeOption> children) {
        this.children = children;
    }
}

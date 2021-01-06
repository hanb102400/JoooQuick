package com.shawn.jooo.framework.base;

import com.shawn.jooo.framework.tree.Tree;
import com.shawn.jooo.framework.tree.TreeHelper;
import com.shawn.jooo.framework.tree.TreeNode;

import java.io.Serializable;
import java.util.List;

public class TreeServiceImpl<T extends TreeNode, ID extends Serializable> extends BaseServiceImpl implements TreeService {


    @Override
    public Tree selectTree() {
        List<T> list = this.findAll();
        return TreeHelper.listToTree(list);
    }
}

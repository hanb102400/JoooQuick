package com.shawn.jooo.framework.base;

import com.shawn.jooo.framework.core.tree.Tree;
import com.shawn.jooo.framework.core.tree.TreeHelper;
import com.shawn.jooo.framework.core.tree.TreeNode;

import java.io.Serializable;
import java.util.List;

public class TreeServiceImpl<T extends TreeNode, ID extends Serializable> extends BaseServiceImpl implements TreeService {


    @Override
    public Tree selectTree() {
        List<T> list = this.findAll();
        return TreeHelper.listToTree(list);
    }
}

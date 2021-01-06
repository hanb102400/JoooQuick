package com.shawn.jooo.framework.base;


import com.shawn.jooo.framework.tree.Tree;
import com.shawn.jooo.framework.tree.TreeNode;

import java.io.Serializable;

public interface TreeService<T extends TreeNode, ID extends Serializable> extends BaseService<T, ID> {

    Tree selectTree();
}

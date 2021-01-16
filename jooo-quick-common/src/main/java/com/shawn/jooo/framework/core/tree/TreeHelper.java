package com.shawn.jooo.framework.core.tree;

import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree工具类，默认id，父id为：id，parentId
 */
public class TreeHelper {

    private static final String ID_KEY = "id";

    private static final String PARENT_ID_KEY = "parentId";

    public static <T extends TreeNode> Tree listToTree(List<T> list) {
        return listToTree(list, ID_KEY, PARENT_ID_KEY);
    }

    public static <T extends TreeNode> Tree listToTree(List<T> list, String idKey, String parentIdKey) {
        Tree<T> tree = new Tree<>();
        tree.setIdKey(idKey);
        tree.setParentIdKey(parentIdKey);
        List<T> roots = new ArrayList<>();

        //生成树
        for (T ch : list) {
            Number parentId = (Number) BeanReflections.readField(parentIdKey, ch);
            if (parentId == null || 0L == parentId.longValue()) {
                //找到根节点，解析当前节点的子节点数据
                T root = genChildren(ch, list, idKey, parentIdKey);
                roots.add(root);
            }
        }

        //配置root
        if (roots.size() > 0) {
            tree.setContent(roots);
        } else {
            tree.setContent(null);
        }
        return tree;
    }


    /**
     * 递归
     *
     * @param node
     * @param list
     * @return
     */
    private static <T extends TreeNode> T genChildren(T node, List<T> list, String IdKey, String parentIdKey) {
        List<T> children = new ArrayList<>();
        for (T item : list) {
            Number parentId = (Number) BeanReflections.readField(parentIdKey, item);
            Number id = (Number) BeanReflections.readField(IdKey, node);
            if (parentId.longValue() == id.longValue()) {
                children.add(genChildren(item, list, IdKey, parentIdKey));
            }
        }
        if(!CollectionUtils.isEmpty(children)) {
            node.setChildren(children);
        }
        return node;
    }
}

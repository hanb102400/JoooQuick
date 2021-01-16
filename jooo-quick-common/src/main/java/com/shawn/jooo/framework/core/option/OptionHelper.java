package com.shawn.jooo.framework.core.option;

import com.shawn.jooo.framework.core.tree.Tree;
import com.shawn.jooo.framework.core.tree.TreeHelper;
import com.shawn.jooo.framework.core.tree.TreeNode;
import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OptionHelper {

    public static <T> List<TreeOption> listToTreeOption(List<T> list, String idKey, String parentIdKey, String labelKey) {
        List<TreeOption> options = new ArrayList<>();
        //生成树
        for (T ch : list) {
            Number parentId = (Number) BeanReflections.readField(parentIdKey, ch);
            if (parentId == null || 0L == parentId.longValue()) {
                //找到根节点，解析当前节点的子节点数据
                TreeOption root = genChildren(ch, list, idKey, parentIdKey, labelKey);
                options.add(root);

            }
        }

        //配置root
        if (options.size() > 0) {
            return options;
        }
        return Collections.emptyList();
    }


    /**
     * 递归
     *
     * @param node
     * @param list
     * @return
     */
    private static <T> TreeOption genChildren(T node, List<T> list, String IdKey, String parentIdKey, String labelKey) {
        TreeOption option = new TreeOption();
        List<TreeOption> children = new ArrayList<>();
        for (T item : list) {

            //查找当前节点下的子元素
            Number id = (Number) BeanReflections.readField(IdKey, node);
            Number parentId = (Number) BeanReflections.readField(parentIdKey, item);
            String label = (String) BeanReflections.readField(labelKey, node);

            option.setId(String.valueOf(id));
            option.setParentId(String.valueOf(parentId));
            option.setLabel(label);

            Assert.notNull(parentId, "parentId must be not null");
            Assert.notNull(id, "id must be not null");
            //找到子元素后处理
            if (parentId.longValue() == id.longValue()) {
                TreeOption child = genChildren(item, list, IdKey, parentIdKey, labelKey);
                children.add(child);
            }
        }
        if (!CollectionUtils.isEmpty(children)) {
            option.setChildren(children);
        }
        return option;
    }

}

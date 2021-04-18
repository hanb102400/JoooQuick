package com.shawn.jooo.framework.core.option;

import com.shawn.jooo.framework.mybatis.condition.Direction;
import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.mybatis.condition.Sort;
import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import com.shawn.jooo.framework.utils.SpringContextHolder;
import com.shawn.jooo.module.dict.entity.SysDictItem;
import com.shawn.jooo.module.dict.service.SysDictItemService;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OptionHelper {

    private static final String DEFAULT_ID_KEY = "id";

    private static final String DEFAULT_PARENT_ID_KEY = "parentId";

    /**
     * 字典数据转为option
     *
     * @param dictType
     * @param <T>
     * @return
     */
    public static <T> List<Option> dictToOption(String dictType) {
        SysDictItemService dictItemService = SpringContextHolder.getBean(SysDictItemService.class);
        Example example = new Example();
        example.setOrderByClause(Sort.orderBy(Direction.ASC, SysDictItem::getSort));
        example.<SysDictItem>createCriteria().andEqualTo(SysDictItem::getDictType, dictType);
        List<SysDictItem> dictItemList = dictItemService.findAll(example);
        List<Option> options = dictItemList.stream().map(item -> Option.of(item.getItemValue(), item.getItemLabel())).collect(Collectors.toList());
        return options;
    }

    /**
     * 列表数据转为option
     *
     * @param list
     * @param valueName
     * @param labelName
     * @param <T>
     * @return
     */
    public static <T> List<Option> listToOption(List<T> list, String valueName, String labelName) {
        List<Option> options = new ArrayList<>();
        for (T obj : list) {
            Object value = BeanReflections.readField(valueName, obj);
            String label = BeanReflections.readField(labelName, obj).toString();
            options.add(Option.of(value, label));
        }
        return options;
    }

    /**
     * 列表数据转为treeOption
     *
     * @param list
     * @param labelKey
     * @param <T>
     * @return
     */
    public static <T> List<TreeOption> listToTreeOption(List<T> list, String labelKey) {
        return listToTreeOption(list, DEFAULT_ID_KEY, DEFAULT_PARENT_ID_KEY, labelKey);
    }

    /**
     * 列表数据转为treeOption
     *
     * @param list
     * @param idKey
     * @param parentIdKey
     * @param labelKey
     * @param <T>
     * @return
     */
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

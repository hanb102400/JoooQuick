package com.shawn.jooo.framework.page;

import java.util.Iterator;
import java.util.List;

/**
 * 分页返回对象接口，参考JPA实现
 *
 * @author shawn
 */
public interface Page<T> {

    int getPageNo();

    int getPageSize();

    long getTotalPage();

    long getTotalCount();

    boolean hasPrevious();

    boolean hasNext();

    boolean isFirst();

    boolean isLast();

    Iterator<T> iterator();

    List<T> getContent();

    boolean haContent();
}

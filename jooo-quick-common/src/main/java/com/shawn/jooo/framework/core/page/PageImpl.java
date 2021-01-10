package com.shawn.jooo.framework.core.page;


/**
 * 分页返回对象实现，参考JPA实现
 *
 * @author shawn
 */

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PageImpl<T> implements Page<T> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private int pageNo;

    private int pageSize;

    private long totalPage;

    private long totalCount;

    private final List<T> content = new ArrayList();

    public PageImpl() {
        super();
    }

    public PageImpl(List<T> content, Pageable pageable, long totalCount) {
        this(content, pageable);
        this.totalCount = totalCount;
    }

    public PageImpl(List<T> content, Pageable pageable) {
        Assert.notNull(content, "Content must not be null!");
        Assert.notNull(pageable, "Pageable must not be null!");
        this.content.clear();
        this.content.addAll(content);
        this.pageNo = pageable.getPageNo();
        this.pageSize = pageable.getPageSize();
        this.totalCount = pageable.getTotalCount();
    }

    public PageImpl(List<T> content, int pageNo, int pageSize, long totalCount) {
        this.content.clear();
        this.content.addAll(content);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }


    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getTotalPage() {
        return this.getPageSize() == 0 ? 1 : (int) Math.ceil((double) this.totalCount / (double) this.getPageSize());
    }

    @Override
    public long getTotalCount() {
        return totalCount;
    }

    @Override
    public boolean hasNext() {
        return this.getPageNo() < this.getTotalPage();
    }

    @Override
    public boolean isLast() {
        return !this.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return this.getPageNo() > 1;
    }

    @Override
    public boolean isFirst() {
        return !this.hasPrevious();
    }

    @Override
    public Iterator<T> iterator() {
        return this.content.iterator();
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean haContent() {
        return content != null && !getContent().isEmpty();
    }

    public String toString() {
        String contentType = "UNKNOWN";
        List<T> content = this.getContent();
        if (content.size() > 0) {
            contentType = content.get(0).getClass().getName();
        }
        return String.format("Page %s of %d containing %s instances", this.getPageNo(), this.getTotalPage(), contentType);
    }

}

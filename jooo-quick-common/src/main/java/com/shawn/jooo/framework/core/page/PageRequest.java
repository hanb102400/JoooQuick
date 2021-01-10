package com.shawn.jooo.framework.core.page;

import java.io.Serializable;

/**
 * 分页参数实现，参考JPA实现
 *
 * @author shawn
 */
public class PageRequest extends Pageable implements Serializable {


    private static final long serialVersionUID = 1232825578694716871L;

    private int pageNo;

    private int pageSize;

    private long totalCount;

    private PageRequest(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        } else if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        } else {
            this.pageNo = page;
            this.pageSize = size;
        }
        int offset = (pageNo - 1) * pageSize;
        int limit = pageSize;
        this.setOffsetLimit(offset, limit);
    }

    public static PageRequest of(int page, int size) {
        return new PageRequest(page, size);
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
    public long getTotalCount() {
        return totalCount;
    }

    @Override
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Page request [page: %d, size %d]", this.getPageNo(), this.getPageSize());
    }


}

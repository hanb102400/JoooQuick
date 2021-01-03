package com.shawn.jooo.framework.page;

import org.apache.ibatis.session.RowBounds;

import java.util.Optional;


/**
 * 分页参数接口，参考JPA实现
 *
 * @author shawn
 */
public abstract class Pageable extends RowBounds {

    public static final int NO_ROW_OFFSET = 0;
    public static final int NO_ROW_LIMIT = 2147483647;

    private int offset;

    private int limit;

    protected Pageable() {
        this.offset = NO_ROW_OFFSET;
        this.limit = NO_ROW_LIMIT;
    }

    protected void setOffsetLimit(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public abstract int getPageNo();

    public abstract int getPageSize();

    public abstract long getTotalCount();

    public abstract void setTotalCount(long totalCount);

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public Optional<Pageable> toOptional() {
        return Optional.ofNullable(this);
    }

}

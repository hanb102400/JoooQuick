package com.shawn.jooo.framework.page;

/**
 * PageHolder上下文
 *
 * @author shawn
 */
public class PageHolder {

    private static final ThreadLocal<Pageable> pageHolder = new ThreadLocal<Pageable>();

    public static void setPage(Pageable pageable) {
        if (!pageable.equals(pageHolder.get())) {
            pageHolder.set(pageable);
        }
    }

    public static Pageable getPage() {
        return pageHolder.get();
    }

    public static void clearContext() {
        pageHolder.remove();
    }
}

package com.shawn.jooo.framework.core.page;

import com.shawn.jooo.framework.config.Paging;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 分页工具类，获取分页参数
 *
 * @author shawn
 */
@Component
public class PageHelper extends Paging {

    /**
     * getPage
     *
     * @return
     */
    public static PageRequest getPage() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(servletRequestAttributes, "servletRequestAttributes must not be null");
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return getPage(request);
    }

    /**
     * getPage
     *
     * @param request
     * @return
     */
    public static PageRequest getPage(HttpServletRequest request) {
        Assert.notNull(request, "request must not be null");
        int page = getPageNo(request);
        int size = getPageSize(request);
        return PageHelper.getPage(page, size);
    }


    /**
     * getPage
     *
     * @param page
     * @param size
     * @return
     */
    public static PageRequest getPage(int page, int size) {
        return PageRequest.of(page, size);
    }

    /**
     * getPage
     *
     * @param page
     * @return
     */
    public static PageRequest getPage(int page) {
        return getPage(page, Paging.pageSizeValue());
    }

    private static int getPageNo(HttpServletRequest request) {
        int pageNo;
        String no = request.getParameter(Paging.pageNoKey());
        if (StringUtils.isNumeric(no)) {
            pageNo = Integer.parseInt(no);
            if (pageNo < 1) {
                pageNo = 1;
            }
        } else {
            pageNo = Paging.pageNoValue();
        }
        return pageNo;
    }

    private static int getPageSize(HttpServletRequest request) {
        int pageSize;
        String size = request.getParameter(Paging.pageSizeKey());
        if (StringUtils.isNumeric(size)) {
            pageSize = Integer.parseInt(size);
        } else {
            pageSize = Paging.pageSizeValue();
        }
        return pageSize;
    }


}

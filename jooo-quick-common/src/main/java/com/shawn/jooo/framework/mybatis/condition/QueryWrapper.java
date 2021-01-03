package com.shawn.jooo.framework.mybatis.condition;


import com.shawn.jooo.framework.page.Page;


/**
 * 查询包装器，用来装配PO和VO类的映射
 *
 * @author shawn
 */
public class QueryWrapper {

    Class<?> poClazz;

    Class<?> voClazz;

    public QueryWrapper(Class<?> poClazz, Class<?> voClazz) {
        this.poClazz = poClazz;
        this.voClazz = voClazz;
    }

    public <VO> Example ofExample(VO vo) {
        return QueryHelper.getExample(vo, poClazz);
    }

    public <VO> Page<VO> ofVoPage(Page page) {
        return QueryHelper.getVoPage(page, voClazz);
    }
}

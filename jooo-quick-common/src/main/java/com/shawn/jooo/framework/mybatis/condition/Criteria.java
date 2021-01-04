package com.shawn.jooo.framework.mybatis.condition;

public enum Criteria {
    NONE,
    EQ,
    NOT_EQ,
    GT,
    GT_OR_EQ,
    LT,
    LT_OR_EQ,
    LIKE,
    NOT_LIKE;
    //IS_NULL,
    //IS_NOT_NULL,
    //IN,
    //NOT_IN,
    //BETWEEN,
    //NOT_BETWEEN;
    private Criteria() {
    }
}



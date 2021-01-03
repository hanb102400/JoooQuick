package com.shawn.jooo.framework.mybatis.condition;


/**
 * like的匹配模式工具
 * <p>
 * like的exact描述，参考实现org.hibernate.criterion.MatchMode
 *
 * @author shawn
 */
public enum MatchMode {

    EXACT {
        @Override
        public String contact(String pattern) {
            return pattern;
        }
    },
    START {
        @Override
        public String contact(String pattern) {
            return pattern + "%";
        }
    },
    END {
        @Override
        public String contact(String pattern) {
            return "%" + pattern;
        }
    },
    ANY {
        @Override
        public String contact(String pattern) {
            return "%" + pattern + "%";
        }
    };

    MatchMode() {
    }

    public abstract String contact(String var1);
}

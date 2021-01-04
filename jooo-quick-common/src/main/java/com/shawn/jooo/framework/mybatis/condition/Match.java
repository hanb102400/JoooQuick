package com.shawn.jooo.framework.mybatis.condition;


/**
 * like的匹配模式工具
 * <p>
 * like的exact描述，参考实现org.hibernate.criterion.Match
 *
 * @author shawn
 */
public enum Match {

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

    Match() {
    }

    public abstract String contact(String var1);
}

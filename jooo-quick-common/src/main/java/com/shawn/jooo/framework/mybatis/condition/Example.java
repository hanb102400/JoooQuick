package com.shawn.jooo.framework.mybatis.condition;


import com.shawn.jooo.framework.mybatis.reflect.Fn;
import com.shawn.jooo.framework.mybatis.reflect.FnReflections;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Example: mybatis查询条件处理
 * <p>
 * Example部分，参考mybatis-generator实现
 * weekend部分，参考abel533/Mapper框架实现
 *
 * @author shawn
 */
public class Example {

    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public Example() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public void setOrderByClause(Sort... sorts) {
        this.orderByClause = Sort.toOrderBySql(sorts);
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }


    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public <U> Criteria<U> or() {
        Criteria<U> criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public <U> Criteria<U> or(Class<U> requiredType) {
        Criteria<U> criteria = createCriteriaInternal(requiredType);
        oredCriteria.add(criteria);
        return criteria;
    }

    public <U> Criteria<U> and() {
        if (CollectionUtils.isEmpty(oredCriteria)) {
            return createCriteria();
        } else {
            return oredCriteria.get(0);

        }
    }

    public <U> Criteria<U> and(Class<U> requiredType) {
        if (CollectionUtils.isEmpty(oredCriteria)) {
            return createCriteria(requiredType);
        } else {
            return oredCriteria.get(0);
        }
    }

    public <U> Criteria<U> and(int index) {
        if (oredCriteria.size() > index) {
            return oredCriteria.get(index);
        } else {
            throw new IndexOutOfBoundsException("Criteria index is out of oredCriteria");
        }
    }

    public <U> Criteria<U> and(int index, Class<U> requiredType) {
        if (oredCriteria.size() > index) {
            return oredCriteria.get(index);
        } else {
            throw new IndexOutOfBoundsException("Criteria index is out of oredCriteria");
        }
    }


    public <U> Criteria<U> createCriteria() {
        Criteria<U> criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    public <U> Criteria<U> createCriteria(Class<U> entityType) {
        Criteria<U> criteria = createCriteriaInternal(entityType);
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected <U> Criteria<U> createCriteriaInternal() {
        Criteria<U> criteria = new Criteria();
        return criteria;
    }

    protected <U> Criteria<U> createCriteriaInternal(Class<U> entityType) {
        Criteria<U> criteria = new Criteria();
        return criteria;
    }


    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * GeneratedCriteria
     */
    protected abstract static class GeneratedCriteria {

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected <U> void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected <U> void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected <U> void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public <U> Criteria<U> andIsNull(String property) {
            addCriterion(property + " is null");
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andIsNotNull(String property) {
            addCriterion(property + " is not null");
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andEqualTo(String property, Object value) {
            addCriterion(property + " =", value, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andNotEqualTo(String property, Object value) {
            addCriterion(property + " <>", value, property);
            return (Criteria) this;
        }

        public <U> Criteria<U> andGreaterThan(String property, Object value) {
            addCriterion(property + " >", value, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria andGreaterThanOrEqualTo(String property, Object value) {
            addCriterion(property + " >=", value, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andLessThan(String property, Object value) {
            addCriterion(property + " <", value, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andLessThanOrEqualTo(String property, Object value) {
            addCriterion(property + " <=", value, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andLike(String property, Object value) {
            addCriterion(property + " like", value, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andNotLike(String property, String value) {
            addCriterion(property + " not like", value, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andIn(String property, Iterable values) {
            addCriterion(property + " in", values, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andNotIn(String property, Iterable values) {
            addCriterion(property + " not in", values, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andBetween(String property, Object value1, Object value2) {
            addCriterion(property + " between", value1, value2, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andNotBetween(String property, Object value1, Object value2) {
            addCriterion(property + " not between", value1, value2, property);
            return (Criteria<U>) this;
        }

        public <U> Criteria<U> andIsNull(Fn<U, Object> fn) {
            return andIsNull(FnReflections.fnToColumnName(fn));
        }

        public <U> Criteria<U> andIsNotNull(Fn<U, Object> fn) {
            return andIsNotNull(FnReflections.fnToColumnName(fn));
        }

        public <U> Criteria<U> andEqualTo(Fn<U, Object> fn, Object value) {
            return andEqualTo(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andNotEqualTo(Fn<U, Object> fn, Object value) {
            return andNotEqualTo(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andGreaterThan(Fn<U, Object> fn, Object value) {
            return andGreaterThan(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andGreaterThanOrEqualTo(Fn<U, Object> fn, Object value) {
            return andGreaterThanOrEqualTo(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andLessThan(Fn<U, Object> fn, Object value) {
            return andLessThan(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andLessThanOrEqualTo(Fn<U, Object> fn, Object value) {
            return andLessThanOrEqualTo(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andLike(Fn<U, Object> fn, Object value) {
            return andLike(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andNotLike(Fn<U, Object> fn, String value) {
            return andNotLike(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andIn(Fn<U, Object> fn, Iterable values) {
            return andIn(FnReflections.fnToColumnName(fn), values);
        }

        public <U> Criteria<U> andNotIn(Fn<U, Object> fn, Iterable values) {
            return andNotIn(FnReflections.fnToColumnName(fn), values);
        }

        public <U> Criteria<U> andBetween(Fn<U, Object> fn, Object value1, Object value2) {
            return andBetween(FnReflections.fnToColumnName(fn), value1, value2);
        }

        public <U> Criteria<U> andNotBetween(Fn<U, Object> fn, Object value1, Object value2) {
            return andNotBetween(FnReflections.fnToColumnName(fn), value1, value2);
        }

    }

    /**
     * Criteria
     */
    public static class Criteria<U> extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public <U> Criteria<U> andIsNull(Fn<U, Object> fn) {
            return andIsNull(FnReflections.fnToColumnName(fn));
        }

        public <U> Criteria<U> andIsNotNull(Fn<U, Object> fn) {
            return andIsNotNull(FnReflections.fnToColumnName(fn));
        }

        public <U> Criteria<U> andEqualTo(Fn<U, Object> fn, Object value) {
            return andEqualTo(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andNotEqualTo(Fn<U, Object> fn, Object value) {
            return andNotEqualTo(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andGreaterThan(Fn<U, Object> fn, Object value) {
            return andGreaterThan(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andGreaterThanOrEqualTo(Fn<U, Object> fn, Object value) {
            return andGreaterThanOrEqualTo(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andLessThan(Fn<U, Object> fn, Object value) {
            return andLessThan(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andLessThanOrEqualTo(Fn<U, Object> fn, Object value) {
            return andLessThanOrEqualTo(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andLike(Fn<U, Object> fn, Object value) {
            return andLike(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andNotLike(Fn<U, Object> fn, String value) {
            return andNotLike(FnReflections.fnToColumnName(fn), value);
        }

        public <U> Criteria<U> andIn(Fn<U, Object> fn, Iterable values) {
            return andIn(FnReflections.fnToColumnName(fn), values);
        }

        public <U> Criteria<U> andNotIn(Fn<U, Object> fn, Iterable values) {
            return andNotIn(FnReflections.fnToColumnName(fn), values);
        }

        public <U> Criteria<U> andBetween(Fn<U, Object> fn, Object value1, Object value2) {
            return andBetween(FnReflections.fnToColumnName(fn), value1, value2);
        }

        public <U> Criteria<U> andNotBetween(Fn<U, Object> fn, Object value1, Object value2) {
            return andNotBetween(FnReflections.fnToColumnName(fn), value1, value2);
        }
    }


    /**
     * Criterion
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }


}

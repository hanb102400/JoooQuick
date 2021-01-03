package com.shawn.jooo.framework.validate;


import org.hibernate.validator.HibernateValidator;

import javax.validation.*;
import java.util.Iterator;
import java.util.Set;

public class Validations {

    private static Validator validator;

    static {
        // 快速返回模式
        validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory()
                .getValidator();
    }

    public static Validator getValidator() {
        return validator;
    }

    /**
     * 校验对象
     *
     * @param
     */
    public static void validate(Object param) {
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(param);
        if (!constraintViolationSet.isEmpty()) {
            Iterator<ConstraintViolation<Object>> iter = constraintViolationSet.iterator();
            if (iter.hasNext()) {
                throw new ValidationException(iter.next().getMessage());
            }

        }
    }

    /**
     * 校验对象
     *
     * @param
     */
    public static void validate(Object param, String message) {
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(param);
        if (!constraintViolationSet.isEmpty()) {
            throw new ValidationException(message);
        }
    }

    /**
     * 校验对象
     *
     * @param object
     * @param groups
     * @param <T>
     */
    public static <T> void validate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolationSet = validator.validate(object, groups);
        if (!constraintViolationSet.isEmpty()) {
            throw new ConstraintViolationException(constraintViolationSet);
        }
    }


}


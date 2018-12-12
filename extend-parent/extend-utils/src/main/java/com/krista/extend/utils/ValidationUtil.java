package com.krista.extend.utils;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * ValidationUtil
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @date 2018/12/11 15:42
 */
public class ValidationUtil {
    /**
     * 使用hibernate的注解来进行验证
     */
    private static Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    /**
     * 功能描述: <br>
     * 〈注解验证参数〉
     *
     * @param obj 要验证的参数
     */
    public static <T> String validate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        // 抛出检验异常
        if (constraintViolations.size() > 0) {
            return constraintViolations.iterator().next().getMessage();
        }

        return null;
    }
}

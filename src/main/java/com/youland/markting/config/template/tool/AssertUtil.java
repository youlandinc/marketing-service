/**
 * * Youland.com copyright
 */
package com.youland.markting.config.template.tool;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Set;

//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.youland.markting.config.template.enums.ErrorCode;
import com.youland.markting.config.template.exception.BizExecuteException;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author yeqiu 
 * 2022/7/27 pm5:07:09
 */
public class AssertUtil {

    public static void validate(Object value, ErrorCode errorCode) {
        notEmpty(value, errorCode, "bean can't be null");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> validateProperty = validator.validate(value);
        for (ConstraintViolation<?> constraintViolation : validateProperty) {
            throw new BizExecuteException(errorCode,
                    constraintViolation.getPropertyPath() + constraintViolation.getMessage());
        }
    }

	public static void validate(Object value, ErrorCode errorCode, Class<?>... groups) {
		notEmpty(value, errorCode, "bean can't be null");
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> validateProperty = validator.validate(value, groups);
		for (ConstraintViolation<?> constraintViolation : validateProperty) {
			throw new BizExecuteException(errorCode,
					constraintViolation.getPropertyPath() + constraintViolation.getMessage());
		}
	}

    public static void empty(Object value, ErrorCode errorCode, String msg) {
        isTrue(ObjectUtil.isEmpty(value), errorCode, msg);
    }
    
    public static void updateOne(int affectRows, ErrorCode errorCode, String msg) {
        isTrue(affectRows == 1, errorCode, msg);
    }

    public static void notEmpty(Object value, ErrorCode errorCode, String msg) {
        isTrue(ObjectUtil.isNotEmpty(value), errorCode, msg);
    }

    public static void isBlank(String value, ErrorCode errorCode, String msg) {
        isTrue(StringUtils.isBlank(value), errorCode, msg);
    }

    public static void notBlank(String value, ErrorCode errorCode, String msg) {
        isTrue(StringUtils.isNotBlank(value), errorCode, msg);
    }

    public static void isNull(Object value, ErrorCode errorCode, String msg) {
        isTrue(ObjectUtil.isNull(value), errorCode, msg);
    }

    public static void notNull(Object value, ErrorCode errorCode, String msg) {
        isTrue(ObjectUtil.isNotNull(value), errorCode, msg);
    }

    public static void notEmptyBean(Object value, ErrorCode errorCode) {
        notEmpty(value, errorCode, "bean is empty");
        Class<?> clz = value.getClass();
        for (Field field : ReflectUtil.getFields(clz)) {
            Object propertyValue = ReflectUtil.getFieldValue(value, field);
            notEmpty(propertyValue, errorCode, field.getName() + " filed is empty");
        }
    }

    public static void eqZero(Object value, ErrorCode errorCode, String msg) {
        BigDecimal num = Convert.toBigDecimal(value);
        notEmpty(value, errorCode, msg);
        equal(BigDecimal.ZERO, num, errorCode, msg);
    }

    public static void gtZero(Object value, ErrorCode errorCode, String msg) {
        BigDecimal valueBigDecimal = Convert.toBigDecimal(value);
        notEmpty(value, errorCode, msg);
        isTrue(NumberUtil.isGreater(valueBigDecimal, BigDecimal.ZERO), errorCode, msg);
    }

    public static void gteZero(Object value, ErrorCode errorCode, String msg) {
        BigDecimal valueBigDecimal = Convert.toBigDecimal(value);
        notEmpty(value, errorCode, msg);
        isTrue(NumberUtil.isGreaterOrEqual(valueBigDecimal, BigDecimal.ZERO), errorCode, msg);
    }

    public static void ltZero(Object value, ErrorCode errorCode, String msg) {
        BigDecimal num = Convert.toBigDecimal(value);
        notEmpty(value, errorCode, msg);
        isTrue(NumberUtil.isLess(num, BigDecimal.ZERO), errorCode, msg);
    }

    public static void lteZero(Object value, ErrorCode errorCode, String msg) {
        BigDecimal num = Convert.toBigDecimal(value);
        notEmpty(value, errorCode, msg);
        isTrue(NumberUtil.isLessOrEqual(num, BigDecimal.ZERO), errorCode, msg);
    }

    public static void lte(Object value1, Object value2, ErrorCode errorCode, String msg) {
        BigDecimal big1 = Convert.toBigDecimal(value1);
        BigDecimal big2 = Convert.toBigDecimal(value2);
        isTrue(NumberUtil.isLessOrEqual(big1, big2), errorCode, msg);
    }

    public static void lt(Object value1, Object value2, ErrorCode errorCode, String msg) {
        BigDecimal big1 = Convert.toBigDecimal(value1);
        BigDecimal big2 = Convert.toBigDecimal(value2);
        isTrue(NumberUtil.isLess(big1, big2), errorCode, msg);
    }

    public static void gte(Object value1, Object value2, ErrorCode errorCode, String msg) {
        BigDecimal big1 = Convert.toBigDecimal(value1);
        BigDecimal big2 = Convert.toBigDecimal(value2);
        isTrue(NumberUtil.isGreaterOrEqual(big1, big2), errorCode, msg);
    }

    public static void gt(Object value1, Object value2, ErrorCode errorCode, String msg) {
        BigDecimal big1 = Convert.toBigDecimal(value1);
        BigDecimal big2 = Convert.toBigDecimal(value2);
        isTrue(NumberUtil.isGreater(big1, big2), errorCode, msg);
    }

    public static void equal(Object obj1, Object obj2, ErrorCode errorCode, String msg) {
        isTrue(ObjectUtil.equal(obj1, obj2), errorCode, msg);
    }

    public static void notEqual(Object obj1, Object obj2, ErrorCode errorCode, String msg) {
        isTrue(ObjectUtil.notEqual(obj1, obj2), errorCode, msg);
    }

    public static void equalsAnyIgnoreCase(String value, String[] array, ErrorCode errorCode, String msg) {
        isTrue(StrUtil.equalsAnyIgnoreCase(value, array), errorCode, msg);
    }

    public static void max(Number value, int max, ErrorCode errorCode, String msg) {
        notEmpty(value, errorCode, msg);
        double valueDouble = value.doubleValue();
        isTrue(valueDouble <= max, errorCode, msg);
    }

    public static void min(Number value, int min, ErrorCode errorCode, String msg) {
        notEmpty(value, errorCode, msg);
        double valueDouble = value.doubleValue();
        isTrue(valueDouble >= min, errorCode, msg);
    }

    public static void maxSize(Object value, int max, ErrorCode errorCode, String msg) {
        int valueLength = ObjectUtil.length(value);
        isTrue(valueLength <= max, errorCode, msg);
    }

    public static void minSize(Object value, int min, ErrorCode errorCode, String msg) {
        int valueLength = ObjectUtil.length(value);
        isTrue(valueLength >= min, null, msg);
    }

    public static void betweenOrEquals(Object value, Object min, Object max, ErrorCode errorCode, String msg) {
        double valueDouble = Convert.toDouble(value);
        double minDouble = Convert.toDouble(min);
        double maxDouble = Convert.toDouble(max);
        notEmpty(value, errorCode, "target value can't be null");
        notEmpty(min, errorCode, "min value can't be null");
        notEmpty(max, errorCode, "max value can't be null");
        isTrue(minDouble <= valueDouble && maxDouble >= valueDouble, errorCode, msg);
    }

    public static void isTrue(boolean flag, ErrorCode errorCode, String msg) {
        isFalse(!flag, errorCode, msg);
    }

    public static void isFalse(boolean flag, ErrorCode errorCode, String msg) {
        if (flag == false) {
            return;
        }
        throw new BizExecuteException(errorCode, msg);
    }

}

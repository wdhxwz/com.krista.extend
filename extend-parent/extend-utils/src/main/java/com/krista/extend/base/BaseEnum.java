package com.krista.extend.base;

import java.util.Arrays;
import java.util.Optional;

/**
 * BaseEnum 枚举基接口，基于JDK 8
 *
 * @author dw_wangdonghong
 * @version V1.0
 * @since 2018/12/13 10:33
 */
public interface BaseEnum<T> {
    /**
     * 返回枚举值
     *
     * @return T
     */
    T getCode();

    /**
     * 返回枚举含义
     *
     * @return java.lang.String
     */
    String getDesc();

    /**
     * parse : 将值转换成枚举类型
     *
     * @param enumType 枚举类型
     * @param code     枚举值
     * @return C
     * @author dw_wangdonghong
     * @date 2018/11/6 14:51
     */
    static <C extends BaseEnum<?>> C parse(Class<C> enumType, Object code) {
        Optional<C> existEnum = Arrays.asList(enumType.getEnumConstants())
                .stream()
                .filter(t -> t.getCode().equals(code))
                .findFirst();
        if (existEnum.isPresent()) {
            return existEnum.get();
        }

        throw new IllegalArgumentException("There is not exists " + enumType.getCanonicalName() + "." + code);
    }

    /**
     * checkValue : 检查值是否是指定枚举类型
     *
     * @param enumType 枚举类型
     * @param code     枚举值
     * @return boolean
     * @author dw_wangdonghong
     * @date 2018/11/6 14:51
     */
    static <C extends BaseEnum<?>> boolean checkValue(Class<C> enumType, Object code) {
        try {
            parse(enumType, code);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

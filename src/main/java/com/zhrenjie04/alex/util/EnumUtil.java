package com.zhrenjie04.alex.util;

import com.zhrenjie04.alex.core.BasicEnum;

public class EnumUtil {

    public static <T extends Enum<?> & BasicEnum> T codeOf(Class<T> enumClass, Object code) {
        T[] enumConstants = enumClass.getEnumConstants();
        for (T t : enumConstants) {
            if (t.getDbCode().equals(code)) {
                return t;
            }
        }
        return null;
    }
}
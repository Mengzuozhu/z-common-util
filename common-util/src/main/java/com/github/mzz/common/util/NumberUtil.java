package com.github.mzz.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * The type Number util.
 *
 * @author mengzz
 */
public class NumberUtil {

    /**
     * 是否是数字
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isNumeric(String str) {
        return toBigDecimal(str) != null;
    }

    /**
     * To big decimal
     *
     * @param obj the obj
     * @return the big decimal
     */
    public static BigDecimal toBigDecimal(Object obj) {
        return obj == null ? null : toBigDecimal(obj.toString());
    }

    /**
     * 是否是整数
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isInteger(String str) {
        return isNumeric(str) && !str.contains(".");
    }

    /**
     * 数字转为类似Excel列样式的大写字母（0->A, 1->B, 26->AA）
     *
     * @param index the index
     * @return the string
     */
    public static String numToUpperAlphabet(int index) {
        //A的Unicode码
        int begin = 65;
        return numberToAlphabet(index, begin);
    }

    /**
     * 数字转为类似Excel列样式的小写字母（0->a, 1->b, 26->aa）
     *
     * @param index the index
     * @return the string
     */
    public static String numToLowerAlphabet(int index) {
        //a的Unicode码
        int begin = 97;
        return numberToAlphabet(index, begin);
    }

    /**
     * 数字转为类似Excel列样式的字母（0->a, 1->b, 26->aa）
     *
     * @param index the index
     * @return the string
     */
    private static String numberToAlphabet(int index, int begin) {
        if (index < 0) {
            return "null";
        }
        String colName = "";
        for (int i = index; i >= 0; i--) {
            int remainder = i % 26;
            char cur = (char) (remainder + begin);
            colName = String.format("%s%s", cur, colName);
            //以26为进制
            i = i / 26;
        }
        return colName;
    }

    /**
     * To big decimal.
     *
     * @param str the str
     * @return the big decimal
     */
    private static BigDecimal toBigDecimal(String str) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new BigDecimal(str);
        } catch (Exception e) {
            return null;
        }
    }

}

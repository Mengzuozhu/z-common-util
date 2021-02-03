package com.github.mzz.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author mengzz
 **/
class NumberUtilTest {

    @Test
    void toBigDecimal() {
        BigDecimal bigDecimal = NumberUtil.toBigDecimal("12");
        Assertions.assertEquals(BigDecimal.valueOf(12), bigDecimal);
    }

    @Test
    void isInteger() {
        Assertions.assertTrue(NumberUtil.isInteger("12"));
        Assertions.assertFalse(NumberUtil.isInteger("0.12"));
    }

    @Test
    void isNumeric() {
        Assertions.assertTrue(NumberUtil.isNumeric("123"));
        Assertions.assertFalse(NumberUtil.isNumeric("a123"));
        Assertions.assertFalse(NumberUtil.isNumeric(null));
    }

    @Test
    void numToUpperAlphabet() {
        Assertions.assertEquals("A", NumberUtil.numToUpperAlphabet(0));
        Assertions.assertEquals("AA", NumberUtil.numToUpperAlphabet(26));
    }

    @Test
    void numToLowerAlphabet() {
        Assertions.assertEquals("a", NumberUtil.numToLowerAlphabet(0));
        Assertions.assertEquals("ab", NumberUtil.numToLowerAlphabet(27));
    }

}

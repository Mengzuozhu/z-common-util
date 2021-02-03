package com.github.mzz.common.util;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * The type Map util test.
 *
 * @author mengzz
 */
public class MapUtilTest {

    @Test
    public void getAnyOneValue() {
        String val = "val";
        Map<String, String> map = ImmutableMap.of("1", val, "2", "val2");
        Assertions.assertEquals(val, MapUtil.getAnyValue(map));
        Assertions.assertEquals(val, MapUtil.getAnyEntry(map).getValue());
    }
}

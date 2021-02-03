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
    private String val = "val";
    private Map<String, String> map = ImmutableMap.of("1", val, "2", val);

    @Test
    public void getAnyValue() {
        Assertions.assertEquals(val, MapUtil.getAnyValue(map));
    }

    @Test
    public void getAnyEntry() {
        Assertions.assertEquals(val, MapUtil.getAnyEntry(map).getValue());
    }
}

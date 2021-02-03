package com.github.mzz.common.util;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author mengzz
 **/
public class StreamUtilTest {

    @Test
    public void toMapByStream() {
        List<String> strings = Lists.newArrayList("1", "2", null);
        Map<String, String> map = StreamUtil.toMapByStream(strings, k -> k, v -> v);
        System.out.println(map);
    }

}

package com.github.mzz.json;

import com.alibaba.fastjson.JSONObject;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengzz
 **/
@ExtendWith(RandomBeansExtension.class)
class TypeReferenceUtilTest {

    @Random(type = String.class, size = 2)
    private List<String> strings;
    @Random
    private String key;
    @Random
    private Long val;

    @Test
    void getListTypeReference() {
        JSONObject jsonObject = JsonUtil.newJsonObject(key, strings);
        List<String> res = jsonObject.getObject(key, TypeReferenceUtil.getListTypeReference(String.class));
        assertEquals(strings, res);
    }

    @Test
    void getMapTypeReference() {
        JSONObject value = JsonUtil.newJsonObject(key, val);
        JSONObject jsonObject = JsonUtil.newJsonObject(key, value);
        Map<String, Long> res = jsonObject.getObject(key, TypeReferenceUtil.getMapTypeReference(String.class,
                Long.class));
        assertEquals(value, res);
    }
}

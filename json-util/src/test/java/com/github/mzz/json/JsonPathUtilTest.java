package com.github.mzz.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author mengzz
 **/
class JsonPathUtilTest {

    @Test
    public void getJsonEval() {
        JSONObject json = getJson();
        Object eval = JsonPathUtil.jsonEvalWithRoot(json, "$.simple.number2");
        System.out.println(eval);
        assertEquals(2, eval);
    }

    @Test
    public void all() {
        JSONObject json = getJson();
        Object all = JsonPathUtil.jsonEval(json, "$.simple");
        System.out.println(all);
        JSONObject jsonAll = all instanceof JSONObject ? ((JSONObject) all) : null;
        if (jsonAll != null) {
            assertEquals("字符串", jsonAll.getString("str"));
        }
    }

    @Test
    public void jsonEvalForArray() {
        JSONObject json = getJson();
        JSONArray eval1 = JsonPathUtil.jsonEvalForArray(json, "$..array.author");
        System.out.println(eval1);
        assertEquals(3, eval1.size());
    }

    private JSONObject getJson() {
        try {
            String jsonFile = "src/test/resources/demo.json";
            return JsonFileUtil.readFromJson(jsonFile, JSONObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

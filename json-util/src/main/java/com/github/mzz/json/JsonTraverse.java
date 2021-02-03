package com.github.mzz.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.mzz.common.util.TypeUtil;

import java.util.function.Consumer;

/**
 * The type Json traverse.
 *
 * @author mengzz
 */
public class JsonTraverse {

    /**
     * Deep traverse json.
     *
     * @param json     the json
     * @param consumer the consumer
     */
    public static void deepTraverseJson(JSON json, Consumer<JSONObject> consumer) {
        JSONArray array = TypeUtil.convertAs(json, JSONArray.class);
        if (array != null) {
            JsonUtil.foreachJsonArray(array, obj -> deepTraverseJson(obj, consumer));
        } else {
            JSONObject jsonObject = TypeUtil.convertAs(json, JSONObject.class);
            if (jsonObject != null) {
                consumer.accept(jsonObject);
            }
        }
    }

}

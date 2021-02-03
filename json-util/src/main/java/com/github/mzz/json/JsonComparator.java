package com.github.mzz.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Json比较器
 *
 * @author mengzz
 **/
public class JsonComparator {

    /**
     * 判断两个Json的内容是否一致，可忽略子元素顺序。
     * 但是该方法对于子数组中的元素顺序不一致的情况，还是会判断为不相等，
     * 例如：{"value": ["1","2"]}与{"value": ["2","1"]}，判断为不相等
     *
     * @param jObj1 the j obj 1
     * @param jObj2 the j obj 2
     * @return the boolean
     */
    public static boolean isJsonObjEqual(Map<String, Object> jObj1, Map<String, Object> jObj2) {
        Boolean checked = checkNull(jObj1, jObj2);
        if (checked != null) {
            return checked;
        }

        //先转为json字符串，再转为json对象进行比较，这样可以避免一些错判
        return JsonUtil.parseToJsonObject(jObj1).equals(JsonUtil.parseToJsonObject(jObj2));
    }

    /**
     * JSONArray的内容是否相等，顺序也要一一对应
     *
     * @param array1 the array 1
     * @param array2 the array 2
     * @return the boolean
     */
    public static boolean isJsonArrayEqual(JSONArray array1, JSONArray array2) {
        Boolean checked = checkNull(array1, array2);
        if (checked != null) {
            return checked;
        }
        if (array1.size() != array2.size()) {
            return false;
        }
        for (int i = 0; i < array1.size(); i++) {
            boolean equal = isJsonObjEqual(array1.getJSONObject(i), array2.getJSONObject(i));
            if (!equal) {
                return false;
            }
        }
        return true;
    }

    /**
     * 比较json中选中字段的值是否一样
     *
     * @param jObj1 the j obj 1
     * @param jObj2 the j obj 2
     * @param keys  the keys
     * @return the boolean
     */
    public static boolean isSelectedKeysJsonObjEqual(JSONObject jObj1, JSONObject jObj2, Set<String> keys) {
        Boolean checked = checkNull(jObj1, jObj2);
        if (checked != null) {
            return checked;
        }

        return copySelectKeysJson(jObj1, keys).equals(copySelectKeysJson(jObj2, keys));
    }

    /**
     * Check null.
     *
     * @param jObj1 the j obj 1
     * @param jObj2 the j obj 2
     * @return the boolean
     */
    public static Boolean checkNull(Object jObj1, Object jObj2) {
        if (jObj1 == jObj2) {
            return true;
        }
        if (jObj1 == null || jObj2 == null) {
            return false;
        }
        return null;
    }

    private static JSONObject copySelectKeysJson(JSONObject jsonObj, Set<String> keys) {
        JSONObject copy = new JSONObject();
        keys.forEach(prop -> copy.put(prop, jsonObj.get(prop)));
        return JsonUtil.parseToJsonObject(copy);
    }

}

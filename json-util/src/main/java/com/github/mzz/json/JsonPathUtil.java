package com.github.mzz.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.github.mzz.common.util.CollectionUtils;
import com.github.mzz.common.util.TypeUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JSONPath工具
 *
 * @author mengzz
 **/
public class JsonPathUtil {
    private static final String JSON_PATH_ROOT_PREFIX = "$.";
    private static final String PERCENT = "%";

    /**
     * 根据json路径获取值
     *
     * @param field the field
     * @param path  the path
     * @return the json eval
     */
    public static Object jsonEval(Object field, String path) {
        if (field == null || StringUtils.isEmpty(path)) {
            return null;
        }
        return JSONPath.eval(field, path);
    }

    /**
     * 根据json路径获取值(不添加头)
     *
     * @param field the field
     * @param path  the path
     * @return json eval 2
     */
    public static Object jsonEvalWithRoot(Object field, String path) {
        if (field == null || StringUtils.isEmpty(path)) {
            return null;
        }
        path = addRootPrefix(path);
        return JSONPath.eval(field, path);
    }

    /**
     * 根据json路径获取值，并转换为对应的类
     *
     * @param <T>   the type parameter
     * @param field the field
     * @param path  the path
     * @param cls   the cls
     * @return the json eval
     */
    public static <T> T jsonEval(Object field, String path, Class<T> cls) {
        return TypeUtil.convertAs(jsonEval(field, path), cls);
    }

    /**
     * 根据json路径获取值列表
     *
     * @param jsonObj the json obj
     * @param path    the path
     * @return the json eval for list
     */
    public static JSONArray jsonEvalForArray(JSONObject jsonObj, String path) {
        return JsonUtil.addOrToJsonArray(jsonEval(jsonObj, path));
    }

    /**
     * 根据jsonPath设置值
     *
     * @param field the field
     * @param path  the path
     * @param val   the val
     */
    public static void setJsonEval(Object field, String path, Object val) {
        if (val == null) {
            return;
        }
        setJsonEvalAllowNull(field, path, val);
    }

    /**
     * 根据jsonPath设置值，允许null
     *
     * @param field the field
     * @param path  the path
     * @param val   the val
     */
    public static void setJsonEvalAllowNull(Object field, String path, Object val) {
        if (field == null || StringUtils.isEmpty(path)) {
            return;
        }
        path = addRootPrefix(path);
        JSONPath.set(field, path, val);
    }

    /**
     * 按条件过滤
     *
     * @param <T>       the type parameter
     * @param field     the field
     * @param condition the condition
     * @param cls       the cls
     * @return the list
     */
    public static <T> List<T> filter(Object field, String condition, Class<T> cls) {
        if (field == null || StringUtils.isEmpty(condition)) {
            return new ArrayList<>();
        }
        Object eval = JSONPath.eval(field, condition);
        T val = TypeUtil.convertAs(eval, cls);
        if (val != null) {
            List<T> list = new ArrayList<>();
            list.add(val);
            return list;
        } else {
            JSONArray jsonArray = TypeUtil.convertAs(eval, JSONArray.class);
            return jsonArray != null ? jsonArray.toJavaList(cls) : new ArrayList<>();
        }
    }

    /**
     * 按条件过滤，并获取第一个匹配的值
     *
     * @param <T>       the type parameter
     * @param field     the field
     * @param condition the condition
     * @param cls       the cls
     * @return the t
     */
    public static <T> T filterForFirst(Object field, String condition, Class<T> cls) {
        List<T> filter = filter(field, condition, cls);
        return CollectionUtils.isNotEmpty(filter) ? filter.get(0) : null;
    }

    /**
     * 根据jsonPath，获取数组节点带索引的jsonPath和对应的值
     * 例如：path为：val.sub.a，结果为：{"val.sub[0].a", "1"}，其中sub为json数组
     *
     * @param json the json
     * @param path the path
     * @return the json path and values
     */
    public static Map<String, Object> getJsonPathAndValues(Object json, String path) {
        String[] names = path.split("\\.");
        if (ArrayUtils.isEmpty(names) || json == null) {
            return new LinkedHashMap<>();
        }
        StringBuilder allPaths = new StringBuilder();
        Map<String, Object> pathAndValues = new LinkedHashMap<>();
        findPathAndValue(pathAndValues, json, names, 0, allPaths);
        return pathAndValues;
    }

    private static void findPathAndValue(Map<String, Object> pathAndValues, Object object, String[] names, int index,
                                         StringBuilder allPaths) {
        if (object == null || index >= names.length) {
            pathAndValues.put(allPaths.toString(), object);
            return;
        }

        //使用接口Map，因为json里面的数据转换为java类时，不一定都是jsonObject类
        if (object instanceof Map) {
            String name = names[index];
            if (allPaths.length() != 0) {
                allPaths.append(".");
            }
            allPaths.append(name);
            Map jsonObj = (Map) object;
            findPathAndValue(pathAndValues, jsonObj.get(name), names, ++index, allPaths);
        } else if (object instanceof List) {
            List array = (List) object;
            for (int i = 0; i < array.size(); i++) {
                Object obj = array.get(i);
                StringBuilder subPaths = new StringBuilder(allPaths.toString());
                if (subPaths.length() != 0) {
                    subPaths.append(String.format("[%d]", i));
                }
                findPathAndValue(pathAndValues, obj, names, index, subPaths);
            }
        }
    }

    private static String addRootPrefix(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        if (!path.startsWith(JSON_PATH_ROOT_PREFIX)) {
            path = String.format("%s%s", JSON_PATH_ROOT_PREFIX, path);
        }
        return escapePath(path);
    }

    private static String escapePath(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        //转义 %
        if (path.contains(PERCENT)) {
            path = StringUtils.replace(path, PERCENT, "\\%");
        }
        return path;
    }
}

package com.github.mzz.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Json file util.
 *
 * @author mengzz
 */
public class JsonFileUtil {

    /**
     * Parse json object.
     *
     * @param inputStream the input stream
     * @return the json object
     * @throws IOException the io exception
     */
    public static JSONObject parseObject(InputStream inputStream) throws IOException {
        return JSON.parseObject(inputToString(inputStream));
    }

    /**
     * Parse object json object.
     *
     * @param file the file
     * @return the json object
     */
    public static JSONObject parseObject(String file) {
        try {
            return readFromJson(file, JSONObject.class);
        } catch (IOException e) {
            ExceptionUtils.rethrow(e);
        }
        return null;
    }

    /**
     * Parse json array.
     *
     * @param inputStream the input stream
     * @return the json array
     * @throws IOException the io exception
     */
    public static JSONArray parseArray(InputStream inputStream) throws IOException {
        return JSON.parseArray(inputToString(inputStream));
    }

    /**
     * Parse array json array.
     *
     * @param file the file
     * @return the json array
     */
    public static JSONArray parseArray(String file) {
        try {
            return readFromJson(file, JSONArray.class);
        } catch (IOException e) {
            ExceptionUtils.rethrow(e);
        }
        return null;
    }

    /**
     * Read from json.
     *
     * @param <T>   the type parameter
     * @param file  the file
     * @param clazz the clazz
     * @return the t
     * @throws IOException the io exception
     */
    public static <T> T readFromJson(String file, Class<T> clazz) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(new File(file))) {
            return readFromJson(inputStream, clazz);
        }
    }

    /**
     * Read from json.
     *
     * @param <T>         the type parameter
     * @param inputStream the input stream
     * @param clazz       the clazz
     * @return the t
     * @throws IOException the io exception
     */
    public static <T> T readFromJson(InputStream inputStream, Class<T> clazz) throws IOException {
        return JSON.parseObject(inputToString(inputStream), clazz);
    }

    /**
     * Write to file.
     *
     * @param path the path
     * @param data the data
     * @throws IOException the io exception
     */
    public static void writeToFile(String path, JSON data) throws IOException {
        FileUtils.writeStringToFile(new File(path), data.toJSONString(), Charset.defaultCharset());
    }

    private static String inputToString(InputStream inputStream) throws IOException {
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

}

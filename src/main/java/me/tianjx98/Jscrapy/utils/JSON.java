package me.tianjx98.Jscrapy.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import me.tianjx98.Jscrapy.pipeline.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @ClassName JSON
 * @Description TODO
 * @Author tian
 * @Date 2019/7/23 11:30
 * @Version 1.0
 */
public class JSON {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSON.class);

    private static ObjectMapper mapper;
    private static ObjectWriter prettyWriter;
    private static boolean prettyFormat;

    static {
        mapper = new ObjectMapper();
        // 允许字段名没有双引号
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        prettyWriter = mapper.writerWithDefaultPrettyPrinter();
    }

    /**
     * 设置输出格式，有紧凑和容易阅读两种格式
     *
     * @param prettyFormat ture 设置为易于阅读的格式，否则默认为紧凑格式
     */
    public static void setPrettyFormat(boolean prettyFormat) {
        JSON.prettyFormat = prettyFormat;
    }

    /**
     * 读取json对象，可以直接通过get方法获取节点数据
     *
     * @param jsonString json字符串
     * @return JsonNode对象
     */
    public static JsonNode getJosnNode(String jsonString) {
        try {
            return mapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将java对象转为Json字符串
     *
     * @param item 需要转换的对象
     * @return 如果转换成功，返回Json字符串，否则返回null
     */
    public static String getJson(Item item) {
        try {
            return prettyFormat ? prettyWriter.writeValueAsString(item) : mapper.writeValueAsString(item);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * 获取一个对象来连续写出Json数据到一个Json对象数组
     * 写出完毕后要调用close()方法来关闭写处对象
     *
     * @param out 输出流
     * @return 写出Json数据的对象
     */
    public static SequenceWriter getJsonWriter(OutputStream out) {
        try {
            //prettyWriter.writeValue(out, object);
            return prettyFormat ? prettyWriter.writeValuesAsArray(out) : mapper.writer().writeValuesAsArray(out);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取Json字符串为Java对象
     *
     * @param jsonString Json字符串
     * @param clazz      Java类对象
     * @return 如果转换成功，返回Java对象，否则返回null
     */
    public static <T> T getObject(String jsonString, Class<T> clazz) {
        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * 读取Json字符串为List对象
     *
     * @param jsonString Json字符串
     * @param type       要转换的对象类型，例如new TypeReference<ArrayList<TestItem>>(){}
     * @return 如果转换成功，返回List，否则返回null
     */
    public static <T> T getList(String jsonString, TypeReference<T> type) {
        try {
            return mapper.readValue(jsonString, type);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    /**
     * 从输入流中读取Json字符串为List对象
     *
     * @param in   输入流
     * @param type 要转换的对象类型，例如new TypeReference<ArrayList<TestItem>>(){}
     * @return 如果转换成功，返回List，否则返回null
     */
    public static <T> T getList(InputStream in, TypeReference<T> type) {
        try {
            return mapper.readValue(in, type);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }
}

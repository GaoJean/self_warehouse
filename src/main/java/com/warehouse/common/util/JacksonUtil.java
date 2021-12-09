package com.warehouse.common.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JacksonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

    private JacksonUtil() {
    }

    private static final class HOLDER {
        private static final ObjectMapper INSTANT = new ObjectMapper();

        static {
            INSTANT.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
    }

    public static ObjectMapper getInstance() {
        return HOLDER.INSTANT;
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return getInstance().readValue(json, clazz);
        } catch (IOException e) {
            LOGGER.error("json 解析异常", e);
        }
        return null;
    }

    public static <T> T toObject(byte[] bytes, Class<T> clazz) {
        if (bytes == null) {
            return null;
        }
        try {
            return getInstance().readValue(bytes, clazz);
        } catch (IOException e) {
            LOGGER.error("json 解析异常", e);
        }
        return null;
    }

    public static <T> String toJson(T t) {
        if (t == null) {
            return null;
        }
        try {
            return getInstance().writeValueAsString(t);
        } catch (JsonProcessingException e) {
            LOGGER.error("json 序列化异常", e);
        }
        return null;
    }

    public static <T> byte[] toBytes(T t) {
        if (t == null) {
            return null;
        }
        try {
            return getInstance().writeValueAsBytes(t);
        } catch (JsonProcessingException e) {
            LOGGER.error("json 序列化异常", e);
        }
        return null;
    }

    /**
     * 判定一个字符串是否为合法的JSON格式字符串
     *
     * @param str 待判定的字符串
     * @return true-合法JSON格式字符串, false-非JSON格式字符串
     */
    public static boolean isValidJsonStr(String str) {
        if (str == null) {
            return false;
        }
        str = str.trim();
        if ("".equals(str) || !str.startsWith("{") || !str.endsWith("}")) {
            return false;
        }
        try {
            JacksonUtil.getInstance().readTree(str);
            return true;
        } catch (Exception ex) {
            LOGGER.error("验证json合法性错误", ex);
        }
        return false;
    }
}

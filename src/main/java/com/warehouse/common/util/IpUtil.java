package com.warehouse.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/25 21:52
 */
public class IpUtil {

    public static String getClientAddr(HttpServletRequest request) {
        String str = request.getHeader("X-Forwarded-For");
        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("X-Real-IP");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("Proxy-Client-IP");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("WL-Proxy-Client-IP");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("HTTP_CLIENT_IP");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (str == null || str.length() == 0 || "unknown".equalsIgnoreCase(str)) {
            str = request.getRemoteAddr();
        }

        if (str != null && str.indexOf(",") != -1) {
            str = str.substring(str.lastIndexOf(",") + 1, str.length()).trim();
        }

        return String.valueOf(str);
    }

    public static String getHttp(String url) {
        String http = "";
        if (url.indexOf("http://") != -1) {
            http = "http://";
        } else if (url.indexOf("https://") != -1) {
            http = "https://";
        }

        return http;
    }
}

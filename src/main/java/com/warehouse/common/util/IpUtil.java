package com.warehouse.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/25 21:52
 */
public class IpUtil {
    public static String localhost = "127.0.0.1";

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

    public static String getLocalIp() throws SocketException {
        String localIp = null;
        InetAddress inetAddress = null;
        Enumeration<NetworkInterface> allNetInterfaces = null;
        allNetInterfaces = NetworkInterface.getNetworkInterfaces();

        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            Enumeration addresses = netInterface.getInetAddresses();

            while (addresses.hasMoreElements()) {
                inetAddress = (InetAddress) addresses.nextElement();
                if (inetAddress != null && inetAddress instanceof Inet4Address) {
                    String host = inetAddress.getHostAddress();
                    if (!localhost.equals(host)) {
                        localIp = host;
                    }
                }
            }
        }

        return localIp;
    }

    public static int ip2Int(String strIp) {
        try {
            int[] ip = new int[4];
            String[] ipArr = strIp.split("\\.");
            ip[0] = Integer.parseInt(ipArr[0]);
            ip[1] = Integer.parseInt(ipArr[1]);
            ip[2] = Integer.parseInt(ipArr[2]);
            ip[3] = Integer.parseInt(ipArr[3]);
            return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
        } catch (Exception var3) {
            throw new IllegalArgumentException("invalid IP " + strIp);
        }
    }

    public static String int2Ip(int intIp) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(intIp >>> 24)).append(".");
        sb.append(String.valueOf((intIp & 16777215) >>> 16)).append(".");
        sb.append(String.valueOf((intIp & '\uffff') >>> 8)).append(".");
        sb.append(String.valueOf(intIp & 255));
        return sb.toString();
    }

}

package com.warehouse.common.util;

import static com.warehouse.common.error.ApiError.TIME_ERROR;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.warehouse.common.exception.HouseException;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/06 17:49
 */
public class DateUtil {
    /**
     * 格式化通配符: yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 字符串格式转换为date
     *
     * @param str
     * @return
     * @throws HouseException
     */
    public static Date formatToTime(String str) throws HouseException {
        try {
            if (StringUtils.isBlank(str)) {
                throw new HouseException(TIME_ERROR);
            }
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME);
            return sdf.parse(str);
        } catch (ParseException parseException) {
            throw new HouseException(TIME_ERROR);
        }
    }

}

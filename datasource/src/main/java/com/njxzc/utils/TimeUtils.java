package com.njxzc.utils;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * projectName:  epidemicSystem
 * packageName: com.njxzc.utils
 * date: 2022-04-05 00:00
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class TimeUtils {
    public static String format(Long timestamp,String pattern){
        return FastDateFormat.getInstance(pattern).format(timestamp);
    }
}

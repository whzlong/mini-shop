package com.cn.chonglin.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 数据处理工具
 *
 * @author wu
 */
public class DataUtils {
    private static final String DATA_SEPARATOR = "$";

    /**
     * 列表变成字符串
     *
     * @param list 集合
     * @return 使用"$"分割字符串
     */
    public static String join(List<? extends Object> list){
        return StringUtils.join(list, DATA_SEPARATOR);
    }

    /**
     * 字符串变成集合
     *
     * @param v 字符串
     * @return 字符串集合
     */
    public static List<String> toList(String v){
        return Arrays.asList(toArray(v));
    }

    /**
     * 字符串变成数组
     *
     * @param v 字符串
     * @return 字符串数组
     */
    public static String[] toArray(String v){
        return StringUtils.isBlank(v) ? new String[0] : StringUtils.split(v, DATA_SEPARATOR);
    }
}

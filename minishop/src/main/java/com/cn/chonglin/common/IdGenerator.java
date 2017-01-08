package com.cn.chonglin.common;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * ID生成
 *
 * @author wu
 */
public class IdGenerator {

    public static String getUuid(){
        return StringUtils.remove(UUID.randomUUID().toString(), "-");
    }
}

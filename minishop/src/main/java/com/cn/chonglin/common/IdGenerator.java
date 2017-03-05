package com.cn.chonglin.common;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
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

    /**
     * 生成优惠码
     *
     * @return
     */
    public static String createCouponCode(){
        int min = 10000000;
        int max = 99999999;

        Random random = new Random();

        String randomStr = String.valueOf(random.ints(min, max + 1).limit(1).findFirst().getAsInt());

        LocalDate localDate = LocalDate.now();

        String year = localDate.format(DateTimeFormatter.ofPattern("YY"));
        String month = localDate.format(DateTimeFormatter.ofPattern("MM"));
        String day = localDate.format(DateTimeFormatter.ofPattern("dd"));

        String code = randomStr.substring(0,2) + year + randomStr.substring(2,4) + month + randomStr.substring(4,5) + day + randomStr.substring(5,8);

        return code;
    }
}

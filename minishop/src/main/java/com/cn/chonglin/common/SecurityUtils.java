package com.cn.chonglin.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * 系统安全处理工具
 *
 * @author wu
 */
public class SecurityUtils {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);
    private static final String RANDOM_CONTENT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * 随即字符串
     *
     * @return
     */
    public static String randomStr(int len){
        return RandomStringUtils.random(len, RANDOM_CONTENT);
    }

    /**
     * 签证字符串
     *
     * @param content 签名字符串
     * @return
     */
    public static String sha1(String content){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] ds = digest.digest(content.getBytes());

            StringBuffer sb = new StringBuffer();

            for(byte d : ds){
                String s = Integer.toHexString(d & 0xFF);
                if(s.length() < 2){
                    sb.append(0);
                }

                sb.append(s);
            }

            logger.debug("Receive message sha1 is {}", sb.toString());

            return sb.toString();
        }catch(Exception e){
            logger.error("Receive message sha1 error, error is {}", e.getMessage());
            return "";
        }
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码,缺省编码字符集是UTF-8
     *
     * @param content  加密内容
     * @return 加密字符串
     */
    public static String encrypt(String content){
        return encrypt(content , "UTF-8");
    }

    /**
     * MD5编码
     *
     * @param content     加密内容
     * @param charsetName 字符集
     * @return md5加密字符串
     */
    public static String encrypt(String content, String charsetName) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            if (StringUtils.isBlank(charsetName)){
                charsetName = StringUtils.isBlank(charsetName) ? "UTF-8" : charsetName;
            }

            StringBuffer sb = new StringBuffer();

            byte[] bytes = md.digest(content.getBytes(charsetName));

            for (byte b : bytes){
                sb.append(byteToHexString(b));
            }

            return sb.toString();
        } catch (Exception ex) {
            logger.error("MD5 encode is failed, error is {}", ex.getMessage());
            return "";
        }
    }
}

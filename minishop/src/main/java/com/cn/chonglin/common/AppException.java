package com.cn.chonglin.common;

/**
 * 业务异常
 *
 * code: 错误编码
 * message：错误信息
 *
 * @author wu
 */
public class AppException extends RuntimeException{
    private static final int DEFAULT_CODE = 1000;
    private int code;

    public AppException(String message){
        this(DEFAULT_CODE, message);
    }

    public AppException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    @Override
    public String toString(){
        return "AppException [code=" + code + ", message=" + getMessage() + "]";
    }

}

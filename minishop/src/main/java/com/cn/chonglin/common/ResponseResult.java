package com.cn.chonglin.common;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 客户端响应对象
 *
 * @author wu
 */
public class ResponseResult<T> {
    private final int code;
    private final String message;
    private final T rs;

    /**
     * 返回成功信息
     *
     * @param rs  输出信息
     * @param <T> 输出信息对象类型
     * @return
     */
    public static <T> ResponseResult<T> success(T rs){
        return new ResponseResult<T>(0, "", rs);
    }

    /**
     * 返回错误信息
     *
     * @param fieldErrors 验证错误对象集合
     * @return
     */
    public static <T> ResponseResult<T> error(List<FieldError> fieldErrors){
        StringBuilder builder = new StringBuilder();

        fieldErrors.forEach(e -> {
            builder.append(String.format("%s &nbsp;--> &nbsp;%s<br>", e.getField(), e.getDefaultMessage()));
        });

        return error(1, builder.toString());
    }

    /**
     * 返回错误信息
     *
     * @param code     错误编码
     * @param message  错误信息
     * @return
     */
    public static <T> ResponseResult<T> error(int code, String message){
        return error(code, message, null);
    }

    /**
     * 返回错误信息
     *
     * @param code     错误编码
     * @param message  错误信息
     * @param rs  输出信息
     * @return
     */
    public static <T> ResponseResult<T> error(int code, String message, T rs){
        return new ResponseResult<T>(code, message, null);
    }

    private ResponseResult(int code, String message, T rs) {
        this.code = code;
        this.message = message;
        this.rs = rs;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getRs() {
        return rs;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");

        sb.append("code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", rs=").append(rs);
        sb.append('}');

        return sb.toString();
    }
}

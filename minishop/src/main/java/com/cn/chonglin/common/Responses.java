package com.cn.chonglin.common;

/**
 * 通用输出实体
 *
 * @author whz
 */
public class Responses {
    /**
     * 编号输出对象
     *
     * @param _id 编号
     * @return 输出对象
     */
    public final static Object id(String _id){
        return new Object(){
            @SuppressWarnings("unused")
            public String id = _id;
        };
    }
}

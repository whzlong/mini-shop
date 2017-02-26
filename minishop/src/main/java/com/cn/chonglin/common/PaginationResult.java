package com.cn.chonglin.common;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 返回分页查询结果
 *
 * @param <T>
 */
public class PaginationResult<T> {
    private final int code;
    private final String message;
    private final int count;
    private final List<T> data;
    private int page;
    private int size;

    public static <T> PaginationResult<T> success(ListPage<T> page){
        return success(page.getCount(), page.getContent());
    }

    public static <I, T> PaginationResult<T> success(ListPage<I> page, ConvertResult<I, T> convert){
        return success(page.getCount(), page.getContent(), convert);
    }

    public static <T> PaginationResult<T> success(int count, List<T> data) {
        return new PaginationResult<T>(0, "", count, data);
    }

    public static <I, T> PaginationResult<T> success(int count, List<I> data, ConvertResult<I, T> convert) {
        return new PaginationResult<T>(0, "", count,
                data.stream().map(e -> convert.convert(e)).collect(Collectors.toList()));
    }

    public static <T> PaginationResult<T> error(String message) {
        return error(5001, message);
    }

    public static <T> PaginationResult<T> error(int code, String message) {
        return new PaginationResult<T>(code, message, -1, null);
    }

    private PaginationResult(int code, String message, int count, List<T> data) {
        this.code = code;
        this.message = message;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getCount() {
        return count;
    }

    public List<T> getData() {
        return data;
    }

    public int getPageCount() {
        return PaginationResultUtils.pageCount(count, size);
    }

    public int getPage() {
        return page;
    }

    public PaginationResult<T> setPage(int page) {
        this.page = page;
        return this;
    }

    public int getSize() {
        return size;
    }

    public PaginationResult<T> setSize(int size) {
        this.size = size;
        return this;
    }

    @FunctionalInterface
    public static interface ConvertResult<I, O>{

        O convert(I i);
    }
}

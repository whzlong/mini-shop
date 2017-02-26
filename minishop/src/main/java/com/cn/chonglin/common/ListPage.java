package com.cn.chonglin.common;

import java.util.List;

/**
 * 列表查询页
 *
 */
public class ListPage<T> {
    private final int count;
    private final List<T> content;

    public ListPage(int count, List<T> content) {
        this.count = count;
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public List<T> getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ListPage [count=" + count + ", content=" + content + "]";
    }
}

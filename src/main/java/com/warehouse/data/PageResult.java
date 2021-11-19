package com.warehouse.data;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/11/08 23:21
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 3952837200343554212L;
    private long totalCount;
    private long totalPage;
    private int pageNum;
    private int pageSize;
    private List<T> list;

    public PageResult() {
    }

    public PageResult(PageResult<?> origin, List<T> list) {
        this(origin.getTotalCount(), origin.getTotalPage(), origin.getPageNum(), origin.getPageSize(), list);
    }

    public PageResult(long totalCount, long totalPage, int pageNum, int pageSize, List<T> list) {
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.list = list;
    }

}

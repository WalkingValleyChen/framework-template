package com.chen.request;

/**
 * 分页请求
 * @author ValleyChen
 * @version 1.0.0
 * @time 2017/4/22
 */
public class Page {

    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

package com.happydriving.rockets.server.model;

/**
 * @author mazhiqiang
 */
public class PaginationInfo {

    private final int pageNum;
    private final int pageSize;

    public PaginationInfo(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }
}

package com.infinite.water.core.controller.response;

public class PagerResponse extends RestResponse {
    private long size;
    private long total;
    private long pages;

    public PagerResponse(String code, String message) {
        super(code, message);
    }

    public PagerResponse(String code, String message, long size, long total, long pages) {
        super(code, message);
        this.size = size;
        this.total = total;
        this.pages = pages;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }
}

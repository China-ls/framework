package com.infinite.water.controller.resp;

public class CodePagerResponse extends CodeRestResponse {
    private long size;
    private long total;
    private long pages;

    public CodePagerResponse(ResponseCode code) {
        super(code);
    }

    public CodePagerResponse(ResponseCode code, Object data) {
        super(code, data);
    }

    public CodePagerResponse(ResponseCode code, long size, long total, long pages) {
        super(code);
        this.size = size;
        this.total = total;
        this.pages = pages;
    }

    public CodePagerResponse(ResponseCode code, Object data, long size, long total, long pages) {
        super(code, data);
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

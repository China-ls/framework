package com.infinite.water.controller.resp;

public class PagerResponse extends RestResponse {
    private long size;
    private long total;
    private long pages;

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

    @Override
    public String toString() {
        return "PagerResponse{" +
                "size=" + size +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }
}

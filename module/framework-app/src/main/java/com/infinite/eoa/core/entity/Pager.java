package com.infinite.eoa.core.entity;

import java.util.List;

public class Pager<T> extends AbstractEntity {
    private long total;
    private int page;
    private int size;
    private int totalPage;
    private boolean hasNext;
    private boolean hasPre;
    private List<T> data;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "total=" + total +
                ", page=" + page +
                ", size=" + size +
                ", totalPage=" + totalPage +
                ", hasNext=" + hasNext +
                ", hasPre=" + hasPre +
                ", data=" + data +
                '}';
    }
}

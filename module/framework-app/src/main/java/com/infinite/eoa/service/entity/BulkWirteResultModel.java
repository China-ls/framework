package com.infinite.eoa.service.entity;


import com.infinite.eoa.core.entity.AbstractEntity;

public class BulkWirteResultModel extends AbstractEntity {
    private int deletedCount;
    private int insertedCount;
    private int matchedCount;
    private int modifiedCount;

    public BulkWirteResultModel(int deletedCount, int insertedCount, int matchedCount, int modifiedCount) {
        this.deletedCount = deletedCount;
        this.insertedCount = insertedCount;
        this.matchedCount = matchedCount;
        this.modifiedCount = modifiedCount;
    }

    public int getDeletedCount() {
        return deletedCount;
    }

    public void setDeletedCount(int deletedCount) {
        this.deletedCount = deletedCount;
    }

    public int getInsertedCount() {
        return insertedCount;
    }

    public void setInsertedCount(int insertedCount) {
        this.insertedCount = insertedCount;
    }

    public int getMatchedCount() {
        return matchedCount;
    }

    public void setMatchedCount(int matchedCount) {
        this.matchedCount = matchedCount;
    }

    public int getModifiedCount() {
        return modifiedCount;
    }

    public void setModifiedCount(int modifiedCount) {
        this.modifiedCount = modifiedCount;
    }

    @Override
    public String toString() {
        return "BulkWirteResultModel{" +
                "deletedCount=" + deletedCount +
                ", insertedCount=" + insertedCount +
                ", matchedCount=" + matchedCount +
                ", modifiedCount=" + modifiedCount +
                '}';
    }
}

package com.infinite.framework.core.object;

import org.bson.types.ObjectId;

/**
 * Created by hx on 16-7-5.
 */
public interface IObjectIdable {
    ObjectId getObjectId();

    void setObjectId(ObjectId objectId);
}

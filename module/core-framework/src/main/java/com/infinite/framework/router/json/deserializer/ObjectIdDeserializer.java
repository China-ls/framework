package com.infinite.framework.router.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * Created by hx on 16-8-29.
 */
public class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {

    @Override
    public Class<?> handledType() {
        return ObjectId.class;
    }

    @Override
    public ObjectId deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
//        ctxt.read
        return null;
    }
}

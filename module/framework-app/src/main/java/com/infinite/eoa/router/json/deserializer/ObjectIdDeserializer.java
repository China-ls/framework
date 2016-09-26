package com.infinite.eoa.router.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdDeserializer extends JsonDeserializer<ObjectId> {

    @Override
    public Class<?> handledType() {
        return ObjectId.class;
    }

    @Override
    public ObjectId deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String hexString = ctxt.readValue(p, String.class);
        return null == hexString || hexString.length() <= 0 ? null : new ObjectId(hexString);
    }
}

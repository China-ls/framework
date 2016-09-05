package com.infinite.framework.router.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

/**
 * Created by hx on 16-8-29.
 */
public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

    @Override
    public Class<ObjectId> handledType() {
        return ObjectId.class;
    }

    @Override
    public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        gen.writeString(value.toHexString());
    }
}

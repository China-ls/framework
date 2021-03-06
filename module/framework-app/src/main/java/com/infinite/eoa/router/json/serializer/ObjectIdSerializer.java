package com.infinite.eoa.router.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> {

    @Override
    public Class<ObjectId> handledType() {
        return ObjectId.class;
    }

    @Override
    public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        if (null == value) {
            gen.writeNull();
        } else {
            gen.writeString(value.toHexString());
        }
    }
}

package com.infinite.eoa.entity;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class PersistentRoleOperationGsonAdapter extends TypeAdapter<PersistentRoleOperation> {

    @Override
    public void write(JsonWriter jsonWriter, PersistentRoleOperation persistentRoleOperation) throws IOException {
        if (null != persistentRoleOperation) {
            jsonWriter.value(persistentRoleOperation.getValue());
        }
    }

    @Override
    public PersistentRoleOperation read(JsonReader jsonReader) throws IOException {
        return PersistentRoleOperation.fromValue(jsonReader.nextString());
    }
}

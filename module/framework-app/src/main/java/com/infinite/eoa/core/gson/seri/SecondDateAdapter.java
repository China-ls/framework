package com.infinite.eoa.core.gson.seri;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

public class SecondDateAdapter extends TypeAdapter<Date> {
    public Date read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        Long time = reader.nextLong();
        return new Date(time * 1000);
    }

    public void write(JsonWriter writer, Date value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value.getTime());
    }
}

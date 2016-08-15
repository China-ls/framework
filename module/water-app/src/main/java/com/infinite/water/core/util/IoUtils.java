package com.infinite.water.core.util;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class IoUtils {

    public static String read(String filePathName) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(filePathName);
        String text = null;
        try {
            text = read(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(fis);
        }
        return text;
    }

    public static String read(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        int len = -1;
        byte[] buffer = new byte[1024];
        IOException exception = null;
        try {
            while ((len = is.read(buffer)) > 0) {
                sb.append(new String(buffer, 0, len));
            }
        } catch (IOException e) {
            exception = e;
        }
        if (null != exception) {
            throw exception;
        }
        return sb.toString();
    }

    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
            } finally {
                closeable = null;
            }
        }
    }
}

package com.infinite.water.core.util;

import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class HttpUtils {
    private static final ArrayList<Pair> HEADERS = new ArrayList<Pair>(0);

    public static void addDefaultHeader(String key, String value) {
        HEADERS.add(new Pair(key, value));
    }

    public static class Pair {
        private String key;
        private String value;

        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    public enum Method {
        POST, GET, PUT, DELETE;
    }

    public static String get(String uri, Pair... params) throws IOException {
        return get(uri, HEADERS, params);
    }

    public static String get(String uri, List<Pair> headers, Pair... params) throws IOException {
        return request(uri, Method.GET, headers, params);
    }

    public static String post(String uri, Pair... params) throws IOException {
        return post(uri, HEADERS, params);
    }

    public static String post(String uri, List<Pair> headers, Pair... params) throws IOException {
        return request(uri, Method.POST, headers, params);
    }

    public static String put(String uri, Pair... params) throws IOException {
        return put(uri, HEADERS, params);
    }

    public static String put(String uri, List<Pair> headers, Pair... params) throws IOException {
        return request(uri, Method.PUT, headers, params);
    }

    public static String delete(String uri, Pair... params) throws IOException {
        return delete(uri, HEADERS, params);
    }

    public static String delete(String uri, List<Pair> headers, Pair... params) throws IOException {
        return request(uri, Method.DELETE, headers, params);
    }

    public static String request(String uri, Method method, List<Pair> headers, Pair... params) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (null != params && params.length > 0) {
            HashMap<String, Pair> parmsMap = new HashMap<String, Pair>();
            for (Pair param : params) {
                Pair pair = parmsMap.get(param.key);
                if (null == pair) {
                    parmsMap.put(param.key, param);
                } else {
                    pair.value += "," + param.value;
                    parmsMap.put(param.key, pair);
                }
            }
            int i = 0;
            for (String key : parmsMap.keySet()) {
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(parmsMap.get(key).value));
                if (i != params.length - 1) {
                    stringBuilder.append("&");
                }
                i++;
            }
        }

        URL url = new URL(method == Method.GET || method == Method.DELETE ? uri + "?" + stringBuilder.toString() : uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        if (headers != null) {
            for (Pair pair : headers) {
//                connection.addRequestProperty(pair.key, URLEncoder.encode(pair.value));
                connection.addRequestProperty(pair.key, pair.value);
            }
        }
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod(method.name());
        connection.setDoInput(true);
        if (method == Method.POST || method == Method.PUT) {
            connection.setDoOutput(true);
        }
        connection.connect();

        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        IOException exception = null;
        OutputStream os = null;
        try {
            if (method != Method.GET && method != Method.DELETE) {
                os = connection.getOutputStream();
                os.write(stringBuilder.toString().getBytes("utf-8"));
            }
            stringBuilder.setLength(0);
            is = connection.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            exception = e;
        } finally {
            IoUtils.closeQuietly(is);
            IoUtils.closeQuietly(isr);
            IoUtils.closeQuietly(br);
            connection.disconnect();
        }
        if (null != exception) {
            throw exception;
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
//        System.out.println(
//                post("http://127.0.0.1:61680/api/json/session/signin",
//                        Arrays.asList(new Pair("AuthPrompt", "false"),
//                                new Pair("Content-Type", "application/json")),
//                        new Pair("username", "admin"), new Pair("password", "password")
//                )
//        );
        String auth = Base64.encodeBase64String("admin:password".getBytes());
        System.out.println( auth );
        System.out.println(
                get("http://127.0.0.1:61680/broker.json",
                        Arrays.asList(
                                new Pair("Authorization", "Basic " + Base64.encodeBase64String("admin:password".getBytes()))
                        )
                )
        );
    }

}

package com.infinite.framework.core.util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public abstract class HttpUtils {
    private static HttpClient httpClient = HttpClients.createDefault();

    public static String get(String uri, NameValuePair... pairs) throws IOException {
        return get(uri, null, pairs);
    }

    public static String get(String uri, Header[] headers, NameValuePair... pairs) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.get(uri);
        return request(requestBuilder, headers, pairs);
    }

    public static String delete(String uri, Header[] headers, NameValuePair... pairs) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.delete(uri);
        return request(requestBuilder, headers, pairs);
    }

    public static String put(String uri, Header[] headers, NameValuePair... pairs) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.put(uri);
        return request(requestBuilder, headers, pairs);
    }

    public static String post(String uri, NameValuePair... pairs) throws IOException {
        return post(uri, null, pairs);
    }

    public static String post(String uri, Header[] headers, NameValuePair... pairs) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.post(uri);
        return request(requestBuilder, headers, pairs);
    }

    public static String request(RequestBuilder requestBuilder, Header[] headers, NameValuePair... pairs) throws IOException {
        if (null != pairs) {
            for (NameValuePair pair : pairs) {
                requestBuilder.addParameter(pair);
            }
        }
        if (null != headers) {
            requestBuilder.addHeader("Content-Type", "text/html;charset=UTF-8");
            for (Header header : headers) {
                requestBuilder.addHeader(header);
            }
        }
        requestBuilder.setCharset(Charset.forName("UTF-8"));
        HttpUriRequest requestConfig = requestBuilder.build();
        try {
            HttpResponse response = httpClient.execute(requestConfig);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw e;
        }
    }
}

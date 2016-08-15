package com.infinite.water.core.util;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public abstract class HttpUtils {
    private static HttpClient httpClient = HttpClients.createDefault();

    public static String get(String uri, NameValuePair... pairs) {
        return get(uri, null, pairs);
    }

    public static String get(String uri, Header[] headers, NameValuePair... pairs) {
        RequestBuilder requestBuilder = RequestBuilder.get(uri);
        return request(requestBuilder, headers, pairs);
    }

    public static String delete(String uri, Header[] headers, NameValuePair... pairs) {
        RequestBuilder requestBuilder = RequestBuilder.delete(uri);
        return request(requestBuilder, headers, pairs);
    }

    public static String put(String uri, Header[] headers, NameValuePair... pairs) {
        RequestBuilder requestBuilder = RequestBuilder.put(uri);
        return request(requestBuilder, headers, pairs);
    }

    public static String post(String uri, NameValuePair... pairs) {
        return post(uri, null, pairs);
    }

    public static String post(String uri, Header[] headers, NameValuePair... pairs) {
        RequestBuilder requestBuilder = RequestBuilder.post(uri);
        return request(requestBuilder, headers, pairs);
    }

    public static String request(RequestBuilder requestBuilder, Header[] headers, NameValuePair... pairs) {
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
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String result = post("http://c.diandingding.com:5000/api/v3300/first-get-monitors", new Header[]{
                new BasicHeader("token", "a8ba90082ca4b6f5726a88768d940340")
        });
        long end = System.currentTimeMillis();
        System.out.println("<------------------------>");
        System.out.println(result);
        System.out.println("<------------------------>");
        System.out.println("cost:" + (end - start));
    }

}

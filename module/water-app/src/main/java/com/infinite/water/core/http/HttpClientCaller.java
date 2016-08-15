package com.infinite.water.core.http;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

public class HttpClientCaller implements IHttpCaller<Object> {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void getAsync(String uri, IHttpAction action) throws IOException {
        HttpGet method = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(method);
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    action.onResponse(instream);
                } catch (Throwable throwable) {
                    action.onFailure(throwable);
                } finally {
                    instream.close();
                }
            }
        } finally {
            response.close();
            action.onComplete();
        }
    }

    public static void main(String[] args) throws IOException {
        getAsync("http://222.73.85.26:19089/gateway?wsdl", new HttpActionAdapter() {
            public void onResponse(InputStream is) throws Throwable {
                int len = -1;
                byte[] buffer = new byte[2048];
                while ( (len = is.read(buffer)) > 0 ) {
                    System.out.println( new String(buffer, 0, len) );
                }
            }
        });

    }

}

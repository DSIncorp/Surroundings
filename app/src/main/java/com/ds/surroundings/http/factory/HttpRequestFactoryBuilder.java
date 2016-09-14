package com.ds.surroundings.http.factory;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class HttpRequestFactoryBuilder {

    private static HttpRequestFactory factory;

    /**
     * Creating http request Factory
     */
    private static void createRequestFactory() {
        HttpTransport transport = new NetHttpTransport();
        factory = transport.createRequestFactory(request -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setUserAgent("AndroidHive-Places-Test");
            request.setHeaders(headers);
            JsonObjectParser parser = new JsonObjectParser(new JacksonFactory());
            request.setParser(parser);
        });
    }

    public static HttpRequestFactory getFactory() {
        if (factory == null) {
            createRequestFactory();
        }
        return factory;
    }
}

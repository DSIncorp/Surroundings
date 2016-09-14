package com.ds.surroundings.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionReader {

    public String readUrl(String stringUrl) throws IOException {
        HttpURLConnection urlConnection = obtainHttpUrlConnection(stringUrl);
        urlConnection.connect();
        return getDataFromUrl(urlConnection);
    }

    private HttpURLConnection obtainHttpUrlConnection(String stringUrl) throws IOException {
        URL url = new URL(stringUrl);
        return (HttpURLConnection) url.openConnection();
    }

    private String getDataFromUrl(HttpURLConnection httpURLConnection) throws IOException {
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            reader.close();
            inputStream.close();
        }
        return sb.toString();
    }
}

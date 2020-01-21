package com.github.theipster.openbookmarks.bookmarks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.HttpClients;

/**
 * Wrapper class around Apache HttpClient, to provider nicer interface.
 */
public class HttpClient {
    private org.apache.http.client.HttpClient httpClient;

    public HttpClient(org.apache.http.client.HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public HttpResponse get(String url) throws IOException, UnsupportedEncodingException {
        HttpGet request = new HttpGet(url);
        return makeEntityRepeatable(httpClient.execute(request));
    }

    public HttpResponse head(String url) throws IOException, UnsupportedEncodingException {
        HttpHead request = new HttpHead(url);
        return httpClient.execute(request);
    }

    private HttpResponse makeEntityRepeatable(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (!entity.isRepeatable()) {
            response.setEntity(new BufferedHttpEntity(entity));  // lolJava, otherwise multiple reads will fail
        }
        return response;
    }

    public HttpResponse postJson(String url, String bodyContent) throws IOException, UnsupportedEncodingException {
        HttpPost request = new HttpPost(url);
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(bodyContent));
        return makeEntityRepeatable(httpClient.execute(request));
    }

    static class Factory {
        static HttpClient getDefaultInstance() {
            return new HttpClient(HttpClients.custom().disableRedirectHandling().build());
        }
    }
}

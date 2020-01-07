package com.github.theipster.openbookmarks.bookmarks;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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

    public HttpResponse post(String url, String bodyContent) throws IOException, UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new StringEntity(bodyContent));
        return httpClient.execute(post);
    }

    static class Factory {
        static HttpClient getDefaultInstance() {
            return new HttpClient(HttpClients.createDefault());
        }
    }
}

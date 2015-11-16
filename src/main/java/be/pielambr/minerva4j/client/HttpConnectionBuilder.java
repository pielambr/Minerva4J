package be.pielambr.minerva4j.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Pieterjan on 16/11/2015.
 */
public class HttpConnectionBuilder {

    private static final String COOKIES_HEADER = "Set-Cookie";

    private String cookie;
    private String baseURL;
    private StringBuilder path;
    private Methods method;

    public enum Methods {
        POST("POST"),
        GET("GET");

        private String method;

        Methods(String name) {
            this.method = name;
        }

        public String getName() {
            return this.method;
        }

    }

    private HttpConnectionBuilder() {

    }

    public static HttpConnectionBuilder builder() {
        return new HttpConnectionBuilder();
    }

    private URL getBaseURL() throws HttpConnectionBuilderException {
        String url = baseURL + path.toString();
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new HttpConnectionBuilderException("URL has an incorrect format - " + url);
        }
    }

    private HttpURLConnection getBaseConnection() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) getBaseURL().openConnection();
        connection.setRequestMethod(method.getName());
        connection.setInstanceFollowRedirects(true);
        if(cookie != null) {
            connection.addRequestProperty(COOKIES_HEADER, cookie);
        }
        if(!method.equals(Methods.GET)) {
            connection.setDoOutput(true);
        }
        return connection;
    }

    public HttpURLConnection build() throws IOException {
        return getBaseConnection();
    }

    public HttpConnectionBuilder setURL(String url) {
        this.baseURL = url;
        return this;
    }

    public HttpConnectionBuilder addToPath(String part) {
        path.append(part);
        return this;
    }

    public HttpConnectionBuilder setRequestMethod(Methods method) {
        this.method = method;
        return this;
    }

    public HttpConnectionBuilder setCookie(String cookie) {
        this.cookie = cookie;
        return this;
    }

    public static String getCookie(HttpURLConnection connection) {
        return connection.getHeaderField(COOKIES_HEADER);
    }

}

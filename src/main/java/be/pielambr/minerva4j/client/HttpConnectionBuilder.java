package be.pielambr.minerva4j.client;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pieterjan on 16/11/2015.
 */
public class HttpConnectionBuilder {

    private static final String SET_COOKIES_HEADER = "Set-Cookie";
    private static final String COOKIES_HEADER = "Cookie";


    private Map<String, String> headers;
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
        path = new StringBuilder();
        headers = new HashMap<String, String>();
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

    private HttpsURLConnection getBaseConnection() throws IOException, KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, null, null);
        HttpsURLConnection connection = (HttpsURLConnection) getBaseURL().openConnection();
        connection.setSSLSocketFactory(sslContext.getSocketFactory());
        if (!method.equals(Methods.GET)) {
            connection.setDoOutput(true);
        }
        connection.setRequestMethod(method.getName());
        connection.setInstanceFollowRedirects(false);
        if (cookie != null) {
            connection.addRequestProperty(COOKIES_HEADER, cookie);
        }

        for (String key : headers.keySet()) {
            connection.addRequestProperty(key, headers.get(key));
        }
        return connection;
    }

    public HttpsURLConnection build() throws IOException {
        try {
            return getBaseConnection();
        } catch (KeyManagementException e) {
            throw new HttpConnectionBuilderException("Problem with SSL key management");
        } catch (NoSuchAlgorithmException e) {
            throw new HttpConnectionBuilderException("SSL has not got a good algorithm");
        }
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
        return connection.getHeaderField(SET_COOKIES_HEADER);
    }

    public HttpConnectionBuilder addHeader(String key, String value) {
        this.headers.put(key, value);
        return this;
    }

}

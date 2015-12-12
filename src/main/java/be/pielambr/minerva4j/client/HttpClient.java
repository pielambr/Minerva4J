package be.pielambr.minerva4j.client;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by Pieterjan on 16/11/2015.
 */
public class HttpClient {

    private String cookie;
    private HttpsURLConnection connection;

    public HttpClient() {

    }

    public String post(String url, String data) throws IOException {
        connection = HttpConnectionBuilder.builder()
                .setURL(url)
                .setCookie(cookie)
                .setRequestMethod(HttpConnectionBuilder.Methods.POST)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        writeData(connection, data);
        cookie = HttpConnectionBuilder.getCookie(connection);
        return getContent(connection);
    }

    public String get(String url) throws IOException {
        connection = HttpConnectionBuilder.builder()
                .setURL(url)
                .setCookie(cookie)
                .setRequestMethod(HttpConnectionBuilder.Methods.GET)
                .build();
        return getContent(connection);
    }

    private String getContent(HttpsURLConnection connection) throws IOException {
        BufferedReader br;
        if (connection.getResponseCode() >= HttpURLConnection.HTTP_OK
                && connection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
            br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
        } else {
            br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
        }
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output).append("\n");
        }
        return sb.toString();
    }

    private void writeData(HttpsURLConnection connection, String data) throws IOException {
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(data.getBytes());
    }

    public HttpURLConnection getConnection() {
        return this.connection;
    }

}

package be.pielambr.minerva4j.client;

import be.pielambr.minerva4j.exceptions.LoginFailedException;
import be.pielambr.minerva4j.utility.Constants;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.jerry.Jerry;
import jodd.lagarto.dom.Node;

/**
 * Created by Pieterjan Lambrecht on 15/06/2015.
 */
public class Client {

    private final HttpBrowser _browser;

    private final String _username;
    private final String _password;

    public Client(String username, String password) {
        _username = username;
        _password = password;
        _browser = new HttpBrowser();
        _browser.setKeepAlive(true);
    }

    public void connect() throws LoginFailedException {
        HttpRequest login = HttpRequest
                .post(Constants.LOGIN_URL)
                .form("username", _username)
                .form("password", _password);
        _browser.sendRequest(login);
        verifyLogin();
    }

    public boolean verifyLogin() throws LoginFailedException {
        // Check index page
        HttpRequest index = HttpRequest.get(Constants.INDEX_URL);
        _browser.sendRequest(index);
        // Check to see if we find a course list
        if (_browser.getHttpResponse() != null) {
            Jerry i = Jerry.jerry(_browser.getHttpResponse().body());
            Node node = i.$(Constants.COURSE_LIST).get(0);
            if (node != null) {
                return true;
            }
        }
        throw new LoginFailedException();
    }

}

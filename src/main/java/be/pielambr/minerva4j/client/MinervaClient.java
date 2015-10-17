package be.pielambr.minerva4j.client;

import be.pielambr.minerva4j.beans.Announcement;
import be.pielambr.minerva4j.beans.Course;
import be.pielambr.minerva4j.beans.Document;
import be.pielambr.minerva4j.beans.Event;
import be.pielambr.minerva4j.exceptions.LoginFailedException;
import be.pielambr.minerva4j.parsers.AnnouncementParser;
import be.pielambr.minerva4j.parsers.CourseParser;
import be.pielambr.minerva4j.parsers.DocumentParser;
import be.pielambr.minerva4j.parsers.EventParser;
import be.pielambr.minerva4j.utility.Constants;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.squareup.okhttp.*;
import jodd.jerry.Jerry;
import jodd.lagarto.dom.Node;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.util.*;

/**
 * Created by Pieterjan Lambrecht on 15/06/2015.
 */
public class MinervaClient {

    private final OkHttpClient _browser;
    private Map<String, List<String>> _map;
    private boolean loggedin;

    private final String _username;
    private final String _password;

    /**
     * Default constructor for the Minerva client
     * @param username Username for the user
     * @param password Password for the user
     */
    public MinervaClient(String username, String password) {
        _username = username;
        _password = password;
        _browser = new OkHttpClient();
        _browser.setFollowRedirects(false);
        // Save cookies
        _browser.setCookieHandler(new CookieHandler() {
            @Override
            public Map<String, List<String>> get(URI uri, Map<String, List<String>> map) throws IOException {
                if(!loggedin && map.containsKey("Location") && map.get("Location").get(0).equals("/mobile/")){
                    _map = new HashMap<String, List<String>>();
                    _map.put("Cookie", map.get("Set-Cookie"));
                    loggedin = true;
                }
                return _map != null? _map :map;
            }

            @Override
            public void put(URI uri, Map<String, List<String>> map) throws IOException {
                if(!loggedin && map.containsKey("Location") && map.get("Location").get(0).equals("/mobile/")){
                    _map = new HashMap<String, List<String>>();
                    _map.put("Cookie", map.get("Set-Cookie"));
                    loggedin = true;
                }
            }
        });
    }

    /**
     * Needs to be called after constructor to login
     * @throws LoginFailedException Is thrown if login fails
     */
    public void connect() throws LoginFailedException, IOException {
        login();
        verifyLogin();
    }

    private void login() throws IOException {
        RequestBody formBody = new FormEncodingBuilder()
                .add("username", _username)
                .add("password", _password)
                .build();
        Request login = new Request.Builder()
                .url(Constants.LOGIN_URL)
                .post(formBody)
                .build();
        _browser.newCall(login).execute();
        return;

    }

    /**
     * Verifies login for the user after calling connect
     * @return Returns true if the login was correct
     * @throws LoginFailedException Is thrown if the login was incorrect
     */
    public boolean verifyLogin() throws LoginFailedException, IOException {
        // Check index page
        Request index = new Request.Builder()
                .url(Constants.INDEX_URL)
                .build();
        Response response = _browser.newCall(index).execute();
        // Check to see if we find a course list
        if (response != null) {
            Jerry i = Jerry.jerry(response.body().string());
            Node node = i.$(Constants.COURSE_LIST).get(0);
            if (node != null) {
                return true;
            }
        }
        throw new LoginFailedException();
    }

    /**
     * Returns a list of announcements
     * @param course The course for which the announcements need to be retrieved
     * @return Returns a list of announcements
     */
    public List<Announcement> getAnnouncements(Course course) throws IOException {
        List<Announcement> announcements = AnnouncementParser.getAnnouncements(this, course);
        return announcements;
    }

    /**
     * Returns a list of courses
     * @return A list of courses for the current user
     */
    public List<Course> getCourses() throws IOException {
        return CourseParser.getCourses(this);
    }

    /**
     * Returns a list of ducments for a given course
     * @param course The course for which the documents needs to be retrieved
     * @return A list of documents
     */
    public List<Document> getDocuments(Course course) throws IOException {
        List<Document> documents = DocumentParser.getDocuments(this, course);
        return documents;
    }

    public OkHttpClient getClient() {
        return this._browser;
    }

    /**
     * Returns a valid download URL for a given document
     * @param course The course in which the document is uploaded
     * @param document The document
     * @return A valid download URL for the document
     */
    public String getDocumentDownloadURL(Course course, Document document) throws IOException {
        Request request = new Request.Builder()
                .url(Constants.AJAX_URL + course.getCode() + Constants.DOCUMENTS + document.getId())
                .build();
        Response response = _browser.newCall(request).execute();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response.body().string());
        return element.getAsJsonObject().get("url").getAsString();
    }

    /**
     * This method checks if the last request redirected us to login,
     * meaning we have been logged out and logs us back in
     */
    public void checkLogin(Response response) throws IOException {
        if(response.request().httpUrl().toString().contains(Constants.LOGIN_URL)) {
            login();
        }
    }

    /**
     * Returns a list of all events
     * @return A list of all events
     */
    public List<Event> getEvents() throws IOException {
        return EventParser.getEvents(this);
    }

    /**
     * Returns a list of all events in a timespan
     * @param start Start of timespan
     * @param end End of timespan
     * @return A list of all events in a timespan
     */
    public List<Event> getEvents(Date start, Date end) throws IOException {
        return EventParser.getEvents(this, start, end);
    }

}

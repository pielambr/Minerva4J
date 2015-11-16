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
import jodd.jerry.Jerry;
import jodd.lagarto.dom.Node;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Pieterjan Lambrecht on 15/06/2015.
 */
public class MinervaClient {

    private final HttpClient _browser;
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
        _browser = new HttpClient();
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
        String loginRequest = "username=" + _username + "&password=" + _password;
        _browser.post(Constants.LOGIN_URL, loginRequest);
        return;

    }

    /**
     * Verifies login for the user after calling connect
     * @return Returns true if the login was correct
     * @throws LoginFailedException Is thrown if the login was incorrect
     */
    public boolean verifyLogin() throws LoginFailedException, IOException {
        // Check index page
        String response = _browser.get(Constants.INDEX_URL);
        // Check to see if we find a course list
        if (response != null) {
            Jerry i = Jerry.jerry(response);
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

    public HttpClient getClient() {
        return this._browser;
    }

    /**
     * Returns a valid download URL for a given document
     * @param course The course in which the document is uploaded
     * @param document The document
     * @return A valid download URL for the document
     */
    public String getDocumentDownloadURL(Course course, Document document) throws IOException {
        String response = _browser.get(Constants.AJAX_URL + course.getCode() + Constants.DOCUMENTS + document.getId());
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(response);
        return element.getAsJsonObject().get("url").getAsString();
    }

    /**
     * This method checks if the last request redirected us to login,
     * meaning we have been logged out and logs us back in
     */
    public void checkLogin(MinervaClient client) throws IOException {
        if(client.getClient().getConnection().getURL().toString().contains(Constants.LOGIN_URL)
                || client.getClient().getConnection().getHeaderField("Location").contains(Constants.LOGIN_URL)) {
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

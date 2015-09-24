package be.pielambr.minerva4j.parsers;

import be.pielambr.minerva4j.beans.Event;
import be.pielambr.minerva4j.parsers.json.JSONEvent;
import be.pielambr.minerva4j.utility.Constants;
import com.google.gson.Gson;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by pieterjan on 23/09/15.
 */
public class EventParser {

    /**
     * EventSource is an enumeration of all the sources for events
     */
    private enum EventSource {
        COURSE("ajax/mycalendar/course"),
        PLATFORM("ajax/mycalendar/platform"),
        PERSONAL("ajax/mycalendar/personal"),
        CENTAURO("ajax/mycalendar/centauro");

        private String url;

        /**
         * Default EventSource constructor
         * @param url EventSource data URL
         */
        EventSource(String url) {
            this.url = url;
        }

        /**
         * Returns the data URL of the current EventSource
         * @return The data url
         */
        public String getURL() {
            return this.url;
        }

        /**
         * Returns an array of all the EventSources
         * @return An array of all the EventSources
         */
        public static EventSource[] getSources() {
            return new EventSource[] {EventSource.CENTAURO, EventSource.COURSE,
            EventSource.PLATFORM, EventSource.PERSONAL};
        }
    }

    /**
     * This method returns all Events
     * @param browser HttpBrowser with the cookies that we'll use for requests
     * @return Returns a list of Events for all EventSources
     */
    public static List<Event> getEvents(HttpBrowser browser) {
        List<Event> events = new ArrayList<Event>();
        for(EventSource s : EventSource.getSources()) {
            events.addAll(getEvents(browser, s));
        }
        return events;
    }

    /**
     * This method returns all Events for certain timespan
     * @param browser HttpBrowser used in request
     * @param start Start date of timespan
     * @param end End date of timespan
     * @return Returns list of events in this timespan
     */
    public static List<Event> getEvents(HttpBrowser browser, Date start, Date end) {
        List<Event> events = new ArrayList<Event>();
        for(EventSource s : EventSource.getSources()) {
            events.addAll(getEvents(browser, s, start, end));
        }
        return events;
    }

    /**
     * Retrieves the Events from given source with HttpBrowser provided
     * @param browser HttpBrowser used for the requests
     * @param source EventSource for which we should pull events
     * @return Returns a list of Events for this EventSource
     */
    private static List<Event> getEvents(HttpBrowser browser, EventSource source) {
        HttpRequest request = HttpRequest.get(Constants.INDEX_URL + source.getURL());
        browser.sendRequest(request);
        String page;
        try {
            page = new String(browser.getHttpResponse().bodyBytes(), "UTF8");
        } catch (UnsupportedEncodingException ex){
            page = browser.getHttpResponse().body();
        }
        return parseEvents(page);
    }

    /**
     * Retrieves the Events from given source with HttpBrowser provided
     * @param browser HttpBrowser used for the requests
     * @param source EventSource for which we should pull events
     * @param start Start date for events to retrieve
     * @param end End date for events to retrieve
     * @return Returns a list of Events for this EventSource
     */
    private static List<Event> getEvents(HttpBrowser browser, EventSource source, Date start, Date end) {
        HttpRequest request = HttpRequest.get(Constants.INDEX_URL + source.getURL());
        request.query("start", String.valueOf(start.getTime() / 1000))
                .query("end", String.valueOf(end.getTime() / 1000));
        browser.sendRequest(request);
        String page;
        try {
            page = new String(browser.getHttpResponse().bodyBytes(), "UTF8");
        } catch (UnsupportedEncodingException ex){
            page = browser.getHttpResponse().body();
        }
        return parseEvents(page);
    }

    /**
     * Parses a Jerry page and returns the Events found in the JSON of this page
     * @param page Jerry page containing the Events
     * @return Returns a list of Events found on the page
     */
    private static List<Event> parseEvents(String page) {
        List<Event> events = new ArrayList<Event>();
        JSONEvent[] jsonEvents = new Gson().fromJson(page, JSONEvent[].class);
        for(JSONEvent e : jsonEvents) {
            events.add(new Event(e.getId(), e.getTitle(), e.getDescription(), e.getStart(), e.getEnd()));
        }
        return events;
    }
}

package be.pielambr.minerva4j.parsers;

import be.pielambr.minerva4j.beans.Event;
import be.pielambr.minerva4j.client.MinervaClient;
import be.pielambr.minerva4j.parsers.json.JSONEvent;
import be.pielambr.minerva4j.utility.Constants;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
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
     * @param client An instance of the MinervaClient client
     * @return Returns a list of Events for all EventSources
     */
    public static List<Event> getEvents(MinervaClient client) throws IOException {
        List<Event> events = new ArrayList<Event>();
        for(EventSource s : EventSource.getSources()) {
            events.addAll(getEvents(client, s));
        }
        return events;
    }

    /**
     * This method returns all Events for certain timespan
     * @param client An instance of the MinervaClient client
     * @param start Start date of timespan
     * @param end End date of timespan
     * @return Returns list of events in this timespan
     */
    public static List<Event> getEvents(MinervaClient client, Date start, Date end) throws IOException {
        List<Event> events = new ArrayList<Event>();
        for(EventSource s : EventSource.getSources()) {
            events.addAll(getEvents(client, s, start, end));
        }
        return events;
    }

    /**
     * Retrieves the Events from given source with HttpBrowser provided
     * @param client An instance of the MinervaClient client
     * @param source EventSource for which we should pull events
     * @return Returns a list of Events for this EventSource
     */
    private static List<Event> getEvents(MinervaClient client, EventSource source) throws IOException {
        Request request = new Request.Builder()
                .url(Constants.INDEX_URL + source.getURL())
                .build();
        Response response = client.getClient().newCall(request).execute();
        String page;
        try {
            page = new String(response.body().bytes(), "UTF8");
        } catch (UnsupportedEncodingException ex){
            page = response.body().string();
        }
        return parseEvents(page);
    }

    /**
     * Retrieves the Events from given source with HttpBrowser provided
     * @param client An instance of the MinervaClient client
     * @param source EventSource for which we should pull events
     * @param start Start date for events to retrieve
     * @param end End date for events to retrieve
     * @return Returns a list of Events for this EventSource
     */
    private static List<Event> getEvents(MinervaClient client, EventSource source, Date start, Date end) throws IOException {
        String params = "/?start=" + String.valueOf(start.getTime() / 1000) +
                "&end=" + String.valueOf(end.getTime() / 1000);
        Request request = new Request.Builder()
                .url(Constants.INDEX_URL + source.getURL() + params)
                .build();
        Response response = client.getClient().newCall(request).execute();
        String page;
        try {
            page = new String(response.body().bytes(), "UTF8");
        } catch (UnsupportedEncodingException ex){
            page = response.body().string();
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

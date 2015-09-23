package be.pielambr.minerva4j.beans;

import java.util.Date;

/**
 * Created by pieterjan on 23/09/15.
 */
public class Event {

    private final String _id;
    private final String _title;
    private final String _description;
    private final Date _start;
    private final Date _end;

    /**
     * Default constructor for an event
     * @param id The id of this event
     * @param title The title of this event
     * @param description The description of this event
     * @param start The start epoch of this event
     * @param end The end epoch of this event
     */
    public Event(String id, String title, String description, long start, long end) {
        this._id = id;
        this._title = title;
        this._description = description;
        this._start = new Date(start * 1000);
        this._end = new Date(end * 1000);
    }

    /**
     * Returns the ID of this event
     * @return The ID of this event
     */
    public String getId() {
        return _id;
    }

    /**
     * Returns the title of this event
     * @return Returns the title of this event
     */
    public String getTitle() {
        return _title;
    }

    /**
     * Returns the description of this event
     * @return Returns the description of this event
     */
    public String getDescription() {
        return _description;
    }

    /**
     * Gets the start date of this event
     * @return The start date of this event
     */
    public Date getStart() {
        return _start;
    }


    /**
     * Returns the end date of this event
     * @return The end date of this event
     */
    public Date getEnd() {
        return _end;
    }
}

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
     * Gets the end date of this event
     * @return The end date of this event
     */
    public Date getEnd() {
        return _end;
    }

    /**
     * {@inheritDoc}
     *
     * @param o The object to compare to
     * @return Whether these two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (_id != null ? !_id.equals(event._id) : event._id != null) return false;
        if (_title != null ? !_title.equals(event._title) : event._title != null) return false;
        if (_description != null ? !_description.equals(event._description) : event._description != null) return false;
        if (!_start.equals(event._start)) return false;
        return _end.equals(event._end);

    }

    /**
     * {@inheritDoc}
     *
     * @return A hashcode for this event
     */
    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (_title != null ? _title.hashCode() : 0);
        result = 31 * result + (_description != null ? _description.hashCode() : 0);
        result = 31 * result + (_start.hashCode());
        result = 31 * result + (_end.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return String representation of an Event
     */
    @Override
    public String toString() {
        return "Event{" +
                "_id='" + _id + '\'' +
                ", _title='" + _title + '\'' +
                ", _description='" + _description + '\'' +
                ", _start=" + _start +
                ", _end=" + _end +
                '}';
    }
}

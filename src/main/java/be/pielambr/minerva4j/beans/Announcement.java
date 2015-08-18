package be.pielambr.minerva4j.beans;

import java.util.Date;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class Announcement implements Comparable<Announcement> {

    private final String _content;
    private final String _title;
    private final Date _posted;

    /**
     * Default constructor for announcements
     * @param content The main content for the announcement
     * @param title The title for the announcement
     * @param posted The date the announcement was posted
     */
    public Announcement(String content, String title, Date posted) {
        _content = content;
        _title = title;
        _posted = posted;
    }

    /**
     * Returns the body for this announcement
     * @return The main content for this announcement
     */
    public String getContent() {
        return _content;
    }

    /**
     * Returns the title for this announcement
     * @return The title for this announcement
     */
    public String getTitle() {
        return _title;
    }

    /**
     * Returns the date when the announcement was posted
     * @return The date the announcement was posted
     */
    public Date getPosted() {
        return _posted;
    }

    /**
     * Compares two announcements, based on their time posted
     * @param o The other announcement to compare to
     * @return Returns whether the other object is greater, equal or smaller than this one
     */
    public int compareTo(Announcement o) {
        return _posted.compareTo(o.getPosted());
    }

    /**
     * Returns whether two object are equal
     * @param o The object to compare to this object
     * @return Whether both objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Announcement that = (Announcement) o;
        return _content.equals(that._content)
                && _title.equals(that._title)
                && _posted.equals(that._posted);
    }

    /**
     * Returns a hashcode for this announcement
     * @return A hashcode for this announcement
     */
    @Override
    public int hashCode() {
        int result = _content.hashCode();
        result = 11 * result + _title.hashCode();
        return 11 * result + _posted.hashCode();
    }

    /**
     * Returns a basic String representation of an Announcement
     *
     * @return String representation of an Announcement
     */
    @Override
    public String toString() {
        return "Announcement{" +
                "title='" + _title + '\'' +
                ", posted=" + _posted +
                '}';
    }
}

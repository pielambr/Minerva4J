package be.pielambr.minerva4j.beans;

import java.util.Date;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class Announcement {

    private String _content;
    private String _title;
    private Date _posted;

    public Announcement(String content, String title, Date posted) {
        _content = content;
        _title = title;
        _posted = posted;
    }

    public String getContent() {
        return _content;
    }

    public String getTitle() {
        return _title;
    }

    public Date getPosted() {
        return _posted;
    }
}

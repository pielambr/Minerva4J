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
        this._content = content;
        this._title = title;
        this._posted = posted;
    }

    public String get_content() {
        return _content;
    }

    public String get_title() {
        return _title;
    }

    public Date get_posted() {
        return _posted;
    }
}

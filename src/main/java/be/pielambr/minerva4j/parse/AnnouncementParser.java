package be.pielambr.minerva4j.parse;

import be.pielambr.minerva4j.beans.Announcement;
import be.pielambr.minerva4j.beans.Course;
import jodd.http.HttpBrowser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class AnnouncementParser {

    public static List<Announcement> getAnnouncements(Course course, HttpBrowser browser) {
        List<Announcement> announcements = new ArrayList<Announcement>();

        return announcements;
    }
}

package be.pielambr.minerva4j.parsers;

import be.pielambr.minerva4j.beans.Announcement;
import be.pielambr.minerva4j.beans.Course;
import be.pielambr.minerva4j.client.MinervaClient;
import be.pielambr.minerva4j.utility.Constants;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class AnnouncementParser {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Parses the course page for the given course and returns the announcements
     * @param course The course for which the announcements need to be retrieved
     * @param client The MinervaClient instance
     * @return Returns a list of announcements for this course
     */
    public static List<Announcement> getAnnouncements(MinervaClient client, Course course) throws IOException {
        String response = client.getClient().get(Constants.COURSE_URL + course.getCode() + Constants.ANNOUNCEMENT);
        Jerry coursePage;
        try {
            coursePage = Jerry.jerry(new String(response.getBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            coursePage = Jerry.jerry(response);
        }
        client.checkLogin(client);
        return parseAnnouncements(coursePage);
    }

    /**
     * Parses the given Jerry object to a list of announcements
     * @param page The Jerry object to be parsed
     * @return A list of announcements
     */
    private static List<Announcement> parseAnnouncements(Jerry page) {
        final List<Announcement> announcements = new ArrayList<Announcement>();
        page.$(Constants.ANNOUNCEMENT_LIST).each(new JerryFunction() {
            public boolean onNode(Jerry jerry, int i) {
                Announcement announcement = parseAnnouncement(jerry);
                if(announcement != null) {
                    announcements.add(announcement);
                }
                return true;
            }
        });
        return announcements;
    }

    /**
     * Parses the Jerry object to an announcement object
     * @param announcement the Jerry object to be converted
     * @return An announcement object with a title, date and some content
     */
    private static Announcement parseAnnouncement(Jerry announcement) {
        String dateString = announcement.$(Constants.ANNOUNCEMENT_DATE).text();
        Date date = parseDate(dateString);
        String title = announcement.$(Constants.ANNOUNCEMENT_TITLE).text();
        String content = announcement.$(Constants.ANNOUNCEMENT_BODY).html();
        return new Announcement(content, title, date);
    }

    /**
     * Parses a string to a java.util.Date object
     * @param time String containing the time
     * @return returns either the date of today if parsing fails, or the date of the string
     */
    private static Date parseDate(String time) {
        try {
            return DATE_FORMAT.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }
}

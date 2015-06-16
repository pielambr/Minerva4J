package be.pielambr.minerva4j.parse;

import be.pielambr.minerva4j.beans.Announcement;
import be.pielambr.minerva4j.beans.Course;
import be.pielambr.minerva4j.utility.Constants;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;

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
     * @param browser An instance of the Jerry browser
     * @return Returns a list of announcements for this course
     */
    public static List<Announcement> getAnnouncements(Course course, HttpBrowser browser) {
        final List<Announcement> announcements = new ArrayList<Announcement>();
        HttpRequest request = HttpRequest.get(Constants.COURSE_URL + course.getCode() + Constants.ANNOUNCEMENT);
        browser.sendRequest(request);
        Jerry coursePage;
        try {
            coursePage = Jerry.jerry(new String(browser.getHttpResponse().bodyBytes(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            coursePage = Jerry.jerry(browser.getHttpResponse().body());
        }
        coursePage.$(Constants.ANNOUNCEMENT_LIST).each(new JerryFunction() {
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
        String content = announcement.$("div.accordion-body div.accordion-inner").html();
        Announcement a = new Announcement(content, title, date);
        return a;
    }

    /**
     * Parses a string to a java.util.Date object
     * @param time String containing the time
     * @return returns either the date of today if parsing fails, or the date of the string
     */
    public static Date parseDate(String time) {
        try {
            return DATE_FORMAT.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }
}

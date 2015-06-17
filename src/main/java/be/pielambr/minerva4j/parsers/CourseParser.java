package be.pielambr.minerva4j.parsers;

import be.pielambr.minerva4j.beans.Course;
import be.pielambr.minerva4j.utility.Constants;
import jodd.http.HttpBrowser;
import jodd.http.HttpRequest;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;
import jodd.lagarto.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class CourseParser {

    /**
     * Retrieves a list of courses from the Minerva index page
     * @param browser An instance of the Minerva browser
     * @return A list of courses on the Minerva index page
     */
    public static List<Course> getCourses(HttpBrowser browser) {
        HttpRequest index = HttpRequest.get(Constants.INDEX_URL);
        browser.sendRequest(index);
        return parseCourses(Jerry.jerry(browser.getHttpResponse().body()));
    }

    /**
     * Parses a given Jerry node and returns the courses
     * @param page The Jerry node to be parsed
     * @return A list of parsed courses
     */
    private static List<Course> parseCourses(Jerry page) {
        final List<Course> courses = new ArrayList<Course>();
        Node node = page.$(Constants.COURSE_LIST).get(0);
        if (node != null) {
            page.$(Constants.COURSE).each(new JerryFunction() {
                public boolean onNode(Jerry jerry, int i) {
                    String[] cName = jerry.text().split("\n");
                    String[] href = jerry.attr("href").split("/");
                    Course course = new Course(href[href.length - 1].trim(), cName[1].trim());
                    courses.add(course);
                    return true;
                }
            });
        }
        return courses;
    }

}

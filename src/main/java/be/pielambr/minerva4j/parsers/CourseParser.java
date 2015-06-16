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
        final List<Course> courses = new ArrayList<Course>();
        Jerry indexPage = Jerry.jerry(browser.getHttpResponse().body());
        Node node = indexPage.$(Constants.COURSE_LIST).get(0);
        if (node != null) {
            // Do this for each course
            indexPage.$(Constants.COURSE).each(new JerryFunction() {
                public boolean onNode(Jerry jerry, int i) {
                    String[] cName = jerry.text().split("\n");
                    String[] href = jerry.attr("href").split("/");
                    Course course = new Course(cName[1].trim(), href[href.length - 1].trim());
                    courses.add(course);
                    return true;
                }
            });
        }
        return courses;
    }

}

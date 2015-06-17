import be.pielambr.minerva4j.beans.Course;
import be.pielambr.minerva4j.parsers.CourseParser;
import jodd.jerry.Jerry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class TestCourseParser {

    private String html;

    /**
     * Load an HTML example page with courses
     */
    @Before
    public void loadProperties() {
        InputStream in = null;
        try {
            in = new FileInputStream("src/test/resources/courses_example.html");
            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = in.read()) != -1){
                builder.append((char)ch);
            }
            html = builder.toString();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("HTML file not found");
        } catch (IOException e) {
            System.out.println("Error closing HTML file");
        }
    }

    /**
     * Check if the courses found on the page are correct
     */
    @Test
    public void testGettingCourses() {
        Jerry jerry = Jerry.jerry(html);
        Method method = null;
        try {
            method = CourseParser.class.getDeclaredMethod("parseCourses", Jerry.class);
            method.setAccessible(true);
            List<Course> courses = (List<Course>) method.invoke(null, jerry);
            Assert.assertEquals(29, courses.size());
            Assert.assertEquals("Algoritmen en datastructuren II", courses.get(0).getName());
            Assert.assertEquals("C00269202013", courses.get(0).getCode());
            Assert.assertEquals("StuW - Studentenvertegenwoordiging FWE", courses.get(28).getName());
            Assert.assertEquals("CSR_ext", courses.get(28).getCode());
        } catch (NoSuchMethodException e) {
            System.out.println("Method to be tested was not found");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

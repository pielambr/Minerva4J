import be.pielambr.minerva4j.beans.Announcement;
import be.pielambr.minerva4j.parsers.AnnouncementParser;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class TestAnnouncementParser {

    private String html;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Load an HTML example page with courses
     */
    @Before
    public void loadProperties() {
        InputStream in = null;
        try {
            in = new FileInputStream("src/test/resources/announcements_example.html");
            StringBuilder builder = new StringBuilder();
            int ch;
            while((ch = in.read()) != -1){
                builder.append((char)ch);
            }
            html = builder.toString();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("HTML file not found");
            Assert.fail();
        } catch (IOException e) {
            System.out.println("Error closing HTML file");
            Assert.fail();
        }
    }

    @Test
    public void testGettingAnnouncements() throws ParseException {
        Jerry jerry = Jerry.jerry(html);
        Method method = null;
        try {
            method = AnnouncementParser.class.getDeclaredMethod("parseAnnouncements", Jerry.class);
            method.setAccessible(true);
            List<Announcement> announcements = (List<Announcement>) method.invoke(null, jerry);
            Assert.assertEquals(10, announcements.size());
            Assert.assertEquals("Feedback examen wiskunde 1", announcements.get(0).getTitle());
            Assert.assertEquals(DATE_FORMAT.parse("04/02/2015"), announcements.get(0).getPosted());
            Assert.assertEquals("monitoraat", announcements.get(9).getTitle());
            Assert.assertEquals(DATE_FORMAT.parse("06/11/2014"), announcements.get(9).getPosted());
        } catch (NoSuchMethodException e) {
            System.out.println("Method to be tested was not found");
            Assert.fail();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}

import be.pielambr.minerva4j.client.Client;
import be.pielambr.minerva4j.exceptions.LoginFailedException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class TestAnnouncementParser {

    private String html;

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
            System.out.println("Properties file not found");
        } catch (IOException e) {
            System.out.println("Error closing HTML file");
        }
    }

    @Test
    public void testGettingAnnouncements() {
        System.out.println("Testing getting announcements");
    }

}

import be.pielambr.minerva4j.beans.Course;
import be.pielambr.minerva4j.beans.Document;
import be.pielambr.minerva4j.client.Client;
import be.pielambr.minerva4j.exceptions.LoginFailedException;
import org.junit.Assert;
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
public class TestClient {

    private String _username;
    private String _password;

    /**
     * Loads the username and the password from the settings.properties file
     */
    @Before
    public void loadProperties() {
        Properties properties = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream("src/test/resources/settings.properties");
            properties.load(in);
            in.close();
            _username = properties.getProperty("username");
            _password = properties.getProperty("password");
        } catch (FileNotFoundException e) {
            System.out.println("Properties file not found");
        } catch (IOException e) {
            System.out.println("Error closing properties file");
        }
    }

    /**
     * Tests the login to Minerva and the detection for wrong credentials
     */
    @Test
    public void testConnect() {
        // This should not fail
        Client client = new Client(_username, _password);
        try {
            client.connect();
        } catch (LoginFailedException e) {
            Assert.fail("Login failed");
        }
        // But this should though
        client = new Client("john", "doe");
        try {
            client.connect();
            Assert.fail("Login should have failed");
        } catch (LoginFailedException e) {
            // It's all fine
        }
    }

    @Test
    public void testDownloadURL() {
        Client client = new Client(_username, _password);
        try {
            Course course = new Course("E70103102014", "Milieubeheer");
            Document document = new Document("13889171", "Materialendecreet.mp4");
            client.connect();
            String url = client.getDocumentDownloadURL(course, document);
            Assert.assertNotNull(url);
            Assert.assertNotEquals("", url);
        } catch (LoginFailedException e) {
            // It's all fine
        }
    }
}

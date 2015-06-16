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

    private String _username;
    private String _password;

    private Client _client;

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
            _client = new Client(_username, _password);
            _client.connect();
        } catch (FileNotFoundException e) {
            System.out.println("Properties file not found");
        } catch (IOException e) {
            System.out.println("Properties file could not be opened");
        } catch (LoginFailedException e) {
            System.out.println("Login failed");
        }
    }

    @Test
    public void testGetAnnouncements() {
        System.out.println("Testing getting announcements");
    }

    @After
    public void closeConnection() {
        _client.close();
    }
}

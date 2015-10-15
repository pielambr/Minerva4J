import be.pielambr.minerva4j.client.MinervaClient;
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
    public void testConnect() throws IOException {
        // This should not fail
        MinervaClient client = new MinervaClient(_username, _password);
        try {
            client.connect();
        } catch (LoginFailedException e) {
            Assert.fail("Login failed");
        }
        // But this should though
        client = new MinervaClient("john", "doe");
        try {
            client.connect();
            Assert.fail("Login should have failed");
        } catch (LoginFailedException e) {
            // It's all fine
        }
    }
}

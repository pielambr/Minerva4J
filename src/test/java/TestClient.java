import be.pielambr.minerva4j.client.Client;
import be.pielambr.minerva4j.exceptions.LoginFailedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Pieterjan Lambrecht on 16/06/2015.
 */
public class TestClient {

    @Before
    public void loadProperties() {

    }

    @Test
    public void testConnect() {
        // This should not fail
        System.out.println("Testing login...");
        Client client = new Client("", "");
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

}

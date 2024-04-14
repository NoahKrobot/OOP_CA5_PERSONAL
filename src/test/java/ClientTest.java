import junit.framework.TestCase;

public class ClientTest extends TestCase {

    public void testStart() {
        Client client = new Client();
        client.start();
        assertTrue(true); //wont be done if the client.start() throws exception
    }
}
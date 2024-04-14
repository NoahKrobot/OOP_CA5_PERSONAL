import junit.framework.TestCase;

public class ServerTest extends TestCase {

    public void testStart() {
        Server server = new Server();
        server.start();
        assertTrue(true);
    }
}
import Server_Client.Client;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTest extends TestCase {
    public ClientTest() throws IOException {
    }
    public void testStart() throws IOException {
        Server_Client.Client client = new Server_Client.Client();
        client.start();
        assertTrue(true); //wont be done if the client.start() throws exception
    }

    Socket socket = new Socket("localhost", 1090);
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    @Test
    public void testGetPosterList() throws IOException {
        String expected = "Poster list : 1.Blade Runner 2.Dune 3.Poor things";
        String actual = Server_Client.Client.sendRequest(out, "getPosterList", in);
        assertEquals(expected, actual);
    }
    @Test
    public void testGetMovieByID() throws IOException {
        String expected = "{\"id\":1,\"movieName\":\"Dune Part 1\",\"directorName\":\"Jackie Chan\",\"genre\":\"Horror\",\"studio\":\"Electra Records\",\"year\":4,\"boxOfficeGain\":4563560.0}";

        String actual = Client.sendRequest(out, "getMovieByID", in);
        assertEquals(expected, actual);
    }
}
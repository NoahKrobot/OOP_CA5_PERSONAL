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

    @Test
    public void testGetAllMovies() throws IOException {
        String expected = "[{\"id\":1,\"movieName\":\"Dune Part 1\",\"directorName\":\"Jackie Chan\",\"genre\":\"Horror\",\"studio\":\"Electra Records\",\"year\":4,\"boxOfficeGain\":4563560.0},{\"id\":2,\"movieName\":\"Blade Runner 2049\",\"directorName\":\"Denis Villenueve\",\"genre\":\"Sci-Fi\",\"studio\":\"Warner Bros. Picture\",\"year\":2017,\"boxOfficeGain\":267.7},{\"id\":3,\"movieName\":\"Poor Things\",\"directorName\":\"Yorgos Lanthimos\",\"genre\":\"Comedy\",\"studio\":\"Searchlight Pictures\",\"year\":2023,\"boxOfficeGain\":104.0},{\"id\":4,\"movieName\":\"Interstellar\",\"directorName\":\"Cristopher Nolan\",\"genre\":\"Sci-Fi\",\"studio\":\"Paramount Pictures\",\"year\":2014,\"boxOfficeGain\":701.7},{\"id\":5,\"movieName\":\"Barbie\",\"directorName\":\"Greta Gerwig\",\"genre\":\"Comedy\",\"studio\":\"Warner Bros. Picture\",\"year\":2023,\"boxOfficeGain\":1446.0},{\"id\":6,\"movieName\":\"The Lovely Bones\",\"directorName\":\"Peter Jackson\",\"genre\":\"Drama\",\"studio\":\"Paramount Pictures\",\"year\":2010,\"boxOfficeGain\":93.6},{\"id\":7,\"movieName\":\"Roma\",\"directorName\":\"Alfonso Cuarón\",\"genre\":\"Drama\",\"studio\":\"\\tEspectáculos Fílmicos El Coyúl Pimienta Films\",\"year\":2018,\"boxOfficeGain\":5.1},{\"id\":8,\"movieName\":\"Aftersun\",\"directorName\":\"Charlotte Wells\",\"genre\":\"Drama\",\"studio\":\"A24\",\"year\":2022,\"boxOfficeGain\":8.4},{\"id\":9,\"movieName\":\"Lost in translation\",\"directorName\":\"Sofia Copolla\",\"genre\":\"Romance\",\"studio\":\"Focus Features\",\"year\":2003,\"boxOfficeGain\":118.7},{\"id\":10,\"movieName\":\"Hereditary\",\"directorName\":\"Ari Aster\",\"genre\":\"Horror\",\"studio\":\"A24\",\"year\":2018,\"boxOfficeGain\":82.8}]\n";
        String actual = Client.sendRequest(out, "getAllMovies", in);
        assertEquals(expected, actual);
    }
}
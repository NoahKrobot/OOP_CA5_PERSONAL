import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;
import com.google.gson.Gson;

import java.util.List;

public class JsonConverter {
    static MovieDAOInterface movieDao = new MySqlMovieDao();

    public static void converteSingleToJSON() throws DaoException {

        List<Movie> movies = movieDao.getAllMovies();
        Gson gsonParser = new Gson();
        String jsonString = gsonParser.toJson(movies);
        System.out.println("All movies JSON: \n"+jsonString);

    }
}



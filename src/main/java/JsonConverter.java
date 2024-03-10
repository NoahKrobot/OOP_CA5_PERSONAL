import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;
import com.google.gson.Gson;

import java.util.List;

public class JsonConverter {
    static MovieDAOInterface movieDao = new MySqlMovieDao();

    public static void converteSingleToJSON() throws DaoException {
        Gson gsonParser = new Gson();

        List<Movie> movies = movieDao.getAllMovies();
        String jsonString = gsonParser.toJson(movies);
        System.out.println("All movies JSON: \n"+jsonString);


        String input = key.next();
        Movie usersMovie = movieDao.findMovieByName(input);
        String jsonStringOneMovie = gsonParser.toJson(usersMovie);
        System.out.println("All movies JSON: \n"+jsonStringOneMovie);

    }
}



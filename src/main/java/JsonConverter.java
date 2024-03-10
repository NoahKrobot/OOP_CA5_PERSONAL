import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;
import com.google.gson.Gson;

import java.util.List;

public class JsonConverter {
    static MovieDAOInterface movieDao = new MySqlMovieDao();

    public static void converteSingleToJSON() throws DaoException {

    

    }
}



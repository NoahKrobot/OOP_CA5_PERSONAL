package Databases.BusinessObjects;

import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;
import com.google.gson.Gson;

import java.util.List;
import java.util.Scanner;

//author: Noah Krobot
//Modifications: Mariela Machuca Palmeros
public class JsonConverter {
    static MovieDAOInterface movieDao = new MySqlMovieDao();
    static Scanner key = new Scanner(System.in);
    public static String converteAllMoviesToJSON(List<Movie> movies) throws DaoException {
        Gson gsonParser = new Gson();

        //List<Movie> movies = movieDao.getAllMovies();
        String jsonString = gsonParser.toJson(movies);
//        System.out.println("All movies JSON: \n"+jsonString);

        return jsonString;
    }
    public static String convertSingleToJSON(Movie m) throws DaoException {
        Gson gsonParser = new Gson();
        String jsonStringOneMovie = gsonParser.toJson(m);
        return jsonStringOneMovie;
    }
}



package Databases.BusinessObjects;

import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;
import com.google.gson.Gson;

import java.util.List;
import java.util.Scanner;

//author: Noah Krobot
public class JsonConverter {
    static MovieDAOInterface movieDao = new MySqlMovieDao();
    static Scanner key = new Scanner(System.in);
    public static void converteAllMoviesToJSON() throws DaoException {
        Gson gsonParser = new Gson();

        List<Movie> movies = movieDao.getAllMovies();
        String jsonString = gsonParser.toJson(movies);
        System.out.println("All movies JSON: \n"+jsonString);
    }

    public static void converteSingleToJSON() throws DaoException {
        Gson gsonParser = new Gson();

        String input = key.next();
        Movie usersMovie = movieDao.findMovieByName(input);
        String jsonStringOneMovie = gsonParser.toJson(usersMovie);
        System.out.println("Single movie JSON: \n"+jsonStringOneMovie);
    }

}



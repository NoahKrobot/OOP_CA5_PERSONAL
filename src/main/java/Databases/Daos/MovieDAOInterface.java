package Databases.Daos;

import Databases.Exceptions.DaoException;

import java.util.List;
import Databases.DTOs.Movie;

//-- Main Author: Sean Mooney
public interface MovieDAOInterface {
    public List<Movie> getAllMovies() throws DaoException;

    public Movie findMovieByName(String name) throws DaoException;
    int deleteMovieByName(String name) throws DaoException;

    public Movie addMovie(String name, String directorName, String genre, String studio, int year, float boxOfficeGain) throws DaoException;

}

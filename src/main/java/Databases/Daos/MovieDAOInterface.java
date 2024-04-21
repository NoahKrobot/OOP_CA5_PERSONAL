package Databases.Daos;

import Databases.DTOs.MovieComparator;
import Databases.Exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;
import Databases.DTOs.Movie;

//-- Main Author: Sean Mooney
public interface MovieDAOInterface {
    public List<Movie> getAllMovies() throws DaoException;

    public Movie findMovieByName(String name) throws DaoException;
    public Movie findMovieById(int id) throws DaoException;

    int deleteMovie(int id) throws DaoException;

    public Movie createMovie(Movie movie) throws DaoException;

    public Movie updateMovie(int id, Movie movie) throws DaoException;

    public List<Movie> getMoviesByFilter(MovieComparator movieComparator) throws DaoException;

    public int deleteMovieByName(String name) throws DaoException;

    public List<Movie> findMovieLikeActor(String name) throws DaoException;
}

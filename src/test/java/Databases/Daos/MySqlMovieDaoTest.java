package Databases.Daos;

import Databases.DTOs.Movie;
import Databases.DTOs.MovieComparator;
import Databases.Exceptions.DaoException;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


//-- Main Author: Sean Mooney
public class MySqlMovieDaoTest extends TestCase {
    private final MySqlMovieDao mySqlMovieDao = new MySqlMovieDao();


    public void testGetAllMovies() throws DaoException {
        List<Movie> movieList = new ArrayList<>();
        movieList = mySqlMovieDao.getAllMovies();
        assertFalse(movieList.isEmpty());
    }

    public void testCreateMovie() throws DaoException {
        Movie m = new Movie("Dunkirk");
        Movie mReturn = mySqlMovieDao.createMovie(m);
        mySqlMovieDao.deleteMovieByName("Dunkirk"); // delete afterwards
        assertEquals(mReturn.getMovieName(), m.getMovieName());
    }

    public void testFindMovieByNameFindDune() throws DaoException {
        Movie movie = mySqlMovieDao.findMovieByName("Dune: Part One");
        Movie newMovie = new Movie("Dune: Part One");
        assertEquals(movie, newMovie);
    }


    public void testDeleteMovieByNameDeleteDune() throws DaoException {
        int numberOfDeletedRows = mySqlMovieDao.deleteMovieByName("Dune: Part One");
        System.out.println(numberOfDeletedRows);
        // to repopulate db after using test!!!
        Movie m = new Movie("Dune: Part One", "Denis Villenueve", "Sci-Fi", "Warner Bros. Picture", 2021, 434.8f);
        mySqlMovieDao.createMovie(m);
        assertTrue(numberOfDeletedRows > 0);
    }

    public void testDeleteMovieByNameDeleteReturn0() throws DaoException {
        int numberOfDeletedRows = mySqlMovieDao.deleteMovieByName("Movie doesn't exit");
        System.out.println(numberOfDeletedRows);
        assertEquals(0, numberOfDeletedRows);
    }

    public void testUpdateMovie() throws DaoException {
        // Step 1: Create a movie and insert it into the database
        Movie movieToCreate = new Movie("Interstellar", "Christopher Nolan", "Sci-Fi", "Paramount Pictures", 2014, 677.5f);
        Movie createdMovie = mySqlMovieDao.createMovie(movieToCreate);
        Movie originalMovie = null;
        try {
            originalMovie = mySqlMovieDao.findMovieByName(createdMovie.getMovieName());
            assertNotNull(originalMovie);
            originalMovie.setDirectorName("James Cameron");
            originalMovie.setBoxOfficeGain(1000.0f);
            Movie updatedMovie = mySqlMovieDao.updateMovie(originalMovie.getId(), originalMovie);
            assertNotNull(updatedMovie);
            Movie retrievedUpdatedMovie = mySqlMovieDao.findMovieByName(originalMovie.getMovieName());
            assertNotNull(retrievedUpdatedMovie);
            assertEquals(originalMovie.getDirectorName(), retrievedUpdatedMovie.getDirectorName());
            assertEquals(originalMovie.getBoxOfficeGain(), retrievedUpdatedMovie.getBoxOfficeGain());

        } finally {
            mySqlMovieDao.updateMovie(originalMovie.getId(), originalMovie);
        }
    }

    public void testGetMoviesByFilter() throws DaoException {
        List<Movie> movieList = new ArrayList<>();
        movieList = mySqlMovieDao.getMoviesByFilter(new MovieComparator("GENRE","Sci-Fi"));
        assertFalse(movieList.isEmpty());
    }
}

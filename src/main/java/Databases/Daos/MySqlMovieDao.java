package Databases.Daos;

import Databases.DTOs.Movie;
import Databases.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//-- Main Author: Sean Mooney
public class MySqlMovieDao  extends MySqlDao implements MovieDAOInterface{

    @Override
    public List<Movie> getAllMovies() throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Movie> movieList = new ArrayList<>();

        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM MOVIES";
            preparedStatement = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int movieId = resultSet.getInt("MOVIE_ID");
                String movieName = resultSet.getString("MOVIE_NAME");
                String directorName = resultSet.getString("DIRECTOR_NAME");
                String genre = resultSet.getString("GENRE");
                String studio = resultSet.getString("STUDIO");
                int year = resultSet.getInt("YEAR");
                float boxOfficeGain = resultSet.getFloat("BOXOFFICE_GAIN");

                Movie m = new Movie(movieId,movieName,directorName,genre,studio,year,boxOfficeGain);
                movieList.add(m);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllMovies() exception " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return movieList;     // may be empty
    }


    @Override
    public Movie findMovieByName(String name) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Movie movie = null;
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM MOVIES WHERE MOVIE_NAME = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                int movieId = resultSet.getInt("MOVIE_ID");
                String movieName = resultSet.getString("MOVIE_NAME");
                String directorName = resultSet.getString("DIRECTOR_NAME");
                String genre = resultSet.getString("GENRE");
                String studio = resultSet.getString("STUDIO");
                int year = resultSet.getInt("YEAR");
                float boxOfficeGain = resultSet.getFloat("BOXOFFICE_GAIN");

                movie = new Movie(movieId,movieName,directorName,genre,studio,year,boxOfficeGain);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findMovieByName() " + e.getMessage());
            }
        }
        return movie;     // reference to User object, or null value
    }

    @Override
    public int deleteMovieByName(String name) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int numberOfDeletedRows = -1;
        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "DELETE FROM MOVIES WHERE MOVIE_NAME = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString( 1, name );

            //Using a PreparedStatement to execute SQL...
            int result = preparedStatement.executeUpdate();

        } catch (SQLException e)
        {
            throw new DaoException("deleteMovieByName() " + e.getMessage());
        } finally
        {
            try
            {
                if (preparedStatement != null)
                {
                    preparedStatement.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return numberOfDeletedRows;
    }

    @Override
    public Movie addMovie(String name, String directorName, String genre, String studio, int year, float boxOfficeGain) throws DaoException {
        Movie movie = null;

        int insertReturnCode = 0;
        int last_insert_id =0;
        // null required for inserting as id is auto incremented
        String queryInsert = "INSERT INTO MOVIES VALUES (null, ?, ? , ? , ?, ? , ?)";

//        int movieId = resultSet.getInt("MOVIE_ID");
//        String movieName = resultSet.getString("MOVIE_NAME");
//        String directorName = resultSet.getString("DIRECTOR_NAME");
//        String genre = resultSet.getString("GENRE");
//        String studio = resultSet.getString("STUDIO");
//        int year = resultSet.getInt("YEAR");
//        float boxOfficeGain = resultSet.getFloat("BOXOFFICE_GAIN");


        try(Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,directorName);
            preparedStatement.setString(3,genre);
            preparedStatement.setString(4,studio);
            preparedStatement.setInt(5,year);
            preparedStatement.setFloat(6,boxOfficeGain);

            insertReturnCode = preparedStatement.executeUpdate();

            ResultSet generatedKeyResultSet = preparedStatement.getGeneratedKeys();

            if (generatedKeyResultSet.next()){
                last_insert_id = generatedKeyResultSet.getInt(1);
            }

        }catch(SQLException e)
        {
            throw new DaoException("MySqlMovieDao: addMovie() (inserting movie) " + e.getLocalizedMessage());
        }



        String selectQuery = "SELECT * FROM MOVIES WHERE USER_ID - ?";

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery) ) {
            preparedStatement.setInt(1,last_insert_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int movieIdReturn = resultSet.getInt("MOVIE_ID");
                String movieNameReturn = resultSet.getString("MOVIE_NAME");
                String directorNameReturn = resultSet.getString("DIRECTOR_NAME");
                String genreReturn = resultSet.getString("GENRE");
                String studioReturn = resultSet.getString("STUDIO");
                int yearReturn = resultSet.getInt("YEAR");
                float boxOfficeGainReturn = resultSet.getFloat("BOXOFFICE_GAIN");

                movie = new Movie(movieIdReturn,movieNameReturn,directorNameReturn,genreReturn,studioReturn,yearReturn,boxOfficeGainReturn);

            }
        }catch(SQLException e)
        {
            throw new DaoException("MYSqlUserDao: registerUser() (retrieving user row) " + e.getLocalizedMessage());
        }
        return movie;
    }
}

package Databases.Daos;

import Databases.DTOs.User;
import Databases.Exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserDao extends MySqlDao implements UserDAOInterface
{
    /**
     * Will access and return a List of all users in User database table
     * @return List of User objects
     * @throws DaoException
     */


    @Override
    public User registerUser(String username, String lastname, String firstname, String password) throws DaoException{

        User user = null;

        int insertReturnCode = 0;
        int last_insert_id =0;
        // null required for inserting as id is auto incremented
        String queryInsert = "INSERT INTO USER VALUES (null, ?, ? , ? , ?)";

        try(Connection connection = this.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(queryInsert, Statement.RETURN_GENERATED_KEYS))
        {
            preparedStatement.setString(1,firstname);
            preparedStatement.setString(2,lastname);
            preparedStatement.setString(3,username);
            preparedStatement.setString(4,password);

            insertReturnCode = preparedStatement.executeUpdate();

            ResultSet generatedKeyResultSet = preparedStatement.getGeneratedKeys();

            if (generatedKeyResultSet.next()){
                last_insert_id = generatedKeyResultSet.getInt(1);
            }

        }catch(SQLException e)
        {
            throw new DaoException("MYSqlUserDao: registerUser() (inserting user) " + e.getLocalizedMessage());
        }



        String selectQuery = "SELECT * FROM USER WHERE USER_ID - ?";

        try (Connection connection = this.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)   ) {
            preparedStatement.setInt(1,last_insert_id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int userId = resultSet.getInt("USER_ID");
                String usern = resultSet.getString("USERNAME");
                String passw = resultSet.getString("PASSWORD");
                String lastn = resultSet.getString("LAST_NAME");
                String firstn = resultSet.getString("FIRST_NAME");

                 user = new User(userId,usern, lastn, username, firstn);
            }
        }catch(SQLException e)
        {
            throw new DaoException("MYSqlUserDao: registerUser() (retrieving user row) " + e.getLocalizedMessage());
        }



        return user;
    }


    @Override
    public int deleteUserById(int d) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> usersList = new ArrayList<>();

        int numberOfDeletedRows = 0;
        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "DELETE FROM USER WHERE USER_ID = ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt( 1, d );

            //Using a PreparedStatement to execute SQL...
           int result = preparedStatement.executeUpdate();

        } catch (SQLException e)
        {
            throw new DaoException("findAllUseresultSet() " + e.getMessage());
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
        return numberOfDeletedRows;     // may be empty
    }
    @Override
    public List<User> findAllUsersLastNameContains(String subString) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> usersList = new ArrayList<>();

        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "SELECT * FROM USER WHERE USERNAME LIKE ? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString( 1, "%"+subString+"%" );

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int userId = resultSet.getInt("USER_ID");
                String username = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                String lastname = resultSet.getString("LAST_NAME");
                String firstname = resultSet.getString("FIRST_NAME");

                    User u = new User(userId, firstname, lastname, username, password);
                    usersList.add(u);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUsersLastNameContains() " + e.getMessage());
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
        return usersList;     // may be empty
    }

    @Override
    public List<User> findAllUsers() throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> usersList = new ArrayList<>();

        try
        {
            //Get connection object using the getConnection() method inherited
            // from the super class (MySqlDao.java)
            connection = this.getConnection();

            String query = "SELECT * FROM USER";
            preparedStatement = connection.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                int userId = resultSet.getInt("USER_ID");
                String username = resultSet.getString("USERNAME");
                String password = resultSet.getString("PASSWORD");
                String lastname = resultSet.getString("LAST_NAME");
                String firstname = resultSet.getString("FIRST_NAME");
                User u = new User(userId, firstname, lastname, username, password);
                usersList.add(u);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUseresultSet() " + e.getMessage());
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
        return usersList;     // may be empty
    }

    /**
     * Given a username and password, find the corresponding User
     * @param user_name
     * @param password
     * @return User object if found, or null otherwise
     * @throws DaoException
     */
    @Override
    public User findUserByUsernamePassword(String user_name, String password) throws DaoException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        try
        {
            connection = this.getConnection();

            String query = "SELECT * FROM USER WHERE USERNAME = ? AND PASSWORD = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {
                int userId = resultSet.getInt("USER_ID");
                String username = resultSet.getString("USERNAME");
                String pwd = resultSet.getString("PASSWORD");
                String lastname = resultSet.getString("LAST_NAME");
                String firesultSettname = resultSet.getString("FIRST_NAME");

                user = new User(userId, firesultSettname, lastname, username, pwd);
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
                throw new DaoException("findUserByUsernamePassword() " + e.getMessage());
            }
        }
        return user;     // reference to User object, or null value
    }
}

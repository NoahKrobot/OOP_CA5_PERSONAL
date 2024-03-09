package Databases.Daos;

import Databases.Exceptions.DaoException;
import Databases.DTOs.User;

import java.util.List;

public interface UserDAOInterface {
    public List<User> findAllUsers() throws DaoException;

    public User findUserByUsernamePassword(String username,String password) throws DaoException;

    public List<User> findAllUsersLastNameContains(String subString) throws DaoException;
    int deleteUserById(int id) throws DaoException;

    public User registerUser(String username, String lastname, String firstname, String password) throws DaoException;

}

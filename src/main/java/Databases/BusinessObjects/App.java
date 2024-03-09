package Databases.BusinessObjects;

/** OOP Feb 2022
 * This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses Data Access Objects (DAOs),
 * Data Transfer Objects (DTOs), and  a DAO Interface to define
 * a contract between Business Objects and DAOs.
 *
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 *
 * Here, we use one DAO per database table.
 *
 * Use the SQL script "CreateUsers.sql" included with this project
 * to create the required MySQL user_database and User table.
 */

import Databases.Exceptions.DaoException;

import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main(String[] args)
    {
        UserDAOInterface IUserDao = new MySqlUserDao();  //"IUserDao" -> "I" stands for for

//        // Notice that the userDao reference is an Interface type.
//        // This allows for the use of different concrete implementations.
//        // e.g. we could replace the MySqlUserDao with an OracleUserDao
//        // (accessing an Oracle Database)
//        // without changing anything in the Interface.
//        // If the Interface doesn't change, then none of the
//        // code in this file that uses the interface needs to change.
//        // The 'contract' defined by the interface will not be broken.
//        // This means that this code is 'independent' of the code
//        // used to access the database. (Reduced coupling).
//
//        // The Business Objects require that all User DAOs implement
//        // the interface called "UserDaoInterface", as the code uses
//        // only references of the interface type to access the DAO methods.

        try
        {
            System.out.println("\nCall findAllUsers()");
            List<User> users = IUserDao.findAllUsers();     // call a method in the DAO

            if( users.isEmpty() )
                System.out.println("There are no Users");
            else {
                for (User user : users)
                    System.out.println("User: " + user.toString());
            }

            // test dao - with username and password that we know are present in the database

            Scanner userInput = new Scanner(System.in);
            System.out.println("Input username to check");
            String surnameCheckString = userInput.next();
            List<User> usersSurnameCheck = IUserDao.findAllUsersLastNameContains(surnameCheckString);


            Scanner userInput3 = new Scanner(System.in);
            System.out.println("Add new  user");
//            preparedStatement.setString(1,firstname);
//            preparedStatement.setString(2,lastname);
//            preparedStatement.setString(3,username);
//            preparedStatement.setString(4,password);
            System.out.println("Input firstname to add");
            String firstname1 = userInput.next();

            System.out.println("Input lastname to add");
            String lastname1 = userInput.next();

            System.out.println("Input username to add");
            String username1 = userInput.next();

            System.out.println("Input password to add");
            String password1 = userInput.next();
            User newUser =  IUserDao.registerUser(firstname1,lastname1,username1,password1);

            System.out.println("New user");
            System.out.println(newUser);

            Scanner userInput2 = new Scanner(System.in);
            System.out.println("Input userId to delete");
            int userId = userInput.nextInt();
            int deletedRows = IUserDao.deleteUserById(userId);

            System.out.println("Deleted rows = "+deletedRows);


            System.out.println("\nCall: findUserByUsernamePassword()");
            String username = "smithj";
            String password = "password";
            User user = IUserDao.findUserByUsernamePassword(username, password);

            if( usersSurnameCheck.isEmpty() )
                System.out.println("There are no Users");
            else {
                for (User user1 : usersSurnameCheck)
                    System.out.println("User: " + user1.toString());
            }

            if( user != null ) // null returned if userid and password not valid
                System.out.println("User found: " + user);
            else
                System.out.println("Username with that password not found");

            // test dao - with an invalid username (i.e. not in database)
            username = "madmax";
            password = "thunderdome";
            user = IUserDao.findUserByUsernamePassword(username, password);
            if(user != null)
                System.out.println("Username: " + username + " was found: " + user);
            else
                System.out.println("Username: " + username + ", password: " + password +" is not valid.");
        }
        catch( DaoException e )
        {
            e.printStackTrace();
        }
    }
}
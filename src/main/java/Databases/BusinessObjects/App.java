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

import Databases.DTOs.Movie;
import Databases.Daos.MovieDAOInterface;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main(String[] args) throws DaoException {
        runTheApplication();
    }


    //author: Noah Krobot
    public static void runTheApplication() throws DaoException {
        System.out.println("Enter a number to run an action:");
        System.out.println("\t(1) Get All Movies");
        System.out.println("\t(2) Get Movies By Id");
        System.out.println("\t(3) Delete Movies By Id");
        System.out.println("\t(4) Add a Movie");
        System.out.println("\t(5) Update a movie by Id");
        System.out.println("\t(6) Find players using filter");

        int choice = validInt();
        String message = "";
        MovieDAOInterface movieDao = new MySqlMovieDao();
        movieDao = new MySqlMovieDao();



        Scanner key = new Scanner (System.in);
        switch (choice){
            case 1:{
                message = "1";
                System.out.println("\n\n*** getAllMovies ***");
                JsonConverter.converteAllMoviesToJSON();
//                List<Movie> movies = movieDao.getAllMovies();
//                System.out.println("movies: " + movies);
                break;
            }
            case 2:{
                message = "2";
                System.out.println("\n\n*** findMovieByName ***");

                System.out.println("Please, enter movie name: ");
                String input = key.next();
                Movie usersMovie = movieDao.findMovieByName(input);
                System.out.println("Movie you searched: " + usersMovie.toString());
                break;
            }
            case 3:{
                message = "3";
                System.out.println("\n\n*** deleteMovieByName ***");

                System.out.println("Please, enter movie name: ");
                String input = key.next();
                int numberOfDeletedRows = movieDao.deleteMovieByName(input);
                System.out.println("Number of rows you've deleted: " + numberOfDeletedRows);
                break;
            }
            case 4:{
                message = "4";
                System.out.println("\n\n*** createMovie ***");


                System.out.println("Please, enter movie name: ");
                String name = key.next();
                System.out.println("Please, enter director name: ");
                String directorName = key.next();
                System.out.println("Please, enter movie genre: ");
                String genre = key.next();
                System.out.println("Please, enter movie studio: ");
                String studio = key.next();
                System.out.println("Please, enter movie year: ");
                int year = validInt();
                System.out.println("Please, enter movie box office gain: ");
                float boxOfficeGain = validFloat();

                Movie m = new Movie(name,directorName,genre,studio,year,boxOfficeGain);
                Movie usersMovie = movieDao.createMovie(m);
                System.out.println("Movie you searched: " + usersMovie.toString());
                break;
            }
            case 5:{
                System.out.println("\n\n*** updateMovie ***");
                System.out.println("\n --- list of all movies: ");
                List<Movie> movies = movieDao.getAllMovies();
                ArrayList<Integer> movieIDS =  new ArrayList<>();

                int increment = 1;
                for(Movie m: movies){
                    System.out.println("("+increment+ ") "+m);
                    movieIDS.add(m.getId());
                    increment++;
                }

                int userInputIndex = validIntEDIT(movies);
                userInputIndex--;
                    int idFromList = movies.get(userInputIndex).getId();
                    Movie movieToBePassed = movies.get(userInputIndex);
                    Movie updatedMovie =  movieDao.updateMovie(idFromList, movieToBePassed);
                    System.out.println(updatedMovie);

                break;
            }
            case 6:{
                System.out.println("6");
                break;
            }
        }
    }

    //author: Noah Krobot
    public static int validInt(){
        Scanner keyValid = new Scanner(System.in);
        boolean runWhile= true;
        int choice = 0;

        while(runWhile){
            System.out.println("\nEnter your choice:");

            if(keyValid.hasNextInt() ){
                choice = keyValid.nextInt();

                if(choice<7 && choice> 0){
                    runWhile= false;
                }else{
                    System.out.println("Please, enter a number between 1 and 7.");
                }
            }else{
                System.out.println("Please, enter an integer value.");
                keyValid.next();
            }
        }
        return choice;
    }

    public static int validIntEDIT(List<Movie> movies){
        Scanner keyValid = new Scanner(System.in);
        boolean runWhile= true;
        int userInputIndex = 0;

        while(runWhile){
            System.out.println("\nEnter your choice:");

            if(keyValid.hasNextInt() ){
                userInputIndex = keyValid.nextInt();

                if(userInputIndex> movies.size()){
                    System.out.println("Invalid id.");
                }else{
                    runWhile=false;

                }

            }else{
                System.out.println("Please, enter an integer value.");
                keyValid.next();
            }
        }
        return userInputIndex;
    }









    //author: Noah Krobot
    public static float validFloat(){
        Scanner keyValid2 = new Scanner(System.in);
        boolean runWhile= true;
        float choice = 0;

        while(runWhile){
            System.out.println("\nEnter your choice:");

            if(keyValid2.hasNextFloat() ){
                choice = keyValid2.nextFloat();

                if(choice<7 && choice> 0){
                    runWhile= false;
                }else{
                    System.out.println("Please, enter a number between 1 and 7.");
                }
            }else{
                System.out.println("Please, enter an integer value.");
                keyValid2.nextFloat();
            }
        }
        return choice;
    }
}
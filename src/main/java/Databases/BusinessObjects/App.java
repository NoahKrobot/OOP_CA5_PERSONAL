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
import Databases.DTOs.MovieComparator;
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
     static void runTheApplication() throws DaoException {
        System.out.println("Enter a number to run an action:");
        System.out.println("\t(1) Get All Movies");
        System.out.println("\t(2) Get Movies By Id");
        System.out.println("\t(3) Delete Movies By Id");
        System.out.println("\t(4) Add a Movie");
        System.out.println("\t(5) Update a movie by Id");
        System.out.println("\t(6) Find Movie using filter");
         System.out.println("\t(7) Find movies starring an actor");

        int choice = validInt();
        String message = "";
        MovieDAOInterface movieDao = new MySqlMovieDao();
        movieDao = new MySqlMovieDao();



        Scanner key = new Scanner (System.in);
        switch (choice){
            case 1:{
                message = "1";
                System.out.println("\n\n*** getAllMovies ***");
                List<Movie> movies = movieDao.getAllMovies();
                String result = JsonConverter.converteAllMoviesToJSON(movies);
                System.out.println(result);
                break;
            }
            case 2:{
                message = "2";
                System.out.println("\n\n*** findMovieById ***");

                System.out.println("Please, enter movie id: ");
                int input = key.nextInt();
                Movie usersMovie = movieDao.findMovieById(input);
                System.out.println("Movie you searched: " + usersMovie.toString());
                break;
            }
            case 3:{
                message = "3";
                System.out.println("\n\n*** deleteMovieByName ***");

                System.out.println("Please, enter movie id: ");
                int input = key.nextInt();
                int numberOfDeletedRows = movieDao.deleteMovie(input);
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
                    Movie editedMovie = new Movie();

                System.out.println("*** EDIT ***");
                System.out.println("If you enter '0', the value will stay the same.");
                System.out.println("\nPlease, enter movie name");
                System.out.println("Current name: " + movieToBePassed.getMovieName());
                String name = key.nextLine();

                if(name.equals("0")){
                    editedMovie.setMovieName(movieToBePassed.getMovieName());
                }else{
                    editedMovie.setMovieName(name);
                }

                System.out.println("\nPlease, enter director name: ");
                System.out.println("Current director: " + movieToBePassed.getDirectorName());

                String directorName = key.nextLine();

                if(directorName.equals("0")){
                    editedMovie.setDirectorName(movieToBePassed.getDirectorName());
                }else{
                    editedMovie.setDirectorName(directorName);
                }


                System.out.println("\nPlease, enter movie genre: ");
                System.out.println("Current genre: " + movieToBePassed.getGenre());
                String genre = key.nextLine();
                if(genre.equals("0")){
                    editedMovie.setGenre(movieToBePassed.getGenre());
                }else{
                    editedMovie.setGenre(genre);
                }

                System.out.println("\nPlease, enter movie studio: ");
                System.out.println("Current studio: " + movieToBePassed.getStudio());
                String studio = key.nextLine();

                if(studio.equals("0")){
                    editedMovie.setStudio(movieToBePassed.getStudio());
                }else{
                    editedMovie.setStudio(studio);
                }


                System.out.println("\nPlease, enter movie year: ");
                System.out.println("Current year: " + movieToBePassed.getYear());
                int year = validIntYEAR();

                if(year==0){
                    editedMovie.setYear(movieToBePassed.getYear());
                }else{
                    editedMovie.setYear(year);
                }

                System.out.println("\nPlease, enter movie box office gain: ");
                System.out.println("Current box office gain: " + movieToBePassed.getBoxOfficeGain());
                float boxOfficeGain = validFloat();

                if(boxOfficeGain==0){
                    editedMovie.setBoxOfficeGain(movieToBePassed.getBoxOfficeGain());
                }else{
                    editedMovie.setBoxOfficeGain(boxOfficeGain);
                }

                Movie updatedMovie =  movieDao.updateMovie(idFromList, editedMovie);

                System.out.println("\nUpdated movie: ");
                System.out.println(updatedMovie);

                break;
            }
            case 6:{
                int filterChoice = 0;
                System.out.println("By what would you like to filter movies?");
                int counterChoice = 1;
                System.out.println("("+counterChoice+")"+"by DIRECTOR_NAME.");
                counterChoice++;
                System.out.println("("+counterChoice+")"+"by GENRE.");
                counterChoice++;
                System.out.println("("+counterChoice+")"+"by YEAR.");

                System.out.println("\nEnter your choice: ");
                filterChoice = key.nextInt();
                String stringToPass ="";

                switch (filterChoice){

                    case 1:{
                        System.out.println("Enter a director you'd like to filter by: ");
                        stringToPass = key.next();
                        MovieComparator movieComparator = new MovieComparator("DIRECTOR_NAME", stringToPass);
                        List<Movie> movies = movieDao.getMoviesByFilter(movieComparator);
                        loopMovies(movies);

                        break;
                    }
                    case 2:{
                        System.out.println("Enter a genre you'd like to filter by: ");
                        String pass = key.next();
                        MovieComparator movieComparator = new MovieComparator("GENRE", pass);
                        List<Movie> movies = movieDao.getMoviesByFilter(movieComparator);
                        loopMovies(movies);

                        break;
                    }

                    case 3:{
                        System.out.println("Enter a year you'd like to filter by: ");
                        int pass = key.nextInt();
                        MovieComparator movieComparator = new MovieComparator("YEAR", pass);
                        List<Movie> movies = movieDao.getMoviesByFilter(movieComparator);
                        loopMovies(movies);

                        break;
                    }

                }
                break;
            }
            case 7:{
                System.out.println("Enter the actor you would like to search by:");
                String actor = key.nextLine();
                List<Movie> movies = movieDao.findMovieLikeActor(actor);
                if (movies.isEmpty()) {
                    System.out.println("No movies returned");
                } else {
                    for (Movie movie:movies){
                        System.out.println(movie);
                    }
                }
                break;
            }
        }
    }

    public static void loopMovies(List<Movie> movies){

        for(Movie m: movies){
            System.out.println(m);
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

                if(choice<8 && choice> 0){
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

    public static int validIntYEAR(){
        Scanner keyValid = new Scanner(System.in);
        boolean runWhile= true;
        int choice = 0;

        while(runWhile){
            System.out.println("\nEnter your choice:");

            if(keyValid.hasNextInt() ){
                choice = keyValid.nextInt();

                if(choice> 1880){
                    runWhile= false;
                }else{
                    System.out.println("Please, enter a valid year.");
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
                    runWhile= false;

            }else{
                System.out.println("Please, enter an integer value.");
                keyValid2.nextFloat();
            }
        }
        return choice;
    }
}
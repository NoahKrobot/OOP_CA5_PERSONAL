package MultiThread;

import Databases.DTOs.Movie;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

//-- Main Author: Mariela Machuca Palmeros
public class Client {
    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public void start() {

        try (   // create socket to connect to the server
                Socket socket = new Socket("localhost", 8888);
                // get the socket's input and output streams, and wrap them in writer and readers
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.println("Client message: The Client is running and has connected to the server");
            //ask user to enter a command
            Scanner consoleInput = new Scanner(System.in);
            System.out.println("Valid commands are: \"getMovieByID\" to get a movie, \"deleteMovie\" to delete a movie, \"addMovie\" to delete a movie, or \"getAllMovies\" to display all the movies, \"quit\"");
            System.out.println("Please enter a command: ");
            String userRequest = consoleInput.nextLine();

            while(true) {
                // send the command to the server on the socket

                if(userRequest.startsWith("addMovie")){
                    Gson gsonParser = new Gson();
                   String movieName;
                   String directorName;
                    String Genre;
                    String studioName;
                    int year;
                    float boxOffice;

                    System.out.println("What is the name of the movie?");
                    movieName = consoleInput.nextLine();
                    System.out.println("What is the name of the director?");
                    directorName = consoleInput.nextLine();
                    System.out.println("What Genre is the movie?");
                    Genre = consoleInput.nextLine();
                    System.out.println("What is the name of the studio?");
                    studioName = consoleInput.nextLine();
                    System.out.println("What year did the movie came out?");
                    year = consoleInput.nextInt();
                    System.out.println("What was its box office gain?");
                    boxOffice = consoleInput.nextFloat();

                    Movie newMovie = new Movie(movieName, directorName, Genre, studioName, year, boxOffice);

                    String jsonMovie = gsonParser.toJson(newMovie);
                    userRequest += jsonMovie;
                    out.println(userRequest);
                }
                else {
                    out.println(userRequest);      // write the request to socket along with a newline terminator (which is required)
                    // out.flush();                      // flushing buffer NOT necessary as auto flush is set to true

                    // process the answer returned by the server
                    //
                    if (userRequest.startsWith("getMovieByID"))   // if user asked for "time", we expect the server to return a time (in milliseconds)
                    {
                        String timeString = in.readLine();  // (blocks) waits for response from server, then input string terminated by a newline character ("\n")
                        System.out.println("Client message: Response from server after \"getMovieByID\" request: " + timeString);
                    } else if (userRequest.startsWith("getAllMovies")) // if the user has entered the "echo" command
                    {
                        String response = in.readLine();   // wait for response - expecting it to be the same message that we sent to server
                        System.out.println("All Movies from server: \"" + response + "\"");
                    } else if (userRequest.startsWith("deleteMovie")) // if the user has entered the "echo" command
                    {
                        String response = in.readLine();   // wait for response - expecting it to be the same message that we sent to server
                        System.out.println("Movie:  \"" + response + "\"");
                    } else if (userRequest.startsWith("quit")) // if the user has entered the "quit" command
                    {
                        String response = in.readLine();   // wait for response -
                        System.out.println("Client message: Response from server: \"" + response + "\"");
                        break;  // break out of while loop, client will exit.
                    } else {
                        System.out.println("Command unknown. Try again.");
                    }
                }

                consoleInput = new Scanner(System.in);
                System.out.println("Valid commands are: \"time\" to get time, or \"echo <message>\" to get message echoed back, \"quit\"");
                System.out.println("Please enter a command: ");
                userRequest = consoleInput.nextLine();
            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        }
        // sockets and streams are closed automatically due to try-with-resources
        // so no finally block required here.

        System.out.println("Exiting client, but server may still be running.");
    }
}
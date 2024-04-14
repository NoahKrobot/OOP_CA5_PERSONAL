package MultiThread;


import Databases.DTOs.Movie;
import Databases.Daos.MySqlMovieDao;
import Databases.Exceptions.DaoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import  Databases.BusinessObjects.JsonConverter;
import com.google.gson.Gson;


//-- Main Author: Sean Mooney
public class Server {

    final int SERVER_PORT_NUMBER = 8888;  // could be any port from 1024 to 49151 (that doesn't clash with other Apps)

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {

        ServerSocket serverSocket =null;
        Socket clientSocket =null;

        try {
            serverSocket = new ServerSocket(SERVER_PORT_NUMBER);
            System.out.println("Server has started.");
            int clientNumber = 0;  // a number sequentially allocated to each new client (for identification purposes here)

            while (true) {
                System.out.println("Server: Listening/waiting for connections on port ..." + SERVER_PORT_NUMBER);
                clientSocket = serverSocket.accept();
                clientNumber++;
                System.out.println("Server: Listening for connections on port ..." + SERVER_PORT_NUMBER);

                System.out.println("Server: Client " + clientNumber + " has connected.");
                System.out.println("Server: Port number of remote client: " + clientSocket.getPort());
                System.out.println("Server: Port number of the socket used to talk with client " + clientSocket.getLocalPort());

                // create a new ClientHandler for the requesting client, passing in the socket and client number,
                // pass the handler into a new thread, and start the handler running in the thread.
                Thread t = new Thread(new ClientHandler(clientSocket, clientNumber));

                t.start();

                System.out.println("Server: ClientHandler started in thread " + t.getName() + " for client " + clientNumber + ". ");

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        finally{
            try {
                if(clientSocket!=null)
                    clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if(serverSocket!=null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }

        }
        System.out.println("Server: Server exiting, Goodbye!");
    }
}

class ClientHandler implements Runnable   // each ClientHandler communicates with one Client
{
    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket clientSocket;
    final int clientNumber;

    // Constructor
    public ClientHandler(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;  // store socket for closing later
        this.clientNumber = clientNumber;  // ID number that we are assigning to this client
        try {
            // assign to fields
            this.socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * run() method is called by the Thread it is assigned to.
     * This code runs independently of all other threads.
     */
    @Override
    public void run() {
        String request;
        MySqlMovieDao mySqlMovieDao = new MySqlMovieDao();
        JsonConverter jsonConverter = new JsonConverter();
        try {
            while ((request = socketReader.readLine()) != null) {
                System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);

                if (request.startsWith("getMovieByID"))
                {
                    Movie m = mySqlMovieDao.findMovieById(Integer.parseInt(request.substring(13)));

                    if (m != null){
                        String movieString = jsonConverter.convertSingleToJSON(m);
                        socketWriter.println(movieString);
                        System.out.println("Server message: movie sent to client");
                    } else {
                        socketWriter.println("Error retrieving movie from DB");
                        System.out.println("Server message: error retrieving movie from db");
                    }

                } else if (request.startsWith("getAllMovies")) {
                    List<Movie> movieList = mySqlMovieDao.getAllMovies();
                    if (!movieList.isEmpty()){
                        String moviesJson = jsonConverter.converteAllMoviesToJSON(movieList);
                        socketWriter.println(moviesJson);
                        System.out.println("Server message: Sending all movies to client.");
                    } else {
                        socketWriter.println("Error getting movies");
                        System.out.println("Server message: Error Sending all movies to client.");
                    }

                }  else if (request.startsWith("addMovie")) {
                    Gson gsonParser = new Gson();
                    String jsonString = request.substring(8);
                    Movie m = gsonParser.fromJson( jsonString, Movie.class );
                    if (mySqlMovieDao.createMovie(m) == null){
                        socketWriter.println("Movie not added to database");
                        System.out.println("Server message: Error adding movie to DB.");
                    } else {
                        socketWriter.println("Movie added to database");
                        System.out.println("Server message: Movie added to db.");
                    }
                }
                else if (request.startsWith("deleteMovie")) {
                    int movieId = Integer.parseInt(request.substring(12));
                   int deletedRows = mySqlMovieDao.deleteMovie(movieId);
                   if (deletedRows==0){
                       socketWriter.println("Error deleting from database");
                       System.out.println("Server message: Error deleting Movie from database");
                   } else {
                       socketWriter.println("Movie Deleted from database");
                       System.out.println("Server message: Movie deleted from database");
                   }
                }
                else if (request.startsWith("quit"))
                {
                    socketWriter.println("Sorry to see you leaving. Goodbye.");
                    System.out.println("Server message: Client has notified us that it is quitting.");
                }
                else{
                    socketWriter.println("error I'm sorry I don't understand your request");
                    System.out.println("Server message: Invalid request from client.");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } finally {
            this.socketWriter.close();
            try {
                this.socketReader.close();
                this.clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
    }
}



package Server_Client;


import Databases.DTOs.Movie;
import Databases.Daos.MySqlMovieDao;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import Databases.BusinessObjects.JsonConverter;
import Databases.Exceptions.DaoException;
import com.google.gson.Gson;


//-- Main Author: Sean Mooney
public class Server {

    final int SERVER_PORT_NUMBER = 1090;  // could be any port from 1024 to 49151 (that doesn't clash with other Apps)

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

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
        } finally {
            try {
                if (clientSocket != null)
                    clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
            try {
                if (serverSocket != null)
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

    private DataOutputStream dataOutputStream = null;
    private DataInputStream dataInputStream = null;

    private final String[] moviePosterFileNames = {"Images_Server/BladeRunnerServer.jpg", "Images_Server/DunePosterServer.jpg", "Images_Server/PoorThingsPosterServer.jpg"};

    // Constructor
    public ClientHandler(Socket clientSocket, int clientNumber) throws IOException {
        this.clientSocket = clientSocket;  // store socket for closing later
        this.clientNumber = clientNumber;  // ID number that we are assigning to this client
        this.dataInputStream = new DataInputStream(clientSocket.getInputStream());
        this.dataOutputStream = new DataOutputStream( clientSocket.getOutputStream());
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
        try {
            String request;
            MySqlMovieDao mySqlMovieDao = new MySqlMovieDao();

            while ((request = socketReader.readLine()) != null) {
                System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + request);
                switch (request) {
                    case "getMovieByID":
                        handleGetMovieByID(mySqlMovieDao);
                        break;
                    case "getAllMovies":
                        handleGetAllMovies(mySqlMovieDao);
                        break;
                    case "getPosterList":
                        handleGetPosterList();
                        break;
                    case "getPosterImage":
                        handleGetPosterImage();
                        break;
                    case "addMovie":
                        handleAddMovie(mySqlMovieDao);
                        break;
                    case "deleteMovie":
                        handleDeleteMovie(mySqlMovieDao);
                        break;
                    case "quit":
                        handleQuit();
                        break;
                    default:
                        handleInvalidRequest();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeResources();
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
    }

    private void handleGetMovieByID(MySqlMovieDao mySqlMovieDao) throws IOException, DaoException {
        int movieId = Integer.parseInt(socketReader.readLine().substring(13));
        Movie movie = mySqlMovieDao.findMovieById(movieId);

        if (movie != null) {
            String movieString = JsonConverter.convertSingleToJSON(movie);
            socketWriter.println(movieString);
            System.out.println("Server message: movie sent to client");
        } else {
            socketWriter.println("Error retrieving movie from DB");
            System.out.println("Server message: error retrieving movie from db");
        }
    }

    private void handleGetAllMovies(MySqlMovieDao mySqlMovieDao) throws DaoException {
        List<Movie> movieList = mySqlMovieDao.getAllMovies();
        if (!movieList.isEmpty()) {
            String moviesJson = JsonConverter.converteAllMoviesToJSON(movieList);
            socketWriter.println(moviesJson);
            System.out.println("Server message: Sending all movies to client.");
        } else {
            socketWriter.println("Error getting movies");
            System.out.println("Server message: Error Sending all movies to client.");
        }
    }

    private void handleGetPosterList() {
        socketWriter.println("Poster list : 1.Blade Runner 2.Dune 3.Poor things");
    }

    private void handleGetPosterImage() throws Exception {
        socketWriter.println("Enter a poster number");
         String request = socketReader.readLine();
        int fileNumber = Integer.parseInt(request) - 1;
        System.out.println("FILE NUMBER "+fileNumber);
            if (Integer.parseInt(request)>-1 && Integer.parseInt(request)<20) {
                String fileToGet = moviePosterFileNames[fileNumber];
                socketWriter.println(fileToGet.substring(14));
                sendFile(fileToGet);
            } else {
                socketWriter.println("Invalid request for file");
            }
    }

    private void sendFile(String path) throws Exception {
        int bytes = 0;
        File file = new File(path);

        System.out.println(file);
        FileInputStream fileInputStream = new FileInputStream(file);
        this.dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024]; // 4 kilobyte buffer

        while ((bytes = fileInputStream.read(buffer)) != -1) {
            this.dataOutputStream.write(buffer, 0, bytes);
            this.dataOutputStream.flush();
        }
        fileInputStream.close();
    }

    private void handleAddMovie(MySqlMovieDao mySqlMovieDao) throws IOException, DaoException {
        Gson gsonParser = new Gson();
        String jsonString = socketReader.readLine().substring(8);
        Movie movie = gsonParser.fromJson(jsonString, Movie.class);
        if (mySqlMovieDao.createMovie(movie) == null) {
            socketWriter.println("Movie not added to database");
            System.out.println("Server message: Error adding movie to DB.");
        } else {
            socketWriter.println("Movie added to database");
            System.out.println("Server message: Movie added to db.");
        }
    }

    private String handleDeleteMovie(MySqlMovieDao mySqlMovieDao) throws IOException, DaoException {
        String message = "";
        int movieId = Integer.parseInt(socketReader.readLine().substring(12));
        int deletedRows = mySqlMovieDao.deleteMovie(movieId);
        if (deletedRows == 0) {
            socketWriter.println("Error deleting from database");
            System.out.println("Server message: Error deleting Movie from database");
            message = "Server message: Error deleting Movie from database";
        } else {
            socketWriter.println("Movie Deleted from database");
            System.out.println("Server message: Movie deleted from database");
            message = "Server message: Movie deleted from database";
        }
        return message;
    }

    private void handleQuit() {
        socketWriter.println("Sorry to see you leaving. Goodbye.");
        System.out.println("Server message: Client has notified us that it is quitting.");
    }

    private void handleInvalidRequest() {
        socketWriter.println("Error. I'm sorry I don't understand your request");
        System.out.println("Server message: Invalid request from client.");
    }

    private void closeResources() {
        try {
            socketWriter.close();
            socketReader.close();
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}



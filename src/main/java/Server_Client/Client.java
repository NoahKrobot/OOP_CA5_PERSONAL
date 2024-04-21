package Server_Client;

import Databases.DTOs.Movie;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

import java.util.Scanner;

//-- Main Author: Mariela Machuca Palmeros
//-- Edit: Sean Mooney
public class Client {

    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;


    public static void main(String[] args) throws IOException {
        Client client = new Client();
        start();
    }
    public static void start() throws IOException {
        try (
                Socket socket = new Socket("localhost", 1050);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream( socket.getOutputStream());
            System.out.println("Client message: The Client is running and has connected to the server");
            Scanner consoleInput = new Scanner(System.in);

            while (true) {
                System.out.println("Select an option:");
                System.out.println("1. Add Movie");
                System.out.println("2. Get Poster List");
                System.out.println("3. Download a Poster");
                System.out.println("4. Get Movie by ID");
                System.out.println("5. Get All Movies");
                System.out.println("6. Delete Movie");
                System.out.println("7. Quit");

                int choice = consoleInput.nextInt();
                consoleInput.nextLine();

                switch (choice) {
                    case 1: addMovie(out, in, consoleInput);
                        break;
                    case 2: sendRequest(out,"getPosterList ", in);
                        break;
                    case 3: sendPosterRequest(out, in);
                        break;
                    case 4: sendRequestGetMovieById(out, "getMovieByID ", in);
                        break;
                    case 5: sendRequest(out, "getAllMovies ", in);
                        break;
                    case 6: deleteMovie(out, "deleteMovie ", in);
                        break;
                    case 7: sendRequest(out, "quit ", in);
                        System.out.println("Exiting client, but server may still be running.");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (IOException e) {
            System.out.println("Client message: IOException: " + e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void addMovie(PrintWriter out, BufferedReader in, Scanner consoleInput) throws IOException {
        Gson gsonParser = new Gson();
        System.out.println("What is the name of the movie?");
        String movieName = consoleInput.nextLine();
        System.out.println("What is the name of the director?");
        String directorName = consoleInput.nextLine();
        System.out.println("What Genre is the movie?");
        String genre = consoleInput.nextLine();
        System.out.println("What is the name of the studio?");
        String studioName = consoleInput.nextLine();
        System.out.println("What year did the movie come out?");
        int year = consoleInput.nextInt();
        System.out.println("What was its box office gain?");
        float boxOffice = consoleInput.nextFloat();

        Movie newMovie = new Movie(movieName, directorName, genre, studioName, year, boxOffice);
        String jsonMovie = gsonParser.toJson(newMovie);

        sendRequest(out, "addMovie " + jsonMovie, in);
    }

    public static String sendRequest(PrintWriter out, String request, BufferedReader in) throws IOException {
        out.println(request);
        String response = in.readLine();
        System.out.println(response);
        return response;
    }


    public static void deleteMovie(PrintWriter out, String request, BufferedReader in) throws IOException {
        System.out.println("Enter the id of movie you want to delete: ");
        Scanner input = new Scanner(System.in);
        String movieNumber = input.next();
        out.println(request+" "+movieNumber);
        String response = in.readLine();
        System.out.println(response);
    }
    public static String sendRequestGetMovieById(PrintWriter out, String request, BufferedReader in) throws IOException {
        System.out.println("Enter the id of movie you are looking for: ");
        Scanner input = new Scanner(System.in);
        String movieNumber = input.next();
        out.println(request+" "+movieNumber);
        String response = in.readLine();
        System.out.println(response);
        return response;
    }
    private static void sendPosterRequest(PrintWriter out, BufferedReader in) throws Exception {
        out.println("getPosterImage ");
        String response = in.readLine();
        System.out.println(response);
        Scanner input = new Scanner(System.in);
        String userNumber = input.next();
        out.println(userNumber);
        String filename = in.readLine();
        System.out.println(filename);
        receiveFile(filename);
    }
    private static void receiveFile(String fileName) throws Exception
    {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream("images_Client/"+fileName);

        long size = dataInputStream.readLong();
        System.out.println("Server: file size in bytes = " + size);

        byte[] buffer = new byte[4 * 1024];         // 4 kilobyte buffer

        System.out.println("Server:  Bytes remaining to be read from socket: ");

        while (size > 0 &&
                (bytes = dataInputStream.read(buffer, 0,(int)Math.min(buffer.length, size))) != -1) {
            fileOutputStream.write(buffer, 0, bytes);
            size = size - bytes;
            System.out.print(size + ", ");
        }

        System.out.println("File is Received");

        System.out.println("Look in the images_Client folder to see the transferred file");
        fileOutputStream.close();
    }


}
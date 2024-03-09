package Databases.DTOs;
//CREATE TABLE `movies` (
//        `MOVIE_ID` int(11) NOT NULL AUTO_INCREMENT,
//        `MOVIE_NAME` varchar(255) NOT NULL,
//        `DIRECTOR_NAME` varchar(50) NOT NULL,
//        `GENRE` varchar(50) NOT NULL,
//        `STUDIO` varchar(255) NOT NULL,
//        `YEAR` int(11) NOT NULL,
//        `BOXOFFICE_GAIN` FLOAT NOT NULL,
//
//        PRIMARY KEY  (`MOVIE_ID`)
//        );

//-- Main Author: Sean Mooney
public class Movie {

    private int id;

    private String movieName;

    private String directorName;

    private String genre;

    private String studio;

    private int year;

    private float boxOfficeGain;

    public Movie() {
    }

    public Movie(int id, String movieName, String directorName, String genre, String studio, int year, float boxOfficeGain) {
        this.id = id;
        this.movieName = movieName;
        this.directorName = directorName;
        this.genre = genre;
        this.studio = studio;
        this.year = year;
        this.boxOfficeGain = boxOfficeGain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public float getBoxOfficeGain() {
        return boxOfficeGain;
    }

    public void setBoxOfficeGain(float boxOfficeGain) {
        this.boxOfficeGain = boxOfficeGain;
    }
}

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

    // for testing equals
    public Movie(String movieName) {
        this.movieName = movieName;
        this.directorName = "";
        this.genre = "";
        this.studio = "";
        this.year = 0;
        this.boxOfficeGain = 0;
    }

    @Override
    public String toString() {
        return "Movie[" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", directorName='" + directorName + '\'' +
                ", genre='" + genre + '\'' +
                ", studio='" + studio + '\'' +
                ", year=" + year +
                ", boxOfficeGain=" + boxOfficeGain +
                ']';
    }

    //-- Main Author: Sean Mooney
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Movie otherMovie = (Movie) obj;
        return this.movieName.equals(otherMovie.getMovieName());
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

    public Movie(String movieName, String directorName, String genre, String studio, int year, float boxOfficeGain) {
        this.id = 0;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

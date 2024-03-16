package Databases.DTOs;

import java.util.Comparator;

public class MovieComparator implements Comparator<Movie> {

    private final String compareBy;
    private final String compareToString;

    private final int compareToInt;

    private final float compareToFloat;

    private final String compareType;

    public String getCompareType() {
        return compareType;
    }

    public MovieComparator(String compareBy, String compareTo){
        this.compareBy = compareBy;
        this.compareToString = compareTo;
        this.compareToInt = 0;
        this.compareToFloat =-1;
        compareType = "String";
    }

    public MovieComparator(String compareBy,int compareTo){
        this.compareBy = compareBy;
        this.compareToString = "";
        this.compareToInt = compareTo;
        this.compareToFloat =-1;
        compareType = "Integer";
    }

    public MovieComparator(String compareBy,float compareTo){
        this.compareBy = compareBy;
        this.compareToString = "";
        this.compareToInt = 0;
        this.compareToFloat = compareTo;
        compareType = "Float";
    }

    public int getCompareToInt() {
        return compareToInt;
    }

    public float getCompareToFloat() {
        return compareToFloat;
    }

    public String getCompareToString() {
        return compareToString;
    }

    public String getCompareBy() {
        return compareBy;
    }


    @Override
    public int compare(Movie m1, Movie m2) {
        switch (this.compareBy) {
            case "MOVIE_ID":
                return Integer.compare(m1.getId(), m2.getId());
            case "MOVIE_NAME":
                return m1.getMovieName().compareTo(m2.getMovieName());
            case "DIRECTOR_NAME":
                return m1.getDirectorName().compareTo(m2.getDirectorName());
            case "GENRE":
                return m1.getGenre().compareTo(m2.getGenre());
            case "STUDIO":
                return m1.getStudio().compareTo(m2.getStudio());
            case "YEAR":
                return Integer.compare(m1.getYear(), m2.getYear());
            case "BOXOFFICE_GAIN":
                return Float.compare(m1.getBoxOfficeGain(), m2.getBoxOfficeGain());
            default:
                return 0;
        }
    }
}

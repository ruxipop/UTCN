package model;

public class Movie implements  java.io.Serializable{



    private String title;
    private String type;
    private String category;
    private int year;


    public Movie(String title, String type, String category, int year) {
        this.title = title;
        this.type = type;
        this.category = category;
        this.year = year;

    }

    public Movie() {
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ",type='" + type + '\'' +
                ",category='" + category + '\'' +
                ",year=" + year +
                '}';
    }


}

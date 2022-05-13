package Model;

public class MovieBuilder {
    private String title;
    private String type;
    private String category;
    private int year;

    public MovieBuilder() {

    }

    public MovieBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MovieBuilder type(String type) {
        this.type = type;
        return this;
    }

    public MovieBuilder category(String category) {
        this.category = category;
        return this;
    }

    public MovieBuilder year(int year) {
        this.year = year;
        return this;
    }

    public Movie build() {
        return new Model.Movie(title, type, category, year);
    }

}

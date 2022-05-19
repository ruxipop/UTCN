package bll;


import java.util.*;

import com.*;
import model.*;

import java.io.*;

public class MovieBLL {

    private Client client;
    private List<Movie> moviesList = new ArrayList<>();

    public MovieBLL(Client client) {
        this.client = client;

    }

    public List<Movie> getMoviesList() throws IOException {
        this.client.sendMessage("allMovie");
        moviesList = (List<model.Movie>) (Object) client.listenForMessage();
        return moviesList;
    }

    public void updateMovie(Movie movie, int year1) {

        this.client.sendMessage("updateMovie " + movie.getTitle() + " " + movie.getType() + " " + movie.getCategory() + " " + movie.getYear() + " " + year1);


    }

    public void deleteMovie(String title, int year) {
        this.client.sendMessage("deleteMovie " + title + " " + year);


    }

    public void insertMovie(Movie movie) {
        this.client.sendMessage("insertMovie " + movie.getTitle() + " " + movie.getType() + " " + movie.getCategory() + " " + movie.getYear());


    }


    public List<Movie> searchMovieByTitle(String title) throws java.io.IOException {
        List<Movie> list = new java.util.ArrayList<>();
        for (Movie movie : getMoviesList()) {
            if (movie.getTitle().equals(title)) {
                list.add(movie);
            }
        }
        return list;
    }

    public List<String> typeOfType() throws java.io.IOException {
        List<String> ss = new ArrayList<String>();
        for (Movie movie : moviesList) {

            ss.add(movie.getType());
        }
        Set<String> set = new HashSet<>(ss);
        ss.clear();
        ss.addAll(set);
        return ss;
    }

    public List<String> typeOfCategory() throws java.io.IOException {
        List<String> ss = new ArrayList<String>();
        for (Movie movie : moviesList) {

            ss.add(movie.getCategory());
        }
        Set<String> set = new HashSet<>(ss);
        ss.clear();
        ss.addAll(set);
        return ss;
    }


    public List<Movie> filterMovie(String category, String type, int year) throws java.io.IOException {


        List<Movie> m = this.findItemAll(getMoviesList(), category, type, year);

        if (!m.isEmpty()) {

            return m;

        }

        return null;
    }

    public List<Movie> findItemAll(final List<Movie> list, final String category, final String type, final int year) {

        return list.stream().filter(p -> {
            return (category.equals(" ") || p.getCategory().contains(category)) && ((year == -1) || p.getYear() == year) && (type.equals(" ") || p.getType().contains(type));

        }).collect(java.util.stream.Collectors.toList());

    }

    public int select_type(String type) throws java.io.IOException {
        client.sendMessage("selectType " + type);
        return client.listenForInt();
    }

    public int select_category(String category) throws java.io.IOException {
        client.sendMessage("selectCategory " + category);
        return client.listenForInt();
    }

}

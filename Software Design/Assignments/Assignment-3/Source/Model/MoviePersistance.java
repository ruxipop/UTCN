package Model;

import org.json.simple.JSONArray;
import org.json.simple.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.sql.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;
import java.util.ArrayList;


import org.jfree.chart.*;

public class MoviePersistance {
    private static int fileCount = 1;
    private List<Movie> moviesList;
    private static int nr = 0;
    private String jdbcURL = "jdbc:mysql://192.168.64.2:3306/moviehouse";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";


    private static final String INSERT_MOVIES_SQL = "INSERT INTO movies" + "  (movieID, title, type,category,year) VALUES " +
            " (?,?,?,?,?);";
    private static final String SELECT_ALL_MOVIES = "SELECT * FROM movies";
    private static final String DELETE_MOVIES_SQL = "DELETE FROM movies WHERE title = ? and year=?";
    private static final String UPDATE_MOVIES_SQL = "UPDATE movies SET type=? ,category=?,year=? where title = ? and year=?";
    private static final String SELECT_MOVIE_BY_TITLE_YEAR = "SELECT title,type,category,year FROM movies WHERE title =? and year=?";
    private static final String SELECT_MOVIE_BY_TITLE = "SELECT title,type,category,year FROM movies WHERE title =?";
    private static final String COUNT_NB_TYPE = "SELECT COUNT(*) AS  total from movies WHERE type=?";
    private static final String COUNT_NB_CATEGORY = "SELECT COUNT(*) AS  total from movies WHERE category=?";

    public MoviePersistance() {
        this.moviesList = new ArrayList<Movie>();
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        return connection;
    }

    public List<Movie> getMoviesList() {
        moviesList.clear();
        selectAllMovies();
        return moviesList;
    }

    public List<String> typeOfType() {
        List<String> ss = new ArrayList<String>();
        for (Movie movie : getMoviesList()) {

            ss.add(movie.getType());
        }
        Set<String> set = new HashSet<>(ss);
        ss.clear();
        ss.addAll(set);
        return ss;
    }


    public List<String> typeOfCategory() {
        List<String> ss = new ArrayList<String>();
        for (Movie movie : getMoviesList()) {

            ss.add(movie.getCategory());
        }
        Set<String> set = new HashSet<>(ss);
        ss.clear();
        ss.addAll(set);
        return ss;
    }


    public int select_type(String type) throws SQLException {
        int total = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_NB_TYPE);) {
            preparedStatement.setString(1, type);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return total;
    }

    public int select_category(String category) throws SQLException {
        int total = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_NB_CATEGORY);) {
            preparedStatement.setString(1, category);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return total;
    }

    public void insertMovie(Movie movie) throws SQLException {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MOVIES_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, String.valueOf(movie.getMovieID()));
            preparedStatement.setString(2, movie.getTitle());
            preparedStatement.setString(3, movie.getType());
            preparedStatement.setString(4, movie.getCategory());
            preparedStatement.setInt(5, movie.getYear());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            printSQLException(e);
        }
    }

    public void selectAllMovies() {

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MOVIES);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("movieID");
                String title = rs.getString("title");
                String type = rs.getString("type");
                String category = rs.getString("category");
                int year = rs.getInt("year");
                moviesList.add(new Model.Movie(title, type, category, year));

            }
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    public boolean deleteMovie(String title, int year) throws SQLException {

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_MOVIES_SQL);) {
            statement.setString(1, title);
            statement.setInt(2, year);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return false;
    }

    public void updateMovie(Movie movie, int year1) throws SQLException {

        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_MOVIES_SQL);) {
            statement.setString(1, movie.getType());
            statement.setString(2, movie.getCategory());
            statement.setInt(3, movie.getYear());
            statement.setString(4, movie.getTitle());
            statement.setInt(5, year1);
            statement.executeUpdate();
        }

    }

    public Movie selectMovieYear(String title1, int year1) {

        Movie movie = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MOVIE_BY_TITLE_YEAR);) {
            preparedStatement.setString(1, title1);
            preparedStatement.setInt(2, year1);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String type = rs.getString("type");
                String category = rs.getString("category");
                int year = rs.getInt("year");
                movie = new MovieBuilder().title(title).type(type).category(category).year(year).build();

            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return movie;
    }

    public Movie selectMovie(String title1) {

        Movie movie = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MOVIE_BY_TITLE);) {
            preparedStatement.setString(1, title1);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                String type = rs.getString("type");
                String category = rs.getString("category");
                int year = rs.getInt("year");
                movie = new MovieBuilder().title(title).type(type).category(category).year(year).build();

            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return movie;
    }

    public List<Movie> searchMovieByTitle(String title) {
        List<Movie> list = new java.util.ArrayList<>();
        for (Movie movie : getMoviesList()) {
            if (movie.getTitle().equals(title)) {
                list.add(movie);
            }
        }
        return list;
    }

    public List<Movie> filterMovie(String category, String type, int year) {


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


    public void saveReports(int nb) {
        selectAllMovies();
        if (nb == 0) {
            try (PrintWriter writer = new PrintWriter("Report_" + fileCount + ".csv")) {
                fileCount++;
                StringBuilder sb = new StringBuilder();
                sb.append("Title");
                sb.append(',');
                sb.append("Type");
                sb.append(',');
                sb.append("Category");
                sb.append(',');
                sb.append("Year");
                sb.append('\n');
                for (Movie movie : this.moviesList) {
                    sb.append(movie.getTitle());
                    sb.append(',');
                    sb.append(movie.getType());
                    sb.append(',');
                    sb.append(movie.getCategory());
                    sb.append(',');
                    sb.append(String.valueOf(movie.getYear()));
                    sb.append('\n');
                }
                writer.write(sb.toString());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else if (nb == 1) {
            JSONArray movies = new JSONArray();
            for (Movie movie : this.moviesList) {
                JsonObject jsonObject = new JsonObject();

                jsonObject.put("year", String.valueOf(movie.getYear()));
                jsonObject.put("type", movie.getType());
                jsonObject.put("category", movie.getCategory());
                jsonObject.put("title", movie.getTitle());
                movies.add(jsonObject);
            }
            try (FileWriter file = new FileWriter("Report_" + fileCount + ".json")) {
                fileCount++;
                file.write(movies.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (nb == 2) {
            try {

                DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
                Document document = documentBuilder.newDocument();
                Element root = document.createElement("movieHouse");
                document.appendChild(root);

                for (Movie m : getMoviesList()) {

                    Element movie = document.createElement("movie");
                    root.appendChild(movie);

                    Element title = document.createElement("title");
                    title.appendChild(document.createTextNode(m.getTitle()));
                    root.appendChild(title);

                    Element type = document.createElement("type");
                    type.appendChild(document.createTextNode(m.getType()));

                    root.appendChild(type);

                    Element category = document.createElement("category");
                    category.appendChild(document.createTextNode(m.getCategory()));
                    root.appendChild(category);

                    Element year = document.createElement("year");
                    year.appendChild(document.createTextNode(String.valueOf(m.getYear())));
                    root.appendChild(year);


                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource domSource = new DOMSource(document);
                StreamResult streamResult = new StreamResult(new File("Report_" + fileCount + ".xml"));
                transformer.transform(domSource, streamResult);


            } catch (javax.xml.parsers.ParserConfigurationException | javax.xml.transform.TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (javax.xml.transform.TransformerException e) {
                e.printStackTrace();
            }
        }
    }


    public void deleteFile(String nameFile) {

        File f = new File(nameFile);

        f.delete();


    }

    public void generate(String nbMovies, String movieCategory, String movieType) throws SQLException {
        int pp = nr - 1;
        deleteFile("chart_column" + pp + ".jpg");
        deleteFile("chart_column2" + pp + ".jpg");
        deleteFile("chart_radial" + pp + ".jpg");
        deleteFile("chart_radial2" + pp + ".jpg");
        deleteFile("chart_inelar" + pp + ".jpg");
        deleteFile("chart_inelar2" + pp + ".jpg");
        var dataset = new org.jfree.data.category.DefaultCategoryDataset();
        for (String s : typeOfType()) {
            dataset.setValue(select_type(s), nbMovies, s);


        }

        var dataset1 = new org.jfree.data.category.DefaultCategoryDataset();
        for (String s : typeOfCategory()) {
            dataset1.setValue(select_category(s), nbMovies, s);

        }
        var dataset2 = new org.jfree.data.general.DefaultPieDataset();
        for (String s : typeOfType()) {
            dataset2.setValue(s, select_type(s));

        }
        var dataset3 = new org.jfree.data.general.DefaultPieDataset();
        for (String s : typeOfCategory()) {
            dataset3.setValue(s, select_category(s));

        }

        JFreeChart barChart = org.jfree.chart.ChartFactory.createBarChart(
                movieType,
                "",
                nbMovies,
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                false, true, false);
        JFreeChart barChart1 = org.jfree.chart.ChartFactory.createBarChart(
                movieCategory,
                "",
                nbMovies,
                dataset1,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                false, true, false);
        JFreeChart pieChart = org.jfree.chart.ChartFactory.createPieChart(
                movieType,
                dataset2,
                false, true, false);
        JFreeChart pieChart1 = org.jfree.chart.ChartFactory.createPieChart(
                movieCategory,
                dataset3,
                false, true, false);


        JFreeChart chart = ChartFactory.createRingChart(movieType, dataset2, true, true, false);
        JFreeChart chart1 = ChartFactory.createRingChart(movieCategory, dataset3, true, true, false);

        try {
            ChartUtils.saveChartAsJPEG(new File("chart_column" + nr + ".jpg"),
                    barChart, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_column2" + nr + ".jpg"),
                    barChart1, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_radial" + nr + ".jpg"),
                    pieChart, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_radial2" + nr + ".jpg"),
                    pieChart1, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_inelar" + nr + ".jpg"),
                    chart, 400, 270);
            ChartUtils.saveChartAsJPEG(new File("chart_inelar2" + nr + ".jpg"),
                    chart1, 400, 270);
        } catch (IOException e) {
            System.out.println("Problem in creating chart.");
        }
        nr++;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}



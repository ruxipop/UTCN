package Model;

import org.json.simple.JSONArray;
import org.json.simple.JsonObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoviePersistance {
    private static int fileCount = 1;
    private static final String FILENAME = "/Users/popruxi/Desktop/an3sem2/MVP-project/staff.xml";
    private List<Movie> moviesList;

    public MoviePersistance() {
        this.moviesList = new ArrayList<Movie>();
    }

    public List<Movie> getMoviesList() {

        readMovies();
        return moviesList;
    }


    public void readMovies() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));
            Element root = doc.getDocumentElement();
            NodeList list = doc.getElementsByTagName("movie");
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                String title = element.getElementsByTagName("title").item(0).getTextContent();
                String type = element.getElementsByTagName("type").item(0).getTextContent();
                String category = element.getElementsByTagName("category").item(0).getTextContent();
                String year1 = element.getElementsByTagName("year").item(0).getTextContent();
                int year = Integer.parseInt(year1);
                Movie movie = new Movie(title, type, category, year);
                this.moviesList.add(movie);
            }


        } catch (Exception e) {
            this.moviesList = null;
        }
    }

    public boolean deleteMovie(String title) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(FILENAME));
            Element root = document.getDocumentElement();

            NodeList allUserNodes = root.getElementsByTagName("movie");
            for (int i = 0; i < allUserNodes.getLength(); i++) {
                Element element = (Element) allUserNodes.item(i);
                if (element.getElementsByTagName("title").item(0).getTextContent().equals(title)) {
                    root.removeChild(element);
                }
            }


            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("staff.xml");
            transformer.transform(source, result);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public boolean addMovie(Movie movie) {


        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(FILENAME));
            Element root = document.getDocumentElement();

            Element newMovie = document.createElement("movie");

            Text lb = document.createTextNode("\n");
            Text lb1 = document.createTextNode("\n");
            Text lb2 = document.createTextNode("\n");
            Text lb3 = document.createTextNode("\n");
            Text lb4 = document.createTextNode("\n");
            Text lb5 = document.createTextNode("\n");
            Text lb6 = document.createTextNode("\n");
            Element title = document.createElement("title");
            newMovie.appendChild(lb);
            title.appendChild(document.createTextNode(movie.getTitle()));

            newMovie.appendChild(title);

            newMovie.appendChild(lb5);
            Element type = document.createElement("type");
            type.appendChild(document.createTextNode(movie.getType()));
            newMovie.appendChild(type);
            newMovie.appendChild(lb1);

            Element category = document.createElement("category");
            category.appendChild(document.createTextNode(movie.getCategory()));

            newMovie.appendChild(category);

            newMovie.appendChild(lb2);

            Element year = document.createElement("year");
            year.appendChild(document.createTextNode(String.valueOf(movie.getYear())));

            newMovie.appendChild(year);
            newMovie.appendChild(lb3);

            root.appendChild(newMovie);
            root.appendChild(lb4);


            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("staff.xml");
            transformer.transform(source, result);
            return true;
        } catch (Exception e) {
        }
        return false;
    }


    public boolean updateMovie(Movie movie, String title) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(FILENAME));
            Element root = document.getDocumentElement();

            NodeList allUserNodes = root.getElementsByTagName("movie");
            for (int i = 0; i < allUserNodes.getLength(); i++) {
                Element element = (Element) allUserNodes.item(i);
                if (element.getElementsByTagName("title").item(0).getTextContent().equals(title)) {
                    if (!movie.getTitle().isEmpty()) {

                        element.getElementsByTagName("title").item(0).setTextContent(movie.getTitle());
                    }
                    if (!movie.getType().isEmpty()) {
                        element.getElementsByTagName("type").item(0).setTextContent(movie.getType());
                    }
                    if (!movie.getCategory().isEmpty()) {
                        element.getElementsByTagName("category").item(0).setTextContent(movie.getCategory());
                    }
                    if (movie.getYear() != 0 || movie.getYear() > 0) {
                        element.getElementsByTagName("year").item(0).setTextContent(String.valueOf(movie.getYear()));
                    }

                }
            }


            DOMSource source = new DOMSource(document);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult("staff.xml");
            transformer.transform(source, result);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public Movie searchMovieByTitle(String title) {
        readMovies();
        for (Movie movie : this.moviesList) {
            if (movie.getTitle().equals(title)) {
                return movie;
            }
        }
        return null;
    }

    public void saveReports(int nb) {
        readMovies();
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
        }
    }

    public List<Movie> filterMovie(String category, String type, String year) {

        String category1 = (category.equals("Select")) ? " " : category;
        int year1 = (year.equals("Select")) ? -1 : Integer.parseInt(year);
        String type1 = (type.equals("Select")) ? " " : type;

        List<Movie> m = this.findItemAll(this.getMoviesList(), category1, type1, year1);

        if (!m.isEmpty()) {

            return m;

        }

        return null;
    }

    public List<Movie> findItemAll(final List<Movie> list, final String category, final String type, final int year) {

        return list.stream().filter(p -> {
            return (category.equals(" ") || p.getCategory().contains(category)) && ((year == -1) || p.getYear() == year) && (type.equals(" ") || p.getType().contains(type));

        }).collect(Collectors.toList());

    }
    public boolean existMovie(Movie movie){

     boolean exist= this.getMoviesList().stream().filter(o -> o.getTitle().equals(movie.getTitle())).findFirst().isPresent();

     if(exist && movie.getType().equals("Serial")){

         for(Movie m: this.getMoviesList()){

             if(m.getTitle().equals(movie.getTitle()) && m.getYear()==movie.getYear()) {

                 return true;
             }
         }
     }else if(exist && movie.getType().equals("Artistic")){

         return true;
     }

        return false;

    }

}


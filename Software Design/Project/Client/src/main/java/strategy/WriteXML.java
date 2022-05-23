package strategy;

import java.util.*;

public class WriteXML implements WriteStrategy {
    private static int fileCount = 1;

    @Override
    public void writeFile(List<model.Movie> list) {
        try {

            javax.xml.parsers.DocumentBuilderFactory documentFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.newDocument();
            org.w3c.dom.Element root = document.createElement("movieHouse");
            document.appendChild(root);

            for (model.Movie m : list) {

                org.w3c.dom.Element movie = document.createElement("movie");
                root.appendChild(movie);

                org.w3c.dom.Element title = document.createElement("title");
                title.appendChild(document.createTextNode(m.getTitle()));
                root.appendChild(title);

                org.w3c.dom.Element type = document.createElement("type");
                type.appendChild(document.createTextNode(m.getType()));

                root.appendChild(type);

                org.w3c.dom.Element category = document.createElement("category");
                category.appendChild(document.createTextNode(m.getCategory()));
                root.appendChild(category);

                org.w3c.dom.Element year = document.createElement("year");
                year.appendChild(document.createTextNode(String.valueOf(m.getYear())));
                root.appendChild(year);


            }
            javax.xml.transform.TransformerFactory transformerFactory = javax.xml.transform.TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer = transformerFactory.newTransformer();
            javax.xml.transform.dom.DOMSource domSource = new javax.xml.transform.dom.DOMSource(document);
            javax.xml.transform.stream.StreamResult streamResult = new javax.xml.transform.stream.StreamResult(new java.io.File("Report_" + fileCount + ".xml"));
            fileCount++;
            transformer.transform(domSource, streamResult);


        } catch (javax.xml.parsers.ParserConfigurationException | javax.xml.transform.TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (javax.xml.transform.TransformerException e) {
            e.printStackTrace();
        }
    }
}

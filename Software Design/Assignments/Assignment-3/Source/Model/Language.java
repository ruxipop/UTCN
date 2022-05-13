package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Map;


public class Language {
    private String language;

    public Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Map<String, String> getTextLanguage() {
        Map<String, String> date = new java.util.HashMap<>();
        final String FILENAME = "/Users/popruxi/Desktop/an3sem2/ps/MVC-project/informatii.xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));

            doc.getDocumentElement().normalize();


            NodeList list = doc.getElementsByTagName(this.language);

            for (int temp = 0; temp < list.getLength(); temp++) {

                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element element = (Element) node;

                    date.put("usernameLabel", element.getElementsByTagName("usernameLabel").item(0).getTextContent());
                    date.put("passwordLabel", element.getElementsByTagName("passwordLabel").item(0).getTextContent());
                    date.put("buttonLogin", element.getElementsByTagName("buttonLogin").item(0).getTextContent());
                    date.put("labelSpec", element.getElementsByTagName("labelSpec").item(0).getTextContent());
                    date.put("labelPass", element.getElementsByTagName("labelPass").item(0).getTextContent());
                    date.put("labelSince", element.getElementsByTagName("labelSince").item(0).getTextContent());
                    date.put("logOutButton", element.getElementsByTagName("logOutButton").item(0).getTextContent());
                    date.put("titleLabel", element.getElementsByTagName("titleLabel").item(0).getTextContent());
                    date.put("categoryLabel", element.getElementsByTagName("categoryLabel").item(0).getTextContent());
                    date.put("yearLabel", element.getElementsByTagName("yearLabel").item(0).getTextContent());
                    date.put("checkArtisitc", element.getElementsByTagName("checkArtisitc").item(0).getTextContent());
                    date.put("checkSerial", element.getElementsByTagName("checkSerial").item(0).getTextContent());
                    date.put("buttonFilter", element.getElementsByTagName("buttonFilter").item(0).getTextContent());
                    date.put("buttonSeacrh", element.getElementsByTagName("buttonSeacrh").item(0).getTextContent());
                    date.put("buttonAdaugare", element.getElementsByTagName("buttonAdaugare").item(0).getTextContent());
                    date.put("buttonStergere", element.getElementsByTagName("buttonStergere").item(0).getTextContent());
                    date.put("buttonActualizare", element.getElementsByTagName("buttonActualizare").item(0).getTextContent());
                    date.put("buttonSalvare", element.getElementsByTagName("buttonSalvare").item(0).getTextContent());
                    date.put("buttonShow", element.getElementsByTagName("buttonShow").item(0).getTextContent());
                    date.put("buttonStatistici", element.getElementsByTagName("buttonStatistici").item(0).getTextContent());
                    date.put("firstName", element.getElementsByTagName("firstName").item(0).getTextContent());
                    date.put("lastName", element.getElementsByTagName("lastName").item(0).getTextContent());
                    date.put("checkAdmin", element.getElementsByTagName("checkAdmin").item(0).getTextContent());
                    date.put("checkEmployee", element.getElementsByTagName("checkEmployee").item(0).getTextContent());
                    date.put("labelBest", element.getElementsByTagName("labelBest").item(0).getTextContent());
                    date.put("labelUser", element.getElementsByTagName("labelUser").item(0).getTextContent());
                    date.put("usernameLabel1", element.getElementsByTagName("usernameLabel1").item(0).getTextContent());
                    date.put("passwordLabel1", element.getElementsByTagName("passwordLabel1").item(0).getTextContent());
                    date.put("tableFirst", element.getElementsByTagName("tableFirst").item(0).getTextContent());
                    date.put("tableNume", element.getElementsByTagName("tableNume").item(0).getTextContent());
                    date.put("tableParola", element.getElementsByTagName("tableParola").item(0).getTextContent());
                    date.put("tableRol", element.getElementsByTagName("tableRol").item(0).getTextContent());
                    date.put("tableUsername", element.getElementsByTagName("tableUsername").item(0).getTextContent());
                    date.put("buttonSelect", element.getElementsByTagName("buttonSelect").item(0).getTextContent());
                    date.put("labelMovies", element.getElementsByTagName("labelMovies").item(0).getTextContent());
                    date.put("labelProduced", element.getElementsByTagName("labelProduced").item(0).getTextContent());
                    date.put("nbMovies", element.getElementsByTagName("nbMovies").item(0).getTextContent());
                    date.put("movieType", element.getElementsByTagName("movieType").item(0).getTextContent());
                    date.put("movieCategory", element.getElementsByTagName("movieCategory").item(0).getTextContent());
                    date.put("labelType", element.getElementsByTagName("labelType").item(0).getTextContent());
                    date.put("notExist", element.getElementsByTagName("notExist").item(0).getTextContent());
                    date.put("selectCri", element.getElementsByTagName("selectCri").item(0).getTextContent());
                    date.put("nothingUpdate", element.getElementsByTagName("nothingUpdate").item(0).getTextContent());
                    date.put("notUsername", element.getElementsByTagName("notUsername").item(0).getTextContent());
                    date.put("selectUser", element.getElementsByTagName("selectUser").item(0).getTextContent());
                    date.put("selectType", element.getElementsByTagName("selectType").item(0).getTextContent());
                    date.put("completeAll", element.getElementsByTagName("completeAll").item(0).getTextContent());
                    date.put("userExist", element.getElementsByTagName("userExist").item(0).getTextContent());
                    date.put("deleteUser", element.getElementsByTagName("deleteUser").item(0).getTextContent());
                    date.put("selectMovie", element.getElementsByTagName("selectMovie").item(0).getTextContent());
                    date.put("invalidYear", element.getElementsByTagName("invalidYear").item(0).getTextContent());
                    date.put("movieExist", element.getElementsByTagName("movieExist").item(0).getTextContent());
                    date.put("selectTypeM", element.getElementsByTagName("selectTypeM").item(0).getTextContent());
                    date.put("typeCant", element.getElementsByTagName("typeCant").item(0).getTextContent());
                    date.put("titleCant", element.getElementsByTagName("titleCant").item(0).getTextContent());
                    date.put("nothingMovie", element.getElementsByTagName("nothingMovie").item(0).getTextContent());
                    date.put("insertTitle", element.getElementsByTagName("insertTitle").item(0).getTextContent());
                    date.put("chooseOp", element.getElementsByTagName("chooseOp").item(0).getTextContent());
                    date.put("page", element.getElementsByTagName("page").item(0).getTextContent());
                    date.put("radial", element.getElementsByTagName("radial").item(0).getTextContent());
                    date.put("ring", element.getElementsByTagName("ring").item(0).getTextContent());
                    date.put("column", element.getElementsByTagName("column").item(0).getTextContent());
                    date.put("enterUsername", element.getElementsByTagName("enterUsername").item(0).getTextContent());
                    date.put("enterPassword", element.getElementsByTagName("enterPassword").item(0).getTextContent());
                    date.put("tryAgain", element.getElementsByTagName("tryAgain").item(0).getTextContent());


                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return date;
    }
}

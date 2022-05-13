package Model;

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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserPersistance {

    private static  String FILENAME = "/Users/popruxi/Desktop/an3sem2/MVP-project/staff.xml";
    private List<User> usersList = new ArrayList<User>();

    public List<User> getUsersList() {
        readUser(); return usersList;
    }

    public void readUser() {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FILENAME));
            Element root = doc.getDocumentElement();
            NodeList list = doc.getElementsByTagName("user");
            for (int i = 0; i < list.getLength(); i++) {
                Element element = (Element) list.item(i);
                String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                String username = element.getElementsByTagName("username").item(0).getTextContent();
                String password = element.getElementsByTagName("password").item(0).getTextContent();
                String role = element.getElementsByTagName("role").item(0).getTextContent();
                User user = new User(firstName, lastName, username, password, role);
                this.usersList.add(user);
            }


        } catch (Exception e) {
            this.usersList = null;
        }
    }

    public boolean deleteUser(String username) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(FILENAME));
            Element root = document.getDocumentElement();

            NodeList allUserNodes = root.getElementsByTagName("user");
            for (int i = 0; i < allUserNodes.getLength(); i++) {
                Element element = (Element) allUserNodes.item(i);
                if (element.getElementsByTagName("username").item(0).getTextContent().equals(username)) {
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

    public boolean addUser(User user) {


        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(FILENAME));
            Element root = document.getDocumentElement();
            Text lb = document.createTextNode("\n");
            Text lb1 = document.createTextNode("\n");
            Text lb2 = document.createTextNode("\n");
            Text lb3 = document.createTextNode("\n");
            Text lb4 = document.createTextNode("\n");
            Text lb5 = document.createTextNode("\n");
            Text lb6 = document.createTextNode("\n");
            Element newUser = document.createElement("user");

            newUser.appendChild(lb2);
            Element firstName = document.createElement("firstName");

            firstName.appendChild(document.createTextNode(user.getFirstName()));
            newUser.appendChild(firstName);
            newUser.appendChild(lb);

            Element lastName = document.createElement("lastName");
            lastName.appendChild(document.createTextNode(user.getLastName()));
            newUser.appendChild(lastName);
            newUser.appendChild(lb1);


            Element username = document.createElement("username");
            username.appendChild(document.createTextNode(user.getUsername()));

            newUser.appendChild(username);
            newUser.appendChild(lb3);


            Element password = document.createElement("password");
            password.appendChild(document.createTextNode(user.getPassword()));

            newUser.appendChild(password);

            newUser.appendChild(lb4);
            Element role = document.createElement("role");
            role.appendChild(document.createTextNode(user.getRole()));

            newUser.appendChild(role);
            newUser.appendChild(lb5);

            root.appendChild(newUser);
            root.appendChild(lb6);

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

    public boolean updateUser(User user, String username) {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(FILENAME));
            Element root = document.getDocumentElement();

            NodeList allUserNodes = root.getElementsByTagName("user");
            for (int i = 0; i < allUserNodes.getLength(); i++) {
                Element element = (Element) allUserNodes.item(i);
                if (element.getElementsByTagName("username").item(0).getTextContent().equals(username)) {
                    if (!user.getFirstName().isEmpty()) {
                        element.getElementsByTagName("firstName").item(0).setTextContent(user.getFirstName());
                    }
                    if (!user.getLastName().isEmpty()) {
                        element.getElementsByTagName("lastName").item(0).setTextContent(user.getLastName());
                    }
                    if (!user.getUsername().isEmpty()) {
                        element.getElementsByTagName("username").item(0).setTextContent(user.getUsername());
                    }
                    if (!user.getPassword().isEmpty()) {
                        element.getElementsByTagName("password").item(0).setTextContent(user.getPassword());
                    }
                    if (!user.getRole().isEmpty()) {
                        System.out.println("intra2");
                        element.getElementsByTagName("role").item(0).setTextContent(user.getRole());
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
    public boolean existUser(User user){
        boolean exist= this.getUsersList().stream().filter(o -> o.getUsername().equals(user.getUsername())).findFirst().isPresent();
        if (exist){
            return true;
        }
        return false;
    }


}
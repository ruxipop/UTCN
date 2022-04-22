package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import model.Client;
import model.OrderW;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.NoSuchElementException;

/**
 * Clasa care implementeaza Presentation  Layer.
 * Aici se face legatura dintre utilizator si interfata.
 */
public class Controller {
    private ClientGUI clientGUI;
    private ClientBLL client;
    private ProductGUI productGUI;
    private ProductBLL product;
    private OrderGUI orderGUI;
    private OrderBLL order;

    public Controller() {
        client = new ClientBLL();
        clientGUI = new ClientGUI(this);
        product = new ProductBLL();
        productGUI = new ProductGUI(this);
        order = new OrderBLL();
        orderGUI = new OrderGUI(this);
        clientGUI.addLinstener(new AddListener());
        clientGUI.removeLinstener(new RemoveListener());
        clientGUI.editLinstener(new EditListener());
        productGUI.addLinstener(new AddPListener());
        productGUI.removePLinstener(new RemovePListener());
        productGUI.editLinstener(new EditPListener());
        orderGUI.addOLinstener(new AddOrderListener());
        actualizareC();
        actualizareP();
        actualizareO();
        View mainGUI = new View(clientGUI, productGUI, orderGUI);
        mainGUI.setVisible(true);


    }

    /**
     * Metoda pentru adaugare unui client.
     * Atributele clientului sunt preluate din interfata clientGUI.
     * Se verifica daca sunt completate toate field-urile din interfata.
     * Iar daca mai exista un client cu acelasi id se va returna o eroare.
     */
    class AddListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            boolean err = false;
            boolean err1 = false;
            if (clientGUI.getID() == -1 || clientGUI.getAge() == -1 || clientGUI.getName() == null || clientGUI.getAddress() == null) {
                err1 = true;

            }
            if (!err1) {
                if (client.getClients() != null) {
                    for (Client c : client.getClients()) {
                        if (c.getIdClient() == clientGUI.getID())
                            err = true;

                    }
                }
                if (!err) {

                    client.insertClient(new Client(clientGUI.getID(), clientGUI.getName(), clientGUI.getAddress(), clientGUI.getAge()));

                    actualizareC();

                } else
                    JOptionPane.showMessageDialog(null, "Mai exista un client cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Trebuie completate toate campurile", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * Metoda pentru stergerea unui client.
     * Id-ului clientului care urmeaza sa fie sters este preluat din interfata ClientGUI.
     * Se verifica daca exista un client cu acest id.
     * Iar daca  nu exista se va returna o eroare.
     */
    class RemoveListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Client c = client.findClient(clientGUI.getID());

                client.deleteClient(c);
                actualizareC();

            } catch (NoSuchElementException ex) {
                JOptionPane.showMessageDialog(null, "Nu exista client cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    /**
     * Metoda pentru editarea anumitor campuri ale unui client.
     * Atributele clientului sunt preluate din interfata clientGUI.
     * Se verifica daca sunt completate toate field-urile din interfata.
     * Daca unele field-uri sunt goale,campul respectiv va pastra valoarea anterioara.
     * Se verifica daca exista un client cu acest id ,iar daca  nu exista se va returna o eroare.
     */

    class EditListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            int age = clientGUI.getAge();
            String name = clientGUI.getName();
            String address = clientGUI.getAddress();
            try {
                Client c = client.findClient(clientGUI.getID());
                if (clientGUI.getAddress() == null) {
                    address = c.getAddress();
                }
                if (clientGUI.getName() == null) {
                    name = c.getName();
                }
                if (clientGUI.getAge() == -1) {
                    age = c.getAge();
                }


                client.updateClient(clientGUI.getID(), name, address, age);
                actualizareC();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nu exista client cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    /**
     * Metoda pentru stergerea unui produs.
     * Id-ului produsului care urmeaza sa fie sters este preluat din interfata ProductGUI.
     * Se verifica daca exista un client cu acest id.
     * Iar daca  nu exista se va returna o eroare.
     */
    class RemovePListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Product p = product.findProduct(productGUI.getID());
                product.deleteProduct(p);
                actualizareP();
            } catch (NoSuchElementException ex) {
                JOptionPane.showMessageDialog(null, "Nu exista produs cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }


    /**
     * Metoda pentru adaugare unui produs.
     * Atributele produsului sunt preluate din interfata productGUI.
     * Se verifica daca sunt completate toate field-urile din interfata ,daca nu se va returna o eroare.
     * Iar daca mai exista un client cu acelasi id se va returna o eroare.
     */
    class AddPListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            boolean err = false;
            boolean err1 = false;
            if (productGUI.getQuantity() == 0 || productGUI.getPrice() == -1 || productGUI.getID() == -1 || productGUI.getName() == null)
                err1 = true;

            if (!err1) {
                if (product.getProducts() != null) {
                    for (Product p : product.getProducts()) {
                        if (p.getIdProduct() == productGUI.getID())
                            err = true;

                    }
                }
                if (!err) {
                    product.insertProduct(new Product(productGUI.getID(), productGUI.getQuantity(), productGUI.getName(), productGUI.getPrice()));

                    actualizareP();

                } else
                    JOptionPane.showMessageDialog(null, "Mai exista un produs cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "Trebuie completate toate campurile", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Metoda pentru editarea anumitor campuri ale unui produs.
     * Atributele produsului sunt preluate din interfata productGUI.
     * Se verifica daca sunt completate toate field-urile din interfata.
     * Daca unele field-uri sunt goale,campul respectiv va pastra valoarea anterioara.
     * Se verifica daca exista un produs cu acest id ,iar daca  nu exista se va returna o eroare.
     */
    class EditPListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            String name;
            double price;
            int quantity;
            try {
                Product p = product.findProduct(productGUI.getID());
                if (productGUI.getName() == null) {
                    name = p.getNameProduct();
                } else name = productGUI.getName();
                if (productGUI.getPrice() == -1) {
                    price = p.getPrice();
                } else price = productGUI.getPrice();
                if (productGUI.getQuantity() == -1) {
                    quantity = p.getQuantity();
                } else quantity = productGUI.getQuantity();
                product.updateProduct(productGUI.getID(), quantity, name, price);
                actualizareP();
            } catch (NoSuchElementException ex) {
                JOptionPane.showMessageDialog(null, "Nu exista produs cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }

    /**
     * Actualizarea tabelului corespunzator modelului Client.
     */
    public void actualizareC() {


        try {
            clientGUI.setTableModel(client.clientTable(client.getClients()));
        } catch (
                IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "SOMETHING IS WRONG!!!!");
        }

    }

    /**
     * Actualizarea tabelului corespunzator modelului Product.
     */
    public void actualizareP() {

        try {

            productGUI.setTableModel(product.productTable((product.getProducts())));

        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "SOMETHING IS WRONG!!!!");

        }
    }

    /**
     * Actualizarea tabelului corespunzator modelului OrderW.
     */
    public void actualizareO() {
        try {
            orderGUI.setTableModel(order.orderTable(order.getOrders()));
        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "SOMETHING IS WRONG!!!!");

        }
    }

    /**
     * Metoda pentru adaugare unui comenzi.
     * Atributele comenzi sunt preluate din interfata orderGUI.
     * Se verifica daca mai exista o comanda  cu acest id,daca da se va returna o eroare.
     * Se verifica daca exista un client cu acest id,daca nu se va returna o eroare.
     * Se verifica daca exista un produs cu acest id,daca nu se va returna o eroare.
     * Se verifica daca exista stoc sufiecient ,daca nu se va returna o eroare.
     */

    class AddOrderListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            Product p;
            Client c;

            boolean err = false;
           if(order.getOrders()!=null) {
               for (OrderW r : order.getOrders()) {
                   if (r.getIdOrder() == orderGUI.getIdOrder()) {
                       err = true;
                   }
               }
           }
            if (!err) {
                try {
                    c = client.findClient(orderGUI.getIdClient());
                } catch (NoSuchElementException ex1) {
                    JOptionPane.showMessageDialog(null, "Nu exista  client cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                try {
                    p = product.findProduct(orderGUI.getIdProduct());

                    if (p.getQuantity() < orderGUI.getQuantity()) {
                        JOptionPane.showMessageDialog(null, "Stoc insuficient.", "", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    } else {
                        double price = p.getPrice() * orderGUI.getQuantity();
                        OrderW o = new OrderW(orderGUI.getIdOrder(), orderGUI.getIdProduct(), orderGUI.getIdClient(), orderGUI.getQuantity(), price);

                        order.insertOrder(o);
                        product.updateQuantity(p.getIdProduct(), p.getQuantity() - orderGUI.getQuantity());
                        for (Product pr : product.getProducts()) {
                            if (pr.getQuantity() == 0)
                                product.deleteProduct(pr);
                        }


                        actualizareO();
                        actualizareP();
                        printBill(o);

                    }
                } catch (NoSuchElementException ex) {
                    JOptionPane.showMessageDialog(null, "Nu exista  produs cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
                    return;

                }


            } else {
                JOptionPane.showMessageDialog(null, "Exista deja o comanda cu acest ID!!!", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }
    /**
     * Metoda care creaza un document de tip PDF
     * @param order reprezinta comanda pentru care se va face factura
     */
    public void printBill(OrderW order) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("order" + order.getIdOrder() + ".pdf"));

        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("ORDER BILL:  " + order.getIdOrder(), font);
        try {
            Product p = product.findProduct(orderGUI.getIdProduct());
            Client c = client.findClient(orderGUI.getIdClient());
            document.setPageCount(1);
            document.add(chunk);
            document.add(new Paragraph("Client (" + c.getIdClient() + ") : " + c.getName()));
            document.add(new Paragraph("         Adresa: " + c.getAddress()));
            document.add(new Paragraph("         Varsta: " + c.getAge()));
            document.add(new Paragraph(" Produs (" + p.getIdProduct() + ") :"));
            document.add(new Paragraph("            " + p.getNameProduct() + "  " + order.getQuantity() + "  buc.  " + order.getTotal() + "  lei "));
            document.addAuthor("Pop Ruxandra");
            document.addCreator("Pop Ruxandra");

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.getMessage();
        }

        document.close();

    }
}

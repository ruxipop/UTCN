package presentationLayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListPanel extends JPanel implements ActionListener {
    private JScrollPane scrollPanel;

    private JPanel btnPanel;
    private JButton btnAll;
    private JButton btnMain;
    private JButton headImport;
    private JButton btnDrink;
    private Controller controller;
    private JTable itemsTable;

    public MenuListPanel(Controller controller) {

        this.controller = controller;
        itemsTable = new JTable(0, 0);
        this.setLayout(new BorderLayout());

        scrollPanel = new JScrollPane(itemsTable);
        scrollPanel.setPreferredSize(new Dimension(200, 400));
        add(scrollPanel, BorderLayout.CENTER);

        btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnAll = new JButton("All");
        btnAll.addActionListener(this);
        btnMain = new JButton("Base products");
        btnMain.addActionListener(this);
        btnDrink = new JButton("Composite products");
        btnDrink.addActionListener(this);
        headImport = new JButton("Import products");
        headImport.setVisible(false);
        headImport.addActionListener(this);

        btnPanel.add(btnAll);
        btnPanel.add(btnMain);
        btnPanel.add(btnDrink);
        btnPanel.add(headImport);
        itemsTable.setModel(controller.setMenu(3));
        add(btnPanel, BorderLayout.SOUTH);
    }


    public void setButtonV() {
        headImport.setVisible(true);
    }

    public void setButtonI() {
        headImport.setVisible(false);
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnAll) {

            itemsTable.setModel(controller.setMenu(0));


        } else if (ae.getSource() == btnMain) {
            itemsTable.setModel(controller.setMenu(2));

        } else if (ae.getSource() == btnDrink) {
            itemsTable.setModel(controller.setMenu(1));

        } else if (ae.getSource() == headImport) {
            controller.createALLProducts();

        }

    }

}
package presentationLayer;

import businessLayer.MenuItem;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SearchMenu extends JPanel implements ActionListener {

    private JTable menuTable;

    private JLabel lblRightTitle;

    private JScrollPane menuScrollPanel;
    private JLabel createItemNameLabel2;

    private JLabel createItemPriceLabel2;
    private JLabel createItemFatLabel2;
    private JTextField nameTextField2;

    private JLabel createItemRatingLabel2;
    private JTextField createTextRating2;
    private JTextField priceTextField2;
    private JLabel createItemCaloriesLabel2;
    private JTextField createTextCalories2;

    private JLabel createItemProteinLabel2;
    private JTextField createTextProtein2;
    private JLabel createItemFatLabe2l;
    private JTextField createTextFat2;
    private JLabel createItemSodiumLabel2;
    private JTextField createTextSodium2;


    private JTable menuItems;

    private JButton btnSearchItem;


    private JList itemList;


    private JPanel searchPanel;
    private JPanel menuListPanel;
    private Controller controller;

    public SearchMenu(Controller controller) {
        this.controller = controller;
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));


        searchPanel = new JPanel();


        GridBagLayout gbLayout = new GridBagLayout();
        searchPanel.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);


        createItemNameLabel2 = new JLabel("Title:");
        createItemNameLabel2.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemNameLabel2, gbc);
        searchPanel.add(createItemNameLabel2);

        nameTextField2 = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbLayout.setConstraints(nameTextField2, gbc);
        searchPanel.add(nameTextField2);
        createItemRatingLabel2 = new JLabel("Rating");
        createItemNameLabel2.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemRatingLabel2, gbc);
        searchPanel.add(createItemRatingLabel2);
        createTextRating2 = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 2;

        gbLayout.setConstraints(createTextRating2, gbc);
        searchPanel.add(createTextRating2);


        createItemCaloriesLabel2 = new JLabel("Calories");
        createItemCaloriesLabel2.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemCaloriesLabel2, gbc);
        searchPanel.add(createItemCaloriesLabel2);
        createTextCalories2 = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 3;

        gbLayout.setConstraints(createTextCalories2, gbc);
        searchPanel.add(createTextCalories2);

        createItemProteinLabel2 = new JLabel("Protein");
        createItemProteinLabel2.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemProteinLabel2, gbc);
        searchPanel.add(createItemProteinLabel2);
        createTextProtein2 = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 4;

        gbLayout.setConstraints(createTextProtein2, gbc);
        searchPanel.add(createTextProtein2);


        createItemFatLabel2 = new JLabel("Fat");
        createItemProteinLabel2.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemFatLabel2, gbc);
        searchPanel.add(createItemFatLabel2);
        createTextFat2 = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 5;

        gbLayout.setConstraints(createTextFat2, gbc);
        searchPanel.add(createTextFat2);


        createItemSodiumLabel2 = new JLabel("Sodium");
        createItemCaloriesLabel2.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemSodiumLabel2, gbc);
        searchPanel.add(createItemSodiumLabel2);
        createTextSodium2 = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 6;

        gbLayout.setConstraints(createTextSodium2, gbc);
        searchPanel.add(createTextSodium2);


        createItemPriceLabel2 = new JLabel("Price:");
        createItemPriceLabel2.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemPriceLabel2, gbc);
        searchPanel.add(createItemPriceLabel2);

        priceTextField2 = new JTextField(7);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbLayout.setConstraints(priceTextField2, gbc);
        searchPanel.add(priceTextField2);

        btnSearchItem = new JButton("Search");
        btnSearchItem.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbLayout.setConstraints(btnSearchItem, gbc);
        searchPanel.add(btnSearchItem);

        //Right panel
        menuListPanel = new JPanel();

        menuListPanel.setLayout(gbLayout);
//
        menuItems = new JTable(0, 0);

        lblRightTitle = new JLabel("Menu list");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipadx = 0;
        gbc.gridwidth = 5;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbLayout.setConstraints(lblRightTitle, gbc);
        menuListPanel.add(lblRightTitle);

        menuTable = new JTable(0, 0);

        menuScrollPanel = new JScrollPane(menuTable);

        menuScrollPanel.setPreferredSize(new Dimension(400, 400));
        gbc.gridy = 1;
        gbc.weighty = 1.0;

        gbLayout.setConstraints(menuScrollPanel, gbc);
        menuListPanel.add(menuScrollPanel);


        LineBorder border = new LineBorder(Color.BLACK, 1, false);
        menuListPanel.setBorder(border);
        searchPanel.setBorder(border);
        this.add(searchPanel);
        this.add(menuListPanel);

    }

    public void showErrorDialog(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }


    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == btnSearchItem) {
            String title = (nameTextField2.getText().isEmpty()) ? " " : nameTextField2.getText();
            float rating = (createTextRating2.getText().isEmpty()) ? -1 : Float.parseFloat(createTextRating2.getText());
            float calories = (createTextCalories2.getText().isEmpty()) ? -1 : Float.parseFloat(createTextCalories2.getText());
            float protein = (createTextProtein2.getText().isEmpty()) ? -1 : Float.parseFloat(createTextProtein2.getText());
            float fat = (createTextFat2.getText().isEmpty()) ? -1 : Float.parseFloat(createTextFat2.getText());
            float sodium = (createTextSodium2.getText().isEmpty()) ? -1 : Float.parseFloat(createTextSodium2.getText());
            float price = (priceTextField2.getText().isEmpty()) ? -1 : Float.parseFloat(priceTextField2.getText());
            if (title == " " && rating == -1 && calories == -1 && protein == -1 && fat == -1 && sodium == -1 && price == -1) {
                showErrorDialog("Error", "Enter a criteria");

            } else {
                List<MenuItem> list = new ArrayList<MenuItem>(controller.getRestaurant().getMenuItems().values());
                java.util.List<MenuItem> m = controller.findItemAll(list, rating, title, calories, protein, fat, price, sodium);

                if (!m.isEmpty()) {
                    menuTable.setModel(controller.setMenuSearch(m));

                } else {
                    showErrorDialog("Error", "No products with this criteria");
                }

            }
        }
    }


}


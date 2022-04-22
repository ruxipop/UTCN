package presentationLayer;

import businessLayer.BaseProduct;
import businessLayer.CompositeProduct;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuManagementPanel extends JPanel implements ActionListener {
    private JRadioButton baseProductCheckBox;
    private JRadioButton compositeProductCheckBox;
    private ButtonGroup group;
    private JLabel createItemNameLabel;
    private JLabel createItemNameLabel1;
    private JLabel createItemPriceLabel1;
    private JLabel createItemPriceLabel;
    private JTextField nameTextField;
    private JTextField nameTextField1;
    private JLabel createItemRatingLabel;
    private JTextField createTextRating;

    private JLabel createItemCaloriesLabel;
    private JTextField createTextCalories;

    private JLabel createItemProteinLabel;
    private JTextField createTextProtein;
    private JLabel createItemFatLabel;
    private JTextField createTextFat;
    private JLabel createItemSodiumLabel;
    private JTextField createTextSodium;


    private JTextField priceTextField;
    private JTextField priceTextField1;
    private JComboBox addItemComponents;
    private JComboBox component;
    private JComboBox deleteItemComponent;
    private JComboBox selectItem;
    private JComboBox selectComponent;
    private JButton btnAdd;
    private JButton btnAddItem;
    private JButton btnEditItem;
    private JButton btnRemoveItem;
    private JButton btnRemoveItemComponent;
    private Controller controller;

    public MenuManagementPanel(Controller controller) {
        this.controller = controller;
        JPanel CreateItem = new JPanel(new BorderLayout());

        JPanel AddItem = new JPanel(new BorderLayout());
        JPanel EditItem = new JPanel(new BorderLayout());
        GridBagLayout gbLayout = new GridBagLayout();
        CreateItem.setLayout(gbLayout);
        AddItem.setLayout(gbLayout);
        EditItem.setLayout(gbLayout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        baseProductCheckBox = new JRadioButton("Base");
        compositeProductCheckBox = new JRadioButton("Composite");
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        CreateItem.add(baseProductCheckBox, gbc);
        gbc.gridx = 4;
        gbc.gridy = 0;
        CreateItem.add(compositeProductCheckBox, gbc);
        group = new ButtonGroup();
        group.add(baseProductCheckBox);
        group.add(compositeProductCheckBox);
        baseProductCheckBox.addActionListener(this);
        compositeProductCheckBox.addActionListener(this);
        createItemNameLabel = new JLabel("Title:");
        createItemNameLabel.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemNameLabel, gbc);
        CreateItem.add(createItemNameLabel);
        nameTextField = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbLayout.setConstraints(nameTextField, gbc);
        CreateItem.add(nameTextField);
        createItemRatingLabel = new JLabel("Rating");
        createItemNameLabel.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemRatingLabel, gbc);
        CreateItem.add(createItemRatingLabel);
        createTextRating = new JTextField(7);
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbLayout.setConstraints(createTextRating, gbc);
        CreateItem.add(createTextRating);
        createItemCaloriesLabel = new JLabel("Calories");
        createItemCaloriesLabel.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemCaloriesLabel, gbc);
        CreateItem.add(createItemCaloriesLabel);
        createTextCalories = new JTextField(7);
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbLayout.setConstraints(createTextCalories, gbc);
        CreateItem.add(createTextCalories);
        createItemProteinLabel = new JLabel("Protein");
        createItemProteinLabel.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemProteinLabel, gbc);
        CreateItem.add(createItemProteinLabel);
        createTextProtein = new JTextField(7);
        gbc.gridx = 6;
        gbc.gridy = 1;
        gbLayout.setConstraints(createTextProtein, gbc);
        CreateItem.add(createTextProtein);
        createItemFatLabel = new JLabel("Fat");
        createItemProteinLabel.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemFatLabel, gbc);
        CreateItem.add(createItemFatLabel);
        createTextFat = new JTextField(7);
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbLayout.setConstraints(createTextFat, gbc);
        CreateItem.add(createTextFat);
        createItemSodiumLabel = new JLabel("Sodium");
        createItemCaloriesLabel.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemSodiumLabel, gbc);
        CreateItem.add(createItemSodiumLabel);
        createTextSodium = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbLayout.setConstraints(createTextSodium, gbc);
        CreateItem.add(createTextSodium);
        createItemPriceLabel = new JLabel("Price:");
        createItemPriceLabel.setPreferredSize(new Dimension(80, 30));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbLayout.setConstraints(createItemPriceLabel, gbc);
        CreateItem.add(createItemPriceLabel);
        priceTextField = new JTextField(7);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbLayout.setConstraints(priceTextField, gbc);
        CreateItem.add(priceTextField);
        btnAdd = new JButton("Create");
        gbc.gridx = 6;
        gbc.gridy = 3;
        CreateItem.add(btnAdd, gbc);
        btnAdd.addActionListener(this);
        this.add(CreateItem);
        addItemComponents = new JComboBox();
        component = new JComboBox();
        addItemComponents.setPreferredSize(new Dimension(200, 20));
        gbLayout.setConstraints(addItemComponents, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        AddItem.add(addItemComponents, gbc);
        addItemComponents.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 0;
        component.setPreferredSize(new Dimension(200, 20));
        component.addActionListener(this);
        gbLayout.setConstraints(component, gbc);
        AddItem.add(component);
        btnAddItem = new JButton("Add item to component");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbLayout.setConstraints(btnAddItem, gbc);
        AddItem.add(btnAddItem);
        btnAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compositeName = (String) component.getSelectedItem();
                String itemName = (String) addItemComponents.getSelectedItem();
                controller.getRestaurant().addItemToComponent(compositeName, itemName);

                fill4();
            }
        });
        this.add(AddItem);
        gbc.gridx = 9;
        gbc.gridy = 0;
        selectItem = new JComboBox();
        selectItem.setPreferredSize(new Dimension(200, 20));
        EditItem.add(selectItem);
        gbc.gridx = 0;
        gbc.gridy = 1;
        createItemNameLabel1 = new JLabel("New Name");
        EditItem.add(createItemNameLabel1, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        nameTextField1 = new JTextField(10);
        gbLayout.setConstraints(nameTextField1, gbc);
        EditItem.add(nameTextField1);
        gbc.gridx = 0;
        gbc.gridy = 2;
        createItemPriceLabel1 = new JLabel("New Price");
        gbLayout.setConstraints(createItemPriceLabel1, gbc);
        EditItem.add(createItemPriceLabel1);
        gbc.gridx = 1;
        gbc.gridy = 2;
        priceTextField1 = new JTextField(10);
        gbLayout.setConstraints(priceTextField1, gbc);
        EditItem.add(priceTextField1);
        selectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compositeName = (String) selectItem.getSelectedItem();

                if (compositeName != null) {
                    businessLayer.MenuItem item = controller.getRestaurant().getMenuItems().get(compositeName);
                    if (item instanceof CompositeProduct) {

                        priceTextField1.setVisible(false);
                        createItemPriceLabel1.setVisible(false);
                        nameTextField1.setText(item.getName());
                    }
                    if (item instanceof BaseProduct) {
                        nameTextField1.setVisible(false);
                        createItemNameLabel1.setVisible(false);
                        priceTextField1.setText(String.valueOf(item.computePrice()));
                    }

                }
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        btnEditItem = new JButton("Edit item");
        gbLayout.setConstraints(btnEditItem, gbc);
        EditItem.add(btnEditItem);
        btnEditItem.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 3;
        btnRemoveItem = new JButton("Delete item");
        gbLayout.setConstraints(btnRemoveItem, gbc);
        EditItem.add(btnRemoveItem);
        btnRemoveItem.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 4;
        selectComponent = new JComboBox();
        selectComponent.setPreferredSize(new Dimension(200, 20));
        gbLayout.setConstraints(selectComponent, gbc);
        EditItem.add(selectComponent);
        gbc.gridx = 1;
        gbc.gridy = 4;
        deleteItemComponent = new JComboBox();
        deleteItemComponent.setPreferredSize(new Dimension(200, 20));
        gbLayout.setConstraints(deleteItemComponent, gbc);
        EditItem.add(deleteItemComponent);
        gbc.gridx = 2;
        gbc.gridy = 4;
        btnRemoveItemComponent = new JButton("Delete item from component");
        gbLayout.setConstraints(btnRemoveItemComponent, gbc);
        EditItem.add(btnRemoveItemComponent);
        btnRemoveItemComponent.addActionListener(this);
        this.add(EditItem);
        nameTextField.setVisible(false);
        createItemNameLabel.setVisible(false);
        setInvi();
        selectComponent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String compositeName = (String) selectComponent.getSelectedItem();
                businessLayer.MenuItem item = controller.getRestaurant().getMenuItems().get(compositeName);
                if (item instanceof CompositeProduct)
                    fill3(deleteItemComponent, compositeName);
            }

        });
    }

    public void setInvi() {

        priceTextField.setVisible(false);
        createItemPriceLabel.setVisible(false);
        createTextCalories.setVisible(false);
        createItemCaloriesLabel.setVisible(false);
        createTextFat.setVisible(false);
        createItemFatLabel.setVisible(false);
        createTextRating.setVisible(false);
        createItemRatingLabel.setVisible(false);
        createTextProtein.setVisible(false);
        createItemProteinLabel.setVisible(false);
        createTextSodium.setVisible(false);
        createItemSodiumLabel.setVisible(false);
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == baseProductCheckBox) {
            nameTextField.setVisible(true);
            createItemNameLabel.setVisible(true);
            priceTextField.setVisible(true);
            createItemPriceLabel.setVisible(true);
            createTextCalories.setVisible(true);
            createItemCaloriesLabel.setVisible(true);
            createTextFat.setVisible(true);
            createItemFatLabel.setVisible(true);
            createTextRating.setVisible(true);
            createItemRatingLabel.setVisible(true);
            createTextProtein.setVisible(true);
            createItemProteinLabel.setVisible(true);
            createTextSodium.setVisible(true);
            createItemSodiumLabel.setVisible(true);
        } else if (ae.getSource() == compositeProductCheckBox) {
            nameTextField.setVisible(true);
            createItemNameLabel.setVisible(true);
            setInvi();
        } else if (ae.getSource() == component) {

            String compositeName = (String) component.getSelectedItem();
            fillError(addItemComponents, compositeName);
        } else if (ae.getSource() == btnAdd) {
            String itemName = nameTextField.getText();
            if (itemName != null) {
                if (baseProductCheckBox.isSelected()) {
                    float rating = Float.parseFloat(createTextRating.getText());
                    float calories = Float.parseFloat(createTextCalories.getText());
                    float protein = Float.parseFloat(createTextProtein.getText());
                    float fat = Float.parseFloat(createTextFat.getText());
                    float sodium = Float.parseFloat(createTextSodium.getText());
                    float price = Float.parseFloat(priceTextField.getText());
                    BaseProduct item = new BaseProduct(itemName, rating, calories, protein, fat, sodium, price);
                    controller.getRestaurant().createMenuItem(item);
                    nameTextField.setText("");
                    createTextSodium.setText("");
                    createTextCalories.setText("");
                    createTextProtein.setText("");
                    createTextFat.setText("");
                    priceTextField.setText("");
                } else {
                    nameTextField.setText("");
                    controller.getRestaurant().createMenuItem(itemName);
                }
                fill4();

            }
        } else if (ae.getSource() == btnEditItem) {
            String itemName = (String) selectItem.getSelectedItem();
            String itemNewName = nameTextField1.getText();
            String priceString = priceTextField1.getText();
            businessLayer.MenuItem item = controller.getRestaurant().getMenuItems().get(itemName);

            if (item instanceof BaseProduct) {
                float price = priceString.equals("") ? 0.0f : Float.parseFloat(priceString);
                controller.getRestaurant().editMenuItem((BaseProduct) item, price);

            }
            if (item instanceof CompositeProduct) {

                controller.getRestaurant().editMenuItem((CompositeProduct) item, itemNewName);
            }
            fill4();

        } else if (ae.getSource() == btnRemoveItem) {
            String removed = (String) selectItem.getSelectedItem();
            controller.getRestaurant().deleteMenuItem(removed);
            fill4();
        } else if (ae.getSource() == btnRemoveItemComponent) {
            String itemName = (String) selectComponent.getSelectedItem();
            String componentName = (String) deleteItemComponent.getSelectedItem();
            controller.getRestaurant().removeItemFromComponent(itemName, componentName);

            fill4();

        }

    }

    private void fill1(JComboBox comboBox) {
        comboBox.removeAllItems();
        for (businessLayer.MenuItem item : controller.getRestaurant().getMenuItems().values()) {
            if (item instanceof CompositeProduct) {
                comboBox.addItem(item.getName());
            }
        }

    }

    private void fill2(JComboBox comboBox) {
        comboBox.removeAllItems();
        for (businessLayer.MenuItem item : controller.getRestaurant().getMenuItems().values()) {
            comboBox.addItem(item.getName());
        }
    }

    void fill4() {
        fill1(selectComponent);
        fill1(component);
        fillError(addItemComponents, (String) component.getSelectedItem());
        if (controller.getRestaurant().getMenuItems().get(selectComponent.getSelectedItem()) instanceof CompositeProduct) {

            fill3(deleteItemComponent, (String) selectComponent.getSelectedItem());
        }
        fill2(selectItem);
    }

    private void fill3(JComboBox comboBox, String compositeName) {
        comboBox.removeAllItems();

        CompositeProduct composite = (CompositeProduct) controller.getRestaurant().getMenuItems().get(compositeName);

        for (BaseProduct item : composite.getItems()) {
            comboBox.addItem(item.getName());
        }
    }

    private void fillError(JComboBox comboBox, String itemName) {
        comboBox.removeAllItems();
        for (businessLayer.MenuItem item : controller.getRestaurant().getMenuItems().values()) {
            if (!item.getName().equals(itemName)) {
                comboBox.addItem(item.getName());
            }
        }
    }

}



package View;

import Model.Movie;


import javax.swing.JTabbedPane;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

import Model.*;

import java.awt.event.ActionListener;

@lombok.Getter
@lombok.Setter
public class UserGUI implements Model.Observer {

    private ModelUser modelUser;
    private Map<String, String> language;
    private JTable showTable;
    public JPanel panel;
    private JScrollPane scrollPane;
    private JTextField category;
    private JTextField year;
    private JTextField title;
    private JCheckBox ArtisticB;
    private JCheckBox SerialB;
    private ButtonGroup checkBoxGroup;
    private JComboBox comboType;
    private JComboBox comboYear;
    private JComboBox comboCategory;
    private JLabel label11 = new JLabel();
    private JLabel label12 = new JLabel();
    private JPanel panelLab1 = new JPanel();
    private JPanel panelLab2 = new JPanel();
    private JLabel label21 = new JLabel();
    private JLabel label22 = new JLabel();
    private JPanel panelLab3 = new JPanel();
    private JLabel label31 = new JLabel();
    private JLabel label32 = new JLabel();
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton showButton;
    private JButton saveButton;
    private JButton filterButton;
    private JButton logOutButton;
    private JButton statisticButton;
    private JLabel titleLabel;
    private JLabel categoryLabel;
    private JLabel yearLabel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JLabel filedMovies;
    private JLabel filedProduced;


    JFrame frame1 = new JFrame("Employee page");
    DefaultTableModel model;

    JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel number1 = new JLabel();

    public UserGUI(ModelUser modelUser) {
        this.modelUser = modelUser;

        language = this.modelUser.getLanguage().getTextLanguage();
        modelUser.add(this);
        fillYear(comboYear);
        fillType(comboType);
        fillCategory(comboCategory);
        checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(ArtisticB);
        checkBoxGroup.add(SerialB);
        panelLab1.add(label11);
        panelLab1.add(label12);
        panelLab2.add(label21);
        panelLab2.add(label22);
        panelLab3.add(label31);
        panelLab3.add(label32);
        button1.setBorder(BorderFactory.createEmptyBorder());
        button2.setBorder(BorderFactory.createEmptyBorder());
        button3.setBorder(BorderFactory.createEmptyBorder());
        frame1.add(tabbedPane);
        frame1.setPreferredSize(new java.awt.Dimension(900, 600));
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();

        modelUser.notify("show");
        changeLanguage();
        frame1.setVisible(true);

    }

    public void roB(ActionListener avl) {
        button1.addActionListener(avl);


    }

    public void enB(ActionListener avl) {
        button2.addActionListener(avl);


    }

    public void spB(ActionListener avl) {
        button3.addActionListener(avl);


    }

    public void reset() {
        title.setText("");
        category.setText("");
        year.setText("");
        checkBoxGroup.clearSelection();
    }

    public String selectie(int index) {

        int row = showTable.getSelectedRow();
        if (row >= 0) {
            String value = showTable.getModel().getValueAt(row, index).toString();
            return value;
        }
        return "";
    }

    public void setTable(List<Movie> list1) {
        String title = modelUser.getLanguage().getTextLanguage().get("titleLabel");
        String category = modelUser.getLanguage().getTextLanguage().get("categoryLabel");
        String year = modelUser.getLanguage().getTextLanguage().get("yearLabel");
        String type = modelUser.getLanguage().getTextLanguage().get("labelType");

        model = new DefaultTableModel(new Object[0][0], new String[]{title, type, category, year});

        java.util.Comparator<Movie> compareByCategory = java.util.Comparator.comparing(Movie::getCategory).thenComparing(Movie::getYear);
        List<Movie> list = list1.stream().sorted(compareByCategory).collect(java.util.stream.Collectors.toList());
        int rowCount = this.model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }

        for (Model.Movie movie : list) {

            Object[] o = new Object[4];

            o[0] = movie.getTitle();
            o[1] = movie.getType();
            o[2] = movie.getCategory();
            o[3] = movie.getYear();

            model.addRow(o);


        }
        showTable.setModel(model);
    }

    private void fillCategory(JComboBox comboBox) {
        removeCombo(comboBox);
        comboBox.addItem(language.get("buttonSelect"));

        for (String s : this.modelUser.getMoviePersistance().typeOfCategory()) {

            comboBox.addItem(s);

        }

    }

    private void fillYear(JComboBox comboBox) {

        List<Integer> in = new ArrayList<>();


        removeCombo(comboBox);
        comboBox.addItem(language.get("buttonSelect"));

        for (Movie movie : this.modelUser.getMoviePersistance().getMoviesList()) {

            in.add(movie.getYear());


        }
        List<Integer> listWithoutDuplicates = in.stream().distinct().collect(java.util.stream.Collectors.toList());

        for (Integer i : listWithoutDuplicates) {

            comboBox.addItem(i);
        }


    }

    private void fillType(JComboBox comboBox) {
        removeCombo(comboBox);
        comboBox.addItem(language.get("buttonSelect"));


        for (String s : this.modelUser.getMoviePersistance().typeOfType()) {

            comboBox.addItem(s);

        }

    }

    public void fillAll() {
        fillType(comboType);
        fillYear(comboYear);
        fillCategory(comboCategory);
    }

    public void removeCombo(JComboBox comboBox) {
        int number = comboBox.getItemCount();
        for (int i = 0; i < number; i++) {

            comboBox.removeItem(comboBox.getItemAt(0));


        }
    }

    public void logOut(ActionListener avl) {
        logOutButton.addActionListener(avl);


    }


    public void deleteMovie(ActionListener avl) {
        deleteButton.addActionListener(avl);

    }

    public void addMovieB(ActionListener avl) {
        addButton.addActionListener(avl);


    }

    public void updateMovieB(ActionListener avl) {
        updateButton.addActionListener(avl);


    }

    public void searchMovie(ActionListener avl) {
        searchButton.addActionListener(avl);

    }

    public void saveReportsB(ActionListener avl) {
        saveButton.addActionListener(avl);

    }


    public void filterMovieB(ActionListener avl) {
        filterButton.addActionListener(avl);

    }


    public void showStatistic(ActionListener avl) {
        statisticButton.addActionListener(avl);

    }


    @Override
    public void update(Object o) {
        String s = (String) o;
        switch (s) {
            case "language":
                changeLanguage();
                break;
            case "show":
                this.setTable(modelUser.getMoviePersistance().getMoviesList());
                this.fillAll();


        }
    }

    public void changeLanguage() {
        language = this.modelUser.getLanguage().getTextLanguage();
        tabbedPane.add(this.modelUser.getLanguage().getTextLanguage().get("page"), panel);
        tabbedPane.add(this.modelUser.getLanguage().getTextLanguage().get("radial"), panelLab2);
        tabbedPane.add(this.modelUser.getLanguage().getTextLanguage().get("ring"), panelLab3);
        tabbedPane.add(this.modelUser.getLanguage().getTextLanguage().get("column"), panelLab1);
        logOutButton.setText(language.get("logOutButton"));
        yearLabel.setText(language.get("yearLabel"));
        titleLabel.setText(language.get("titleLabel"));
        categoryLabel.setText(language.get("categoryLabel"));
        ArtisticB.setText(language.get("checkArtisitc"));
        SerialB.setText(language.get("checkSerial"));
        filterButton.setText(language.get("buttonFilter"));
        searchButton.setText(language.get("buttonSeacrh"));
        addButton.setText(language.get("buttonAdaugare"));
        deleteButton.setText(language.get("buttonStergere"));
        updateButton.setText(language.get("buttonActualizare"));
        saveButton.setText(language.get("buttonSalvare"));
        statisticButton.setText(language.get("buttonStatistici"));
        filedMovies.setText(language.get("labelMovies"));
        filedProduced.setText(language.get("labelProduced"));
    }
}
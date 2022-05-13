package View;

import Model.Movie;
import Presenter.UserP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserGUI implements IUserView, ActionListener {
    private JTable showTable;
    private JTextField category;
    private JTextField year;

    private JTextField title;
    private JRadioButton artisticRadioButton;
    private JRadioButton serialRadioButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    private JButton searchButton;
    private JButton showButton;
    private JButton saveButton;
    private JButton filterButton;
    private JButton logOutButton;
    public JPanel panel;
    private JScrollPane scrollPane;
    private JComboBox comboType;
    private JComboBox comboYear;
    private JComboBox comboCategory;
    ButtonGroup g = new ButtonGroup();

    JFrame frame1 = new JFrame("Employee page");
    DefaultTableModel model;

    public UserGUI() {

        model = new DefaultTableModel(new Object[0][0], new String[]{"Title", "Type", "Category", "Year"});
        g.add(artisticRadioButton);
        g.add(serialRadioButton);
        frame1.add(panel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
        logOutButton.addActionListener(this);
        showButton.addActionListener(this);
        deleteButton.addActionListener(this);
        searchButton.addActionListener(this);
        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        saveButton.addActionListener(this);
        filterButton.addActionListener(this);
        fillCategory(comboCategory);
        fillYear(comboYear);
        fillType(comboType);
    }


    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == logOutButton) {
            logOut(ae);
        }
        if (ae.getSource() == deleteButton) {

            deleteMovie(ae);
        }
        if (ae.getSource() == searchButton) {

            searchMovie(ae);
        }
        if (ae.getSource() == showButton) {

            showMovies(ae);
        }
        if (ae.getSource() == addButton) {

            addMovieB(ae);
        }
        if (ae.getSource() == updateButton) {

            updateMovieB(ae);
        }
        if (ae.getSource() == saveButton) {

            saveReportsB(ae);
        }
        if (ae.getSource() == filterButton) {

            filterMovieB(ae);
        }

    }

    public void logOut(ActionEvent actionEvent) {
        frame1.setVisible(false);
        new LoginGUI();

    }


    public void deleteMovie(ActionEvent actionEvent) {
        UserP userP = new UserP(this);
        userP.deleteMovie();

    }

    public void searchMovie(ActionEvent actionEvent) {
        UserP userP = new UserP(this);
        userP.searchMovie();

    }

    public void addMovieB(ActionEvent actionEvent) {
        UserP userP = new UserP(this);
        userP.addMovie();
        fillCategory(comboCategory);
        fillYear(comboYear);

    }

    public void updateMovieB(ActionEvent actionEvent) {
        UserP userP = new UserP(this);
        userP.updateMovie();
        fillCategory(comboCategory);
        fillYear(comboYear);
    }

    public void saveReportsB(ActionEvent actionEvent) {
        UserP userP = new UserP(this);
        userP.saveReports();

    }

    public void filterMovieB(ActionEvent actionEvent) {
        UserP userP = new UserP(this);
        userP.filterMovie();

    }


    private void showMovies(ActionEvent actionEvent) {
        UserP userP = new UserP(this);
        userP.showMoviesB();

    }
    @Override
    public DefaultTableModel getModel(){
        return this.model;
    }
    @Override
    public void setTable(DefaultTableModel model){
        this.showTable.setModel(model);
    }
    @Override
    public String getCategory() {
        return category.getText();
    }

    @Override
    public void setCategory(String category1) {
        category.setText(category1);

    }

    @Override
    public int getYear() {
        int d=0;
        String year1 = this.year.getText();
        if (year1.isEmpty()) {
            return 0;
        }


        try {
             d =  Integer.parseInt(year1);
        } catch (NumberFormatException nfe) {

            return  -1;
        }
        return d;
    }

    @Override
    public void setYear(String year1) {
        year.setText(year1);
    }


    @Override
    public String getTitle() {
        return title.getText();
    }

    @Override
    public void setTitle(String title1) {
        title.setText(title1);
    }

    @Override
    public String selectie() {
        int row = showTable.getSelectedRow();
        if (row >= 0) {
            String value = showTable.getModel().getValueAt(row, 0).toString();
            return value;
        }
        return "";
    }

    @Override
    public void showSearch(Movie movie) {
        Object[] o = new Object[4];

        int rowCount = this.model.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--) {
            this.model.removeRow(i);
        }

        o[0] = movie.getTitle();
        o[1] = movie.getType();
        o[2] = movie.getCategory();
        o[3] = movie.getYear();

        model.addRow(o);

        showTable.setModel(model);
    }

    @Override
    public boolean artistSelect() {
        System.out.println("artist");
        return artisticRadioButton.isSelected();

    }

    @Override
    public boolean serialSelect() {
        System.out.println("serial");
        return serialRadioButton.isSelected();

    }

    @Override
    public String comboCategorySelect() {
        return (String) comboCategory.getSelectedItem();
    }

    @Override
    public String comboYearSelect() {
        return (String) comboYear.getSelectedItem();
    }

    @Override
    public String comboTypeSelect() {
        return (String) comboType.getSelectedItem();
    }

    @Override
    public void setShow(List<Movie> movies) {
        int rowCount = this.model.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--) {
            this.model.removeRow(i);
        }


        for (Movie movie : movies) {

            Object[] o = new Object[4];

            o[0] = movie.getTitle();
            o[1] = movie.getType();
            o[2] = movie.getCategory();
            o[3] = movie.getYear();

            this.model.addRow(o);

        }

        showTable.setModel(this.model);
    }


    private void fillCategory(JComboBox comboBox) {
        UserP userP = new UserP(this);
        List<Movie> movies = userP.getMovies();
        List<String> ss = new ArrayList<String>();
        comboBox.removeAllItems();
        comboBox.addItem("Select");
        for (Movie movie : movies) {

            ss.add(movie.getCategory());

        }
        Set<String> set = new HashSet<>(ss);
        ss.clear();
        ss.addAll(set);
        for (String s : ss) {

            comboBox.addItem(s);

        }

    }

    private void fillYear(JComboBox comboBox) {
        UserP userP = new UserP(this);
        List<Movie> movies = userP.getMovies();
        List<Integer> in = new ArrayList<Integer>();
        comboBox.removeAllItems();
        comboBox.addItem("Select");
        for (Movie movie : movies) {

            in.add(movie.getYear());

        }
        List<Integer> listWithoutDuplicates = in.stream().distinct().collect(Collectors.toList());
        for (Integer i : listWithoutDuplicates) {

            comboBox.addItem(String.valueOf(i));

        }

    }

    private void fillType(JComboBox comboBox) {

        comboBox.addItem("Select");
        comboBox.addItem("Artistic");
        comboBox.addItem("Serial");


    }
}
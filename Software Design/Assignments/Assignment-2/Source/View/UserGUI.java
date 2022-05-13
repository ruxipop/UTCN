package View;

import Model.Movie;
import ViewModel.*;
import triggers.ComboBoxChange;

import javax.swing.JTabbedPane;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sds.mvvm.bindings.*;


public class UserGUI implements ActionListener {
    private VMUser vmUser;

    @Bind(value = "model", target = "model.value", type = net.sds.mvvm.bindings.BindingType.TARGET_TO_SOURCE)
    @Bind(value = "selectedRow", target = "row.value", type = net.sds.mvvm.bindings.BindingType.BI_DIRECTIONAL)

    private JTable showTable;
    public JPanel panel;
    private JScrollPane scrollPane;


    @Bind(value = "text", target = "category.value")
    private JTextField category;
    @Bind(value = "text", target = "year.value")
    private JTextField year;
    @Bind(value = "text", target = "title.value")
    private JTextField title;
    @Bind(value = "selected", target = "artistic.value")
    private JCheckBox ArtisticB;
    @Bind(value = "selected", target = "serial.value")
    private JCheckBox SerialB;

    private ButtonGroup checkBoxGroup;

    private JComboBox comboType;
    private JComboBox comboYear;
    private JComboBox comboCategory;

    private JLabel label11 = new javax.swing.JLabel();
    private JLabel label12 = new javax.swing.JLabel();
    private JPanel panelLab1 = new javax.swing.JPanel();

    private JPanel panelLab2 = new javax.swing.JPanel();
    private JLabel label21 = new javax.swing.JLabel();
    private JLabel label22 = new javax.swing.JLabel();

    private JPanel panelLab3 = new javax.swing.JPanel();
    private JLabel label31 = new javax.swing.JLabel();
    private JLabel label32 = new javax.swing.JLabel();

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton showButton;
    private JButton saveButton;
    private JButton filterButton;
    private JButton logOutButton;
    private JButton statisticButton;


    JFrame frame1 = new JFrame("Employee page");
    DefaultTableModel model;

    JTabbedPane tabbedPane = new JTabbedPane();
    private JLabel number1 = new javax.swing.JLabel();

    public UserGUI() {

        comboType.addItem("Select");
        comboYear.addItem("Select");
        comboCategory.addItem("Select");
        fillYear(comboYear);
        fillType(comboType);
        fillCategory(comboCategory);


        model = new DefaultTableModel(new Object[0][0], new String[]{"Title", "Type", "Category", "Year"});
        this.vmUser = new ViewModel.VMUser();
        try {
            new BindingBuilder<ImageIcon, ImageIcon>().withSourceSupplier(vmUser.getLabel11()::get)
                    .withSourceTrigger((b, d) -> vmUser.getLabel11().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(icon -> label11.setIcon(icon))
                    .build().apply(net.sds.mvvm.bindings.Direction.UP);


            new BindingBuilder<ImageIcon, ImageIcon>().withSourceSupplier(vmUser.getLabel12()::get)
                    .withSourceTrigger((b, d) -> vmUser.getLabel12().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(icon -> label12.setIcon(icon))
                    .build().apply(net.sds.mvvm.bindings.Direction.UP);
            new BindingBuilder<ImageIcon, ImageIcon>().withSourceSupplier(vmUser.getLabel21()::get)
                    .withSourceTrigger((b, d) -> vmUser.getLabel21().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(icon -> label21.setIcon(icon))
                    .build().apply(net.sds.mvvm.bindings.Direction.UP);
            new BindingBuilder<ImageIcon, ImageIcon>().withSourceSupplier(vmUser.getLabel22()::get)
                    .withSourceTrigger((b, d) -> vmUser.getLabel22().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(icon -> label22.setIcon(icon))
                    .build().apply(net.sds.mvvm.bindings.Direction.UP);

            new BindingBuilder<ImageIcon, ImageIcon>().withSourceSupplier(vmUser.getLabel31()::get)
                    .withSourceTrigger((b, d) -> vmUser.getLabel31().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(icon -> label31.setIcon(icon))
                    .build().apply(net.sds.mvvm.bindings.Direction.UP);
            new BindingBuilder<ImageIcon, ImageIcon>().withSourceSupplier(vmUser.getLabel32()::get)
                    .withSourceTrigger((b, d) -> vmUser.getLabel32().addPropertyChangeListener(e -> b.apply(d)))
                    .withTargetConsumer(icon -> label32.setIcon(icon))
                    .build().apply(net.sds.mvvm.bindings.Direction.UP);

            new BindingBuilder<String, String>()
                    .withSourceSupplier(() -> this.comboYear.getSelectedItem().toString())
                    .withTargetConsumer(vmUser.getComboYear()::set)
                    .withSourceTrigger(new ComboBoxChange(comboYear))
                    .build().apply(Direction.UP);

            new BindingBuilder<String, String>()
                    .withSourceSupplier(() -> java.util.Objects.requireNonNull(this.comboCategory.getSelectedItem()).toString())
                    .withTargetConsumer(vmUser.getComboCat()::set)
                    .withSourceTrigger(new ComboBoxChange(comboCategory))
                    .build().apply(Direction.UP);


            new BindingBuilder<String, String>()
                    .withSourceSupplier(() -> java.util.Objects.requireNonNull(this.comboType.getSelectedItem()).toString())
                    .withTargetConsumer(vmUser.getComboType()::set)
                    .withSourceTrigger(new ComboBoxChange(comboType))
                    .build().apply(Direction.UP);


            Binder.bind(this, vmUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
        checkBoxGroup = new ButtonGroup();
        checkBoxGroup.add(ArtisticB);
        checkBoxGroup.add(SerialB);
        panelLab1.add(label11);
        panelLab1.add(label12);
        panelLab2.add(label21);
        panelLab2.add(label22);
        panelLab3.add(label31);
        panelLab3.add(label32);

        tabbedPane.add("Page", panel);
        tabbedPane.add("Radiala", panelLab2);
        tabbedPane.add("Inelara", panelLab3);
        tabbedPane.add("Coloana", panelLab1);
        frame1.add(tabbedPane);
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
        statisticButton.addActionListener(this);

    }


    @lombok.SneakyThrows
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
        if (ae.getSource() == statisticButton) {

            showStatistic(ae);
        }
    }

    public void logOut(ActionEvent actionEvent) {
        frame1.setVisible(false);
        new LoginGUI();
    }

    public void showStatistic(ActionEvent actionEvent) throws java.sql.SQLException, InterruptedException {


//
//        try {
//            new BindingBuilder<ImageIcon, ImageIcon>().withSourceSupplier(vmUser.getLabel11()::get)
//                    .withSourceTrigger((b, d) -> vmUser.getLabel11().addPropertyChangeListener(e -> b.apply(d)))
//                    .withTargetConsumer(icon -> label11.setIcon(icon))
//                    .build().apply(net.sds.mvvm.bindings.Direction.UP);
//        } catch (net.sds.mvvm.bindings.BindingException e) {
//            e.printStackTrace();
//        }
        vmUser.getShowStatisticsCommand().execute(2);


    }

    public void addMovieB(ActionEvent actionEvent) throws java.sql.SQLException, InterruptedException {

        vmUser.getAddMovieCommand().execute(2);
        fillType(comboType);
        fillYear(comboYear);
        fillCategory(comboCategory);

    }

    private void showMovies(ActionEvent actionEvent) throws java.sql.SQLException {
        vmUser.getShowMovieCommand().execute(2);

        fillType(comboType);
        fillYear(comboYear);
        fillCategory(comboCategory);

    }


    public void deleteMovie(ActionEvent actionEvent) throws java.sql.SQLException {
        vmUser.getDeleteMovieCommand().execute(2);
        fillType(comboType);
        fillYear(comboYear);
        fillCategory(comboCategory);

    }

    public void saveReportsB(ActionEvent actionEvent) throws java.sql.SQLException {
        vmUser.getSaveReportsCommand().execute(2);

    }

    public void updateMovieB(ActionEvent actionEvent) throws java.sql.SQLException {
        vmUser.getUpdateMovieCommand().execute(2);
        fillType(comboType);
        fillYear(comboYear);
        fillCategory(comboCategory);


    }

    public void searchMovie(ActionEvent actionEvent) throws java.sql.SQLException {
        vmUser.getSearchMovieCommand().execute(2);

    }


    public void filterMovieB(ActionEvent actionEvent) throws java.sql.SQLException {
        vmUser.getFilterMovieCommand().execute(2);

    }


    private void fillCategory(JComboBox comboBox) {
        removeCombo(comboBox);
        Model.MoviePersistance moviePersistance = new Model.MoviePersistance();


        for (String s : moviePersistance.typeOfCategory()) {

            comboBox.addItem(s);

        }

    }

    public void removeCombo(JComboBox combo) {
        int number = combo.getItemCount();
        for (int i = 1; i < number; i++) {

            combo.removeItem(combo.getItemAt(1));


        }
    }

    private void fillYear(JComboBox comboBox) {
        Model.MoviePersistance moviePersistance = new Model.MoviePersistance();
        List<Integer> in = new ArrayList<Integer>();

        removeCombo(comboBox);

        for (Movie movie : moviePersistance.getMoviesList()) {

            in.add(movie.getYear());


        }
        List<Integer> listWithoutDuplicates = in.stream().distinct().collect(java.util.stream.Collectors.toList());

        for (Integer i : listWithoutDuplicates) {

            comboBox.addItem(i);
        }


    }


    private void fillType(JComboBox comboBox) {
        removeCombo(comboBox);
        Model.MoviePersistance moviePersistance = new Model.MoviePersistance();


        for (String s : moviePersistance.typeOfType()) {

            comboBox.addItem(s);

        }

    }
}
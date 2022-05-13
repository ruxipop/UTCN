package Controller;

import Model.*;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.*;

import View.*;

import java.util.*;

public class UserC {
    private ModelUser modelUser;
    private UserGUI userGUI;
    static int nr = 0;

    public UserC(UserGUI userGUI, ModelUser modelUser) {
        this.modelUser = modelUser;
        this.userGUI = userGUI;
        userGUI.logOut(new LogOut());
        userGUI.deleteMovie(new DeleteMovie());
        userGUI.addMovieB(new AddMovie());
        userGUI.updateMovieB(new UpdateMovie());
        userGUI.searchMovie(new SearchMovie());
        userGUI.saveReportsB(new SaveReports());
        userGUI.filterMovieB(new FilterMovies());
        userGUI.showStatistic(new StatisticAbout());
        userGUI.spB(new SpB());
        userGUI.enB(new EnB());
        userGUI.roB(new RoB());
    }

    class RoB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            modelUser.getLanguage().setLanguage("romanian");
            modelUser.notify("language");
            modelUser.notify("show");

        }
    }

    class EnB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {

            modelUser.getLanguage().setLanguage("english");
            modelUser.notify("language");
            modelUser.notify("show");

        }
    }

    class SpB implements ActionListener {

        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {

            modelUser.getLanguage().setLanguage("spanish");
            modelUser.notify("language");
            modelUser.notify("show");

        }
    }

    class LogOut implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            userGUI.getFrame1().setVisible(false);
            ModelAdmin model = new ModelAdmin(new UserPersistance(), modelUser.getLanguage());
            new LoginC(model, new LoginGUI(model));


        }
    }


    class DeleteMovie implements ActionListener {
        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userGUI.selectie(0) != "") {
                int input = JOptionPane.showConfirmDialog(null, modelUser.getLanguage().getTextLanguage().get("deleteUser"));
                // 0=yes, 1=no, 2=cancel
                if (input == 0) {
                    boolean succes = modelUser.getMoviePersistance().deleteMovie(userGUI.selectie(0), Integer.parseInt(userGUI.selectie(3)));

                    if (!succes) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                }
                modelUser.notify("show");
            } else {
                JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("selectMovie"));
                return;
            }
        }

    }

    class AddMovie implements ActionListener {
        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userGUI.getArtisticB().isSelected() || userGUI.getSerialB().isSelected()) {
                String title = userGUI.getTitle().getText();
                String category = userGUI.getCategory().getText();
                String year = userGUI.getYear().getText();
                String type = userGUI.getSerialB().isSelected() ? "Serial" : "Artistic";
                int year1 = 0;
                if (!year.isEmpty()) {
                    try {
                        year1 = Integer.parseInt(year);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("invalidYear"));
                        return;
                    }
                }
                if (!title.isEmpty() && !category.isEmpty() && !year.isEmpty() && !type.isEmpty()) {
                    if (!title.isEmpty()) {
                        Movie movie = modelUser.getMoviePersistance().selectMovie(title);
                        if (movie != null) {
                            if (movie.getType().equals("Artistic")) {
                                JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("movieExist"));
                                return;
                            }
                            if (movie.getType().equals("Serial") && (type.equals("Artistic") || movie.getYear() == year1)) {
                                JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("movieExist"));
                                return;
                            }
                            if (movie.getType().equals("Serial") && movie.getYear() != year1) {
                                Movie movie1 = new MovieBuilder().title(title).category(category).type(type).year(year1).build();
                                modelUser.getMoviePersistance().insertMovie(movie1);
                            }
                        } else {
                            Movie movie1 = new MovieBuilder().title(title).category(category).type(type).year(year1).build();
                            modelUser.getMoviePersistance().insertMovie(movie1);
                        }
                    }
                    userGUI.reset();
                    modelUser.notify("show");
                } else {
                    JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("completeAll"));
                    return;
                }

            } else {
                JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("selectTypeM"));
                return;
            }
        }

    }

    class UpdateMovie implements ActionListener {
        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userGUI.selectie(0) != "") {
                String title = userGUI.getTitle().getText();
                String year = userGUI.getYear().getText();
                String category = userGUI.getCategory().getText();
                int year1 = 0;
                if (!year.isEmpty()) {
                    try {
                        year1 = Integer.parseInt(year);
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("invalidYear"));
                        return;
                    }
                }
                if (!category.isEmpty() || !year.isEmpty()) {

                    Movie movie = modelUser.getMoviePersistance().selectMovieYear(userGUI.selectie(0), Integer.parseInt(userGUI.selectie(3)));
                    movie.setCategory(category.isEmpty() ? movie.getCategory() : category);
                    int year2 = movie.getYear();
                    movie.setYear(year.isEmpty() ? movie.getYear() : year1);
                    modelUser.getMoviePersistance().updateMovie(movie, year2);

                } else {
                    if (userGUI.getSerialB().isSelected() || userGUI.getArtisticB().isSelected()) {
                        JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("typeCant"));
                        return;
                    }
                    if (!title.isEmpty()) {
                        JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("titleCant"));
                        return;
                    } else {
                        JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("nothingMovie"));
                        return;
                    }

                }
                userGUI.reset();
                modelUser.notify("show");

            } else {
                JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("selectMovie"));
                return;
            }
        }


    }

    class SearchMovie implements ActionListener {
        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            if (userGUI.getTitle().getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("insertTitle"));
            } else {
                List<Movie> movies = modelUser.getMoviePersistance().searchMovieByTitle(userGUI.getTitle().getText());
                if (movies.isEmpty()) {
                    JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("notExist"));
                } else {
                    userGUI.setTable(movies);
                }
            }
        }

    }

    class SaveReports implements ActionListener {
        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            modelUser.notify("show");
            Object[] options = {"Csv",//0
                    "Json",//1
                    "Xml"};//2
            int n = JOptionPane.showOptionDialog(null,
                    modelUser.getLanguage().getTextLanguage().get("chooseOp"),
                    "",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (n != -1) {
                modelUser.getMoviePersistance().saveReports(n);
            }

        }
    }

    class FilterMovies implements ActionListener {
        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            String category = userGUI.getComboCategory().getSelectedItem().toString();
            String type = userGUI.getComboType().getSelectedItem().toString();
            String year = userGUI.getComboYear().getSelectedItem().toString();
            String button = modelUser.getLanguage().getTextLanguage().get("buttonSelect");

            if (!category.equals(button) || !type.equals(button) || !year.equals(button)) {
                String category1 = (category.equals(button)) ? " " : category;
                int year1 = (year.equals(button)) ? -1 : Integer.parseInt(year);
                String type1 = (type.equals(button)) ? " " : type;
                List<Movie> movieToShow = modelUser.getMoviePersistance().filterMovie(category1, type1, year1);
                if (movieToShow == null) {
                    JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("notExist"));
                    return;
                } else {
                    userGUI.setTable(movieToShow);
                }
            } else {
                JOptionPane.showMessageDialog(null, modelUser.getLanguage().getTextLanguage().get("selectCri"));
                return;
            }
        }
    }

    class StatisticAbout implements ActionListener {
        @lombok.SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            modelUser.notify("show");
            String nbMovies = modelUser.getLanguage().getTextLanguage().get("nbMovies");
            String movieCategory = modelUser.getLanguage().getTextLanguage().get("movieCategory");
            String movieType = modelUser.getLanguage().getTextLanguage().get("movieType");
            modelUser.getMoviePersistance().generate(nbMovies, movieCategory, movieType);
            userGUI.getLabel11().setIcon(new ImageIcon("chart_column" + nr + ".jpg"));
            userGUI.getLabel12().setIcon(new ImageIcon("chart_column2" + nr + ".jpg"));
            userGUI.getLabel21().setIcon(new ImageIcon("chart_radial" + nr + ".jpg"));
            userGUI.getLabel22().setIcon(new ImageIcon("chart_radial2" + nr + ".jpg"));
            userGUI.getLabel31().setIcon(new ImageIcon("chart_inelar" + nr + ".jpg"));
            userGUI.getLabel32().setIcon(new ImageIcon("chart_inelar2" + nr + ".jpg"));
            nr++;
        }
    }
}

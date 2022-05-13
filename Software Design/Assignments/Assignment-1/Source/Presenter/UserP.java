package Presenter;

import Model.Movie;
import Model.MoviePersistance;
import Model.User;
import View.IUserView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UserP {
    private static IUserView userView;
    private MoviePersistance moviePersistance;

    public UserP(IUserView userView) {

        this.moviePersistance = new MoviePersistance();
        this.userView = userView;

    }

    public List<Movie> getMovies() {


        return moviePersistance.getMoviesList();
    }

    public void deleteMovie() {
        if (this.userView.selectie() != "") {
            int input = JOptionPane.showConfirmDialog(null, "Do you want to delete?");
            // 0=yes, 1=no, 2=cancel
            if (input == 0) {
                boolean succes = this.moviePersistance.deleteMovie(this.userView.selectie());
                if (!succes) {
                    JOptionPane.showMessageDialog(null, "Error!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a movie!!");
            return;

        }
    }

    public void addMovie() {
        if (!this.userView.getTitle().isEmpty() && !this.userView.getCategory().isEmpty() && this.userView.getYear() != 0 && (!this.userView.artistSelect() || !this.userView.serialSelect())) {
            boolean corect = true;

            String type = "";
            if (this.userView.getYear() < 0 && this.userView.getYear() !=-1) {
                corect = false;
                JOptionPane.showMessageDialog(null, "Year cannot be <0");

            }
            if ( this.userView.getYear() ==-1) {
                corect = false;
                JOptionPane.showMessageDialog(null, "Insert a number for year");
            }
            if (corect) {
                if (this.userView.artistSelect()) {
                    type = "Artistic";
                } else if (this.userView.serialSelect()) {
                    type = "Serial";
                }
                Movie movie = new Movie(this.userView.getTitle(), type, this.userView.getCategory(), this.userView.getYear());
                boolean exista = this.moviePersistance.existMovie(movie);
                if (!exista) {

                    boolean succes = this.moviePersistance.addMovie(movie);
                    if (!succes) {
                        JOptionPane.showMessageDialog(null, "Error!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Exist!");
                }


            }


        } else {

            JOptionPane.showMessageDialog(null, "Complete all fields");
        }
    }


    public void searchMovie() {
        Movie movie = null;

        try {

            movie = this.moviePersistance.searchMovieByTitle(this.userView.getTitle());
            if (movie != null) {
                this.userView.showSearch(movie);
            } else {
                JOptionPane.showMessageDialog(null, "Doesn't exist ");
            }
        } catch (Exception e) {
        }

    }

    public void updateMovie() {
        if (this.userView.selectie() != "") {
            try {

                if (!this.userView.getTitle().isEmpty() || !this.userView.getCategory().isEmpty() || this.userView.getYear() != 0 || (!this.userView.serialSelect() || !this.userView.artistSelect())) {

                    boolean corect = true;
                    String type;

                    if (this.userView.artistSelect()) {
                        type = "Artistic";
                    } else {

                        type = "Serial";
                    }
                    Movie movie = new Movie(this.userView.getTitle(), type, this.userView.getCategory(), this.userView.getYear());

                    boolean succes = this.moviePersistance.updateMovie(movie, this.userView.selectie());

                    if (!succes) {
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                }


            } catch (Exception e) {

            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a movie");
            return;

        }
    }


    public void saveReports() {
        Object[] options = {"Csv",//0
                "Json!"};//1
        int n = JOptionPane.showOptionDialog(null,
                "Choose an option",
                "",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        this.moviePersistance.saveReports(n);
    }

    public void filterMovie() {

        if (!this.userView.comboCategorySelect().isEmpty() || !this.userView.comboTypeSelect().isEmpty() || !this.userView.comboYearSelect().isEmpty()) {

            List<Movie> movieToShow = this.moviePersistance.filterMovie(this.userView.comboCategorySelect(), this.userView.comboTypeSelect(), this.userView.comboYearSelect());
            if (movieToShow == null) {
                JOptionPane.showMessageDialog(null, "Doesn't exitst");
            } else {
                this.userView.setShow(movieToShow);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a criterion!");
        }


    }
    public void showMoviesB(){
        DefaultTableModel model=this.userView.getModel();
        int rowCount = model.getRowCount();

        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        for (Movie movie : this.getMovies()) {
            System.out.println("dd");
            Object[] o = new Object[4];

            o[0] = movie.getTitle();
            o[1] = movie.getType();
            o[2] = movie.getCategory();
            o[3] = movie.getYear();

            model.addRow(o);

        }
        this.userView.setTable(model);

    }

}

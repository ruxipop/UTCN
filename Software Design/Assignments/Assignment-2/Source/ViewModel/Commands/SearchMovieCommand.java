package ViewModel.Commands;

import ViewModel.*;

import javax.swing.table.DefaultTableModel;
import Model.*;


public class SearchMovieCommand implements ICommand {
    private VMUser vmUser;

    public SearchMovieCommand(VMUser vmUser) {
        this.vmUser = vmUser;
    }

    @Override
    public void execute(int ok) throws java.sql.SQLException {
        DefaultTableModel t = new DefaultTableModel();

        Model.MoviePersistance per = new Model.MoviePersistance();
        DefaultTableModel model = new javax.swing.table.DefaultTableModel(new Object[0][0], new String[]{"Title", "Type", "Category", "Year"});


        for (Movie movie : per.searchMovieByTitle(vmUser.getTitle().get())) {

            Object[] o = new Object[4];

            o[0] = movie.getTitle();
            o[1] = movie.getType();
            o[2] = movie.getCategory();
            o[3] = movie.getYear();
            model.addRow(o);
        }
        vmUser.setModel(model);

    }
}

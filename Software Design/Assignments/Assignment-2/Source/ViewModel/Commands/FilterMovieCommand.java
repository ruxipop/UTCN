package ViewModel.Commands;

public class FilterMovieCommand implements ICommand {
    private ViewModel.VMUser vmUser;

    public FilterMovieCommand(ViewModel.VMUser vmUser) {
        this.vmUser = vmUser;
    }

    @Override
    public void execute(int ok) throws java.sql.SQLException {
        Model.MoviePersistance per = new Model.MoviePersistance();
        if (!vmUser.getComboCat().get().isEmpty() || !vmUser.getComboType().get().isEmpty() || !vmUser.getComboYear().get().isEmpty()) {

            java.util.List<Model.Movie> movieToShow = per.filterMovie(vmUser.getComboCat().get(), vmUser.getComboType().get(), vmUser.getComboYear().get());
            if (movieToShow == null) {
                javax.swing.JOptionPane.showMessageDialog(null, "Doesn't exitst");
            } else {
                javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(new Object[0][0], new String[]{"Title", "Type", "Category", "Year"});


                for (Model.Movie movie : movieToShow) {

                    Object[] o = new Object[4];

                    o[0] = movie.getTitle();
                    o[1] = movie.getType();
                    o[2] = movie.getCategory();
                    o[3] = movie.getYear();

                    model.addRow(o);


                }

                vmUser.setModel(model);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Select a criterion!");
        }
    }
}

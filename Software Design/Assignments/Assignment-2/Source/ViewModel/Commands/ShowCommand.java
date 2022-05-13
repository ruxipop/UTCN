package ViewModel.Commands;

import ViewModel.*;

public class ShowCommand implements ICommand {
    private VMAdmin vmAdmin;
    private VMUser vmUser;

    public ShowCommand(ViewModel.VMAdmin vmAdmin, ViewModel.VMUser vmUser) {
        this.vmAdmin = vmAdmin;
        this.vmUser = vmUser;
    }


    @Override
    public void execute(int ok) {
        if (ok == 1) {
            javax.swing.table.DefaultTableModel t = new javax.swing.table.DefaultTableModel();

            Model.UserPersistance per = new Model.UserPersistance();
            per.selectAllUsers();
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(new Object[0][0], new String[]{"First Name", "Last Name", "Username", "Password", "Role"});


            for (Model.User user : per.getUsersList()) {

                Object[] o = new Object[5];

                o[0] = user.getFirstName();
                o[1] = user.getLastName();
                o[2] = user.getUsername();
                o[3] = user.getPassword();
                o[4] = user.getRole();

                model.addRow(o);


            }

            vmAdmin.setModel(model);
        }
        else{
            javax.swing.table.DefaultTableModel t=new javax.swing.table.DefaultTableModel();

            Model.MoviePersistance per=new Model.MoviePersistance();
            per.selectAllMovies();

            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(new Object[0][0], new String[]{"Title", "Type", "Category", "Year"});





            for (Model.Movie movie : per.getMoviesList()) {

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
}


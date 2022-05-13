package ViewModel.Commands;
import ViewModel.*;
public class UpdateCommand implements ICommand {
    private ViewModel.VMAdmin vmAdmin;
    private VMUser vmUser;

    public UpdateCommand(ViewModel.VMAdmin vmAdmin, ViewModel.VMUser vmUser) {
        this.vmAdmin = vmAdmin;
        this.vmUser = vmUser;
    }


    @Override
    public void execute(int ok) throws java.sql.SQLException {
        if (ok == 1) {
            Model.UserPersistance per = new Model.UserPersistance();

            if (vmAdmin.getRow().get() != -1) {
                if (!vmAdmin.getPassword().get().isEmpty() || !vmAdmin.getFirstName().get().isEmpty() || !vmAdmin.getLastName().get().isEmpty() || vmAdmin.getAdmin().get().equals(
                        true) || vmAdmin.getEmployee().get().equals(true)) {
                    Model.User user = per.selectUser(vmAdmin.getModel().get().getValueAt(vmAdmin.getRow().get(), 2).toString());

                    user.setPassword(vmAdmin.getPassword().get().isEmpty() ? user.getPassword() : vmAdmin.getPassword().get());
                    user.setFirstName(vmAdmin.getFirstName().get().isEmpty() ? user.getFirstName() : vmAdmin.getFirstName().get());
                    user.setLastName(vmAdmin.getLastName().get().isEmpty() ? user.getLastName() : vmAdmin.getLastName().get());

                    String role;
                    if (vmAdmin.getAdmin().get().equals(true)) {
                        role = "Admin";
                        vmAdmin.getAdmin().set(false);


                    } else if (vmAdmin.getEmployee().get().equals(true)) {
                        role = "Employee";
                        vmAdmin.getEmployee().set(false);

                    } else {
                        role = user.getRole();
                        System.out.println("noti");
                    }
                    user.setRole(role);
                    per.updateUser(user);
                } else {
                    if (!vmAdmin.getUsername().get().isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Username can't be updated");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Nothing to update for that user!Complete cel pun");
                        return;
                    }
                }


            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Select a user to update them!");
                return;
            }

        }else{
            Model.MoviePersistance per = new Model.MoviePersistance();

            if (vmUser.getRow().get() != -1) {
                if (!vmUser.getCategory().get().isEmpty() || !vmUser.getYear().get().isEmpty()) {
                    Model.Movie movie = per.selectMovieYear(vmUser.getModel().get().getValueAt(vmUser.getRow().get(), 0).toString(), Integer.parseInt(vmUser.getModel().get().getValueAt(vmUser.getRow().get(), 3).toString()));
                    movie.setCategory(vmUser.getCategory().get().isEmpty() ? movie.getCategory() : vmUser.getCategory().get());
                    int year1 = movie.getYear();
                    movie.setYear(vmUser.getYear().get().isEmpty() ? movie.getYear() : Integer.parseInt(vmUser.getYear().get()));
                    per.updateMovie(movie, year1);
                } else {
                    if (vmUser.getSerial().get().equals(
                            true) || vmUser.getArtistic().get().equals(true)) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Type can't be change");
                        return;
                    }
                    if (!vmUser.getTitle().get().isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Title can't be updated");
                        return;
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Nothing to update for that movie!Complete cel pun");
                        return;
                    }
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Select a movie to update them!");
                return;
            }
        }
    }
}

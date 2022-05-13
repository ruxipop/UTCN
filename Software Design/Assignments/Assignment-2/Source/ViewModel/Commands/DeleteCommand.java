package ViewModel.Commands;

import ViewModel.VMAdmin;
import ViewModel.*;
public class DeleteCommand implements ICommand {
    private VMAdmin vmAdmin;
    private VMUser vmUser;

    public DeleteCommand(ViewModel.VMAdmin vmAdmin, ViewModel.VMUser vmUser) {
        this.vmAdmin = vmAdmin;
        this.vmUser = vmUser;
    }

    @Override
    public void execute(int ok) throws java.sql.SQLException {
        if (ok == 1) {
            Model.UserPersistance per = new Model.UserPersistance();
            if (vmAdmin.getRow().get() != -1) {
                int input = javax.swing.JOptionPane.showConfirmDialog(null, "Do you want to delete?");
                if (input==0){
                per.deleteUser(vmAdmin.getModel().get().getValueAt(vmAdmin.getRow().get(), 2).toString());}
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Select a user to delete !");
                return;
            }

        } else {
            Model.MoviePersistance per = new Model.MoviePersistance();
            if(vmUser.getRow().get()!=-1 ) {
                int input = javax.swing.JOptionPane.showConfirmDialog(null, "Do you want to delete?");
                if (input == 0) {
                    per.deleteMovie(vmUser.getModel().get().getValueAt(vmUser.getRow().get(), 0).toString(),Integer.parseInt(vmUser.getModel().get().getValueAt(vmUser.getRow().get(), 3).toString()));

                }

            }else
            {
                javax.swing.JOptionPane.showMessageDialog(null, "Select a movie to delete!");
                return;
            }
        }
    }
}

package ViewModel.Commands;

import ViewModel.*;

import Model.*;

public class AddCommand implements ICommand {
    private VMAdmin vmAdmin;
    private VMUser vmUser;

    public AddCommand(ViewModel.VMAdmin vmAdmin, ViewModel.VMUser vmUser) {
        this.vmAdmin = vmAdmin;
        this.vmUser = vmUser;
    }

    @Override
    public void execute(int ok) throws java.sql.SQLException {
        if (ok == 1) {
            if (vmAdmin.getAdmin().get().equals(true) || vmAdmin.getEmployee().get().equals(true)) {
                if (!vmAdmin.getFirstName().get().isEmpty() && !vmAdmin.getLastName().get().isEmpty() && !vmAdmin.getUsername().get().isEmpty() && !vmAdmin.getPassword().get().isEmpty()) {
                    Model.UserPersistance per = new Model.UserPersistance();
                    String role = vmAdmin.getAdmin().get().equals(true) ? "Admin" : "Employee";
                    User user = new Model.UserBuilder().firstName(vmAdmin.getFirstName().get()).lastName(vmAdmin.getLastName().get()).username(vmAdmin.getUsername().get()).password(vmAdmin.getPassword().get()).role(role).build();
                    per.insertUser(user);

                    if (vmAdmin.getAdmin().get() == true) {
                        vmAdmin.getAdmin().set(false);
                    }
                    if (vmAdmin.getEmployee().get() == true) {
                        vmAdmin.getEmployee().set(false);
                    }

                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Complet all fields!");
                    return;
                }

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Select a type for user!");
                return;
            }
        } else {
            if (vmUser.getArtistic().get().equals(true) || vmUser.getSerial().get().equals(true)) {
                if (!vmUser.getTitle().get().isEmpty() && !vmUser.getCategory().get().isEmpty() && !vmUser.getYear().get().isEmpty()) {
                    MoviePersistance per = new Model.MoviePersistance();
                    String type = vmUser.getArtistic().get().equals(true) ? "Artistic" : "Serial";
                    Movie movie1 = per.selectMovie(vmUser.getTitle().get());
                    int year;
                    try {
                        year = Integer.parseInt(vmUser.getYear().get());

                    } catch (NumberFormatException e1) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Invalid input for year.!");
                        return;
                    }
                    if (movie1 == null) {
                        Movie movie = new MovieBuilder().title(vmUser.getTitle().get()).type(type).category(vmUser.getCategory().get()).year(year).build();
                        per.insertMovie(movie);
                    } else {
                        if (movie1.getType().equals("Artistic")) {
                            javax.swing.JOptionPane.showMessageDialog(null, "Movie exists!");
                            return;
                        }
                        if (movie1.getType().equals("Serial") && movie1.getYear() == Integer.parseInt(vmUser.getYear().get())) {
                            javax.swing.JOptionPane.showMessageDialog(null, "Movie exists!");
                            return;
                        }
                        if (movie1.getType().equals("Serial") && movie1.getYear() != Integer.parseInt(vmUser.getYear().get())) {
                            Movie movie = new MovieBuilder().title(vmUser.getTitle().get()).type(type).category(vmUser.getCategory().get()).year(Integer.parseInt(vmUser.getYear().get())).build();

                            per.insertMovie(movie);
                        }
                    }
                    if (vmUser.getArtistic().get() == true) {
                        vmUser.getArtistic().set(false);
                    }
                    if (vmUser.getSerial().get() == true) {
                        vmUser.getSerial().set(false);
                    }
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Complet all fields!");
                    return;
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Select a type for user!");
                return;
            }
        }
    }
}

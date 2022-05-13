package View;

import javax.swing.table.DefaultTableModel;

public interface IAdministratorView {
    DefaultTableModel getModel();
    void setTable(DefaultTableModel model);
    String getFirstName();
    void setFirstName(String firstName1);
    String getLastName();
    void setLastName(String lastName1);
    String getUsername();
    void setUsername(String username1);
    String getPassword();
    void setPassword(String password1);
    String selectie();
    boolean adminSelect();
    boolean employeeSelect();

}

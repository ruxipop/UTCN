package ViewModel;

import ViewModel.Commands.*;
import net.sds.mvvm.properties.*;
import lombok.Getter;
import lombok.Setter;

import javax.swing.table.DefaultTableModel;

@Getter
@Setter
public class VMAdmin {
    private ICommand addUserCommand;
    private ICommand deleteUserCommand;
    private ICommand showUsersCommand;
    private ICommand updateUserCommand;

    private Property<String> firstName = PropertyFactory.createProperty("text", this, String.class);
    private Property<String> lastName = PropertyFactory.createProperty("text1", this, String.class);
    private Property<String> username = PropertyFactory.createProperty("text2", this, String.class);
    private Property<String> password = PropertyFactory.createProperty("text3", this, String.class);
    private Property<DefaultTableModel> model = PropertyFactory.createProperty("model", this, new javax.swing.table.DefaultTableModel());
    private Property<Integer> row = PropertyFactory.createProperty("row", this, Integer.class);
    private Property<Boolean> Employee = PropertyFactory.createProperty("Employee", this, Boolean.class);
    private Property<Boolean> Admin = PropertyFactory.createProperty("Admin", this, Boolean.class);



    public VMAdmin() {

        this.addUserCommand = new ViewModel.Commands.AddCommand(this,null);
        this.deleteUserCommand = new ViewModel.Commands.DeleteCommand(this,null);
        this.showUsersCommand = new ViewModel.Commands.ShowCommand(this,null);
        this.updateUserCommand = new ViewModel.Commands.UpdateCommand(this,null);


    }
    public void setModel(DefaultTableModel defaultTableModel) {
        model.set(defaultTableModel);
    }
}

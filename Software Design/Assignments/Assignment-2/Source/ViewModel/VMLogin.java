package ViewModel;

import ViewModel.Commands.*;
import net.sds.mvvm.properties.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VMLogin {
    private  ICommand loginCommand;

   private Property<String> usernameField = PropertyFactory.createProperty("text", this, String.class);
   private Property<String> passwordField = PropertyFactory.createProperty("text1", this, String.class);

    public VMLogin() {
        this.loginCommand = new LoginCommand(this);

    }

}

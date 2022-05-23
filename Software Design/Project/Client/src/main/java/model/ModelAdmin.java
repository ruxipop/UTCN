package model;

import bll.*;

@lombok.Getter
@lombok.Setter

public class ModelAdmin extends Subject {

    private Language language;
    private UserBLL userBLL;

    public ModelAdmin(UserBLL userBLL) {
        this.userBLL = userBLL;
        this.language = new Language("english");
    }


    @Override
    public void notify(String op) throws IllegalAccessException, java.io.IOException {

        for (Observer observer : super.getListObs()) {
            observer.update(op);
        }

    }
}

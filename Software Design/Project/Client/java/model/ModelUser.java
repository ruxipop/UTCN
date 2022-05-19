package model;

import bll.*;

@lombok.Getter
@lombok.Setter
public class ModelUser extends Subject {

    private MovieBLL movieBLL;
    private Language language;

    public ModelUser(MovieBLL movieBLL) {
        this.movieBLL = movieBLL;

        this.language = new model.Language("english");
    }


    @Override
    public void notify(String op) throws IllegalAccessException, java.io.IOException {

        for (Observer observer : super.getListObs()) {
            observer.update(op);
        }
    }
}

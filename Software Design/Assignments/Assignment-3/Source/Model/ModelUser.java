package Model;

@lombok.Getter
@lombok.Setter
public class ModelUser extends Subject {
    private MoviePersistance moviePersistance;
    private Language language;

    public ModelUser(MoviePersistance moviePersistance) {
        this.moviePersistance = moviePersistance;
        this.language = new Model.Language("english");
    }

    @Override
    public void notify(String op) {

        for (Observer observer : super.getListObs()) {
            observer.update(op);
        }
    }
}

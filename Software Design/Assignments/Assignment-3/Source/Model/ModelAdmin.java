package Model;

@lombok.Getter
@lombok.Setter

public class ModelAdmin extends Subject {
    private UserPersistance userPersistance;
    private Language language;

    public ModelAdmin(UserPersistance userPersistance) {
        this.userPersistance = userPersistance;
        this.language = new Language("english");
    }

    public ModelAdmin(UserPersistance userPersistance, Language language) {
        this.userPersistance = userPersistance;
        this.language = language;
    }

    @Override
    public void notify(String op) {

        for (Observer observer : super.getListObs()) {
            observer.update(op);
        }

    }
}

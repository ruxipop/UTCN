package Model;

import java.util.*;

public abstract class Subject {
    private List<Observer> listObs = new ArrayList<Observer>();

    public void add(Observer obs) {
        this.listObs.add(obs);
    }

    public void delete(Observer obs) {
        this.listObs.remove(obs);
    }


    public List<Observer> getListObs() {
        return this.listObs;
    }

    public void setListObs(List<Observer> listObs) {
        this.listObs = listObs;
    }

    public abstract void notify(String op);


}

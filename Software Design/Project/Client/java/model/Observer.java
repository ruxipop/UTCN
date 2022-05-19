package model;

public interface Observer {
    void update(Object o) throws IllegalAccessException, java.io.IOException;
}

package model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public interface Strategy {

    public Server addTask(List<Server> queuesServer);
}

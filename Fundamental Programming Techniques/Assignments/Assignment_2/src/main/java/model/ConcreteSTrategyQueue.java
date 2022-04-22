package model;

import java.util.List;
import java.util.ListIterator;

public class ConcreteSTrategyQueue implements Strategy {
    @Override
    public Server addTask(List<Server> servers) {

        if (servers.isEmpty()) return null;

        Server optimalServer = servers.get(0);
        int minimumServeL = optimalServer.getTasks().size();
        for (Server currentServer : servers) {
            if (currentServer.getTasks().size() < minimumServeL) {
                optimalServer = currentServer;
                minimumServeL = currentServer.getTasks().size();
            }

        }
        return optimalServer;
    }
}
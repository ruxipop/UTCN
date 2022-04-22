package model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;
import java.util.ListIterator;

public class ConcreteSTstrategyTime implements Strategy{
    @Override
    public Server addTask(List<Server> servers) {
        if(servers.isEmpty() ) return null;

        Server optimalServer = servers.get(0);
        int minimumWaitingPeriod = optimalServer.getWaitingPeriod();
        for(Server currentServer :servers){
            if(currentServer.getWaitingPeriod()<minimumWaitingPeriod){
                optimalServer=currentServer;
                minimumWaitingPeriod=currentServer.getWaitingPeriod();
            }
        }
        return optimalServer;
    }

}




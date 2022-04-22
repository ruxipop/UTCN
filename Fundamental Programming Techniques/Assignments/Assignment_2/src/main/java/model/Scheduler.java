package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    private List<Server> servers;
    private Integer noOfServers;
    private AtomicInteger totalWaitingTime;

    private AtomicInteger totalTimeP;
    private AtomicInteger totalTasksP;
    private Strategy strategy;


    public Scheduler(Integer noOfServers, SelectionPolicy strategy) {
        if (strategy == SelectionPolicy.SHORTEST_TIME)
            this.strategy = new ConcreteSTstrategyTime();
        servers = new ArrayList<>();

        this.noOfServers = noOfServers;
        for (int i = 0; i < noOfServers; i++) {
            Server server = new Server(i + 1);
            servers.add(server);

            (new Thread(server)).start();
        }
        this.totalWaitingTime = new AtomicInteger(0);
        this.totalTimeP = new AtomicInteger(0);
        this.totalTasksP = new AtomicInteger(0);

    }




    public void dispatchTask(Task task) {
        Server server = strategy.addTask(servers);
        if (server == null)
            return;
        this.totalWaitingTime.addAndGet(server.getWaitingPeriod());
        server.addTask(task);

    }

    public List<Server> getServer() {
        return servers;
    }


    public Integer getTotalWaitingTime() {
        return totalWaitingTime.get();
    }

    public boolean done() {
        for (Server server : servers) {
            if (!server.isEmpty())
                return false;
        }
        return true;
    }




    public void setTimeProcessed() {
        for (Server server: servers) {
            totalTasksP.addAndGet(server.getNoTaskProcessed());
            totalTimeP.addAndGet(server.getTotalTimeP());
        }
    }
    public void stop() {
        for (Server server: servers) {
            server.stop();
        }
    }
    public Integer getTotalTimeP() {
        return totalTimeP.get();
    }

    public Integer getTotalTasksP() {
        return totalTasksP.get();
    }

}

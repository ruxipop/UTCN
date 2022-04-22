package model;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Server implements Runnable {
    private BlockingQueue<Task> tasks;
    private boolean running = true;
    private AtomicInteger waitingPeriod;
    private AtomicInteger noTaskProcessed = new AtomicInteger(0);
    private AtomicInteger totalTimeP = new AtomicInteger(0);
    private int serverID;


    public Server(int serverID) {
        this.serverID = serverID;
        tasks = new LinkedBlockingDeque<>();
        waitingPeriod = new AtomicInteger(0);

    }


    public void addTask(Task task) {
        tasks.offer(task);
        waitingPeriod.addAndGet(task.getServiceTime());
    }

    @Override
    public void run() {
        while (running) {

            if (tasks.isEmpty()) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    break;

                }
            } else {
                Task task = tasks.peek();
                if (task != null) {
                    try {

                        int timeTaskP = 0;
                        while (task.getServiceTime() != 0) {
                            timeTaskP++;

                            task.decrementServiceTime();
                            waitingPeriod.decrementAndGet();
                            sleep(1000);


                        }
                        tasks.take();
                        noTaskProcessed.incrementAndGet();
                        totalTimeP.addAndGet(timeTaskP);
                    } catch (InterruptedException e) {
                        return;
                    }

                }
            }
        }
    }


    public Integer getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public Queue<Task> getTasks() {
        return tasks;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public Integer getNoTaskProcessed() {
        return noTaskProcessed.get();
    }

    public Integer getTotalTimeP() {
        return totalTimeP.get();
    }
    public void stop() {
        this.running = false;
    }

    @Override
    public String toString() {

        StringBuilder string = new StringBuilder("Queue" + serverID + ": ");
        boolean isFree = false;
        for (Task task : tasks) {
            if (task.getServiceTime() != 0) {
                string.append(task);
                isFree = true;
            }

        }
        if (tasks.isEmpty() || isFree == false) return string + "closed.";

        return string.toString();
    }
}

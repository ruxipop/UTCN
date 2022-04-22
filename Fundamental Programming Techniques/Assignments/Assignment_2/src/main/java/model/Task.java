package model;

public class Task implements Comparable<Task> {
    private Integer taskID;
    private Integer arrivalTime;
    private Integer serviceTime;

    public Task(Integer taskID, Integer arrivalTime, Integer serviceTime) {
        this.taskID = taskID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Integer getID() {
        return taskID;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void decrementServiceTime() {
        serviceTime--;
    }


    @Override
    public String toString() {
        return "(" + taskID + ", " + arrivalTime + ", " + serviceTime + ")";
    }

    @Override
    public int compareTo(Task t) {
        if (arrivalTime.compareTo(t.getArrivalTime()) == 0) {
            return taskID.compareTo(t.getID());
        } else
            return arrivalTime.compareTo(t.getArrivalTime());
    }
}


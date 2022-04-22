package model;

import model.*;
import view.*;

import javax.xml.crypto.dsig.spec.HMACParameterSpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;


public class SimulationManager implements Runnable {
    private Integer timeLimit = 100;
    private Integer maxProcessingTime = 10;
    private Integer minProcessingTime = 2;
    private Integer maxArrivalTime = 10;
    private Integer minArrivalTime = 2;
    private Integer numberOfTask = 100;
    private Integer numberOfServers = 3;
    public AtomicInteger simulationTime = new AtomicInteger(0);
    private Scheduler scheduler;
    private List<Task> tasks;
    private FileWriter writer;
    private Logger logger;
    private int maxP;
    private int peakT;

    private Double averageWaitingTime;
    private Double averageServiceTime;


    private SimulationFrame simulationFrame;

    public SimulationManager(SimulationFrame simulationFrame, Integer numberOfTask, Integer numberOfServers, Integer minArrivalTime,
                             Integer maxArrivalTime, Integer minProcessingTime, Integer maxProcessingTime, Integer timeLimit) {

        this.numberOfTask = numberOfTask;
        this.numberOfServers = numberOfServers;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.timeLimit = timeLimit;
        this.scheduler = new Scheduler(numberOfServers, SelectionPolicy.SHORTEST_TIME);
        generateRandomTasks();
        logger = new Logger();
        this.simulationFrame = simulationFrame;


    }

    public SimulationFrame getView() {
        return simulationFrame;
    }


    private void generateRandomTasks() {
        tasks = new ArrayList<>(numberOfTask);
        int processingTime, arrivalTime;
        for (int i = 1; i <= numberOfTask; i++) {
            processingTime = ThreadLocalRandom.current().nextInt(minProcessingTime, maxProcessingTime + 1);
            arrivalTime = ThreadLocalRandom.current().nextInt(minArrivalTime, maxArrivalTime + 1);
            tasks.add(new Task(i, arrivalTime, processingTime));
        }

        Collections.sort(tasks);

    }

    @Override
    public void run() {
        Task currentTask;
        maxP=0;
        peakT=0;
        while (simulationTime.get() < timeLimit) {
            if (!tasks.isEmpty()) {
                currentTask = tasks.get(0);
                while (currentTask.getArrivalTime() == simulationTime.get()) {
                    scheduler.dispatchTask(currentTask);
                    int peakH=calPeak();
                    if(maxP<peakH){
                        maxP=peakH;
                        peakT=simulationTime.get();
                    }

                    tasks.remove(0);
                    if (tasks.isEmpty()) break;
                    currentTask = tasks.get(0);
                }
            }

            logger.log(simulationTime.intValue(), scheduler, tasks, writer);
            simulationTime.incrementAndGet();
            try {
                sleep(1000);
            } catch (InterruptedException exc) {
                break;
            }
            if (tasks.isEmpty() && scheduler.done()) {
                try {
                    writer.close();
                } catch (IOException ignored) {
                }
                break;
            }
        }

        finalResult();
        scheduler.stop();
    }

    public int calPeak(){
        int peakT=0;
        AtomicInteger peakH =new AtomicInteger(0);
        while(peakH.get()<scheduler.getServer().size()){
            peakT+=scheduler.getServer().get(peakH.get()).getTasks().size();
            peakH.getAndIncrement();
        }

        return peakT;
    }
    public void createOutFile() {
        File file = null;
        try {
            LocalDateTime myObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ss");
            file = new File("Result" + myObj.format(myFormatObj) + ".txt");


            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        try {
            writer = new FileWriter(file.getAbsoluteFile());
        } catch (IOException e) {
            System.out.println("Error");
        }
    }


    private void finalResult() {
        Integer totalWaitingTime = scheduler.getTotalWaitingTime();
        try {
            averageWaitingTime =Math.round (((double) totalWaitingTime / (numberOfTask - this.tasks.size()))*100)/100.0;


        } catch (ArithmeticException exc) {
            this.averageWaitingTime = 0.0;
        }
        try {
            scheduler.setTimeProcessed();
          averageServiceTime =Math.round (((double) scheduler.getTotalTimeP()/ scheduler.getTotalTasksP())*100)/100.0;

        } catch (ArithmeticException exc) {
            this.averageServiceTime = 0.0;
        }

        StringBuilder string = new StringBuilder("Average waiting time is : " + averageWaitingTime);
        string.append("\nAverage service time is : " + averageServiceTime);
        string.append("\nPeak hour is : " +peakT);

        logger.logWriteResFinal(string, writer);
    }


    class Logger {
        public void log(int time, Scheduler scheduler, List<Task> tasks, FileWriter writer) {

            try {
                getView().getLtime().setText(" Time" + time + " \n Waiting clients: ");

                getView().add(simulationFrame.getLtime());

                StringBuilder string = new StringBuilder("Time " + time + "\n");
                string.append("Waiting clients: ");
                for (Task task : tasks) {

                    string.append(task.toString());
                    getView().setLabel(task.toString());
                }
                getView().setLabel("\n");
                string.append("\n");
                for (Server servers : scheduler.getServer()) {
                    string.append(servers.toString());
                    string.append("\n");
                    getView().setLabel(servers.toString() + "\n");
                }
                string.append("\n");

                getView().add(simulationFrame.getLtime());
                System.out.println(string.toString());

                writer.append(string.toString());


            } catch (IOException e) {

                System.out.println(e.getMessage());
            }

        }


        public void logWriteResFinal(StringBuilder string, FileWriter writer) {
            try {

                writer.append(string.toString());
                getView().resetLabel();
                getView().setLabel(string.toString());
                getView().add(simulationFrame.getLtime());
                writer.flush();
                System.out.println(string.toString());
            } catch (IOException e) {
                System.out.println("Error while logging avg waiting time.");
            }
        }
    }
}


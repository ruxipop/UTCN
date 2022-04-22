package controller;

import model.SimulationManager;
import view.SimulationFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SimulationController {
    private SimulationFrame simulationFrame;

    public SimulationController(SimulationFrame simulationFrame) {
        this.simulationFrame = simulationFrame;
        simulationFrame.simulateLinstener(new SimulateListener());
        simulationFrame.stopLinstener(new StopListener());

    }


    class SimulateListener implements ActionListener {


        Integer no_of_clients = 0, no_of_queues = 0, simulate_dur = 0, arrive_min = 0, arrive_max = 0, service_min = 0, service_max = 0;
        boolean error = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                no_of_clients = Integer.parseInt(simulationFrame.getNbClients());
            } catch (NumberFormatException e1) {
                simulationFrame.showError("Invalid no_of_clients");
                error = true;
            }
            try {
                no_of_queues = Integer.parseInt(simulationFrame.getNbQueues());
            } catch (NumberFormatException e1) {
                simulationFrame.showError("Invalid no_of_queues");
                error = true;
            }
            try {
                simulate_dur = Integer.parseInt(simulationFrame.getSimTime());
            } catch (NumberFormatException e1) {
                simulationFrame.showError("Invalid simulation Time");
                error = true;
            }
            try {
                arrive_min = Integer.parseInt(simulationFrame.getMinArv());
            } catch (NumberFormatException e1) {
                simulationFrame.showError("Invalid  Arrival min time");
                error = true;
            }
            try {
                arrive_max = Integer.parseInt(simulationFrame.getMaxArv());
            } catch (NumberFormatException e1) {
                simulationFrame.showError("Invalid  Arrival max time");
                error = true;
            }
            try {
                service_min = Integer.parseInt(simulationFrame.getMinServ());
            } catch (NumberFormatException e1) {
                simulationFrame.showError("Invalid  Service  mine time");
                error = true;
            }
            try {
                service_max = Integer.parseInt(simulationFrame.getMaxServ());
            } catch (NumberFormatException e1) {
                simulationFrame.showError("Invalid Service max time");
                error = true;
            }


            if (no_of_clients <= 0 || no_of_queues < 1 || arrive_min < 0 ||
                    arrive_min > arrive_max || service_min < 0 || service_min > service_max || simulate_dur < 0) {
                simulationFrame.showError("Ordine incorecta");
                error = true;

            }
            if (!error) {
                simulationFrame.setInvisible();
                SimulationManager simulation = new SimulationManager(simulationFrame, no_of_clients, no_of_queues, arrive_min, arrive_max, service_min, service_max, simulate_dur);
                if (simulation == null) return;
                simulation.createOutFile();
                Thread thread = new Thread(simulation);
                thread.setName("Simulation ");
                thread.start();

            }
        }
    }

    class StopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);


        }
    }

}

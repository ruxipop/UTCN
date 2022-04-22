package main;

import controller.SimulationController;
import view.SimulationFrame;

public class SimulationClass {
    public static void main(String[] args) {
        SimulationFrame simulationFrame = new SimulationFrame();
        SimulationController simulationController = new SimulationController(simulationFrame);
        simulationFrame.setVisible(true);

    }
}

import org.junit.Test;
import view.*;
import model.SimulationManager;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestMain {
    @Test
    public void test_1() {
        try {
            SimulationFrame simulationFrame = new SimulationFrame();
            SimulationManager simulation = new SimulationManager(simulationFrame, 4, 2, 2, 30, 2, 4, 60);
            simulation.createOutFile();
            Thread thread = new Thread(simulation);
            thread.start();
            thread.join();
            assertTrue("No errors", true);
        } catch (InterruptedException ex) {
            fail("Errors ");
        }
    }

    @Test
    public void test_2() {
        try {
            SimulationFrame simulationFrame = new SimulationFrame();
            SimulationManager simulation = new SimulationManager(simulationFrame, 50, 5, 2, 40, 1, 7, 60);
            simulation.createOutFile();
            Thread thread = new Thread(simulation);
            thread.start();
            thread.join();
            assertTrue("No errors", true);
        } catch (InterruptedException ex) {
            fail("Errors ");
        }
    }

    @Test
    public void test_3() {
        try {
            SimulationFrame simulationFrame = new SimulationFrame();
            SimulationManager simulation = new SimulationManager(simulationFrame, 1000, 20, 10, 100, 3, 9, 200);
            simulation.createOutFile();
            Thread thread = new Thread(simulation);
            thread.start();
            thread.join();
            assertTrue("No errors", true);
        } catch (InterruptedException ex) {
            fail("Errors ");
        }
    }
}

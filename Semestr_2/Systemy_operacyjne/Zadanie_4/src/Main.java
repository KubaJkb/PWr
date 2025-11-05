import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Integer> processesSizes = new ArrayList<>(List.of(100, 100, 50, 150, 200, 100, 50, 200, 200, 100));
        List<Process> processes = new ArrayList<>();
        int i = 1;
        for (Integer size : processesSizes) {
            processes.add(new Process(i, i + size - 1));
            i += size;
        }

        Simulation sim = new Simulation(processes, 500, 10000);
//        System.out.println(sim.references);

        System.out.println("\nLiczba braków strony dla PRZYDZIAŁU PROPORCJONALNEGO:");
        sim.proportional();
        System.out.println("\nLiczba braków strony dla PRZYDZIAŁU RÓWNEGO:");
        sim.equal();
        System.out.println("\nLiczba braków strony dla STEROWANIA CZĘSTOŚCIĄ BŁĘDÓW:");
        sim.pageErrorRate();
        System.out.println("\nLiczba braków strony dla MODELU STREFOWEGO:");
        sim.zoneModel();

        System.out.println();
    }
}

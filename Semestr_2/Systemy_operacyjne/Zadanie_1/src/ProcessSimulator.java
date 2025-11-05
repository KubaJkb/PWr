import java.util.*;

public class ProcessSimulator {

    private static final int NUM_TESTS = 50;
    private static final int NUM_PROCESSES = 100;
    private static final int MIN_BURST_TIME = 1;
    private static final int MAX_BURST_TIME = 20;
    private static final int MAX_ARRIVAL_TIME = 50;
//    private static final int TIME_QUANTUM = 13;

    public static void main(String[] args) {
        List<SchedulingAlgorithm> algorithms = new ArrayList<>();
        algorithms.add(new FCFS());
        algorithms.add(new SJF());
        algorithms.add(new SRTF());
        algorithms.add(new RoundRobin(5));
        algorithms.add(new RoundRobin(13));
        algorithms.add(new RoundRobin(20));

        List<Double> FCFStotalWaitingTime = new ArrayList<>();
        List<Double> SJFavgWaitingTimePerProcess = new ArrayList<>();
        List<Double> SRTFavgWaitingTimePerProcess = new ArrayList<>();
        List<Double> RRavgWaitingTimePerProcess = new ArrayList<>();
        List<Double> RR2avgWaitingTimePerProcess = new ArrayList<>();
        List<Double> RR3avgWaitingTimePerProcess = new ArrayList<>();

        for (int i = 0; i < NUM_TESTS; i++) {
            List<Process> processes = generateProcesses();
            List<Double> avgTime = new ArrayList<>();

            for (SchedulingAlgorithm algorithm : algorithms) {
                avgTime.add(runSimulation(algorithm, processes) / NUM_PROCESSES);
            }

            FCFStotalWaitingTime.add(avgTime.get(0));
            SJFavgWaitingTimePerProcess.add(avgTime.get(1));
            SRTFavgWaitingTimePerProcess.add(avgTime.get(2));
            RRavgWaitingTimePerProcess.add(avgTime.get(3));
            RR2avgWaitingTimePerProcess.add(avgTime.get(4));
            RR3avgWaitingTimePerProcess.add(avgTime.get(5));
        }

        System.out.printf("Average Waiting Time for %38s: %12f\n", algorithms.get(0).getName(), calculateAverage(FCFStotalWaitingTime));
        System.out.printf("Average Waiting Time for %38s: %12f\n", algorithms.get(1).getName(), calculateAverage(SJFavgWaitingTimePerProcess));
        System.out.printf("Average Waiting Time for %38s: %12f\n", algorithms.get(2).getName(), calculateAverage(SRTFavgWaitingTimePerProcess));
        System.out.printf("Average Waiting Time for %38s: %12f\n", algorithms.get(3).getName(), calculateAverage(RRavgWaitingTimePerProcess));
        System.out.printf("Average Waiting Time for %38s: %12f\n", algorithms.get(4).getName(), calculateAverage(RR2avgWaitingTimePerProcess));
        System.out.printf("Average Waiting Time for %38s: %12f\n", algorithms.get(5).getName(), calculateAverage(RR3avgWaitingTimePerProcess));
    }

    public static double calculateAverage(List<Double> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0.0; // Return 0 if the list is empty or null
        }

        double sum = 0.0;
        for (double num : numbers) {
            sum += num; // Summing up all the numbers in the list
        }

        return sum / numbers.size(); // Calculating the average
    }

    private static List<Process> generateProcesses() {
        List<Process> processes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < NUM_PROCESSES; i++) {
            int burstTime = random.nextInt(MAX_BURST_TIME - MIN_BURST_TIME + 1) + MIN_BURST_TIME;
            int arrivalTime = random.nextInt(MAX_ARRIVAL_TIME + 1); // Arrival times can overlap for queue formation
            processes.add(new Process(i, burstTime, arrivalTime));
        }
        return processes;
    }

    private static double runSimulation(SchedulingAlgorithm algorithm, List<Process> processesList) {
        List<Process> processes = new ArrayList<>();
        for (Process proc : processesList) {
            processes.add(proc.clone());
        }

        double totalWaitingTime = 0;

        algorithm.schedule(processes);

        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
        }

        return totalWaitingTime;
    }

    public interface SchedulingAlgorithm {
        String getName();

        void schedule(List<Process> processes);
    }

    public static class FCFS implements SchedulingAlgorithm {

        @Override
        public String getName() {
            return "FCFS (First Come First Served)";
        }

        @Override
        public void schedule(List<Process> processes) {
            processes.sort(Comparator.comparingInt(Process::getArrivalTime));
            int currentTime = 0;
            for (Process process : processes) {
                process.setWaitingTime(Math.max(0, currentTime - process.getArrivalTime()));
                currentTime = Math.max(currentTime, process.getArrivalTime()) + process.getBurstTime();
            }
        }
    }

    public static class SJF implements SchedulingAlgorithm {

        @Override
        public String getName() {
            return "SJF (Shortest Job First)";
        }

        @Override
        public void schedule(List<Process> processes) {
            List<Process> remaining = new ArrayList<>(processes);
            remaining.sort(Comparator.comparing(Process::getArrivalTime));
            List<Process> queue = new ArrayList<>();

            int currentTime = 0;

            for (int n = 0; n < processes.size(); n++) {
                // Dodawanie elementów które pojawiły się od rozpoczęcia poprzedniego procesu
                for (int i = 0; (i < remaining.size()) && (remaining.get(i).getArrivalTime() <= currentTime); i++) {
                    queue.add(remaining.remove(i));
                }

                // Dodawanie najszybciej pojawiajacych się elementów jeżeli kolejka jest pusta
                if (queue.isEmpty()) {
                    currentTime = remaining.getFirst().getArrivalTime();
                    for (int i = 0; (i < remaining.size()) && (remaining.get(i).getArrivalTime() <= currentTime); i++) {
                        queue.add(remaining.remove(i));
                    }
                }

                // Wybieranie obecnie wykonywanego procesu
                queue.sort(Comparator.comparing(Process::getBurstTime));
                Process current = queue.removeFirst();

                // Aktualizowanie czasu po wykonaniu obecnego procesu
                currentTime += current.getBurstTime();

                // Ustawianie czasu oczekiwania dla obecnego procesu
                processes.get(findProcessIndex(processes, current.getId())).setWaitingTime(currentTime - current.getArrivalTime());
            }
        }
    }

    public static class SRTF implements SchedulingAlgorithm {

        @Override
        public String getName() {
            return "SRTF (Shortest Remaining Time First)";
        }

        @Override
        public void schedule(List<Process> processes) {
            processes.sort(Comparator.comparing(Process::getArrivalTime));
            List<Process> remaining = new ArrayList<>(processes);
            List<Process> queue = new ArrayList<>();

            int currentTime = remaining.getFirst().getArrivalTime();

            // Parametry dotyczące obecnie wykonywanego procesu
            Process current = new Process(-1, 999999, 999999);
            int startTime = 0;

            // Pętla podczas pojawiania się kolejnych procesów
            for (Process process : processes) {
                currentTime = process.getArrivalTime();

                current.reduceRemainingBurstTime(currentTime - startTime);
                if (current.getRemainingBurstTime() == 0) {
                    queue.remove(current);
                }

                for (int i = 0; (i < remaining.size()) && (remaining.get(i).getArrivalTime() <= currentTime); i++) {
                    queue.add(remaining.remove(i));
                }
                queue.sort(Comparator.comparing(Process::getRemainingBurstTime));

                current = queue.getFirst();
                startTime = currentTime;

                int waitingTime = currentTime - current.getArrivalTime() - (current.getBurstTime() - current.getRemainingBurstTime());
                processes.get(findProcessIndex(processes, current.getId())).setWaitingTime(waitingTime);
            }

            // Dokończenie wszystkich pozostałych procesów
            while (!queue.isEmpty()) {
                currentTime += current.getRemainingBurstTime();

                current = queue.removeFirst();

                int waitingTime = currentTime - current.getArrivalTime() - (current.getBurstTime() - current.getRemainingBurstTime());
                processes.get(findProcessIndex(processes, current.getId())).setWaitingTime(waitingTime);
            }
        }
    }

    public static class RoundRobin implements SchedulingAlgorithm {

        private final int timeQuantum;

        public RoundRobin(int timeQuantum) {
            this.timeQuantum = timeQuantum;
        }

        @Override
        public String getName() {
            return "Rotary (Round Robin) [kwant czasu " + timeQuantum + "]";
        }

        @Override
        public void schedule(List<Process> processes) {
            processes.sort(Comparator.comparing(Process::getArrivalTime));
            List<Process> remaining = new ArrayList<>(processes);
            Queue<Process> queue = new LinkedList<>();
            Queue<Process> secondQueue = new LinkedList<>();

            int currentTime = 0;

            while (!remaining.isEmpty() || !queue.isEmpty() || !secondQueue.isEmpty()) {
                // Dodawanie kolejnych elementów do kolejki
                for (int i = 0; i < remaining.size() && remaining.get(i).getArrivalTime() <= currentTime; i++) {
                    queue.add(remaining.remove(i));
                }

                // Przyspieszanie czasu gdy kolejka jest pusta
                if (queue.isEmpty() && secondQueue.isEmpty()) {
                    currentTime = remaining.getFirst().getArrivalTime();
                    continue;
                }
                // Gdy każdy proces wykona się przynajmniej raz to przepisywane są niedokończone procesy do głównej kolejki
                else if (queue.isEmpty()) {
                    queue.offer(secondQueue.poll());
                }

                // Przypisywanie procesora do kolejnego procesu
                Process current = queue.poll();
                assert current != null;

                // Obliczanie postępu czasu w tym kroku
                int executionTime = Math.min(timeQuantum, current.getRemainingBurstTime());
                current.reduceRemainingBurstTime(executionTime);
                currentTime += executionTime;

                if (current.getRemainingBurstTime() > 0) {
                    secondQueue.offer(current);
                } else {
                    current.setWaitingTime(currentTime - current.getArrivalTime() - current.getBurstTime());
                }
            }
        }
    }

    public static int findProcessIndex(List<Process> list, int id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return i; // Return the index of the element if found
            }
        }
        return -1; // Return -1 if the element is not found in the list
    }

}
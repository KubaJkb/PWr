import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Simulation {

    List<Processor> processors;
    List<Task> tasksQueue;
    int N;  // LICZBA PROCESÓW
    int p;  // WARTOŚĆ PROGOWA P - MAKSYMALNE OBIĄŻENIE
    int r;  // PRÓG R - MINIMALNE OBCIĄŻENIE
    int z;  // MAKSYMALNA LICZBA LOSOWAŃ PROCESU

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");


    public Simulation(int N, int p, int r, int z, int numOfTasks, int maxTaskLoad) {
        this.processors = new ArrayList<>();
        this.tasksQueue = new LinkedList<>();
        this.N = N;
        this.p = p;
        this.r = r;
        this.z = z;

        for (int i = 0; i < N; i++) {
            processors.add(new Processor());
        }

        for (int i = 0; i < numOfTasks; i++) {
            tasksQueue.add(new Task(maxTaskLoad));
        }
    }


    public void strategy1(ArrayList<Task> tasks1, ArrayList<Processor> processors1) {
        int tasksSize = tasks1.size();
        int queries = 0, migrations = 0;

        ArrayList<Integer> averageLoadList = new ArrayList<>(), deviationList = new ArrayList<>();

        while (!tasks1.isEmpty()) {
            Random rand = new Random();

            List<Integer> remainingToBeDrawn = new ArrayList<>(); // Lista procesorów z których wybieramy proc X i Y
            for (int i = 0; i < N; i++) {
                remainingToBeDrawn.add(i);
            }
            Processor processorX = processors1.get(remainingToBeDrawn.remove(rand.nextInt(N)));
            Task currentTask = tasks1.getFirst(); // Pobranie kolejnego procesu z kolejki
            boolean found = false; // Czy znaleziono proces y
            int time = 0; // Czas

            for (int i = 0; !found && i < z; i++) {
                Processor processorY = processors1.get(remainingToBeDrawn.remove(rand.nextInt(remainingToBeDrawn.size())));
                // Wylosowanie procesora y (który musi być inny)

                if (processorY.load < p) {
                    processorY.addTask(currentTask); // Znaleziono proc Y spełniający warunek <P
                    tasks1.removeFirst();

                    found = true;
                    migrations++;
                }
                queries++;
                time++;
            }

            if (!found) { // Nie znaleziono proc Y
                if (processorX.load < 100) {
                    processorX.addTask(currentTask);
                    tasks1.removeFirst();
                }
            }

            for (Processor proc : processors1) { // Wykonywanie wszystkich tasków
                for (Task task : proc.tasks) {
                    task.executionTime = task.executionTime - time;
                    if (task.isDone()) {
                        proc.load -= task.taskLoad;
                    }
                }
                proc.tasks.removeIf(task -> task.executionTime <= 0); // Usuwanie ukończonych tasków
            }

            if (tasks1.size() % (tasksSize / 50) == 0) { // Co 1/50 listy tasków
                int loadSum = 0;
                for (Processor proc : processors1) {
                    loadSum += proc.load;
                }
                averageLoadList.add(loadSum / N); // Zapisujemy średnie obciążenie procesorów

                int deviationSum = 0;
                for (Processor proc : processors1) {
                    deviationSum += Math.abs((loadSum/N) - proc.load);
                }
                deviationList.add(deviationSum / N);
            }
        }

        double averageLoad = 0, deviation = 0; // Obliczanie średniego obciążenia procesorów
        for (int i = 0; i < averageLoadList.size(); i++) {
            averageLoad += averageLoadList.get(i);
            deviation += deviationList.get(i);
        }
        averageLoad = averageLoad / averageLoadList.size();
        deviation = deviation / deviationList.size();

        System.out.println("Strategia 1:");
        System.out.println("Srednie obciążenie: " + DECIMAL_FORMAT.format(averageLoad) + "%");
        System.out.println("Odchylenie: " + DECIMAL_FORMAT.format(deviation) + "%");
        System.out.println("Zapytania: " + queries);
        System.out.println("Migracje: " + migrations + "\n");
    }

    public void strategy2(ArrayList<Task> tasks2, ArrayList<Processor> processors2) {
        int tasksSize = tasks2.size();
        int queries = 0, migrations = 0;

        ArrayList<Integer> averageLoadList = new ArrayList<>(), deviationList = new ArrayList<>();

        while (!tasks2.isEmpty()) {
            Random rand = new Random();

            List<Integer> remainingToBeDrawn = new ArrayList<>(); // Lista procesorów z których wybieramy proc X i Y
            for (int i = 0; i < N; i++) {
                remainingToBeDrawn.add(i);
            }
            Processor processorX = processors2.get(remainingToBeDrawn.remove(rand.nextInt(N)));
            Task currentTask = tasks2.getFirst(); // Pobranie kolejnego procesu z kolejki
            boolean found = false; // Czy znaleziono proces y
            int time = 0; // Czas

            if (processorX.load + currentTask.taskLoad > p) {
                int remToBeDrawnSize = remainingToBeDrawn.size();
                for (int i = 0; !found && i < remToBeDrawnSize; i++) {
                    Processor processorY = processors2.get(remainingToBeDrawn.remove(rand.nextInt(remainingToBeDrawn.size())));
                    // Wylosowanie procesora y (który musi być inny)

                    if (processorY.load <= p) {
                        processorY.addTask(currentTask); // Znaleziono proc Y spełniający warunek <=P
                        tasks2.removeFirst();

                        found = true;
                        migrations++;
                    }
                    queries++;
                    time++;
                }
            }
            // Nie przekroczono progu X lub przekroczono ale nie znaleziono proc Y
            if (processorX.load + currentTask.taskLoad <= p || !found) {
                if (processorX.load < 100) {
                    processorX.addTask(currentTask);
                    tasks2.removeFirst();
                }
            }

            for (Processor proc : processors2) { // Wykonywanie wszystkich tasków
                for (Task task : proc.tasks) {
                    task.executionTime = task.executionTime - time;
                    if (task.isDone()) {
                        proc.load -= task.taskLoad;
                    }
                }
                proc.tasks.removeIf(task -> task.executionTime <= 0); // Usuwanie ukończonych tasków
            }

            if (tasks2.size() % (tasksSize / 50) == 0) { // Co 1/50 listy tasków
                int loadSum = 0;
                for (Processor proc : processors2) {
                    loadSum += proc.load;
                }
                averageLoadList.add(loadSum / N); // Zapisujemy średnie obciążenie procesorów

                int deviationSum = 0;
                for (Processor proc : processors2) {
                    deviationSum += Math.abs((loadSum/N) - proc.load);
                }
                deviationList.add(deviationSum / N);
            }
        }

        double averageLoad = 0, deviation = 0; // Obliczanie średniego obciążenia procesorów
        for (int i = 0; i < averageLoadList.size(); i++) {
            averageLoad += averageLoadList.get(i);
            deviation += deviationList.get(i);
        }
        averageLoad = averageLoad / averageLoadList.size();
        deviation = deviation / averageLoadList.size();

        System.out.println("Strategia 2:");
        System.out.println("Srednie obciążenie: " + DECIMAL_FORMAT.format(averageLoad) + "%");
        System.out.println("Odchylenie: " + DECIMAL_FORMAT.format(deviation) + "%");
        System.out.println("Zapytania: " + queries);
        System.out.println("Migracje: " + migrations + "\n");
    }

    public void strategy3(ArrayList<Task> tasks3, ArrayList<Processor> processors3) {
        int tasksSize = tasks3.size();
        int queries = 0, migrations = 0;

        ArrayList<Integer> averageLoadList = new ArrayList<>(), deviationList = new ArrayList<>();

        while (!tasks3.isEmpty()) {
            Random rand = new Random();

            List<Integer> remainingToBeDrawn = new ArrayList<>(); // Lista procesorów z których wybieramy proc X i Y
            for (int i = 0; i < N; i++) {
                remainingToBeDrawn.add(i);
            }
            Processor processorX = processors3.get(remainingToBeDrawn.remove(rand.nextInt(N)));
            Task currentTask = tasks3.getFirst(); // Pobranie kolejnego procesu z kolejki
            boolean found = false; // Czy znaleziono proces y
            int time = 0; // Czas

            if (processorX.load + currentTask.taskLoad > p) {
                int remToBeDrawnSize = remainingToBeDrawn.size();
                for (int i = 0; !found && i < remToBeDrawnSize; i++) {
                    Processor processorY = processors3.get(remainingToBeDrawn.remove(rand.nextInt(remainingToBeDrawn.size())));
                    queries++;
                    time++;
                    // Wylosowanie procesora y (który musi być inny)

                    if (processorY.load <= p) {
                        processorY.addTask(currentTask); // Znaleziono proc Y spełniający warunek <=P
                        tasks3.removeFirst();

                        found = true;
                        migrations++;
                    }
                }
            }
            // Nie przekroczono progu X lub przekroczono ale nie znaleziono proc Y
            if (processorX.load + currentTask.taskLoad <= p || !found) {
                if (processorX.load < 100) {
                    processorX.addTask(currentTask);
                    tasks3.removeFirst();
                }
            }


            if (tasks3.size() % (tasksSize / 20000) == 0) { // Co 1/20000 listy procesów
                time++;
                for (int i = 0; i < N; i++) { // Przeglądamy wszystkie procesy
                    Processor procGiveTo = processors3.get(i);

                    if (procGiveTo.load < r) { // Jeżeli procesor ma obciążenie <r
                        remainingToBeDrawn = new ArrayList<>();
                        for (int j = 0; j < N; j++) {
                            remainingToBeDrawn.add(j);
                        }
                        remainingToBeDrawn.remove(i);

                        Processor procTakeFrom = null;
                        int remToBeDrawnSize = remainingToBeDrawn.size();
                        for (int j = 0; j < remToBeDrawnSize; j++) {
                            queries++;
                            Processor proc = processors3.get(remainingToBeDrawn.remove(rand.nextInt(remainingToBeDrawn.size())));
                            if (proc.load > p) { // Szukamy procesora o obciążeniu >p
                                procTakeFrom = proc;
                                break;
                            }
                        }

                        if (procTakeFrom != null) { // Jeżeli znaleźliśmy procesor o obciążeniu >p
                            while (procTakeFrom.load > procGiveTo.load) { // Dopóki obciążenia procesorów nie będą w miarę wyrównane
                                Task migratingTask = procTakeFrom.tasks.removeLast();
                                procTakeFrom.load -= migratingTask.taskLoad; // Odbieranie taska obciążonemu procesorowi

                                procGiveTo.addTask(migratingTask); // Przekazywanie taska drugiemu procesorowi

                                migrations++;
                            }
                        }
                    }
                }
            }

            for (Processor proc : processors3) { // Wykonywanie wszystkich tasków
                for (Task task : proc.tasks) {
                    task.executionTime = task.executionTime - time;
                    if (task.isDone()) {
                        proc.load -= task.taskLoad;
                    }
                }
                proc.tasks.removeIf(task -> task.executionTime <= 0); // Usuwanie ukończonych tasków
            }

            if (tasks3.size() % (tasksSize / 50) == 0) { // Co 1/50 listy tasków
                int loadSum = 0;
                for (Processor proc : processors3) {
                    loadSum += proc.load;
                }
                averageLoadList.add(loadSum / N); // Zapisujemy średnie obciążenie procesorów

                int deviationSum = 0;
                for (Processor proc : processors3) {
                    deviationSum += Math.abs((loadSum/N) - proc.load);
                }
                deviationList.add(deviationSum / N);
            }
        }

        double averageLoad = 0, deviation = 0; // Obliczanie średniego obciążenia procesorów
        for (int i = 0; i < averageLoadList.size(); i++) {
            averageLoad += averageLoadList.get(i);
            deviation += deviationList.get(i);
        }
        averageLoad = averageLoad / averageLoadList.size();
        deviation = deviation / averageLoadList.size();

        System.out.println("Strategia 3:");
        System.out.println("Srednie obciążenie: " + DECIMAL_FORMAT.format(averageLoad) + "%");
        System.out.println("Odchylenie: " + DECIMAL_FORMAT.format(deviation) + "%");
        System.out.println("Zapytania: " + queries);
        System.out.println("Migracje: " + migrations + "\n");
    }

    public void run() {
        ArrayList<Task> tasks1 = new ArrayList<>();
        ArrayList<Task> tasks2 = new ArrayList<>();
        ArrayList<Task> tasks3 = new ArrayList<>();

        ArrayList<Processor> processors1 = new ArrayList<>();
        ArrayList<Processor> processors2 = new ArrayList<>();
        ArrayList<Processor> processors3 = new ArrayList<>();

        for (Task task : tasksQueue) {
            tasks1.add(new Task(task));
            tasks2.add(new Task(task));
            tasks3.add(new Task(task));
        }

        for (Processor proc : processors) {
            processors1.add(new Processor(proc));
            processors2.add(new Processor(proc));
            processors3.add(new Processor(proc));
        }

        this.strategy1(tasks1, processors1);
        this.strategy2(tasks2, processors2);
        this.strategy3(tasks3, processors3);
    }

}
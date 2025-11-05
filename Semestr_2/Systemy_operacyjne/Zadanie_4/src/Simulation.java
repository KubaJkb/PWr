import java.util.*;

public class Simulation {
    public List<Process> processes;
    public List<Page> references;
    public Queue<Frame> availableFrames;
    public int NUMBER_OF_FRAMES;
    public int NUMBER_OF_PAGES;
    public int currentTime;

    public int NUMBER_OF_REFERENCES;

    public double minPFF = 0.3;
    public double maxPFF = 0.5;
    public List<Double> PFFs = new ArrayList<>();
    public int tPFF = 100;

    public int D;
    public int tWSS = 800;


    Simulation(List<Process> processes, int NUMBER_OF_FRAMES, int NUMBER_OF_REFERENCES) {
        this.processes = processes;
        this.NUMBER_OF_FRAMES = NUMBER_OF_FRAMES;
        this.NUMBER_OF_REFERENCES = NUMBER_OF_REFERENCES;

        for (Process p : processes) {
            NUMBER_OF_PAGES += p.pageRange;
            PFFs.add(0.5);
        }

        // SPOSÓB GENEROWANIA CIĄGU REFERENCJI
//        references = createListOfReferences();
        references = createListOfReferences_Other();

        availableFrames = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_FRAMES; i++) {
            availableFrames.add(new Frame());
        }


    }

    private void resetSim() {
        availableFrames = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_FRAMES; i++) {
            availableFrames.add(new Frame());
        }

        for (Process p : processes) {
            p.frames = new ArrayList<>();
            p.timeTable = new LinkedList<>();
            p.NoMPtimeTable = new LinkedList<>();
        }
    }

    private List<Page> createListOfReferences() {
        ArrayList<Page> listOfReferences = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < NUMBER_OF_REFERENCES; i++) {
            int currentProcess = rand.nextInt(processes.size());

            int n = rand.nextInt(10); // Losujemy ile lokalnych odwołań do danego procesu
            i += n;

            int k = rand.nextInt(processes.get(currentProcess).pageRange) + processes.get(currentProcess).firstPageNr; // Losujemy pierwsze odwołanie
            for (int j = 0; j < n; j++) {
                listOfReferences.add(new Page(k));

                int difference = rand.nextInt(3) - 1;
                k += difference;

                if (k > processes.get(currentProcess).lastPageNr) {
                    k--;
                } else if (k < processes.get(currentProcess).firstPageNr) {
                    k++;
                }
            }
        }

        return listOfReferences;
    }

    private List<Page> createListOfReferences_Other() {
        ArrayList<Page> listOfReferences = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < NUMBER_OF_REFERENCES; i++) {
            int currentProcess = rand.nextInt(processes.size());

            int n = rand.nextInt(10); // Losujemy ile odwołań do danego procesu
            i += n;

            for (int j = 0; j <= n; j++) {
                listOfReferences.add(new Page(rand.nextInt(processes.get(currentProcess).pageRange) + processes.get(currentProcess).firstPageNr));
            }
        }

        return listOfReferences;
    }

    public void proportional() {
        resetSim();

        // Przydzielanie procesom odpowiedniej ilości ramek w zależności od liczby stron poszczególnych procesów
        for (Process process : processes) {
            for (int i = 0; i < process.pageRange * NUMBER_OF_FRAMES / NUMBER_OF_PAGES; i++) {
                process.addFrame(availableFrames.poll());
            }
        }

        int[] NoMP = new int[processes.size()];
        currentTime = -1;

        for (Page ref : references) {
            currentTime++;
            ref.timeLastUsed = currentTime;

            int currentProcessNr = 0;
            for (int j = 0; j < processes.size(); j++) {
                if (ref.reference >= processes.get(j).firstPageNr && ref.reference <= processes.get(j).lastPageNr) {
                    currentProcessNr = j;
                    break;
                }
            }
            Process currentProcess = processes.get(currentProcessNr);

            if (currentProcess.LRU(ref)) {
                NoMP[currentProcessNr]++;
            }
        }

        int sum = 0;
        for (int i = 0; i < processes.size(); i++) {
            System.out.print("P" + i + ": " + NoMP[i] + "   ");
            sum += NoMP[i];
        }
        System.out.println("\nŁącznie błędów stron: " + sum);
    }

    public void equal() {
        resetSim();

        // Przydzielanie procesom odpowiedniej ilości ramek po równo
        for (Process process : processes) {
            for (int i = 0; i < NUMBER_OF_FRAMES / processes.size(); i++) {
                process.addFrame(availableFrames.poll());
            }
        }

        int[] NoMP = new int[processes.size()];
        currentTime = -1;

        for (Page ref : references) {
            currentTime++;
            ref.timeLastUsed = currentTime;

            int currentProcessNr = 0;
            for (int j = 0; j < processes.size(); j++) {
                if (ref.reference >= processes.get(j).firstPageNr && ref.reference <= processes.get(j).lastPageNr) {
                    currentProcessNr = j;
                    break;
                }
            }
            Process currentProcess = processes.get(currentProcessNr);

            if (currentProcess.LRU(ref)) {
                NoMP[currentProcessNr]++;
            }
        }

        int sum = 0;
        for (int i = 0; i < processes.size(); i++) {
            System.out.print("P" + i + ": " + NoMP[i] + "   ");
            sum += NoMP[i];
        }
        System.out.println("\nŁącznie błędów stron: " + sum);
    }

    public void pageErrorRate() {
        resetSim();

        // Przydzielanie procesom odpowiedniej ilości ramek po równo
        for (Process process : processes) {
            for (int i = 0; i < NUMBER_OF_FRAMES / processes.size(); i++) {
                process.addFrame(availableFrames.poll());
            }
        }

        int[] NoMP = new int[processes.size()];
        currentTime = -1;

        for (int i = 0; i < references.size(); i++) {
            currentTime++;
            references.get(i).timeLastUsed = currentTime;

            int currentProcessNr = 0;
            for (int j = 0; j < processes.size(); j++) {
                if (references.get(i).reference >= processes.get(j).firstPageNr && references.get(i).reference <= processes.get(j).lastPageNr) {
                    currentProcessNr = j;
                    break;
                }
            }
            Process currentProcess = processes.get(currentProcessNr);

            currentProcess.timeTable.add(currentTime);
            if (currentProcess.LRU(references.get(i))) {
                NoMP[currentProcessNr]++;
                currentProcess.NoMPtimeTable.add(currentTime);
            }

            // JEŻELI NIE MA WYSTARCZAJĄCO ODWOŁAŃ DO OBLICZENIA PFF TO CONTINUE
            if (currentProcess.timeTable.size() < tPFF) {
                continue;
            }

            // ZACHOWYWANIE TYLKO OSTATNICH tPFF ODWOŁAŃ DLA KAŻDEGO PROCESU
            while (currentProcess.timeTable.size() > tPFF) {
                while (!currentProcess.NoMPtimeTable.isEmpty() && currentProcess.NoMPtimeTable.getFirst() <= currentProcess.timeTable.getFirst()) {
                    currentProcess.NoMPtimeTable.removeFirst();
                }
                currentProcess.timeTable.removeFirst();
            }

            // OBLICZANIE PFF
            PFFs.set(currentProcessNr, ((double) currentProcess.NoMPtimeTable.size() / (double) tPFF));

            // ZABIERANIE LUB DAWANIE RAMKI W ZALEŻNOŚCI OD PFF
            if (PFFs.get(currentProcessNr) < minPFF) {
                if (currentProcess.stealFrame()) {
                    availableFrames.add(new Frame());
                }
                currentProcess.timeTable = new LinkedList<>();
                currentProcess.NoMPtimeTable = new LinkedList<>();
            } else if (PFFs.get(currentProcessNr) > maxPFF) {
                if (!availableFrames.isEmpty()) {
                    currentProcess.addFrame(availableFrames.poll());
                }
                currentProcess.timeTable = new LinkedList<>();
                currentProcess.NoMPtimeTable = new LinkedList<>();
            }

        }

        int sum = 0;
        for (int i = 0; i < processes.size(); i++) {
            System.out.print("P" + i + ": " + NoMP[i] + "   ");
            sum += NoMP[i];
        }
        System.out.println("\nŁącznie błędów stron: " + sum);
    }

    public void zoneModel() {
        resetSim();

        // Przydzielanie procesom odpowiedniej ilości ramek po równo
        for (Process process : processes) {
            for (int i = 0; i < NUMBER_OF_FRAMES / processes.size(); i++) {
                process.addFrame(availableFrames.poll());
            }
        }

        int[] NoMP = new int[processes.size()];
        currentTime = -1;

        for (int i = 0; i < references.size(); i++) {
            currentTime++;
            references.get(i).timeLastUsed = currentTime;

            int currentProcessNr = 0;
            for (int j = 0; j < processes.size(); j++) {
                if (references.get(i).reference >= processes.get(j).firstPageNr && references.get(i).reference <= processes.get(j).lastPageNr) {
                    currentProcessNr = j;
                    break;
                }
            }
            Process currentProcess = processes.get(currentProcessNr);

            currentProcess.timeTable.add(currentTime);
            if (currentProcess.LRU(references.get(i))) {
                NoMP[currentProcessNr]++;
            }

            // OBLICZAMY ZBIORY ROBOCZE TYLKO CO tWSS/2
            if (currentTime > (tWSS * 5) && currentTime % (tWSS / 2) == 0) {

                // OBLICZANIE SUMY WSZYSTKICH ZBIORÓW ROBOCZYCH
                D = 0;
                for (Process p : processes) {
                    p.WSS = new HashSet<>();
                    for (Integer j : p.timeTable) {
                        if (j > currentTime - tWSS) {
                            p.WSS.add(references.get(j).reference);
                        }
                    }
                    D += p.WSS.size();
                }

                // JEŻELI SUMA ZBIORÓW ROBOCZYCH JEST ZA DUŻA, TO WSTRZYMUJEMY PROCESY
                int k = 0;
                while (D > NUMBER_OF_FRAMES) {
                    int lessNeededFrames = processes.get(k).WSS.size();
                    int stoppedFrames = processes.get(k).stopProcess();
                    for (int j = 0; j < stoppedFrames; j++) {
                        availableFrames.add(new Frame());
                    }
                    k++;
                    D -= lessNeededFrames;
                }

                // ZABIERAMY RAMKI JEŻELI OBECNA ILOŚĆ RAMEK PROCESU JEST WIĘKSZA OD ZBIORU ROBOCZEGO
                for (int j = k; j < processes.size(); j++) {
                    while (processes.get(j).frames.size() > processes.get(j).WSS.size()) {
                        if (processes.get(j).stealFrame()) {
                            availableFrames.add(new Frame());
                        }
                    }
                }

                // ODDAJEMY RAMKI JEŻELI OBECNA ILOŚĆ RAMEK PROCESU JEST WIĘKSZA OD ZBIORU ROBOCZEGO
                for (int j = k; j < processes.size(); j++) {
                    while (processes.get(j).frames.size() < processes.get(j).WSS.size()) {
                        if (!availableFrames.isEmpty()) {
                            processes.get(j).addFrame(availableFrames.poll());
                        }
                    }
                }
            }

        }

        int sum = 0;
        for (int i = 0; i < processes.size(); i++) {
            System.out.print("P" + i + ": " + NoMP[i] + "   ");
            sum += NoMP[i];
        }
        System.out.println("\nŁącznie błędów stron: " + sum);
    }

}

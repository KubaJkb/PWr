package SortingTester;

import SortingAlgorithms.MergeSortQueue;
import SortingAlgorithms.QuickSortLomuto;
import SortingAlgorithms.QuickSortOptimized;
import SortingTester.core.AbstractSwappingSortingAlgorithm;
import SortingTester.core.AbstractSortingAlgorithm;
import SortingTester.testing.MarkedValue;
import SortingTester.testing.Tester;
import SortingTester.testing.comparators.IntegerComparator;
import SortingTester.testing.comparators.MarkedValueComparator;
import SortingTester.testing.generation.*;
import SortingTester.testing.generation.conversion.LinkedListGenerator;
import SortingTester.testing.generation.conversion.MarkingGenerator;
import SortingTester.testing.results.swapping.Result;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Comparator<MarkedValue<Integer>> markedComparator = new MarkedValueComparator<>(new IntegerComparator());
        test(new MergeSortQueue<>(markedComparator/*, new QuickSortOptimized.RandomElementPivotSelector<>()*/));

    }

    static void dumbTestMergeSort() {
        List<Integer> intList = new ArrayList<>();
        intList.add(76);
        intList.add(71);
        intList.add(57);
        intList.add(12);
        intList.add(50);
        intList.add(12);
        intList.add(93);
        intList.add(43);
        intList.add(85);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();

        Comparator<Integer> intComp = new IntegerComparator();
        AbstractSortingAlgorithm<Integer> algorithm = new MergeSortQueue<>(intComp);
        intList = algorithm.sort(intList);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void dumbTestQuickSort() {
        List<Integer> intList = new ArrayList<>();
        intList.add(76);
        intList.add(71);
        intList.add(57);
        intList.add(12);
        intList.add(50);
        intList.add(12);
        intList.add(93);
        intList.add(43);
        intList.add(85);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();

        Comparator<Integer> intComp = new IntegerComparator();
        AbstractSortingAlgorithm<Integer> algorithm = new QuickSortLomuto<>(intComp, new QuickSortLomuto.RandomElementPivotSelector<>());
        intList = algorithm.sort(intList);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void test(AbstractSwappingSortingAlgorithm<MarkedValue<Integer>> algorithm) {

        Generator<MarkedValue<Integer>> orderedGenerator = new MarkingGenerator<>(new OrderedIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> reversedGenerator = new MarkingGenerator<>(new ReversedIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> shuffledGenerator = new MarkingGenerator<>(new ShuffledIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> randomGenerator = new MarkingGenerator<>(new RandomIntegerArrayGenerator(1000));

        createFile("orderedResults.txt");
        createFile("reversedResults.txt");
        createFile("shuffledResults.txt");
        createFile("randomResults.txt");

        int repetitions = 20;
        for (int size = 10; size <= 9000; size += 10) {
            if (size > 10000) {
                size += 990;
            } else if (size > 1000) {
                size += 90;
            }

            writeResults("orderedResults.txt", size, Tester.runNTimes(algorithm, orderedGenerator, size, repetitions));
            writeResults("reversedResults.txt", size, Tester.runNTimes(algorithm, reversedGenerator, size, repetitions));
            writeResults("shuffledResults.txt", size, Tester.runNTimes(algorithm, shuffledGenerator, size, repetitions));
            writeResults("randomResults.txt", size, Tester.runNTimes(algorithm, randomGenerator, size, repetitions));
        }

    }

    static void test(AbstractSortingAlgorithm<MarkedValue<Integer>> algorithm) {

        Generator<MarkedValue<Integer>> orderedGenerator = new MarkingGenerator<>(new OrderedIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> reversedGenerator = new MarkingGenerator<>(new ReversedIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> shuffledGenerator = new MarkingGenerator<>(new ShuffledIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> randomGenerator = new MarkingGenerator<>(new RandomIntegerArrayGenerator(1000));

        createFile("orderedResults.txt");
        createFile("reversedResults.txt");
        createFile("shuffledResults.txt");
        createFile("randomResults.txt");

        int repetitions = 20;
        for (int size = 10; size <= 9000; size += 10) {
            if (size > 10000) {
                size += 990;
            } else if (size > 1000) {
                size += 90;
            }

            writeResults("orderedResults.txt", size, Tester.runNTimes(algorithm, orderedGenerator, size, repetitions));
            writeResults("reversedResults.txt", size, Tester.runNTimes(algorithm, reversedGenerator, size, repetitions));
            writeResults("shuffledResults.txt", size, Tester.runNTimes(algorithm, shuffledGenerator, size, repetitions));
            writeResults("randomResults.txt", size, Tester.runNTimes(algorithm, randomGenerator, size, repetitions));
        }

    }

    static void linkedTest(AbstractSwappingSortingAlgorithm<MarkedValue<Integer>> algorithm) {

        Generator<MarkedValue<Integer>> orderedGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(new OrderedIntegerArrayGenerator()));
        Generator<MarkedValue<Integer>> reversedGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(new ReversedIntegerArrayGenerator()));
        Generator<MarkedValue<Integer>> shuffledGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(new ShuffledIntegerArrayGenerator()));
        Generator<MarkedValue<Integer>> randomGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(new RandomIntegerArrayGenerator(1000)));

        createFile("orderedResults.txt");
        createFile("reversedResults.txt");
        createFile("shuffledResults.txt");
        createFile("randomResults.txt");

        int repetitions = 20;
        for (int size = 10; size <= 9000; size += 10) {
            if (size > 10000) {
                size += 990;
            } else if (size > 1000) {
                size += 90;
            }

            writeResults("orderedResults.txt", size, Tester.runNTimes(algorithm, orderedGenerator, size, repetitions));
            writeResults("reversedResults.txt", size, Tester.runNTimes(algorithm, reversedGenerator, size, repetitions));
            writeResults("shuffledResults.txt", size, Tester.runNTimes(algorithm, shuffledGenerator, size, repetitions));
            writeResults("randomResults.txt", size, Tester.runNTimes(algorithm, randomGenerator, size, repetitions));
        }

    }

    static void linkedTest(AbstractSortingAlgorithm<MarkedValue<Integer>> algorithm) {

        Generator<MarkedValue<Integer>> orderedGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(new OrderedIntegerArrayGenerator()));
        Generator<MarkedValue<Integer>> reversedGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(new ReversedIntegerArrayGenerator()));
        Generator<MarkedValue<Integer>> shuffledGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(new ShuffledIntegerArrayGenerator()));
        Generator<MarkedValue<Integer>> randomGenerator = new LinkedListGenerator<>(new MarkingGenerator<>(new RandomIntegerArrayGenerator(1000)));

        createFile("orderedResults.txt");
        createFile("reversedResults.txt");
        createFile("shuffledResults.txt");
        createFile("randomResults.txt");

        int repetitions = 20;
        for (int size = 10; size <= 9000; size += 10) {
            if (size > 10000) {
                size += 990;
            } else if (size > 1000) {
                size += 90;
            }

            writeResults("orderedResults.txt", size, Tester.runNTimes(algorithm, orderedGenerator, size, repetitions));
            writeResults("reversedResults.txt", size, Tester.runNTimes(algorithm, reversedGenerator, size, repetitions));
            writeResults("shuffledResults.txt", size, Tester.runNTimes(algorithm, shuffledGenerator, size, repetitions));
            writeResults("randomResults.txt", size, Tester.runNTimes(algorithm, randomGenerator, size, repetitions));
        }

    }

    private static void writeResults(String fileName, int size, SortingTester.testing.results.swapping.Result result) {
        String data = size + "\t" + result.averageTimeInMilliseconds() + "\t" + result.timeStandardDeviation()
                + "\t" + result.averageComparisons() + "\t" + result.comparisonsStandardDeviation()
                + "\t" + result.averageSwaps() + "\t" + result.swapsStandardDeviation();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void writeResults(String fileName, int size, SortingTester.testing.results.Result result) {
        String data = size + "\t" + result.averageTimeInMilliseconds() + "\t" + result.timeStandardDeviation()
                + "\t" + result.averageComparisons() + "\t" + result.comparisonsStandardDeviation();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void createFile(String fileName) {
        try {
            File myObj = new File(fileName);
            if (!myObj.createNewFile()) {
                myObj.delete();
                myObj.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void testDefault() {
        Comparator<MarkedValue<Integer>> markedComparator = new MarkedValueComparator<>(new IntegerComparator());

        Generator<MarkedValue<Integer>> generator = new MarkingGenerator<>(new RandomIntegerArrayGenerator(10));

        AbstractSwappingSortingAlgorithm<MarkedValue<Integer>> algorithm = new BubbleSort<>(markedComparator);

        Result result = Tester.runNTimes(algorithm, generator, 1000, 50);

        printStatistic("time [ms]", result.averageTimeInMilliseconds(), result.timeStandardDeviation());
        printStatistic("comparisons", result.averageComparisons(), result.comparisonsStandardDeviation());
        printStatistic("swaps", result.averageSwaps(), result.swapsStandardDeviation());

        System.out.println("always sorted: " + result.sorted());
        System.out.println("always stable: " + result.stable());
    }

    private static void printStatistic(String label, double average, double stdDev) {
        System.out.println(label + ": " + double2String(average) + " +- " + double2String(stdDev));
    }

    private static void printResults(Result result) {
        printStatistic("time [ms]", result.averageTimeInMilliseconds(), result.timeStandardDeviation());
        printStatistic("comparisons", result.averageComparisons(), result.comparisonsStandardDeviation());
        printStatistic("swaps", result.averageSwaps(), result.swapsStandardDeviation());

        System.out.println("always sorted: " + result.sorted());
        System.out.println("always stable: " + result.stable());
    }

    private static String double2String(double value) {
        return String.format("%.12f", value);
    }
}

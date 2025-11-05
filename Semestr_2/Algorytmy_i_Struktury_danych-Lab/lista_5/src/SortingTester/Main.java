package SortingTester;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import SortingAlgorithms.InsertSortBinarySearch;
import SortingAlgorithms.SelectSortMinMax;
import SortingAlgorithms.ShakerSort;
import SortingTester.core.AbstractSwappingSortingAlgorithm;
import SortingTester.testing.*;
import SortingTester.testing.comparators.*;
import SortingTester.testing.generation.*;
import SortingTester.testing.generation.conversion.*;
import SortingTester.testing.results.swapping.Result;

public class Main {

    public static void main(String[] args) {
        Comparator<MarkedValue<Integer>> markedComparator = new MarkedValueComparator<>(new IntegerComparator());
        test(new InsertSortBinarySearch<>(markedComparator));
    }

    static void dumbTestInsertSort() {
        List<Integer> intList = new ArrayList<>();
        intList.add(76);
        intList.add(71);
        intList.add(57);
        intList.add(12);
        intList.add(50);
        intList.add(20);
        intList.add(93);
        intList.add(20);
        intList.add(55);
        intList.add(62);
        intList.add(5);
        intList.add(3);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();

        Comparator<Integer> intComp = new IntegerComparator();
        AbstractSwappingSortingAlgorithm<Integer> algorithm = new InsertSortBinarySearch<>(intComp);
        algorithm.sort(intList);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void dumbTestSelectSort() {
        List<Integer> intList = new ArrayList<>();
        intList.add(12);
        intList.add(71);
        intList.add(57);
        intList.add(12);
        intList.add(50);
        intList.add(20);
        intList.add(93);
        intList.add(43);
        intList.add(85);
        intList.add(62);
        intList.add(5);
        intList.add(3);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();

        Comparator<Integer> intComp = new IntegerComparator();
        AbstractSwappingSortingAlgorithm<Integer> algorithm = new SelectSortMinMax<>(intComp);
        algorithm.sort(intList);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void dumbTestShakerSort() {
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
        AbstractSwappingSortingAlgorithm<Integer> algorithm = new ShakerSort<>(intComp);
        algorithm.sort(intList);

        for (Integer i : intList) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    static void test(AbstractSwappingSortingAlgorithm<MarkedValue<Integer>> algorithm) {

        Generator<MarkedValue<Integer>> orderedGenerator = new MarkingGenerator<>(new OrderedIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> reversedGenerator = new MarkingGenerator<>(new ReversedIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> shuffledGenerator = new MarkingGenerator<>(new ShuffledIntegerArrayGenerator());
        Generator<MarkedValue<Integer>> randomGenerator = new MarkingGenerator<>(new RandomIntegerArrayGenerator(100));

        createFile("orderedResults.txt");
        createFile("reversedResults.txt");
        createFile("shuffledResults.txt");
        createFile("randomResults.txt");

        int repetitions = 20;
        for (int size = 10; size < 10000; size += 10) {
            if (size > 1000) {
                size += 990;
            }
            writeResults("orderedResults.txt", size, Tester.runNTimes(algorithm, orderedGenerator, size, repetitions));
            writeResults("reversedResults.txt", size, Tester.runNTimes(algorithm, reversedGenerator, size, repetitions));
            writeResults("shuffledResults.txt", size, Tester.runNTimes(algorithm, shuffledGenerator, size, repetitions));
            writeResults("randomResults.txt", size, Tester.runNTimes(algorithm, randomGenerator, size, repetitions));
        }

    }

    private static void writeResults(String fileName, int size, Result result) {
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

        SortingTester.testing.results.swapping.Result result = Tester.runNTimes(algorithm, generator, 1000, 50);

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

    private static void saveResults(Result result) {

    }

    private static String double2String(double value) {
        return String.format("%.12f", value);
    }
}

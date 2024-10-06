import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class Analyzer {
    @Disabled
    @Test
    public void testThatFileOpens() {
        List<String> linesStream = openInputFile();
        assertFalse(linesStream.isEmpty(), "File not found");
    }

    @Disabled
    @Test
    public void CheckLinesCount() {
        List<String> linesStream = openInputFile();
        System.out.println("total lines count: " + linesStream.size());
        System.out.println("not empty lines count: " + linesStream.stream().filter(line -> !line.isEmpty()).count());
    }

    @Disabled
    @Test
    public void checkCorrectNumbers() {
        List<String> linesStream = openInputFile();
        List<String> wrongNumbers = linesStream.stream()
                .map(str -> str.replace(" ", ""))
                .filter(str -> !str.isEmpty())
                .filter(str -> !Analyzer.isNumeric(str)).collect(Collectors.toList());
        assertTrue(wrongNumbers.isEmpty(), "Not all lines are numbers, we should skip them");
    }

    @Disabled
    @Test
    public void findGreatestNumber() {
        List<String> linesStream = openInputFile();
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        int max = linesStream.stream()
                .map(str -> str.replace(" ", ""))
                .filter(str -> !str.isEmpty())
                .filter(Analyzer::isNumeric)
                .map(Integer::parseInt)
                .max(Integer::compareTo).get();
        System.out.println("Greatest number: " + max);
    }

    public List<String> openInputFile() {
        String fileName = "input.txt";
        Path path = Paths.get(fileName);
        try (Stream<String> linesStream = Files.lines(path)) {

            return linesStream.collect(Collectors.toCollection(LinkedList<String>::new));
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    @Test
    public void testOutputFile() {
        String fileName = "output.txt";
        Path path = Paths.get(fileName);
        try (Stream<String> linesStream = Files.lines(path)) {

            System.out.println(linesStream.collect(Collectors.toCollection(LinkedList<String>::new)).size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testThatAfterAppRunningOutputFileExistsAndContainsSameLinesCountAsInput() {
        String input = "input.txt";
        String output = "output.txt";
        Path path = Paths.get(output);

        assertTrue(Files.exists(path), "Output file not found");
        int inputLinesCount = 0;
        int outputLinesCount = 0;
        try (Stream<String> inputLinesStream = Files.lines(Paths.get(input));
             Stream<String> outputLinesStream = Files.lines(path)) {
            inputLinesCount = (int) inputLinesStream.count();
            outputLinesCount = (int) outputLinesStream.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(inputLinesCount, outputLinesCount, "Input and output files have different lines count");
    }

    @Disabled

    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Disabled

    @Test
    public void factorial9999() {
        BigInteger factorial = BigInteger.ONE;
        long nanoTime = System.nanoTime();
        for (int i = 1; i <= 9999; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        System.out.println("factorial9999: " + factorial);
        System.out.println("factorial9999 time: " + (System.nanoTime() - nanoTime) / 1_000_000_000.0);
        byte[] byteArray = factorial.toByteArray();
        System.out.println("factorial9999 byte array length: " + byteArray.length / 1024 + " kb");
    }
}

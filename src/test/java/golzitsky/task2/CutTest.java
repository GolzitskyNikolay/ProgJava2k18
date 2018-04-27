package golzitsky.task2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import com.google.common.collect.Range;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CutTests {
    private final Cut object = new Cut();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }
    @Test
    void consoleTest() throws IOException {
        System.out.print("hello");
        String range = "2-3";
        Range newRange = Range.closed(Integer.parseInt(range.split("-")[0]),
                Integer.parseInt(range.split("-")[1]));
        object.cut("src\\test\\resources\\for_task2",true,range,newRange,false);
        System.out.println(outContent.toString());
    }

    @TestFactory
    Collection<DynamicTest> checkAndConvertRange_IllegalRange() {
        List<String> listOfRanges = new ArrayList<>(Arrays.asList("2-1", "56-32", "12-*", "Bayan one love"));
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for (String range : listOfRanges) {
            Executable exec = () -> assertThrows(IllegalArgumentException.class,
                    () -> object.checkRange(range));
            DynamicTest dTest = DynamicTest.dynamicTest(range, exec);
            dynamicTests.add(dTest);
        }
        return dynamicTests;
    }

    @Test
    void checkAndConvertRange_Exception() {
        String range = "4-1";
        try {
            object.checkRange(range);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid range: [4‥1]", e.getMessage());
        }
    }

    @TestFactory
    Collection<DynamicTest> convertRange_DynamicTest() {
        List<String> listOfRanges = new ArrayList<>(Arrays.asList("6-", "1-4", "-9", "6-6", "-48"));
        List<String> symbolsOrWordsInLine = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
        String[] newRanges = {"[6‥6]", "[1‥4]", "[0‥6]", "[6‥6]", "[0‥6]"};
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for (int i = 0; i < listOfRanges.size(); i++) {
            String range = listOfRanges.get(i);
            Object newRange = newRanges[i];
            Range newRange2 = object.checkRange(range);
            Executable exec = () -> assertEquals(newRange,
                    object.convertRange(range, symbolsOrWordsInLine,newRange2).toString());
            DynamicTest dTest = DynamicTest.dynamicTest(range, exec);
            dynamicTests.add(dTest);
        }
        return dynamicTests;
    }

    @Test
    void listOfWords() {
        List<String> listOfWords = new ArrayList<>(Arrays.asList("123", "234", "567"));
        String line = " 123 234 567 ";
        assertEquals(object.listOfWords(line), listOfWords);
    }

    @Test
    void fileDoesntExist() throws IOException {
        String inputFileName = "Мой зачёт";
        String range = "2-4";
        Range newRange = Range.closed(Integer.parseInt(range.split("-")[0]),
                Integer.parseInt(range.split("-")[1]));
        try {
            object.cut(inputFileName, true, range,newRange,false);
            fail("Exception expected");
        } catch (IOException e) {
            assertEquals("File not found!", e.getMessage());
        }
    }

    @Test
    void outputFile() throws IOException {
        String outputName = "OutputTestName";
        List<String> text = new ArrayList<>();
        text.add("Мама ");
        text.add("мыла ");
        text.add("Рому ");
        object.outputFile(text, outputName);
        Range newRange = Range.closed(1,1);
        assertEquals(text, object.cut(outputName, true, "-1",newRange,true));
        File output = new File(outputName);
        output.delete();
    }

    @Test
    void cutSymbols() throws IOException {
        String range = "33-";
        String inputFileName = "src\\test\\resources\\for_task2";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("моника",
                "й музыкальный инструмент с", "м на", "вым (аккордовым) или готово-выборным", "", ""));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, false));
    }

    @Test
    void cutWords_1() throws IOException {
        String range = "1-2";
        String inputFileName = "src\\test\\resources\\for_task2";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("Баян - ", "язычковый кнопочно-пневматический ",
                "полным хроматическим ", "правой клавиатуре, ", "", "аккомпанементом на "));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, true));
    }

    @Test
    void cutWords_2() throws IOException {
        String range = "-1";
        String inputFileName = "src\\test\\resources\\for_task2";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("Баян ", "язычковый ", "полным ", "правой ",
                "", "аккомпанементом "));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, true));
    }

    @Test
    void cutWords_1st_range() throws IOException {
        String range = "-1";
        String inputFileName = "src\\test\\resources\\q";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("", "1 "));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, true));
    }

    @Test
    void cutSymbols_1st_range() throws IOException {
        String range = "-3";
        String inputFileName = "src\\test\\resources\\w";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("1 2", "", " 1"));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, false));
    }

    @Test
    void cutSymbols_2nd_range() throws IOException {
        String range = "2-4";
        String inputFileName = "src\\test\\resources\\w";
        List<String> cutExpression = new ArrayList<>(Arrays.asList(" 2 ", "", "1"));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, false));
    }

    @Test
    void cutWords_2nd_range() throws IOException {
        String range = "2-4";
        String inputFileName = "src\\test\\resources\\w";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("2 3 ", "", ""));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, true));
    }

    @Test
    void cutWords_3rd_range() throws IOException {
        String range = "3-";
        String inputFileName = "src\\test\\resources\\w";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("3 ", "", ""));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, true));
    }

    @Test
    void cutSymbols_3rd_range() throws IOException {
        String range = "3-";
        String inputFileName = "src\\test\\resources\\w";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("2 3", "", ""));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, false));
    }
}
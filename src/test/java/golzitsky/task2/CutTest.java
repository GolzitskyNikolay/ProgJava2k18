package golzitsky.task2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CutTest {
    private Cut object = new Cut();

    @TestFactory
    Collection<DynamicTest> checkAndConvertRange_IllegalRange() {
        List<String> listOfRanges = new ArrayList<>(Arrays.asList("2-1", "56-32", "12-*", "Bayan one love"));
        List<String> symbolsOrWordsInLine = new ArrayList<>(Arrays.asList("1 2 3 4 5 6"));
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for (String range : listOfRanges) {
            Executable exec = () -> assertThrows(IllegalArgumentException.class,
                    () -> object.checkAndConvertRange(range, symbolsOrWordsInLine,true));
            DynamicTest dTest = DynamicTest.dynamicTest(range, exec);
            dynamicTests.add(dTest);
        }
        return dynamicTests;
    }

    @Test
    void checkAndConvertRange_Exception() {
        String range = "4-1";
        List<String> symbolsOrWordsInLine = new ArrayList<>(Arrays.asList("1 2 3 4 5 6"));
        try {
            object.checkAndConvertRange(range, symbolsOrWordsInLine,true);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid range: [4‥1]", e.getMessage());
        }
    }

    @TestFactory
    Collection<DynamicTest> checkAndConvertRange_DynamicTest() {
        List<String> listOfRanges = new ArrayList<>(Arrays.asList("1-4", "5-8", "1-56", "3-", "-9", "6-6", "-48"));
        List<String> symbolsOrWordsInLine = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6"));
        String[] newRanges = {"[1‥4]","[5‥6]","[1‥6]","[3‥6]","[0‥6]","[6‥6]","[0‥6]"};
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for (int i = 0; i < listOfRanges.size(); i++) {
            String range = listOfRanges.get(i);
            Object newRange = newRanges[i];
            Executable exec = () -> assertEquals(newRange,
                    object.checkAndConvertRange(range, symbolsOrWordsInLine,true).toString());
            DynamicTest dTest = DynamicTest.dynamicTest(range, exec);
            dynamicTests.add(dTest);
        }
        return dynamicTests;
    }

    @Test
    void listOfWords() {
        List<String> listOfWords = new ArrayList<>(Arrays.asList("123","234","567"));
        String line = " 123 234 567 ";
        assertEquals(object.listOfWords(line), listOfWords);
    }

    @Test
    void fileDoesntExist() throws IOException {
        String inputFileName = "Мой зачёт";
        try {
            object.cutFromInputFile(inputFileName,true,"2-4");
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
        assertEquals(text, object.cutFromInputFile(outputName,true,"-1"));
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
        List<String> cutExpression = new ArrayList<>(Arrays.asList("1 2","", " 1"));
        assertEquals(cutExpression, object.cutWordsOrSymbols(range, inputFileName, false));
    }

    @Test
    void cutSymbols_2nd_range() throws IOException {
        String range = "2-4";
        String inputFileName = "src\\test\\resources\\w";
        List<String> cutExpression = new ArrayList<>(Arrays.asList(" 2 ","", "1"));
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
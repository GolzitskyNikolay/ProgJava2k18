package task2;

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
        String[] symbolsOrWordsInLine = {"1 2 3 4 5 6"};
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for (String range : listOfRanges) {
            Executable exec = () -> assertThrows(IllegalArgumentException.class,
                    () -> object.checkAndConvertRange(range, symbolsOrWordsInLine));
            DynamicTest dTest = DynamicTest.dynamicTest(range, exec);
            dynamicTests.add(dTest);
        }
        return dynamicTests;
    }

    @Test
    void checkAndConvertRange_Exception() {
        String range = "4-1";
        String[] symbolsOrWordsInLine = {"1 2 3 4 5 6"};
        try {
            object.checkAndConvertRange(range, symbolsOrWordsInLine);
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Range isn't correct", e.getMessage());
        }
    }

    @TestFactory
    Collection<DynamicTest> checkAndConvertRange_DynamicTest() {
        List<String> listOfRanges = new ArrayList<>(Arrays.asList("1-4", "5-8", "1-56", "3-", "-9", "6-6", "-48"));
        String[] symbolsOrWordsInLine = {"1", "2", "3", "4", "5", "6"};
        List<List<Integer>> newRanges = new ArrayList<>(Arrays.asList((Arrays.asList(1, 4)), (Arrays.asList(5, 6)),
                (Arrays.asList(1, 6)), (Arrays.asList(3)), (Arrays.asList(6)), (Arrays.asList(6, 6))
                , (Arrays.asList(6))));
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for (int i = 0; i < listOfRanges.size(); i++) {
            String range = listOfRanges.get(i);
            List<Integer> newRange = newRanges.get(i);
            Executable exec = () -> assertEquals(newRange, object.checkAndConvertRange(range, symbolsOrWordsInLine));
            DynamicTest dTest = DynamicTest.dynamicTest(range, exec);
            dynamicTests.add(dTest);
        }
        return dynamicTests;
    }

    @Test
    void listOfWords() {
        List<String> listOfLines = new ArrayList<>(Arrays.asList("Что-то  " +
                "  пошло  не так", "пересчитайте", "   и тогда всё норм", "  -  написал    Гоша"));
        List<List<String>> words = new ArrayList<>(Arrays.asList(Arrays.asList("Что-то", "пошло", "не", "так"),
                Arrays.asList("пересчитайте"), Arrays.asList("и", "тогда", "всё", "норм"),
                Arrays.asList("-", "написал", "Гоша")));
        assertEquals(object.listOfWords(listOfLines), words);
    }

    @Test
    void listOfLinesOfInputFile_FileDoesntExist() throws IOException {
        String inputFileName = "Мой зачёт";
        try {
            object.listOfLinesOfInputFile(inputFileName);
            fail("Exception expected");
        } catch (IOException e) {
            assertEquals("File not found!", e.getMessage());
        }
    }

    @Test
    void listOfLinesOfInputFile() throws IOException {
        List<String> lines = new ArrayList<>(Arrays.asList("Баян - русская хроматическая гармоника",
                "язычковый кнопочно-пневматический музыкальный инструмент с", "  полным хроматическим звукорядом на",
                "правой клавиатуре, басами и готовым (аккордовым) или готово-выборным", "",
                "    аккомпанементом на левой."));
        String inputFileName = "input\\task2\\for_task2";
        assertEquals(object.listOfLinesOfInputFile(inputFileName), lines);
    }

    @Test
    void outputFile() throws IOException {
        String outputName = "OutputTestName";
        List<String> text = new ArrayList<>();
        text.add("Мама");
        text.add("мыла");
        text.add("Рому");
        assertEquals(object.outputFile(text, outputName), true);
    }

    @Test
    void cutSymbols() throws IOException {
        String range = "33-";
        String inputFileName = "input\\task2\\for_task2";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("[моника]",
                "[й музыкальный инструмент с]", "[м на]", "[вым (аккордовым) или готово-выборным]", "[]", "[]"));
        assertEquals(cutExpression, object.cutSymbols(range, inputFileName));
    }

    @Test
    void cutWords_1() throws IOException {
        String range = "1-2";
        String inputFileName = "input\\task2\\for_task2";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("[Баян - ]", "[язычковый кнопочно-пневматический ]",
                "[полным хроматическим ]", "[правой клавиатуре, ]", "[]", "[аккомпанементом на ]"));
        assertEquals(cutExpression, object.cutWords(range, inputFileName));
    }

    @Test
    void cutWords_2() throws IOException {
        String range = "-1";
        String inputFileName = "input\\task2\\for_task2";
        List<String> cutExpression = new ArrayList<>(Arrays.asList("[Баян ]", "[язычковый ]", "[полным ]", "[правой ]",
                "[]", "[аккомпанементом ]"));
        assertEquals(cutExpression, object.cutWords(range, inputFileName));
    }
}
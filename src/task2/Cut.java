package task2;

import java.io.*;
import java.util.*;

public class Cut {
    /**
     * @param inputFileName - the name of the input file.
     * @param range - specifies the output range.
     * @return cut words.
     */
    public List<String> cutWords(String range, String inputFileName) throws IOException {
        List<String> result = new ArrayList<>();
        List<Integer> newRange;
        List<String> listOfLines;
        if (inputFileName == null) {
            listOfLines = listOfConsoleLines();
        } else {
            listOfLines = listOfLinesOfInputFile(inputFileName);
        }
        for (int i = 0; i < listOfWords(listOfLines).size(); i++) {
            int size = listOfWords(listOfLines).get(i).size();
            String[] words = listOfWords(listOfLines).get(i).toArray(new String[size]);
            newRange = checkAndConvertRange(range, words);
            result.add(String.valueOf(cutFromText(words, range, newRange, true)));
        }
        return result;
    }

    /**
     * @return cut symbols.
     */
    public List<String> cutSymbols(String range, String inputFileName) throws IOException {
        List<String> result = new ArrayList<>();
        List<Integer> newRange;
        List<String> listOfLines;
        if (inputFileName == null) {
            listOfLines = listOfConsoleLines();
        } else {
            listOfLines = listOfLinesOfInputFile(inputFileName);
        }
        for (String listOfLine : listOfLines) {
            String[] arrayOfStringOfFile = listOfLine.split("");
            newRange = checkAndConvertRange(range, arrayOfStringOfFile);
            result.add(cutFromText(arrayOfStringOfFile, range, newRange, false).toString());
        }
        return result;
    }

    /**
     * @param arrayOfSymbolsOrWords - if you need to cut the characters, it takes an array of characters,
     * if words, then takes an array of words.
     * @param v - all numeric parameters specify indents in words of the input file.
     * @return cut expression.
     */
    private List<String> cutFromText(String[] arrayOfSymbolsOrWords, String range,
                                     List<Integer> newRange, boolean v) {
        StringBuilder string = new StringBuilder();
        List<String> cutExpression = new ArrayList<>();
        if (range.matches("-([1-9][0]*)+")) {
            for (int i = 0; i < newRange.get(0); i++) {
                string.append(arrayOfSymbolsOrWords[i]);
                if (v) {
                    string.append(" ");
                }
                if (i == newRange.get(0) - 1) {
                    cutExpression.add(string.toString());
                }
            }
        } else if (range.matches("([1-9][0]*)+-([1-9][0]*)+")) {
            for (int i = newRange.get(0) - 1; i <= newRange.get(1) - 1; i++) {
                string.append(arrayOfSymbolsOrWords[i]);
                if (v) {
                    string.append(" ");
                }
                if (i == newRange.get(1) - 1) {
                    cutExpression.add(string.toString());
                }
            }
        } else if (range.matches("([1-9][0]*)+-")) {
            for (int i = newRange.get(0) - 1; i < arrayOfSymbolsOrWords.length; i++) {
                string.append(arrayOfSymbolsOrWords[i]);
                if (v) {
                    string.append(" ");
                }
                if (i == arrayOfSymbolsOrWords.length - 1) {
                    cutExpression.add(string.toString());
                }
            }
        }
        return cutExpression;
    }

    /**
     * Adds each line from the console to the new list.
     */
    private List<String> listOfConsoleLines() throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Use a new line and write \"end\" to finish writing!");
            String lineFromInput = reader.readLine();
            while (!lineFromInput.equalsIgnoreCase("END")) {
                result.add(lineFromInput + "\n");
                lineFromInput = reader.readLine();
            }
            reader.close();
        }
        return result;
    }

    /**
     * Adds each line from the input file to the new list.
     * @throws FileNotFoundException if file not found.
     */
    public List<String> listOfLinesOfInputFile(String inputFileName) throws IOException {
        List<String> result = new ArrayList<>();
        File input = new File(inputFileName);
        if (!input.exists()) throw new FileNotFoundException("File not found!");
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line = reader.readLine();
            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }
        }
        return result;
    }

    /**
     * @return list of words.
     * @throws IllegalArgumentException, if range isn't correct.
     */
    public List<List<String>> listOfWords(List<String> inputLines) {
        List<List<String>> words = new ArrayList<>();
        List<String> wordsInLine = new ArrayList<>();
        for (String line : inputLines) {
            wordsInLine.addAll(Arrays.asList(line.split(" +")));
            for (String element : Arrays.asList(line.split(" +"))) {
                if (Objects.equals(element, "")) {
                    wordsInLine.remove(element);
                }
            }
            words.add(new ArrayList<>(wordsInLine));
            wordsInLine.clear();
        }
        return words;
    }

    /**
     * Create a new file.
     * @param outputName - the name of the output file.
     */
    public boolean outputFile(List<String> outputFile, String outputName) throws IOException {
        int i = outputFile.size() - 1;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output\\task2\\" + outputName))) {
            for (int j = 0; j <= i; j++) {
                writer.write(outputFile.get(j));
                writer.newLine();
            }
        }
        return true;
    }

    /**
     * @return the list, that has numbers of range.
     * @throws IllegalArgumentException if range isn't correct.
     */
    public List<Integer> checkAndConvertRange(String range, String[] symbolsOrWordsInLine) {
        List<Integer> newRange = new ArrayList<>();
        if (range.matches("-([1-9][0]*)+")) {
            newRange.add(Integer.parseInt(range.split("-")[1]));
            if (newRange.get(0) > symbolsOrWordsInLine.length) {
                newRange.set(0, symbolsOrWordsInLine.length);
            }
        } else if (range.matches("([1-9][0]*)+-([1-9][0]*)+")) {
            newRange.add(Integer.parseInt(range.split("-")[0]));
            newRange.add(Integer.parseInt(range.split("-")[1]));
            if (newRange.get(0) > newRange.get(1)) throw new IllegalArgumentException("Range isn't correct");
            if (newRange.get(1) > symbolsOrWordsInLine.length) {
                newRange.set(1, symbolsOrWordsInLine.length);
            }
        } else if (range.matches("([1-9][0]*)+-")) {
            newRange.add(Integer.parseInt(range.split("-")[0]));
        } else throw new IllegalArgumentException("Range isn't correct");
        return newRange;
    }
}

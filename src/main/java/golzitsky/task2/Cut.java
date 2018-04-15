package golzitsky.task2;

import com.google.common.collect.Range;

import java.io.*;
import java.util.*;
public class Cut {
    /**
     * @param inputFileName - the name of the input file.
     * @param range - specifies the output range.
     * @param v - all numeric parameters specify indents in words of the input file.
     * @return cut words or cut symbols.
     */
    public List<String> cutWordsOrSymbols(String range, String inputFileName, boolean v) throws IOException {
        List<String> result = new ArrayList<>();
        List<String> listOfLines;
        if (inputFileName == null) {
            listOfLines = listOfConsoleLines();
        } else {
            listOfLines = listOfLinesOfInputFile(inputFileName);
        }
        if (v) {
            for (int i = 0; i < listOfWords(listOfLines).size(); i++) {
                int size = listOfWords(listOfLines).get(i).size();
                String[] words = listOfWords(listOfLines).get(i).toArray(new String[size]);
                Range newRange = checkAndConvertRange(range, words);
                result.add(String.valueOf(cutFromText(words, range, newRange, true)));
            }
        } else {
            for (String listOfLine : listOfLines) {
                String[] arrayOfStringOfFile = listOfLine.split("");
                Range newRange = checkAndConvertRange(range, arrayOfStringOfFile);
                result.add(cutFromText(arrayOfStringOfFile, range, newRange, false).toString());
            }
        }
        return result;
    }

    /**
     * @param arrayOfSymbolsOrWords - if you need to cut the characters, it takes an array of characters,
     * if words, then takes an array of words.
     * @return cut expression.
     */
    private StringBuilder cutFromText(String[] arrayOfSymbolsOrWords, String range,
                                     Range newRange, boolean v) {
        StringBuilder string = new StringBuilder();
        StringBuilder cutExpression = new StringBuilder();
        if (range.matches("-([1-9][0]*)+")) {
            for (int i = 0; i < newRange.upperEndpoint().hashCode(); i++) {
                string.append(arrayOfSymbolsOrWords[i]);
                if (v) {
                    string.append(" ");
                }
                if (i == newRange.upperEndpoint().hashCode() - 1) {
                    cutExpression.append(string.toString());
                }
            }
        } else if (range.matches("([1-9][0]*)+-([1-9][0]*)+") && newRange!=null) {
            for (int i = newRange.lowerEndpoint().hashCode() - 1; i <= newRange.upperEndpoint().hashCode() - 1; i++) {
                string.append(arrayOfSymbolsOrWords[i]);
                if (v) {
                    string.append(" ");
                }
                if (i == newRange.upperEndpoint().hashCode() - 1) {
                    cutExpression.append(string.toString());
                }
            }
        } else if (range.matches("([1-9][0]*)+-") && newRange!=null) {
            for (int i = newRange.lowerEndpoint().hashCode() - 1; i < arrayOfSymbolsOrWords.length; i++) {
                string.append(arrayOfSymbolsOrWords[i]);
                if (v) {
                    string.append(" ");
                }
                if (i == arrayOfSymbolsOrWords.length - 1) {
                    cutExpression.append(string.toString());
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
        inputLines.forEach(line -> {
            List<String> wordsInLine = new ArrayList<>();
            Arrays.stream(line.split(" +")).filter(e -> !e.equals("")).forEach(wordsInLine::add);
            words.add(new ArrayList<>(wordsInLine));
            wordsInLine.clear();
        });
        return words;
    }

    /**
     * Create a new file.
     * @param outputName - the name of the output file.
     */
    public void outputFile(List<String> outputFile, String outputName) throws IOException {
        int i = outputFile.size() - 1;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            for (int j = 0; j <= i; j++) {
                writer.write(outputFile.get(j));
                writer.newLine();
            }
        }
    }

    /**
     * @return the list, that has numbers of range.
     * @throws IllegalArgumentException if range isn't correct.
     */
    public Range checkAndConvertRange(String range, String[] symbolsOrWordsInLine) {
        Range newRange;
        if (range.matches("-([1-9][0]*)+")) {
            newRange = Range.closed(0, Integer.parseInt(range.split("-")[1]));
            if (newRange.upperEndpoint().hashCode() > symbolsOrWordsInLine.length) {
                newRange = Range.closed(0, symbolsOrWordsInLine.length);
            }
        } else if (range.matches("([1-9][0]*)+-([1-9][0]*)+")) {
            newRange = Range.closed(Integer.parseInt(range.split("-")[0]),
                    Integer.parseInt(range.split("-")[1]));
            if (newRange.lowerEndpoint().hashCode() > newRange.upperEndpoint().hashCode())
                throw new IllegalArgumentException("Range isn't correct");
            if (newRange.upperEndpoint().hashCode() > symbolsOrWordsInLine.length &&
                    symbolsOrWordsInLine.length >= newRange.lowerEndpoint().hashCode()) {
                newRange = Range.closed(Integer.parseInt(range.split("-")[0]), symbolsOrWordsInLine.length);
            }
            if (symbolsOrWordsInLine.length < newRange.lowerEndpoint().hashCode()) {
                newRange = null;
            }
        } else if (range.matches("([1-9][0]*)+-")) {
            if (symbolsOrWordsInLine.length >= Integer.parseInt(range.split("-")[0])) {
                newRange = Range.closed(Integer.parseInt(range.split("-")[0]), symbolsOrWordsInLine.length);
            } else {
                newRange = null;
            }
        } else throw new IllegalArgumentException("Range isn't correct");
        return newRange;
    }
}

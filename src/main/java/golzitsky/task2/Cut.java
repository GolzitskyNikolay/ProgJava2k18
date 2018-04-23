package golzitsky.task2;

import com.google.common.collect.Range;
import com.google.common.primitives.Chars;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Cut {
    /**
     * @param inputFileName - the name of the input file.
     * @param range - specifies the output range.
     * @param v - all numeric parameters specify indents in words of the input file.
     * @return cut words or cut symbols.
     */
    public List<String> cutWordsOrSymbols(String range, String inputFileName, boolean v) throws IOException {
        List<String> listOfLines;
        if (inputFileName == null) {
            listOfLines = cutFromConsole(v,range);
        } else {
            listOfLines = cutFromInputFile(inputFileName,v,range);
        }
        return listOfLines;
    }

    /**
     * @param listOfSymbolsOrWords - if you need to cut the characters, it takes an list of characters,
     * if words, then takes an list of words.
     * @return cut expression.
     */
    private <T> String cutFromText(List<T> listOfSymbolsOrWords, String range,
                               Range newRange, boolean v) {
        String string = "";
        String cutExpression = "";
        if (range.matches("-([1-9][0]*)+")) {
            for (int i = 0; i < newRange.upperEndpoint().hashCode(); i++) {
                string += listOfSymbolsOrWords.get(i);
                if (v) {
                    string += " ";
                }
                if (i == newRange.upperEndpoint().hashCode() - 1) {
                    cutExpression += string;
                }
            }
        } else if (range.matches("([1-9][0]*)+-([1-9][0]*)+") && newRange != null) {
            for (int i = newRange.lowerEndpoint().hashCode() - 1; i <= newRange.upperEndpoint().hashCode() - 1; i++) {
                string += listOfSymbolsOrWords.get(i);
                if (v) {
                    string += " ";
                }
                if (i == newRange.upperEndpoint().hashCode() - 1) {
                    cutExpression += string;
                }
            }
        } else if (range.matches("([1-9][0]*)+-") && newRange != null) {
            for (int i = newRange.lowerEndpoint().hashCode() - 1; i < listOfSymbolsOrWords.size(); i++) {
                string += listOfSymbolsOrWords.get(i);
                if (v) {
                    string += " ";
                }
                if (i == listOfSymbolsOrWords.size() - 1) {
                    cutExpression += string;
                }
            }
        }
        return cutExpression;
    }

    /**
     * Cut text from the console.
     */
    public List<String> cutFromConsole(Boolean v, String range) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Use a new line and write \"end\" to finish writing!");
            String lineFromInput = reader.readLine();
            while (!lineFromInput.equalsIgnoreCase("END")) {
                if (v) {
                    Range newRange = checkAndConvertRange(range,listOfWords(lineFromInput),true);
                    result.add(cutFromText(listOfWords(lineFromInput), range, newRange, true));
                }else{
                    List<Character> characterList = Chars.asList(lineFromInput.toCharArray());
                    Range newRange = checkAndConvertRange(range,characterList,false);
                    result.add(cutFromText(Chars.asList(lineFromInput.toCharArray()),range,newRange,false));
                }
                lineFromInput = reader.readLine();
            }
            reader.close();
        }
        return result;
    }

    /**
     * Cut text from the input file.
     * @throws FileNotFoundException if file not found.
     */
    public List<String> cutFromInputFile(String inputFileName,Boolean v,String range) throws IOException {
        List<String> result = new ArrayList<>();
        File input = new File(inputFileName);
        if (!input.exists()) throw new FileNotFoundException("File not found!");
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line = reader.readLine();
            while (line != null) {
                if (v) {
                    Range newRange = checkAndConvertRange(range,listOfWords(line), true);
                    result.add(cutFromText(listOfWords(line), range, newRange, true));
                }else{
                    List<Character> characterList = Chars.asList(line.toCharArray());
                    Range newRange = checkAndConvertRange(range,characterList,false);
                    result.add(cutFromText(Chars.asList(line.toCharArray()),range,newRange,false));
                }
                line = reader.readLine();
            }
        }
        return result;
    }

    /**
     * @return list of words.
     * @throws IllegalArgumentException, if range isn't correct.
     */
    public List<String> listOfWords(String inputLine) {
        return Arrays.stream(inputLine.split(" +")).filter(e -> !e.equals("")).
                collect(Collectors.toList());
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
    public <T> Range checkAndConvertRange(String range, List<T> symbolsOrWordsInLine, Boolean v) {
        Range newRange;
        if (range.matches("-([1-9][0]*)+")) {
            newRange = Range.closed(0, Integer.parseInt(range.split("-")[1]));
            if (newRange.upperEndpoint().hashCode() > symbolsOrWordsInLine.size()) {
                newRange = Range.closed(0, symbolsOrWordsInLine.size());
            }
        } else if (range.matches("([1-9][0]*)+-([1-9][0]*)+")) {
            newRange = Range.closed(Integer.parseInt(range.split("-")[0]),
                    Integer.parseInt(range.split("-")[1]));
            if (newRange.lowerEndpoint().hashCode() > newRange.upperEndpoint().hashCode())
                throw new IllegalArgumentException("Range isn't correct");
            if (newRange.upperEndpoint().hashCode() > symbolsOrWordsInLine.size() &&
                    symbolsOrWordsInLine.size() >= newRange.lowerEndpoint().hashCode()) {
                newRange = Range.closed(Integer.parseInt(range.split("-")[0]), symbolsOrWordsInLine.size());
            }
            if (symbolsOrWordsInLine.size() < newRange.lowerEndpoint().hashCode()) {
                newRange = null;
            }
        } else if (range.matches("([1-9][0]*)+-")) {
            if (symbolsOrWordsInLine.size() >= Integer.parseInt(range.split("-")[0])) {
                newRange = Range.closed(Integer.parseInt(range.split("-")[0]), symbolsOrWordsInLine.size());
            } else {
                newRange = null;
            }
        } else throw new IllegalArgumentException("Range isn't correct");
        return newRange;
    }
}

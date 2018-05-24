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
        Range newRange = checkRange(range);
        if (inputFileName == null) {
            listOfLines = cut(v, range,newRange);
        } else {
            listOfLines = cut(inputFileName, v, range, newRange);
        }
        return listOfLines;
    }

    /**
     * @param listOfSymbolsOrWords - if you need to cut the characters, it takes an list of characters,
     * if words, then takes an list of words.
     * @return cut expression.
     */
    private <T> String cutFromText(List<T> listOfSymbolsOrWords, Range<Integer> newRange, boolean v) {
        StringBuilder string = new StringBuilder();
        StringBuilder cutExpression = new StringBuilder();
         if (newRange != null) {
            for (int i = newRange.lowerEndpoint() - 1; i < newRange.upperEndpoint(); i++) {
                string.append(listOfSymbolsOrWords.get(i));
                if (v) {
                    string.append(" ");
                }
                if (i == newRange.upperEndpoint() - 1) {
                    cutExpression.append(string);
                }
            }
        }
        return String.valueOf(cutExpression);
    }

    /**
     * Cut text from the GUI.
     */
    public List<String> cut(Boolean v, String range, Range newRange) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Use a new line and write \"end\" to finish writing!");
            String line = reader.readLine();
            while (!line.equalsIgnoreCase("END")) {
                result = addToResult(range,line,result,v,newRange);
                line = reader.readLine();
            }
            reader.close();
        }
        return result;
    }

    private List<String> addToResult(String range, String line, List<String> result, Boolean v, Range newRange) {
        if (v) {
            newRange = convertRange(listOfWords(line), newRange);
            result.add(cutFromText(listOfWords(line), newRange, true));
        } else {
            List<Character> characterList = Chars.asList(line.toCharArray());
            newRange = convertRange(characterList, newRange);
            result.add(cutFromText(Chars.asList(line.toCharArray()), newRange, false));
        }
        return result;
    }

    /**
     * Cut text from the input file.
     * @throws FileNotFoundException if file not found.
     */
    public List<String> cut(String inputFileName,Boolean v,String range,
                            Range newRange) throws IOException {
        List<String> result = new ArrayList<>();
        File input = new File(inputFileName);
        if (!input.exists()) throw new FileNotFoundException("File not found!");
        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line = reader.readLine();
            while (line != null) {
                result = addToResult(range,line,result,v,newRange);
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
     * Convert range.
     */
    public <T> Range convertRange(List<T> symbolsOrWordsInLine,Range<Integer> newRange) {
        if (newRange.lowerEndpoint()>symbolsOrWordsInLine.size()) newRange = null;
        if (newRange != null && newRange.upperEndpoint() > symbolsOrWordsInLine.size())
            newRange = Range.closed(newRange.lowerEndpoint(), symbolsOrWordsInLine.size());
        return newRange;
    }

    /**
     * @throws IllegalArgumentException if range isn't correct.
     */
    public Range<Integer> checkRange(String range){
        Range<Integer> newRange;
        if (range.matches("-([1-9][0]*)+")) {
            newRange = Range.closed(1, Integer.parseInt(range.split("-")[1]));
        } else if (range.matches("([1-9][0]*)+-([1-9][0]*)+")) {
            newRange = Range.closed(Integer.parseInt(range.split("-")[0]),
                    Integer.parseInt(range.split("-")[1]));
            if (newRange.lowerEndpoint() > newRange.upperEndpoint())
                throw new IllegalArgumentException("Range isn't correct");
        } else if (range.matches("([1-9][0]*)+-")) {
            newRange = Range.closed(Integer.parseInt(range.split("-")[0]),
                    Integer.parseInt(String.valueOf(Integer.MAX_VALUE)));
        } else throw new IllegalArgumentException("Range isn't correct");
        return newRange;
    }
}

package task2;

import java.io.*;
import java.util.List;

import org.kohsuke.args4j.*;

/**
 * Command-line cut launcher.
 * Selecting from each line of a text file a certain substring.
 * Options:
 *    -c             all numeric parameters specify indents in the characters of the input file.
 *    -v             all numeric parameters specify indents in words of the input file.
 *    -o             specifies the name of the output file(If the name of the output file is not entered,
 *                   the result is output to the console).
 *    -r             specifies the output range and has one of the following types(N and K are integers):
 *                   -K range from the beginning of the line to K
 *                   N- range from N to the end of the line
 *                   N-K range from N to K.
 * <p>file           specifies the name of the input file(if the file name is not specified
 *                   or is incorrect, it reads the text from the console).
 *
 * Must be specified at least one of the parameters "-c" or "-v" and "-r".
 * Example:
 * -c -o nameOfOutputFile -r range file
 */

public class CutLauncher {
    @Option(name = "-c", metaVar = "IndentsInCharacters", usage = "Numeric parameters specify indents in characters")
    private boolean indentsInCharacters;

    @Option(name = "-v", metaVar = "IndentsInWords", usage = "Numeric parameters specify the indentation in words")
    private boolean indentsInWords;

    @Option(name = "-o", metaVar = "NameOfOutputFile", usage = "Specifies the name of the output file")
    private String nameOfOutputFile;

    @Option(name = "-r", required = true, metaVar = "Range", usage = "Sets the output range" )
    private String range;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String file;

    public static void main(String[] args) {
        new CutLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            if (!indentsInWords && !indentsInCharacters) throw new NullPointerException("Write -c or -v");
            System.err.println(e.getMessage());
            System.err.println("Expected arguments: [-c|-v] [-o outputFile] -r range [inputName | inputText]");
            parser.printUsage(System.err);
            return;
        }
        try {
            List<String> result;
            if (file == null) {
                result = Cut.cut(indentsInCharacters, indentsInWords,
                        nameOfOutputFile, range, null);
            } else {
                result = Cut.cut(indentsInCharacters, indentsInWords,
                        nameOfOutputFile, range, file);
            }
            for (String line : result) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

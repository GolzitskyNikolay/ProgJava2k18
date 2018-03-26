package task2;

import java.io.*;

import org.kohsuke.args4j.*;


public class CutLauncher {
    @Option(name = "-o", metaVar = "NameOfOutputFile", usage = "Specifies the name of the output file")
    private String nameOfOutputFile;

    @Option(name = "-c", metaVar = "IndentsInCharacters", usage = "Numeric parameters specify indents in characters")
    private boolean indentsInCharacters;

    @Option(name = "-v", metaVar = "IndentsInWords", usage = "Numeric parameters specify the indentation in words")
    private boolean indentsInWords;

    @Argument(metaVar = "InputName", usage = "Input file name")       //стоит ли объединить с range?
    private String inputFileName;

    @Argument(required = true, metaVar = "Range", index = 1, usage = "Sets the output range")
    private String range;

    public static void main(String[] args) {
        new CutLauncher().launch(args);
    }

    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Expected arguments: cut [-c|-w] [-o outputFile] [inputName] range");
            parser.printUsage(System.err);
            return;
        }
        try {
            String[] result = Cut.cut(inputFileName,"",nameOfOutputFile,indentsInCharacters,indentsInWords);
            for (String line: result) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static golzitsky.botForSapper.GUI.Menu.createMenu;

public class BotLauncher {

    public static void main(String[] args) throws IOException {
        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        Field field = new Field();
        BotMovies botMovies = new BotMovies();
        field.mapSize = 15;
        field.chanceOfBombs = 11;
        createMenu(field, jFrame, panel, botMovies);
        startGame(field, jFrame, panel, botMovies);
        jFrame.add(panel);
    }

    static void startGame(Field field, JFrame jFrame, JPanel panel, BotMovies botMovies) {
        botMovies.getTimer().stop(); //reset Timer
        botMovies.setPause(false);

        field.numberOfBombs = 0;     //clear field
        field.quantityOfOpenButtons = 0;
        field.numbersOfButtonsAroundEmptyButton.clear();
        field.allNumbersOfBombs.clear();
        field.numbersOfEmptyButtons.clear();
        field.knownNumbersOfBombs.clear();
        field.knownNumbersOfFlags.clear();
        field.numbersOfOpenCellsWithDigit.clear();
        field.buttonsWithoutBombsAround1.clear();
        field.buttonsWithoutBombsAround2.clear();
        field.buttonsWithoutBombsAround3.clear();
        field.buttonsWithoutBombsAround4.clear();
        field.buttonsWithoutBombsAround5.clear();
        field.buttonsWithoutBombsAround6.clear();
        field.buttonsWithoutBombsAround7.clear();

        int mapSize = field.mapSize;
        jFrame.setBounds(540 - 3 * mapSize, 360 - 20 * mapSize, mapSize * 50, mapSize * 50 + 25);
        panel.setLayout(new GridLayout(mapSize, mapSize));

        field.buttons = new RedrawCell[mapSize * mapSize];  //length of field
        panel.removeAll();

        GameLogic gameLogic = new GameLogic();

        GenerateField generateField = new GenerateField();  //create field
        generateField.createEmptyField(panel, field, gameLogic);
        jFrame.setVisible(true);

        botMovies.begin(field, generateField.getNumberOfOpenButton(), gameLogic); //start Timer
    }
}
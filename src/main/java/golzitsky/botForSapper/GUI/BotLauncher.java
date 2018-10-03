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
        field.mapSize = 15;
        field.chanceOfBombs = 11;
        createMenu(field, jFrame, panel);
        startGame(field, jFrame, panel);
        jFrame.add(panel);
    }

    static void startGame(Field field, JFrame jFrame, JPanel panel) {
        BotMovies botMovies = new BotMovies();
        botMovies.getTimer().stop();

        int mapSize = field.mapSize;
        jFrame.setBounds(540 - 3 * mapSize, 360 - 20 * mapSize, mapSize * 50, mapSize * 50 + 25);
        panel.setLayout(new GridLayout(mapSize, mapSize));
        field.allBombs = 0;
        field.quantityOfOpenButtons = 0;

        field.numbersOfButtonsAroundEmptyButton.clear();
        field.allNumbersOfBombs.clear();
        field.numbersOfEmptyButtons.clear();
        field.knownNumbersOfBombs.clear();
        field.numbersOfOpenCellsWithDigit.clear();
        field.buttonsWithoutBombsAround1.clear();
        field.buttonsWithoutBombsAround2.clear();
        field.buttonsWithoutBombsAround3.clear();
        field.buttonsWithoutBombsAround4.clear();
        field.buttonsWithoutBombsAround5.clear();
        field.buttonsWithoutBombsAround6.clear();
        field.buttonsWithoutBombsAround7.clear();
        field.buttonsWithoutBombsAround8.clear();

        field.buttons = new RedrawCell[mapSize * mapSize];
        panel.removeAll();

        GameLogic gameLogic = new GameLogic();

        GenerateField generateField = new GenerateField();
        generateField.createEmptyField(panel, field, gameLogic);
        jFrame.setVisible(true);

        botMovies.begin(field, generateField.getNumberOfOpenButton(), gameLogic);
    }
}
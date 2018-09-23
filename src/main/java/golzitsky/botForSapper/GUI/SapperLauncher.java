package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.Field;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static golzitsky.botForSapper.GUI.Menu.createMenu;

public class SapperLauncher {

    public static void main(String[] args) throws IOException {
        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        Field board = new Field();
        board.mapSize = 15;
        board.chanceOfBombs = 30;
        createMenu(board, jFrame, panel);
        startGame(board, jFrame, panel);
        jFrame.add(panel);
    }

    static void startGame(Field classField, JFrame jFrame, JPanel panel) {
        int mapSize = classField.mapSize;
        jFrame.setBounds(540 - 3 * mapSize, 360 - 20 * mapSize, mapSize * 50, mapSize * 50 + 25);
        panel.setLayout(new GridLayout(mapSize, mapSize));
        classField.allBombs = 0;
        classField.quantityOfOpenButtons = 0;
        classField.numbersOfButtonsAroundEmptyButton.clear();
        classField.numbersOfBombs.clear();
        classField.buttons = new RedrawCell[mapSize * mapSize];
        panel.removeAll();
        GenerateField field = new GenerateField();
        field.createEmptyField(panel, classField, jFrame);
        jFrame.setVisible(true);
    }
}
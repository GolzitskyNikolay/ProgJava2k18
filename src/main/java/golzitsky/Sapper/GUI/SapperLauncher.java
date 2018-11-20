package golzitsky.Sapper.GUI;

import golzitsky.Sapper.core.Field;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import static golzitsky.Sapper.GUI.Menu.createMenu;

public class SapperLauncher {

    public static void main(String[] args) throws IOException {
        JFrame jFrame = new JFrame();
        JPanel panel = new JPanel();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        Field board = new Field();
        board.mapSize = 8;
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
        classField.numbersOfEmptyButtons.clear();
        classField.numbersOfBombs.clear();
        classField.buttons = new RedrawCell[mapSize * mapSize];
        panel.removeAll();
        GenerateField field = new GenerateField();
        field.createEmptyField(panel, classField, jFrame);
        jFrame.setVisible(true);
    }

    static void endGame(String message, Field classField, JFrame jFrame, JPanel panel) {
        for (RedrawCell button : classField.buttons) {
            button.setPressedIcon(null);
            for (ActionListener actionListener : button.getActionListeners()) {
                button.removeActionListener(actionListener);
            }
            for (MouseListener mouseListener : button.getMouseListeners()) {
                button.removeMouseListener(mouseListener);
            }
        }
        int result = JOptionPane.showConfirmDialog(null,
                message + "\n" + "Do you want to start a new game?");
        if (result == JOptionPane.YES_OPTION) startGame(classField, jFrame, panel);
        if (result == JOptionPane.NO_OPTION) System.exit(0);
    }
}
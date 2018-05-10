package golzitsky.task3.GUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import static golzitsky.task3.GUI.Menu.createMenu;
import static golzitsky.task3.core.Field.*;

public class SapperLauncher {
    static int bombs = 30;
    static int mapSize = 8;
    static boolean playSound = true;
    static JFrame jFrame = new JFrame();
    private static JPanel panel = new JPanel();

    public static void main(String[] args) throws IOException {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        createMenu();
        startGame();
        jFrame.add(panel);
    }

    static void startGame() {
        jFrame.setBounds(540 - 3 * mapSize,360 - 20 * mapSize,mapSize * 50,mapSize * 50 + 25);
        panel.setLayout(new GridLayout(mapSize, mapSize));
        quantityOfOpenButtons = 0;
        numbersOfEmptyButtons.clear();
        numbersOfBombs.clear();
        buttons = new RedrawCell[mapSize * mapSize];
        panel.removeAll();
        GenerateField field = new GenerateField();
        field.createEmptyField(panel);
        jFrame.setVisible(true);
    }

    static void winOrLose(String message) {
        for (RedrawCell button : buttons) {
            button.setPressedIcon(null);
            for (ActionListener actionListener : button.getActionListeners()) {
                button.removeActionListener(actionListener);
            }
        }
        int result = JOptionPane.showConfirmDialog(null,
                message + "\n" + "Do you want to start a new game?");
        if (result == JOptionPane.YES_OPTION) startGame();
        if (result == JOptionPane.NO_OPTION) System.exit(0);
    }
}
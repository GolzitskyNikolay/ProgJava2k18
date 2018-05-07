package golzitsky.task3;

import javax.swing.*;

import java.awt.*;

public class SapperLauncher {
    private static JFrame jFrame = new JFrame();
    private static JPanel panel = new JPanel();

    public static void main(String[] args) throws Exception {
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setBounds(465, 200, 450, 450);
        jFrame.setResizable(false);
        panel.setLayout(new GridLayout(8, 8));
        startGame();
        jFrame.add(panel);
    }

    private static void startGame() {
        panel.removeAll();
        Field field = new Field();
        field.createEmptyField(panel);
        jFrame.setVisible(true);
    }

    static void winOrLose(String message) {
        int result = JOptionPane.showConfirmDialog(null, message + "\n" + "Play new game?");
        if (result == JOptionPane.YES_OPTION) {
            Field.quantityOfOpenButtons = 0;
            Field.numbersOfEmptyButtons.clear();
            Field.numbersOfBombs.clear();
            SapperLauncher.startGame();
        } else if (result == JOptionPane.NO_OPTION || result == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }
    }
}

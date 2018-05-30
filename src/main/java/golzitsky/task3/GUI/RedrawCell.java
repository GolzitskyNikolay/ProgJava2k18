package golzitsky.task3.GUI;

import golzitsky.task3.core.Cell;
import golzitsky.task3.core.Field;
import golzitsky.task3.core.GameLogic;

import javax.swing.*;

import static golzitsky.task3.GUI.PlaySound.playSound;
import static golzitsky.task3.GUI.SapperLauncher.endGame;

public class RedrawCell extends Cell {
    private GameLogic gameLogic = new GameLogic();

    private void showAllBombs(Field classField, JFrame jFrame, JPanel panel) {
        for (Integer number : classField.numbersOfBombs) {
            classField.buttons[number].setIcon(new ImageIcon("src\\main\\resources\\images\\bombed.png"));
        }
        playSound("src\\main\\resources\\sounds\\boom.wav");
        endGame("Game over!", classField, jFrame, panel);
    }

    void changeButton(Cell button, Field classField, JFrame jFrame, JPanel panel) {
        button.isOpen = true;
        if (gameLogic.isLose(button)) showAllBombs(classField, jFrame, panel);
        if (countOfBombs == 0) button.setIcon(new ImageIcon("src\\main\\resources\\images\\zero.png"));
        if (countOfBombs == 1) button.setIcon(new ImageIcon("src\\main\\resources\\images\\num1.png"));
        if (countOfBombs == 2) button.setIcon(new ImageIcon("src\\main\\resources\\images\\num2.png"));
        if (countOfBombs == 3) button.setIcon(new ImageIcon("src\\main\\resources\\images\\num3.png"));
        if (countOfBombs == 4) button.setIcon(new ImageIcon("src\\main\\resources\\images\\num4.png"));
        if (countOfBombs == 5) button.setIcon(new ImageIcon("src\\main\\resources\\images\\num5.png"));
        if (countOfBombs == 6) button.setIcon(new ImageIcon("src\\main\\resources\\images\\num6.png"));
        if (countOfBombs == 7) button.setIcon(new ImageIcon("src\\main\\resources\\images\\num7.png"));
        if (countOfBombs == 8) button.setIcon(new ImageIcon("src\\main\\resources\\images\\num8.png"));
        button.setPressedIcon(null);
        isOpen = true;
    }

    void makeFlag(Cell button) {
        if (!hasFlag) {
            button.setIcon(new ImageIcon("src\\main\\resources\\images\\flaged.png"));
            button.setPressedIcon(null);
            hasFlag = true;
        } else {
            button.setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
            button.setPressedIcon(new ImageIcon("src\\main\\resources\\images\\inform.png"));
            hasFlag = false;
        }
    }
}
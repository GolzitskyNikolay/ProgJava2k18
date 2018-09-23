package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;

import javax.swing.*;

import java.util.*;

import static golzitsky.botForSapper.GUI.PlaySound.playSound;

public class RedrawCell extends Cell {
    private GameLogic gameLogic = new GameLogic();

    private void showAllBombs(Field field) {
        for (Integer number : field.numbersOfBombs) {
            field.buttons[number].setIcon(new ImageIcon("src\\main\\resources\\images\\bombed.png"));
        }
        playSound("src\\main\\resources\\sounds\\boom.wav");
        JOptionPane.showMessageDialog(null,"Game Over!!!");
    }

    void openButton(Cell[] buttons, Field field, int i, int mapSize, Queue<Integer> buttonsAroundEmptyButton) {
        buttons[i].isOpen = true;
        field.quantityOfOpenButtons++;
        if (gameLogic.isLose(buttons[i])) showAllBombs(field);
        if (buttons[i].countOfBombs == 0) {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\zero.png"));
            field.numbersOfEmptyButtons.add(i);
            gameLogic.openOrCountButtonsAroundCell(buttons, i, mapSize, buttonsAroundEmptyButton, false);
        }
        if (buttons[i].countOfBombs == 1)
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num1.png"));
        if (buttons[i].countOfBombs == 2)
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num2.png"));
        if (buttons[i].countOfBombs == 3)
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num3.png"));
        if (buttons[i].countOfBombs == 4)
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num4.png"));
        if (buttons[i].countOfBombs == 5)
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num5.png"));
        if (buttons[i].countOfBombs == 6)
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num6.png"));
        if (buttons[i].countOfBombs == 7)
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num7.png"));
        if (buttons[i].countOfBombs == 8)
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num8.png"));
    }

    void makeFlag(Cell[] buttons, int i) {
        if (!hasFlag) {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\flaged.png"));
            buttons[i].setPressedIcon(null);
            buttons[i].hasFlag = true;
        } else {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
            buttons[i].setPressedIcon(new ImageIcon("src\\main\\resources\\images\\inform.png"));
            buttons[i].hasFlag = false;
        }
    }

}
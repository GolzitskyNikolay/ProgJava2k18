package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.BotLogic;
import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;

import javax.swing.*;

import java.util.*;

class RedrawCell extends Cell {
    private GameLogic gameLogic = new GameLogic();
    private BotLogic botLogic = new BotLogic();

    private void showAllBombs(Field field) {
        for (Integer number : field.allNumbersOfBombs) {
            field.buttons[number].setIcon(new ImageIcon("src\\main\\resources\\images\\bombed.png"));
        }
        PlaySound.playSound("src\\main\\resources\\sounds\\boom.wav");
    }

    void openButton(Cell[] buttons, Field field, int i, int mapSize, Queue<Integer> buttonsAroundEmptyButton) {
        field.numbersOfDigits.add(i);
        buttons[i].isOpen = true;
        field.quantityOfOpenButtons++;
        if (gameLogic.isLose(buttons[i])) {
            System.out.println(i);
            showAllBombs(field);
        }
        if (buttons[i].countOfBombs == 0) {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\zero.png"));
            field.numbersOfEmptyButtons.add(i);
            botLogic.openOrCountNotOpenButtonsOrFlagsAroundCell(buttons, i, mapSize, buttonsAroundEmptyButton, false, false);
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
        if (!buttons[i].hasFlag) {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\flaged.png"));
            buttons[i].setPressedIcon(null);
            buttons[i].hasFlag = true;
        }
    }

}
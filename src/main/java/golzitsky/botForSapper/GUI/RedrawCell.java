package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.BotLogic;
import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;

import javax.swing.*;

import java.util.*;

class RedrawCell extends Cell {
    private BotLogic botLogic = new BotLogic();

    private void showAllBombs(Field field) {
        for (Integer number : field.allNumbersOfBombs) {
            field.buttons[number].setIcon(new ImageIcon("src\\main\\resources\\images\\bombed.png"));
        }
    }

    void openButton(Cell[] buttons, Field field, int i, int mapSize,
                    Queue<Integer> buttonsAroundEmptyButton, GameLogic gameLogic) {
        if (!buttons[i].isOpen()) {
            buttons[i].setOpen(true);
            field.quantityOfOpenButtons++;
            if (gameLogic.isLose(buttons[i])) showAllBombs(field);
            else if (buttons[i].countOfBombs == 0) {
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\zero.png"));
                field.numbersOfEmptyButtons.add(i);
                botLogic.openOrCountNotOpenButtonsOrFlagsAroundCell(buttons, i, mapSize,
                        buttonsAroundEmptyButton, false, false);
            } else field.numbersOfOpenCellsWithDigit.add(i);
            if (buttons[i].countOfBombs == 1)
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num1.png"));
            else if (buttons[i].countOfBombs == 2)
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num2.png"));
            else if (buttons[i].countOfBombs == 3)
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num3.png"));
            else if (buttons[i].countOfBombs == 4)
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num4.png"));
            else if (buttons[i].countOfBombs == 5)
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num5.png"));
            else if (buttons[i].countOfBombs == 6)
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num6.png"));
            else if (buttons[i].countOfBombs == 7)
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num7.png"));
            else if (buttons[i].countOfBombs == 8)
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num8.png"));
        }
    }

    void makeFlag(Cell[] buttons, int i) {
        if (!buttons[i].isHasFlag()) {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\flaged.png"));
            buttons[i].setPressedIcon(null);
            buttons[i].setFlag(true);
        }
    }

}
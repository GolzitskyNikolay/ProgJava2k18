package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.BotLogic;
import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;

import javax.swing.*;

import java.util.*;

class RedrawCell extends Cell {
    private BotLogic botLogic = new BotLogic();

    /**
     * If Bot opened cell with bomb, then this method show all bombs in field.
     */
    private void showAllBombs(Field field, int i) {
        field.buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\bombed.png"));
        field.allNumbersOfBombs.remove(i);
        field.allNumbersOfBombs.removeAll(field.knownNumbersOfFlags);
        for (Integer number : field.knownNumbersOfFlags) {
            field.buttons[number].setIcon(new ImageIcon("src\\main\\resources\\images\\foundBomb.png"));
        }
        for (Integer number : field.allNumbersOfBombs) {
            field.buttons[number].setIcon(new ImageIcon("src\\main\\resources\\images\\bomb.png"));
        }
    }

    /**
     * Change picture of cell, when Bot open this cell. If cell is empty, then call method
     * "openOrCountNotOpenedCellsOrFlagsAroundCell()" in BotLogic.
     */
    void openButton(Cell[] buttons, Field field, int i, int mapSize,
                    Queue<Integer> buttonsAroundEmptyButton, GameLogic gameLogic) {
        if (!buttons[i].isOpen()) {
            buttons[i].setOpen(true);
            field.quantityOfOpenButtons++;
            if (gameLogic.isLose(buttons[i])) showAllBombs(field, i);
            else if (buttons[i].countOfBombs == 0) {
                buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\zero.png"));
                field.numbersOfEmptyButtons.add(i);
                botLogic.openOrCountNotOpenedCellsOrFlagsAroundCell(buttons, i, mapSize,
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

    /**
     * When Bot found cell without bombs, it makes flag for this cell.
     */
    void makeFlag(Cell[] buttons, int i, Field field) {
        if (!buttons[i].isHasFlag()) {
            field.knownNumbersOfFlags.add(i);
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\flaged.png"));
            buttons[i].setFlag(true);
        }
    }

}
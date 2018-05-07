package golzitsky.task3;

import java.util.*;
import javax.swing.*;

public class Cell extends JButton {
    private boolean hasBomb;
    private int countOfBombs;
    private boolean isOpen = false;
    private boolean hasFlag = false;

    boolean isHasFlag() {
        return hasFlag;
    }

    boolean isOpen() {
        return isOpen;
    }

    boolean isHasBomb() {
        return hasBomb;
    }

    void getNumberOfBombs(int numberOfBombs) {
        countOfBombs = numberOfBombs;
    }

    void firstButtonHasntBomb() {
        hasBomb = false;
    }

    private void showEmptyButtons(Cell[] buttons, int i) {
        buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\zero.png"));

        if ((Field.numbersOfEmptyButtons.contains(i - 8) && i > 8 && !buttons[i].isOpen)) {
            forShowEmptyButtons(buttons, i - 8);
        }
        if (Field.numbersOfEmptyButtons.contains(i + 8) && i < 53 && !buttons[i].isOpen) {
            forShowEmptyButtons(buttons, i + 8);
        }
        if (Field.numbersOfEmptyButtons.contains(i + 1) && (i < i + 1) && i != 31 && !buttons[i].isOpen) {
            forShowEmptyButtons(buttons, i + 1);
        }
        if (Field.numbersOfEmptyButtons.contains(i - 1) && (i > i - 1) && i != 32 && !buttons[i].isOpen) {
            forShowEmptyButtons(buttons, i - 1);
        }
    }

    private void forShowEmptyButtons(Cell[] buttons, int i) {
        if (!buttons[i].isOpen) {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\zero.png"));
            buttons[i].setPressedIcon(null);
            buttons[i].isOpen = true;
            Field.quantityOfOpenButtons++;
        }
    }

    private void showAllBombs(Cell[] buttons) {
        for (Integer number : Field.numbersOfBombs) {
            buttons[number].setIcon(new ImageIcon("src\\main\\resources\\images\\bombed.png"));
        }
        SapperLauncher.winOrLose("Game over!");
    }

    void changeButton(Cell[] buttons, int i) {
        if (countOfBombs == -1) showAllBombs(buttons);
        if (countOfBombs == 0) showEmptyButtons(buttons, i);
        if (countOfBombs == 1) buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num1.png"));
        if (countOfBombs == 2) buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num2.png"));
        if (countOfBombs == 3) buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num3.png"));
        if (countOfBombs == 4) buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num4.png"));
        if (countOfBombs == 5) buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num5.png"));
        if (countOfBombs == 6) buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num6.png"));
        if (countOfBombs == 7) buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num7.png"));
        if (countOfBombs == 8) buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\num8.png"));
        buttons[i].setPressedIcon(null);
        isOpen = true;
    }

    void makeFlag(Cell[] buttons, int i) {
        if (!hasFlag) {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\flaged.png"));
            buttons[i].setPressedIcon(null);
            hasFlag = true;
        } else {
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
            buttons[i].setPressedIcon(new ImageIcon("src\\main\\resources\\images\\inform.png"));
            hasFlag = false;
        }
    }

    void chanceOfBomb() {
        Random rnd = new Random();
        hasBomb = rnd.nextInt(100) < 25;
    }
}


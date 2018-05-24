package golzitsky.task3.GUI;

import golzitsky.task3.core.Cell;
import golzitsky.task3.core.Field;

import javax.swing.*;
import java.util.Random;

import static golzitsky.task3.GUI.PlaySound.playSound;
import static golzitsky.task3.GUI.SapperLauncher.bombs;
import static golzitsky.task3.GUI.SapperLauncher.winOrLose;

public class RedrawCell extends Cell {

    void getNumberOfBombs(int numberOfBombs) {
        countOfBombs = numberOfBombs;
    }

    void firstButtonHasntBomb() {
        hasBomb = false;
    }

    private void showEmptyButtons(Cell[] buttons, int i) {
        buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\zero.png"));
    }

    private void showAllBombs(Cell[] buttons) {
        for (Integer number : Field.numbersOfBombs) {
            buttons[number].setIcon(new ImageIcon("src\\main\\resources\\images\\bombed.png"));
        }
        playSound("src\\main\\resources\\sounds\\boom.wav");
        winOrLose("Game over!");
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
        if (!isOpen) {
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
    }

    void chanceOfBomb() {
        Random rnd = new Random();
        hasBomb = rnd.nextInt(100) < bombs;
    }
}
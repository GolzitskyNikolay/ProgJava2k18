package golzitsky.task3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Field {
    static int quantityOfOpenButtons = 0;
    static HashSet<Integer> numbersOfBombs = new HashSet<>();
    static HashSet<Integer> numbersOfEmptyButtons = new HashSet<>();
    private Cell[] buttons = new Cell[64];
    private int allBombs = 0;
    private HashSet<Integer> digits = new HashSet<>(Arrays.asList(0, 1, 2, 5, 6, 7, 8, 9,
            14, 15, 16, 23, 40, 47, 48, 49, 54, 55, 56, 57, 58, 61, 62, 63));

    void createEmptyField(JPanel panel) {
        for (int i = 0; i < 64; i++) {
            buttons[i] = new Cell();
            Cell button = buttons[i];
            button.setPreferredSize(new Dimension(50, 50));
            if (digits.contains(i)) {
                button.setEnabled(false);
                button.setBorderPainted(false);
            } else {
                button.setPressedIcon(new ImageIcon("src\\main\\resources\\images\\inform.png"));
                button.setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
                addActionListener(button, i);
                addMouseListener(button, i);
            }
            panel.add(button);
        }
    }

    private void addActionListener(Cell button, int i) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (quantityOfOpenButtons == 0) {
                    generateFieldByFirstClick(buttons, i);
                }
                if (!buttons[i].isHasFlag() && !buttons[i].isOpen()) {
                    quantityOfOpenButtons++;
                    buttons[i].changeButton(buttons, i);
                }
                if (quantityOfOpenButtons + allBombs == 40) {
                    SapperLauncher.winOrLose("You Win!!!");
                }
            }
        });
    }

    private void addMouseListener(Cell button, int i) {
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (!buttons[i].isOpen()) buttons[i].makeFlag(buttons, i);
                }
            }
        });
    }

    private void generateFieldByFirstClick(Cell[] buttons, int i) {
        buttons[i].firstButtonHasntBomb();
        for (int j = 3; j < 64; j++) {
            if (!digits.contains(j) && j != i) {
                buttons[j].chanceOfBomb();
                if (buttons[j].isHasBomb()) {
                    allBombs++;
                    numbersOfBombs.add(j);
                }
            }
        }

        for (int j = 3; j < 64; j++) {
            if (!digits.contains(j)) {
                int numberOfBombs = -1;
                if (!buttons[j].isHasBomb()) {
                    numberOfBombs = countNumberOfBombs(buttons, j);
                }
                if (numberOfBombs == 0) numbersOfEmptyButtons.add(j);
                buttons[j].getNumberOfBombs(numberOfBombs);
            }
        }
    }

    private int countNumberOfBombs(Cell[] buttons, int i) {
        int numberOfBombs = 0;
        if ((i >= 9) && buttons[i - 9].isHasBomb()) numberOfBombs++;

        if ((i >= 8) && buttons[i - 8].isHasBomb()) numberOfBombs++;

        if ((i >= 7 && i != 31 && i != 39) && buttons[i - 7].isHasBomb()) numberOfBombs++;

        if (i != 32 && buttons[i - 1].isHasBomb()) numberOfBombs++;

        if ((i < 60 && i != 31) && buttons[i + 1].isHasBomb()) numberOfBombs++;

        if ((i < 54 && i != 32 && i != 24) && buttons[i + 7].isHasBomb()) numberOfBombs++;

        if ((i < 53) && buttons[i + 8].isHasBomb()) numberOfBombs++;

        if ((i < 52) && buttons[i + 9].isHasBomb()) numberOfBombs++;
        return numberOfBombs;
    }
}

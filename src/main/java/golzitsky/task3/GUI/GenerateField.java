package golzitsky.task3.GUI;

import golzitsky.task3.core.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static golzitsky.task3.GUI.PlaySound.playSound;
import static golzitsky.task3.GUI.SapperLauncher.mapSize;
import static golzitsky.task3.GUI.SapperLauncher.winOrLose;

public class GenerateField extends Field {

    void createEmptyField(JPanel panel) {
        for (int i = 0; i < mapSize * mapSize; i++) {
            buttons[i] = new RedrawCell();
            RedrawCell button = buttons[i];
            button.setPreferredSize(new Dimension(50, 50));
            button.setPressedIcon(new ImageIcon("src\\main\\resources\\images\\inform.png"));
            button.setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
            addActionListener(button, i);
            addMouseListener(button, i);
            panel.add(button);
        }
    }

    private void addActionListener(RedrawCell button, int i) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (quantityOfOpenButtons == 0) {
                    generateFieldByFirstClick(buttons, i);
                }
                if (!buttons[i].isHasFlag() && !buttons[i].isOpen()) {
                    quantityOfOpenButtons++;
                    buttons[i].changeButton(buttons, i);
                }
                if (quantityOfOpenButtons + allBombs == mapSize * mapSize) {
                    playSound("src\\main\\resources\\sounds\\win.wav");
                    winOrLose("You Win!!!");
                }
            }
        });
    }

    private void addMouseListener(RedrawCell button, int i) {
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (!buttons[i].isOpen()) buttons[i].makeFlag(buttons, i);
                }
            }
        });
    }

    private void generateFieldByFirstClick(RedrawCell[] fieldButtons, int i) {
        fieldButtons[i].firstButtonHasntBomb();
        for (int j = 0; j < mapSize * mapSize; j++) {
            if (j != i) {
                fieldButtons[j].chanceOfBomb();
                if (fieldButtons[j].isHasBomb()) {
                    allBombs++;
                    numbersOfBombs.add(j);
                }
            }
        }

        for (int j = 0; j < mapSize * mapSize; j++) {
            int numberOfBombs = -1;
            if (!fieldButtons[j].isHasBomb()) {
                numberOfBombs = countNumberOfBombs(fieldButtons, j);
            }
            if (numberOfBombs == 0) numbersOfEmptyButtons.add(j);
            fieldButtons[j].getNumberOfBombs(numberOfBombs);
        }
    }

    private int countNumberOfBombs(RedrawCell[] fieldButtons, int i) {
        int numberOfBombs = 0;
        if (i % mapSize != 0 && i >= mapSize +1 && fieldButtons[i - (mapSize +1)].isHasBomb()) numberOfBombs++;

        if ((i >= mapSize) && fieldButtons[i - mapSize].isHasBomb()) numberOfBombs++;

        if ((i >= mapSize -1 && i % mapSize != mapSize -1) &&
                fieldButtons[i - (mapSize -1)].isHasBomb()) numberOfBombs++;

        if (i % mapSize != 0 && fieldButtons[i - 1].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize && i % mapSize != mapSize -1 &&
                fieldButtons[i + 1].isHasBomb()) numberOfBombs++;

        if (i % mapSize != 0 && i <= mapSize * mapSize - mapSize &&
                fieldButtons[i + mapSize -1].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize - mapSize && fieldButtons[i + mapSize].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize - mapSize && i % mapSize != mapSize -1 &&
                fieldButtons[i + mapSize +1].isHasBomb()) numberOfBombs++;
        return numberOfBombs;
    }
}

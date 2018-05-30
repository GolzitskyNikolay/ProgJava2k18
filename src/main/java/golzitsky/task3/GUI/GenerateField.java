package golzitsky.task3.GUI;

import golzitsky.task3.core.Field;
import golzitsky.task3.core.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static golzitsky.task3.GUI.PlaySound.playSound;
import static golzitsky.task3.GUI.SapperLauncher.endGame;

public class GenerateField extends Field {
    private GameLogic gameLogic = new GameLogic();
    private int mapSize;

    void createEmptyField(JPanel panel, Field classField, JFrame jFrame) {
        mapSize = classField.mapSize;
        for (int i = 0; i < mapSize * mapSize; i++) {
            classField.buttons[i] = new RedrawCell();
            RedrawCell button = classField.buttons[i];
            button.setPreferredSize(new Dimension(50, 50));
            button.setPressedIcon(new ImageIcon("src\\main\\resources\\images\\inform.png"));
            button.setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
            addActionListener(button, i, classField, jFrame, panel);
            addMouseListener(button);
            panel.add(button);
        }
    }

    private void addActionListener(RedrawCell button, int i, Field classField, JFrame jFrame, JPanel panel) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (classField.quantityOfOpenButtons == 0) {
                    gameLogic.generateFieldByFirstClick(i, classField, mapSize);
                }
                if (!button.isHasFlag() && !button.isOpen()) {
                    classField.quantityOfOpenButtons++;
                    button.changeButton(button, classField, jFrame, panel);
                }
                if (gameLogic.isWin(classField)) {
                    playSound("src\\main\\resources\\sounds\\win.wav");
                    endGame("You Win!!!", classField, jFrame, panel);
                }
            }
        });
    }

    private void addMouseListener(RedrawCell button) {
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (!button.isOpen()) button.makeFlag(button);
                }
            }
        });
    }
}
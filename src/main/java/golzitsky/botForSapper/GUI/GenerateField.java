package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.BotLogic;
import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GenerateField extends Field {
    private GameLogic gameLogic = new GameLogic();
    private BotLogic botLogic = new BotLogic();
    private int mapSize;

    void createEmptyField(JPanel panel, Field field, JFrame jFrame) {
        mapSize = field.mapSize;
        for (int i = 0; i < mapSize * mapSize; i++) {
            field.buttons[i] = new RedrawCell();
            field.buttons[i].setPreferredSize(new Dimension(50, 50));
            field.buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
            panel.add(field.buttons[i]);
        }
        generateFieldByFirstRandomOpenButton(field, jFrame, panel);
    }

    private void generateFieldByFirstRandomOpenButton(Field field, JFrame jFrame, JPanel panel) {
        final int[] numberOfOpenButton = {botLogic.numberOfRandomOpenButton(mapSize)};
        Cell[] buttons = field.buttons;
        buttons[numberOfOpenButton[0]].firstButtonHasntBomb();
        for (int j = 0; j < mapSize * mapSize; j++) {
            if (j != numberOfOpenButton[0]) {
                buttons[j].chanceOfBomb(field, buttons[j]);
                if (buttons[j].isHasBomb()) {
                    field.allBombs++;
                    field.allNumbersOfBombs.add(j);
                }
            }
        }
        for (int j = 0; j < mapSize * mapSize; j++) {
            int numberOfBombs = -1;
            if (!buttons[j].isHasBomb()) {
                numberOfBombs = gameLogic.countNumberOfBombsAroundCell(buttons, j, mapSize);
            }
            buttons[j].countOfBombs(numberOfBombs);
        }

        RedrawCell redrawCell = new RedrawCell();
        field.numbersOfDigits.add(numberOfOpenButton[0]);

        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                redrawCell.openButton(buttons, field, numberOfOpenButton[0],
                        mapSize, numbersOfButtonsAroundEmptyButton);

                while (!numbersOfButtonsAroundEmptyButton.isEmpty()) {       //открываем всё вокруг пустой клетки
                    redrawCell.openButton(buttons, field, numbersOfButtonsAroundEmptyButton.poll(),
                            mapSize, numbersOfButtonsAroundEmptyButton);
                }

                field.numbersOfDigits.removeAll(field.numbersOfEmptyButtons);

                for (int element : field.numbersOfDigits) {           //записываем номера бомб в knownNumbersOfBombs
                    if (field.buttons[element].isOpen)                //для пометки флагом
                        botLogic.maybeAroundCellOnlyBombs(element, field.buttons, mapSize, field);
                }

                if (!field.knownNumbersOfBombs.isEmpty()) {              //cтавим флаги на известные места бомб
                    for (int button : field.knownNumbersOfBombs) {
                        redrawCell.makeFlag(buttons, button);
                    }
                    field.knownNumbersOfBombs.clear();
                }

                botLogic.knowButtonsWithoutBombs(buttons, mapSize, field);

                if (gameLogic.isLose(field.buttons[numberOfOpenButton[0]]) || gameLogic.isWin(field)) {
                    System.out.println("Game over!!!");
                }
                numberOfOpenButton[0] = botLogic.numberOfNextOpenButton(numberOfOpenButton[0],
                        buttons, mapSize, field);
            }
        });
        timer.setRepeats(true);
        timer.start();
    }
}
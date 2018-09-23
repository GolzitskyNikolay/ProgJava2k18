package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;

import javax.swing.*;
import java.awt.*;

class GenerateField extends Field {
    private GameLogic gameLogic = new GameLogic();
    private int mapSize;

    void createEmptyField(JPanel panel, Field field, JFrame jFrame) {
        mapSize = field.mapSize;
        for (int i = 0; i < mapSize * mapSize; i++) {
            field.buttons[i] = new RedrawCell();
            field.buttons[i].setPreferredSize(new Dimension(50, 50));
            field.buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
            panel.add(field.buttons[i]);
        }
        generateFieldByFirstRandomOpenButton(field);
    }

    private void generateFieldByFirstRandomOpenButton(Field field) {
        int numberOfOpenButton = gameLogic.numberOfRandomOpenButton(mapSize);
        Cell[] buttons = field.buttons;
        buttons[numberOfOpenButton].firstButtonHasntBomb();
        for (int j = 0; j < mapSize * mapSize; j++) {
            if (j != numberOfOpenButton) {
                buttons[j].chanceOfBomb(field, buttons[j]);
                if (buttons[j].isHasBomb()) {
                    field.allBombs++;
                    field.numbersOfBombs.add(j);
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

        redrawCell.openButton(buttons, field, numberOfOpenButton, mapSize, numbersOfButtonsAroundEmptyButton);
        while (!numbersOfButtonsAroundEmptyButton.isEmpty()) {
            redrawCell.openButton(buttons, field, numbersOfButtonsAroundEmptyButton.poll(),
                    mapSize, numbersOfButtonsAroundEmptyButton);
        }
        for (int cell = 0; cell < mapSize * mapSize; cell++) {
            gameLogic.maybeAroundCellOnlyBombs(numberOfOpenButton, buttons, mapSize, field);
        }
        if (!field.knownNumbersOfBombs.isEmpty()) {
            for (int element : field.knownNumbersOfBombs) {
                redrawCell.makeFlag(buttons, element);
            }
            field.knownNumbersOfBombs.clear();
        }
        numberOfOpenButton = gameLogic.numberOfNextOpenButton(numberOfOpenButton, buttons, mapSize, field);

    }
}
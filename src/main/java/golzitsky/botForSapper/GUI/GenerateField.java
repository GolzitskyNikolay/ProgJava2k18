package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.BotLogic;
import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;

import javax.swing.*;
import java.awt.*;

class GenerateField extends Field {
    private GameLogic gameLogic;
    private BotLogic botLogic = new BotLogic();
    private Field field;
    private int mapSize;

    int getNumberOfOpenButton() {
        return numberOfOpenButton;
    }

    private int numberOfOpenButton;
    private Cell[] buttons;

    void createEmptyField(JPanel panel, Field ourField, GameLogic gameLogic) {
        field = ourField;
        buttons = field.buttons;
        mapSize = field.mapSize;
        this.gameLogic = gameLogic;
        for (int i = 0; i < mapSize * mapSize; i++) {
            buttons[i] = new RedrawCell();
            buttons[i].setPreferredSize(new Dimension(50, 50));
            buttons[i].setIcon(new ImageIcon("src\\main\\resources\\images\\closed.png"));
            panel.add(buttons[i]);
        }
        generateFieldByFirstRandomOpenButton();
    }

    private void generateFieldByFirstRandomOpenButton() {
        numberOfOpenButton = botLogic.numberOfRandomOpenButton(mapSize);
        buttons[numberOfOpenButton].firstButtonHasntBomb();
        for (int j = 0; j < mapSize * mapSize; j++) {
            if (j != numberOfOpenButton) {
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
    }
}
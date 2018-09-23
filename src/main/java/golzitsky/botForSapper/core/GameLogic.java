package golzitsky.botForSapper.core;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GameLogic {

    public Boolean isLose(Cell cell) {
        return cell.hasBomb && cell.isOpen;
    }

    public Boolean isWin(Field field) {
        return field.quantityOfOpenButtons + field.allBombs == field.mapSize * field.mapSize;
    }

    public int countNumberOfBombsAroundCell(Cell[] fieldButtons, int i, int mapSize) {
        int numberOfBombs = 0;
        if (i % mapSize != 0 && i >= mapSize + 1 && fieldButtons[i - (mapSize + 1)].isHasBomb()) numberOfBombs++;

        if ((i >= mapSize) && fieldButtons[i - mapSize].isHasBomb()) numberOfBombs++;

        if ((i >= mapSize - 1 && i % mapSize != mapSize - 1) &&
                fieldButtons[i - (mapSize - 1)].isHasBomb()) numberOfBombs++;

        if (i % mapSize != 0 && fieldButtons[i - 1].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize && i % mapSize != mapSize - 1 &&
                fieldButtons[i + 1].isHasBomb()) numberOfBombs++;

        if (i % mapSize != 0 && i <= mapSize * mapSize - mapSize &&
                fieldButtons[i + mapSize - 1].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize - mapSize && fieldButtons[i + mapSize].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize - mapSize && i % mapSize != mapSize - 1 &&
                fieldButtons[i + mapSize + 1].isHasBomb()) numberOfBombs++;
        return numberOfBombs;
    }

    private void forOpenOrCountButtonsAroundCell_1(Cell[] buttons, int i, Queue<Integer> buttonsAroundButton) {
        buttons[i].isOpen = true;
        buttonsAroundButton.add(i);
    }

    private void forOpenOrCountButtonsAroundCell_2(Cell[] buttons, int i, Queue<Integer> buttonsAroundCell,
                                                   Boolean weNeedToCountButtonsAroundCell) {
        if (!weNeedToCountButtonsAroundCell) {
            forOpenOrCountButtonsAroundCell_1(buttons, i, buttonsAroundCell);
        } else buttonsAroundCell.add(i);
    }


    public void openOrCountButtonsAroundCell(Cell[] buttons, int i, int mapSize, Queue<Integer>
            buttonsAroundCell, Boolean weNeedToCountButtonsAroundCell) {
        if (i >= mapSize + 1 && !buttons[i - mapSize - 1].isOpen && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize - 1,
                    buttonsAroundCell, weNeedToCountButtonsAroundCell);
        }
        if (i >= mapSize && !buttons[i - mapSize].isOpen) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize,
                    buttonsAroundCell, weNeedToCountButtonsAroundCell);
        }
        if (i >= mapSize && !buttons[i - mapSize + 1].isOpen && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize + 1,
                    buttonsAroundCell, weNeedToCountButtonsAroundCell);
        }
        if (i >= 1 && !buttons[i - 1].isOpen && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - 1,
                    buttonsAroundCell, weNeedToCountButtonsAroundCell);
        }
        if (i < mapSize * mapSize - mapSize - 1 && !buttons[i + mapSize + 1].isOpen && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize + 1,
                    buttonsAroundCell, weNeedToCountButtonsAroundCell);
        }
        if (i < mapSize * mapSize - mapSize && !buttons[i + mapSize].isOpen) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize,
                    buttonsAroundCell, weNeedToCountButtonsAroundCell);
        }
        if (i <= mapSize * mapSize - mapSize && !buttons[i + mapSize - 1].isOpen && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize - 1,
                    buttonsAroundCell, weNeedToCountButtonsAroundCell);
        }
        if (i < mapSize * mapSize - 1 && !buttons[i + 1].isOpen && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + 1,
                    buttonsAroundCell, weNeedToCountButtonsAroundCell);
        }
    }

    private Queue<Integer> quantityOfNotOpenButtonsAroundButton(int i, Cell[] buttons, int mapSize) {
        Queue<Integer> numbersOfButtons = new LinkedList<>();
        openOrCountButtonsAroundCell(buttons, i, mapSize, numbersOfButtons, true);
        return numbersOfButtons;
    }

    public int numberOfNextOpenButton(int i, Cell[] buttons, int mapSize, Field field) {
        int number = 0;
        int quantityOfNotOpenButtonsAroundButton = quantityOfNotOpenButtonsAroundButton(i, buttons, mapSize).size();
        if (quantityOfNotOpenButtonsAroundButton != 0 && buttons[i].countOfBombs / quantityOfNotOpenButtonsAroundButton
                <= field.chanceOfBombs / 100) {
            if (i % mapSize != 0 && i - 1 >= 0 && !buttons[i - 1].isOpen)
                number = i - 1;
            else if (i - mapSize + 1 >= 0 && i % mapSize != 0 && !buttons[i - mapSize + 1].isOpen)
                number = i - mapSize + 1;
            else if (i - mapSize >= 0 && !buttons[i - mapSize].isOpen)
                number = i - mapSize;
            else if (i % mapSize != 0 && i >= mapSize + 1 && !buttons[i - (mapSize + 1)].isOpen)
                number = i - mapSize - 1;
            else if (i % mapSize != mapSize - 1 && i + 1 <= mapSize * mapSize - 1 && !buttons[i + 1].isOpen)
                number = i + 1;
            else if (i % mapSize != mapSize - 1 && i + mapSize - 1 <= mapSize * mapSize &&
                    !buttons[i + mapSize - 1].isOpen)
                number = i + mapSize - 1;
            else if (i + mapSize <= mapSize * mapSize && !buttons[i + mapSize].isOpen)
                number = i + mapSize;
            else if (i % mapSize != mapSize - 1 && i + mapSize + 1 <= mapSize * mapSize &&
                    !buttons[i + mapSize + 1].isOpen)
                number = i + mapSize + 1;
        } else {
            if (i - 2 * mapSize >= 0) number = i - 2 * mapSize;
            if (i + 2 * mapSize <= mapSize * mapSize - 1) number = i + 2 * mapSize;
        }
        return number;
    }

    public int numberOfRandomOpenButton(int mapSize) {
        Random random = new Random();
        return random.nextInt(mapSize * mapSize);
    }

    /**
     * If the number of not open buttons around the cell is equal to the number of bombs
     * around this cell, then these buttons have bombs.
     */
    public void maybeAroundCellOnlyBombs(int i, Cell[] buttons, int mapSize, Field field) {
        for (int bombs = 1; bombs <= 8; bombs++) {
            if (buttons[i].countOfBombs == bombs &&
                    quantityOfNotOpenButtonsAroundButton(i, buttons, mapSize).size() == bombs) {
                field.knownNumbersOfBombs.addAll(quantityOfNotOpenButtonsAroundButton(i, buttons, mapSize));
            }
        }
    }
}

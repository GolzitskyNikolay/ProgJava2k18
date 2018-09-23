package golzitsky.botForSapper.core;

import java.util.*;

public class BotLogic {

    private void forOpenOrCountButtonsAroundCell_1(Cell[] buttons, int i, Queue<Integer> buttonsAroundButton) {
        buttons[i].isOpen = true;
        buttonsAroundButton.add(i);
    }

    private void forOpenOrCountButtonsAroundCell_2(Cell[] buttons, int i, Queue<Integer> buttonsAroundCell,
                                                   Boolean weNeedToCountButtonsAroundCell, Boolean countNumbersOfFlags) {
        if (weNeedToCountButtonsAroundCell) {
            buttonsAroundCell.add(i);
        } else if (countNumbersOfFlags && buttons[i].hasFlag) {
            buttonsAroundCell.add(i);
        } else if (!countNumbersOfFlags) {
            forOpenOrCountButtonsAroundCell_1(buttons, i, buttonsAroundCell);
        }
    }


    public void openOrCountNotOpenButtonsOrFlagsAroundCell(Cell[] buttons, int i, int mapSize, Queue<Integer>
            buttonsAroundCell, Boolean weNeedToCountNotOpenButtonsAroundCell, Boolean countNumbersOfFlags) {
        if (i >= mapSize + 1 && !buttons[i - mapSize - 1].isOpen && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize - 1,
                    buttonsAroundCell, weNeedToCountNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= mapSize && !buttons[i - mapSize].isOpen) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize,
                    buttonsAroundCell, weNeedToCountNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= mapSize && !buttons[i - mapSize + 1].isOpen && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize + 1,
                    buttonsAroundCell, weNeedToCountNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= 1 && !buttons[i - 1].isOpen && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - 1,
                    buttonsAroundCell, weNeedToCountNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - mapSize - 1 && !buttons[i + mapSize + 1].isOpen && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize + 1,
                    buttonsAroundCell, weNeedToCountNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - mapSize && !buttons[i + mapSize].isOpen) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize,
                    buttonsAroundCell, weNeedToCountNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i <= mapSize * mapSize - mapSize && !buttons[i + mapSize - 1].isOpen && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize - 1,
                    buttonsAroundCell, weNeedToCountNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - 1 && !buttons[i + 1].isOpen && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + 1,
                    buttonsAroundCell, weNeedToCountNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
    }

    private Queue<Integer> notOpenButtonsAroundButton(int i, Cell[] buttons, int mapSize) {
        Queue<Integer> numbersOfButtons = new LinkedList<>();
        openOrCountNotOpenButtonsOrFlagsAroundCell(buttons, i, mapSize, numbersOfButtons, true, false);
        return numbersOfButtons;
    }

    private Queue<Integer> numbersOfFlagsAroundCell(int i, Cell[] buttons, int mapSize) {
        Queue<Integer> numbersOfFlags = new LinkedList<>();
        openOrCountNotOpenButtonsOrFlagsAroundCell(buttons, i, mapSize, numbersOfFlags, false, true);
        return numbersOfFlags;
    }

    public int numberOfNextOpenButton(int i, Cell[] buttons, int mapSize, Field field) {
        int number = 0;
        int quantityOfNotOpenButtonsAroundButton = notOpenButtonsAroundButton(i, buttons, mapSize).size();
        if (!field.buttonsWithoutBombs.isEmpty()) {
            Queue<Integer> queue = new LinkedList<>(field.buttonsWithoutBombs);
            number = queue.poll();
            field.buttonsWithoutBombs.remove(number);
        } else if (quantityOfNotOpenButtonsAroundButton != 0 && buttons[i].countOfBombs / quantityOfNotOpenButtonsAroundButton
                <= field.chanceOfBombs / 100) {
            if (i % mapSize != 0 && i - 1 >= 0 && !buttons[i - 1].isOpen && !buttons[i - 1].hasFlag)
                number = i - 1;
            else if (i - mapSize + 1 >= 0 && i % mapSize != 0 &&
                    !buttons[i - mapSize + 1].isOpen && !buttons[i - mapSize - 1].hasFlag)
                number = i - mapSize + 1;
            else if (i - mapSize >= 0 && !buttons[i - mapSize].isOpen && !buttons[i - mapSize].hasFlag)
                number = i - mapSize;
            else if (i % mapSize != 0 && i >= mapSize + 1 &&
                    !buttons[i - (mapSize + 1)].isOpen && !buttons[i - (mapSize + 1)].hasFlag)
                number = i - mapSize - 1;
            else if (i % mapSize != mapSize - 1 && i + 1 <= mapSize * mapSize - 1 &&
                    !buttons[i + 1].isOpen && !buttons[i + 1].hasFlag)
                number = i + 1;
            else if (i % mapSize != mapSize - 1 && i + mapSize - 1 <= mapSize * mapSize &&
                    !buttons[i + mapSize - 1].isOpen && !buttons[i + mapSize - 1].hasFlag)
                number = i + mapSize - 1;
            else if (i + mapSize <= mapSize * mapSize && !buttons[i + mapSize].isOpen
                    && !buttons[i + mapSize].hasFlag)
                number = i + mapSize;
            else if (i % mapSize != mapSize - 1 && i + mapSize + 1 <= mapSize * mapSize &&
                    !buttons[i + mapSize + 1].isOpen && !buttons[i + mapSize + 1].hasFlag)
                number = i + mapSize + 1;
        } else {
            if (i - 2 * mapSize >= 0 && !buttons[i - 2 * mapSize].isOpen && !buttons[i - 2 * mapSize].hasFlag)
                number = i - 2 * mapSize;
            if (i + 2 * mapSize <= mapSize * mapSize - 1 && !buttons[i + 2 * mapSize].isOpen
                    && !buttons[i + 2 * mapSize].hasFlag) number = i + 2 * mapSize;
        }
        return number;
    }

    public void knowButtonsWithoutBombs(Cell[] buttons, int mapSize, Field field) {
        Queue<Integer> numbersOfDigits = new LinkedList<>(field.numbersOfDigits);
        for (int element : numbersOfDigits) {
            Queue<Integer> queue = numbersOfFlagsAroundCell(element, buttons, mapSize);
            if (field.buttons[element].isOpen && queue.size() <
                    notOpenButtonsAroundButton(element, buttons, mapSize).size() && queue.size() != 0) {
                Set<Integer> set = new HashSet<>(notOpenButtonsAroundButton(element, buttons, mapSize));
                set.removeAll(queue);
                field.buttonsWithoutBombs.addAll(set);
                field.numbersOfDigits.remove(element);
            }
        }
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
                    notOpenButtonsAroundButton(i, buttons, mapSize).size() == bombs) {
                field.knownNumbersOfBombs.addAll(notOpenButtonsAroundButton(i, buttons, mapSize));
            }
        }
    }
}

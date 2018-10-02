package golzitsky.botForSapper.core;

import java.util.*;

public class BotLogic {
    private void forOpenOrCountButtonsAroundCell_1(Cell[] buttons, int i, Queue<Integer> buttonsAroundButton) {
        buttons[i].setOpen(true);
        buttonsAroundButton.add(i);
    }

    private void forOpenOrCountButtonsAroundCell_2(Cell[] buttons, int i, Queue<Integer> buttonsAroundCell, Boolean
            weNeedToCountButtonsAroundCell, Boolean countNumbersOfFlags) {
        if (weNeedToCountButtonsAroundCell) {
            buttonsAroundCell.add(i);
        } else if (countNumbersOfFlags && buttons[i].isHasFlag()) {
            buttonsAroundCell.add(i);
        } else if (!countNumbersOfFlags) {
            forOpenOrCountButtonsAroundCell_1(buttons, i, buttonsAroundCell);
        }
    }

    public void openOrCountNotOpenButtonsOrFlagsAroundCell(Cell[] buttons, int i, int mapSize, Queue<Integer>
            buttonsAroundCell, Boolean countNotOpenButtonsAroundCell, Boolean countNumbersOfFlags) {
        if (i >= mapSize + 1 && !buttons[i - mapSize - 1].isOpen() && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize - 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= mapSize && !buttons[i - mapSize].isOpen()) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= mapSize && !buttons[i - mapSize + 1].isOpen() && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - mapSize + 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= 1 && !buttons[i - 1].isOpen() && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i - 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - mapSize - 1 && !buttons[i + mapSize + 1].isOpen() && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize + 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - mapSize && !buttons[i + mapSize].isOpen()) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i <= mapSize * mapSize - mapSize && !buttons[i + mapSize - 1].isOpen() && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + mapSize - 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - 1 && !buttons[i + 1].isOpen() && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell_2(buttons, i + 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
    }

    public Queue<Integer> notOpenButtonsAroundButton(int i, Cell[] buttons, int mapSize) {
        Queue<Integer> numbersOfButtons = new LinkedList<>();
        openOrCountNotOpenButtonsOrFlagsAroundCell(buttons, i, mapSize, numbersOfButtons, true, false);
        return numbersOfButtons;
    }

    public Queue<Integer> numbersOfFlagsAroundCell(int i, Cell[] buttons, int mapSize) {
        Queue<Integer> numbersOfFlags = new LinkedList<>();
        openOrCountNotOpenButtonsOrFlagsAroundCell(buttons, i, mapSize, numbersOfFlags, false, true);
        return numbersOfFlags;
    }

    private int forNumberOfNextOpenButton(Set<Integer> set) {
        Queue<Integer> queue = new LinkedList<>(set);
        int number = queue.poll();
        set.remove(number);
        return number;
    }

    public int numberOfNextOpenButton(int i, Cell[] buttons, int mapSize, Field field) {
        int number = 0;
        int quantityOfNotOpenButtonsAroundButton = notOpenButtonsAroundButton(i, buttons, mapSize).size();
        if (!field.buttonsWithoutBombsAround1.isEmpty()) {
            number = forNumberOfNextOpenButton(field.buttonsWithoutBombsAround1);
        } else if (!field.buttonsWithoutBombsAround2.isEmpty()) {
            number = forNumberOfNextOpenButton(field.buttonsWithoutBombsAround2);
        } else if (!field.buttonsWithoutBombsAround3.isEmpty()) {
            number = forNumberOfNextOpenButton(field.buttonsWithoutBombsAround3);
        } else if (!field.buttonsWithoutBombsAround4.isEmpty()) {
            number = forNumberOfNextOpenButton(field.buttonsWithoutBombsAround4);
        } else if (!field.buttonsWithoutBombsAround5.isEmpty()) {
            number = forNumberOfNextOpenButton(field.buttonsWithoutBombsAround5);
        } else if (!field.buttonsWithoutBombsAround6.isEmpty()) {
            number = forNumberOfNextOpenButton(field.buttonsWithoutBombsAround6);
        } else if (!field.buttonsWithoutBombsAround7.isEmpty()) {
            number = forNumberOfNextOpenButton(field.buttonsWithoutBombsAround7);
        } else if (!field.buttonsWithoutBombsAround8.isEmpty()) {
            number = forNumberOfNextOpenButton(field.buttonsWithoutBombsAround8);
        } else if (quantityOfNotOpenButtonsAroundButton != 0 && buttons[i].countOfBombs /
                quantityOfNotOpenButtonsAroundButton <= field.chanceOfBombs / 100) {
            if (i % mapSize != 0 && i - 1 >= 0 && !buttons[i - 1].isOpen() && !buttons[i - 1].isHasFlag())
                number = i - 1;
            else if (i - mapSize + 1 >= 0 && i % mapSize != 0 &&
                    !buttons[i - mapSize + 1].isOpen() && !buttons[i - mapSize - 1].isHasFlag())
                number = i - mapSize + 1;
            else if (i - mapSize >= 0 && !buttons[i - mapSize].isOpen() && !buttons[i - mapSize].isHasFlag())
                number = i - mapSize;
            else if (i % mapSize != 0 && i >= mapSize + 1 &&
                    !buttons[i - (mapSize + 1)].isOpen() && !buttons[i - (mapSize + 1)].isHasFlag())
                number = i - mapSize - 1;
            else if (i % mapSize != mapSize - 1 && i + 1 <= mapSize * mapSize - 1 &&
                    !buttons[i + 1].isOpen() && !buttons[i + 1].isHasFlag())
                number = i + 1;
            else if (i % mapSize != mapSize - 1 && i + mapSize - 1 <= mapSize * mapSize &&
                    !buttons[i + mapSize - 1].isOpen() && !buttons[i + mapSize - 1].isHasFlag())
                number = i + mapSize - 1;
            else if (i + mapSize <= mapSize * mapSize && !buttons[i + mapSize].isOpen()
                    && !buttons[i + mapSize].isHasFlag())
                number = i + mapSize;
            else if (i % mapSize != mapSize - 1 && i + mapSize + 1 <= mapSize * mapSize &&
                    !buttons[i + mapSize + 1].isOpen() && !buttons[i + mapSize + 1].isHasFlag())
                number = i + mapSize + 1;
        } else {
            Queue<Integer> queue;
            Set<Integer> set = field.numbersOfOpenCellsWithDigit;
            int minCountOfBombs = 8;
            for (int e : set) {
                queue = notOpenButtonsAroundButton(e, buttons, mapSize);
                queue.removeAll(numbersOfFlagsAroundCell(e, buttons, mapSize));
                if (buttons[e].countOfBombs <= minCountOfBombs && queue.size() != 0) number = queue.poll();
                if (buttons[e].countOfBombs == 1) break;
            }
        }
        return number;
    }

    public void knowButtonsWithoutBombs(Cell[] buttons, int mapSize, Field field) {
        Queue<Integer> numbersOfDigits = new LinkedList<>(field.numbersOfOpenCellsWithDigit);
        for (int element : numbersOfDigits) {
            Queue<Integer> flagsQueue = numbersOfFlagsAroundCell(element, buttons, mapSize);
            if (flagsQueue.size() == buttons[element].countOfBombs && flagsQueue.size() != 0) {
                Set<Integer> set = new HashSet<>(notOpenButtonsAroundButton(element, buttons, mapSize));
                set.removeAll(flagsQueue);
                if (buttons[element].countOfBombs == 1) field.buttonsWithoutBombsAround1.addAll(set);
                else if (buttons[element].countOfBombs == 2) field.buttonsWithoutBombsAround2.addAll(set);
                else if (buttons[element].countOfBombs == 3) field.buttonsWithoutBombsAround3.addAll(set);
                else if (buttons[element].countOfBombs == 4) field.buttonsWithoutBombsAround4.addAll(set);
                else if (buttons[element].countOfBombs == 5) field.buttonsWithoutBombsAround5.addAll(set);
                else if (buttons[element].countOfBombs == 6) field.buttonsWithoutBombsAround6.addAll(set);
                else if (buttons[element].countOfBombs == 7) field.buttonsWithoutBombsAround7.addAll(set);
                else if (buttons[element].countOfBombs == 8) field.buttonsWithoutBombsAround8.addAll(set);
                if (notOpenButtonsAroundButton(element, buttons, mapSize).size() ==
                        numbersOfFlagsAroundCell(element, buttons, mapSize).size())
                    field.numbersOfOpenCellsWithDigit.remove(element);
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

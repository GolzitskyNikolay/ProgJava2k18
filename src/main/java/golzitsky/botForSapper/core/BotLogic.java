package golzitsky.botForSapper.core;

import java.util.*;

public class BotLogic {
    /**
     * This method can be used for:
     * - opening cells around empty cell;
     * - counting not open cells around cell;
     * - counting flags around cell.
     * It has repeating code in method "forOpenOrCountButtonsAroundCell".
     */
    public void openOrCountNotOpenedCellsOrFlagsAroundCell(Cell[] buttons, int i, int mapSize, Queue<Integer>
            buttonsAroundCell, Boolean countNotOpenButtonsAroundCell, Boolean countNumbersOfFlags) {
        if (i >= mapSize + 1 && !buttons[i - mapSize - 1].isOpen() && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell(buttons, i - mapSize - 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= mapSize && !buttons[i - mapSize].isOpen()) {
            forOpenOrCountButtonsAroundCell(buttons, i - mapSize,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= mapSize && !buttons[i - mapSize + 1].isOpen() && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell(buttons, i - mapSize + 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i >= 1 && !buttons[i - 1].isOpen() && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell(buttons, i - 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - mapSize - 1 && !buttons[i + mapSize + 1].isOpen() && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell(buttons, i + mapSize + 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - mapSize && !buttons[i + mapSize].isOpen()) {
            forOpenOrCountButtonsAroundCell(buttons, i + mapSize,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i <= mapSize * mapSize - mapSize && !buttons[i + mapSize - 1].isOpen() && i % mapSize != 0) {
            forOpenOrCountButtonsAroundCell(buttons, i + mapSize - 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
        if (i < mapSize * mapSize - 1 && !buttons[i + 1].isOpen() && i % mapSize != mapSize - 1) {
            forOpenOrCountButtonsAroundCell(buttons, i + 1,
                    buttonsAroundCell, countNotOpenButtonsAroundCell, countNumbersOfFlags);
        }
    }

    private void forOpenOrCountButtonsAroundCell(Cell[] buttons, int i, Queue<Integer> buttonsAroundCell, Boolean
            weNeedToCountButtonsAroundCell, Boolean countNumbersOfFlags) {
        if (weNeedToCountButtonsAroundCell) buttonsAroundCell.add(i);
        else if (countNumbersOfFlags && buttons[i].isHasFlag()) buttonsAroundCell.add(i);
        else if (!countNumbersOfFlags) buttonsAroundCell.add(i);
    }

    public Queue<Integer> notOpenedCellsAroundCell(int i, Cell[] buttons, int mapSize) {
        Queue<Integer> numbersOfButtons = new LinkedList<>();
        openOrCountNotOpenedCellsOrFlagsAroundCell(buttons, i, mapSize, numbersOfButtons, true, false);
        return numbersOfButtons;
    }

    public Queue<Integer> numbersOfFlagsAroundCell(int i, Cell[] buttons, int mapSize) {
        Queue<Integer> numbersOfFlags = new LinkedList<>();
        openOrCountNotOpenedCellsOrFlagsAroundCell(buttons, i, mapSize, numbersOfFlags, false, true);
        return numbersOfFlags;
    }

    /**
     * This method find cells without bombs around opened cells with count of bombs = 1, then = 2 etc.
     */
    public void knowButtonsWithoutBombs(Cell[] buttons, int mapSize, Field field) {
        Queue<Integer> numbersOfDigits = new LinkedList<>(field.numbersOfOpenCellsWithDigit);
        for (int element : numbersOfDigits) {
            Queue<Integer> flagsQueue = numbersOfFlagsAroundCell(element, buttons, mapSize);
            if (flagsQueue.size() == buttons[element].countOfBombs && flagsQueue.size() != 0) {
                Set<Integer> set = new HashSet<>(notOpenedCellsAroundCell(element, buttons, mapSize));
                set.removeAll(flagsQueue);
                if (buttons[element].countOfBombs == 1) field.buttonsWithoutBombsAround1.addAll(set);
                else if (buttons[element].countOfBombs == 2) field.buttonsWithoutBombsAround2.addAll(set);
                else if (buttons[element].countOfBombs == 3) field.buttonsWithoutBombsAround3.addAll(set);
                else if (buttons[element].countOfBombs == 4) field.buttonsWithoutBombsAround4.addAll(set);
                else if (buttons[element].countOfBombs == 5) field.buttonsWithoutBombsAround5.addAll(set);
                else if (buttons[element].countOfBombs == 6) field.buttonsWithoutBombsAround6.addAll(set);
                else if (buttons[element].countOfBombs == 7) field.buttonsWithoutBombsAround7.addAll(set);
                if (notOpenedCellsAroundCell(element, buttons, mapSize).size() ==
                        numbersOfFlagsAroundCell(element, buttons, mapSize).size())
                    field.numbersOfOpenCellsWithDigit.remove(element);
            }
        }
    }

    /**
     * This method choose a number of next open button.
     * It check buttons without bomb around cells with count of bombs = 1, then with count of bombs = 2 etc.
     * If it haven't this cells it use method "cellWithMinChanceOfBomb", that find cell with
     * the smallest count of bombs.
     * It has repeating code in method "forNumberOfNextOpenButton".
     */
    public int numberOfNextOpenButton(Cell[] buttons, int mapSize, Field field) {
        int number = 0;
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
        } else number = cellWithMinChanceOfBomb(field, buttons, mapSize);
        return number;
    }

    private int forNumberOfNextOpenButton(Set<Integer> set) {
        Queue<Integer> queue = new LinkedList<>(set);
        int number = queue.poll();
        set.remove(number);
        return number;
    }

    /**
     * This method find cell with the smallest count of bombs from opened cells.
     */
    private int cellWithMinChanceOfBomb(Field field, Cell[] buttons, int mapSize) {
        int number = 0;
        Queue<Integer> queue;
        Set<Integer> set = field.numbersOfOpenCellsWithDigit;
        int minCountOfBombs = 8;
        for (int e : set) {
            queue = notOpenedCellsAroundCell(e, buttons, mapSize);
            queue.removeAll(numbersOfFlagsAroundCell(e, buttons, mapSize));
            if (buttons[e].countOfBombs <= minCountOfBombs && queue.size() != 0) {
                number = queue.poll();
                if (buttons[e].countOfBombs == 1) break;
            }
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
                    notOpenedCellsAroundCell(i, buttons, mapSize).size() == bombs) {
                field.knownNumbersOfBombs.addAll(notOpenedCellsAroundCell(i, buttons, mapSize));
            }
        }
    }
}

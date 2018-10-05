package golzitsky.botForSapper;


import golzitsky.botForSapper.core.BotLogic;
import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void notOpenButtonsAroundButton() {
        Field field = new Field();
        field.buttons = new Cell[25];
        for (int i = 0; i < 25; i++) {
            field.buttons[i] = new Cell();
        }
        field.buttons[0].setOpen(true);
        field.buttons[1].setOpen(true);
        field.buttons[2].setOpen(true);
        BotLogic botLogic = new BotLogic();
        Set<Integer> set = new HashSet<>(botLogic.notOpenedCellsAroundCell(5, field.buttons, 5));
        assertEquals(set, new HashSet<>(Arrays.asList(10, 11, 6)));
    }

    @Test
    void numbersOfFlagsAroundCell() {
        int mapSize = 5;
        BotLogic botLogic = new BotLogic();
        Field field = new Field();
        field.buttons = new Cell[mapSize * mapSize];
        for (int i = 0; i < mapSize * mapSize; i++) {
            field.buttons[i] = new Cell();
        }
        field.buttons[2].setFlag(true);
        field.buttons[6].setFlag(true);
        field.buttons[15].setFlag(true);
        Queue<Integer> result = new LinkedList<>();
        result.add(2);
        result.add(6);
        assertEquals(botLogic.numbersOfFlagsAroundCell(7, field.buttons, mapSize), result);
        result.clear();
        result.add(15);
        assertEquals(botLogic.numbersOfFlagsAroundCell(16, field.buttons, mapSize), result);
        result.clear();
        field.buttons[1].setFlag(true);
        field.buttons[2].setFlag(true);
        field.buttons[3].setFlag(true);
        field.buttons[6].setFlag(true);
        field.buttons[8].setFlag(true);
        field.buttons[11].setFlag(true);
        field.buttons[12].setFlag(true);
        field.buttons[13].setFlag(true);
        result.addAll(Arrays.asList(1, 2, 3, 6, 13, 12, 11, 8));
        assertEquals(botLogic.numbersOfFlagsAroundCell(7, field.buttons, mapSize), result);
    }

    @Test
    void knowButtonsWithoutBombs() {
        int mapSize = 5;
        BotLogic botLogic = new BotLogic();
        Field field = new Field();
        field.buttons = new Cell[mapSize * mapSize];
        for (int i = 0; i < mapSize * mapSize; i++) {
            field.buttons[i] = new Cell();
        }
        field.buttons[2].setFlag(true);
        field.buttons[6].setFlag(true);
        field.buttons[3].countOfBombs = 1;
        field.buttons[7].countOfBombs = 2;
        field.buttons[8].countOfBombs = 1;
        field.buttons[10].countOfBombs = 1;
        field.buttons[11].countOfBombs = 1;
        field.buttons[12].countOfBombs = 1;
        field.buttons[13].countOfBombs = 0;
        field.buttons[1].countOfBombs = 2;
        field.buttons[0].countOfBombs = 1;
        field.buttons[5].countOfBombs = 1;

        field.buttons[5].setOpen(true);
        field.buttons[7].setOpen(true);
        field.buttons[8].setOpen(true);
        field.buttons[13].setOpen(true);
        field.buttons[11].setOpen(true);
        field.buttons[10].setOpen(true);
        field.numbersOfOpenCellsWithDigit.addAll(Arrays.asList(5, 7, 8, 10, 13, 11));

        botLogic.knowButtonsWithoutBombs(field.buttons, mapSize, field);
        assertEquals(field.buttonsWithoutBombsAround1, new HashSet<>(
                Arrays.asList(0, 1, 3, 4, 9, 12, 14, 15, 16, 17)));
        assertEquals(field.buttonsWithoutBombsAround2, new HashSet<>(Arrays.asList(1, 3, 12)));
        field.buttons[15].setFlag(true);
        field.buttons[16].countOfBombs = 0;
        botLogic.knowButtonsWithoutBombs(field.buttons, mapSize, field);
        assertEquals(field.buttonsWithoutBombsAround1, new HashSet<>(
                Arrays.asList(0, 1, 3, 4, 9, 12, 14, 15, 16, 17)));
    }

    @Test
    void maybeAroundCellOnlyBombs() {
        int mapSize = 5;
        BotLogic botLogic = new BotLogic();
        Field field = new Field();
        field.buttons = new Cell[mapSize * mapSize];
        for (int i = 0; i < mapSize * mapSize; i++) {
            field.buttons[i] = new Cell();
        }
        field.buttons[0].countOfBombs = -1;
        field.buttons[1].countOfBombs = -1;
        field.buttons[2].countOfBombs = 1;
        field.buttons[2].setOpen(true);
        field.buttons[5].countOfBombs = 2;
        field.buttons[5].setOpen(true);
        field.buttons[7].countOfBombs = 1;
        field.buttons[7].setOpen(true);
        field.buttons[10].countOfBombs = 0;
        field.buttons[10].setOpen(true);
        field.buttons[11].countOfBombs = 0;
        field.buttons[11].setOpen(true);
        field.buttons[12].countOfBombs = 0;
        field.buttons[12].setOpen(true);

        field.buttons[6].countOfBombs = 2;
        field.buttons[6].setOpen(true);
        botLogic.maybeAroundCellOnlyBombs(6, field.buttons, mapSize, field);
        assertEquals(new HashSet<>(Arrays.asList(0, 1)), field.knownNumbersOfBombs);

    }
}
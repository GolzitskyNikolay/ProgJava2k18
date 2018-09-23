package golzitsky.botForSapper;


import golzitsky.botForSapper.core.BotLogic;
import golzitsky.botForSapper.core.Cell;
import golzitsky.botForSapper.core.Field;
import golzitsky.botForSapper.core.GameLogic;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @Test
    void numbersOfFlagsAroundCell() {
        int mapSize = 5;
        BotLogic botLogic = new BotLogic();
        Field field = new Field();
        field.buttons = new Cell[mapSize * mapSize];
        for (int i = 0; i < mapSize * mapSize; i++) {
            field.buttons[i] = new Cell();
        }
        field.buttons[2].hasFlag = true;
        field.buttons[6].hasFlag = true;
        field.buttons[3].countOfBombs = 1;
        field.buttons[7].countOfBombs = 2;
        field.buttons[8].countOfBombs = 1;
        field.buttons[10].countOfBombs = 1;
        field.buttons[11].countOfBombs = 1;
        field.buttons[12].countOfBombs = 1;
        field.buttons[1].countOfBombs = 2;
        field.buttons[0].countOfBombs = 1;
        field.buttons[5].countOfBombs = 1;
        Queue<Integer> queue = new LinkedList<>();
        botLogic.openOrCountNotOpenButtonsOrFlagsAroundCell(field.buttons, 7, mapSize, queue, false, true);
        Queue<Integer> result = new LinkedList<>();
        result.add(2);
        result.add(6);
        assertEquals(queue, result);
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
        field.buttons[2].hasFlag = true;
        field.buttons[6].hasFlag = true;
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

        field.buttons[7].isOpen = true;
        field.numbersOfDigits.add(7);

        botLogic.knowButtonsWithoutBombs(field.buttons, mapSize, field);
        Set<Integer> set = new HashSet<>(Arrays.asList(1, 3, 8, 11, 12, 13));
        assertEquals(field.buttonsWithoutBombs, set);
    }
}
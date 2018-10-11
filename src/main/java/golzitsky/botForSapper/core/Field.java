package golzitsky.botForSapper.core;

import java.util.*;

public class Field {
    public Queue<Integer> numbersOfButtonsAroundEmptyButton = new LinkedList<>();
    public Set<Integer> numbersOfEmptyButtons = new HashSet<>();
    public Set<Integer> allNumbersOfBombs = new HashSet<>(); //all indexes of bombs in array of Cells
    public int quantityOfOpenButtons;
    public Cell[] buttons;     //array of Cells that we use to create field
    public int chanceOfBombs;  //we use this variable for difficulty, the it higher, the more bombs
    public int numberOfBombs;  //number of all bombs in field
    public int mapSize;
    public Set<Integer> knownNumbersOfBombs = new HashSet<>();   //bombs found by the bot
    public Set<Integer> knownNumbersOfFlags = new HashSet<>();   //cells with flags
    public Queue<Integer> numbersOfOpenCellsWithDigit = new LinkedList<>(); //all open cells without bombs, not empty.
    public Set<Integer> buttonsWithoutBombsAround1 = new HashSet<>();  //These Sets are Sets with indexes of bombs
    public Set<Integer> buttonsWithoutBombsAround2 = new HashSet<>();  //around cells with number of
    public Set<Integer> buttonsWithoutBombsAround3 = new HashSet<>();  //bombs = 1, 2, 3 and etc.
    public Set<Integer> buttonsWithoutBombsAround4 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround5 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround6 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround7 = new HashSet<>();
}

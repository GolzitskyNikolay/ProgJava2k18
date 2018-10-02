package golzitsky.botForSapper.core;

import java.util.*;

public class Field {
    public Queue<Integer> numbersOfButtonsAroundEmptyButton = new LinkedList<>();
    public Set<Integer> numbersOfEmptyButtons = new HashSet<>();
    public Set<Integer> allNumbersOfBombs = new HashSet<>();
    public int quantityOfOpenButtons;
    public Cell[] buttons;
    public int chanceOfBombs;
    public int allBombs;
    public int mapSize;
    public Set<Integer> knownNumbersOfBombs = new HashSet<>();
    public Set<Integer> numbersOfOpenCellsWithDigit = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround1 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround2 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround3 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround4 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround5 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround6 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround7 = new HashSet<>();
    public Set<Integer> buttonsWithoutBombsAround8 = new HashSet<>();
}

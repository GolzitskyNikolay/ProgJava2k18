package golzitsky.botForSapper.core;

import java.util.*;

public class Field {
    public Queue<Integer> numbersOfButtonsAroundEmptyButton = new LinkedList<>();
    public HashSet<Integer> numbersOfEmptyButtons = new HashSet<>();
    public HashSet<Integer> numbersOfBombs = new HashSet<>();
    public HashSet<Integer> knownNumbersOfBombs = new HashSet<>();
    public int quantityOfOpenButtons;
    public Cell[] buttons;
    public int chanceOfBombs;
    public int allBombs;
    public int mapSize;
}

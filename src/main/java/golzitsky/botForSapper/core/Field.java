package golzitsky.botForSapper.core;

import java.util.*;

public class Field {
    public Queue<Integer> numbersOfButtonsAroundEmptyButton = new LinkedList<>();
    public HashSet<Integer> numbersOfEmptyButtons = new HashSet<>();
    public HashSet<Integer> allNumbersOfBombs = new HashSet<>();     //для генерации поля
    public HashSet<Integer> knownNumbersOfBombs = new HashSet<>();   //бомбы, которые вычислил бот
    public HashSet<Integer> numbersOfDigits = new HashSet<>();       //индексы всех ячеек с цифрами
    public HashSet<Integer> buttonsWithoutBombs = new HashSet<>();
    public int quantityOfOpenButtons;
    public Cell[] buttons;
    public int chanceOfBombs;
    public int allBombs;
    public int mapSize;
}

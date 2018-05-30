package golzitsky.task3.core;

import golzitsky.task3.GUI.RedrawCell;

import java.util.*;

public class Field {
    public HashSet<Integer> numbersOfEmptyButtons = new HashSet<>();
    public HashSet<Integer> numbersOfBombs = new HashSet<>();
    public int quantityOfOpenButtons;
    public RedrawCell[] buttons;
    public int chanceOfBombs;
    public int allBombs;
    public int mapSize;
}
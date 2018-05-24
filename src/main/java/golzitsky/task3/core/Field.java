package golzitsky.task3.core;

import golzitsky.task3.GUI.RedrawCell;

import java.util.*;

public class Field {
    public static int quantityOfOpenButtons;
    public static HashSet<Integer> numbersOfBombs = new HashSet<>();
    public static HashSet<Integer> numbersOfEmptyButtons = new HashSet<>();
    public static RedrawCell[] buttons;
    public int allBombs = 0;
}
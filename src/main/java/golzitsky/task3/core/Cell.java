package golzitsky.task3.core;

import javax.swing.*;

public class Cell extends JButton {
    protected int countOfBombs;
    protected boolean hasFlag = false;
    public boolean hasBomb;
    protected boolean isOpen = false;

    public boolean isHasFlag() {
        return hasFlag;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isHasBomb() {
        return hasBomb;
    }
}
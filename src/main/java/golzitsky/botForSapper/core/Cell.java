package golzitsky.botForSapper.core;

import javax.swing.*;
import java.util.Random;

public class Cell extends JButton {
    public int countOfBombs;
    private boolean hasFlag = false;
    private boolean hasBomb = false;
    private boolean isOpen = false;

    public void countOfBombs(int numberOfBombs) {
        countOfBombs = numberOfBombs;
    }

    public boolean isHasFlag() {
        return hasFlag;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isHasBomb() {
        return hasBomb;
    }

    public void firstButtonHasntBomb() {
        hasBomb = false;
    }

    public void setFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public void chanceOfBomb(Field field, Cell cell) {
        Random rnd = new Random();
        cell.hasBomb = rnd.nextInt(100) <= field.chanceOfBombs;
    }
}
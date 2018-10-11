package golzitsky.botForSapper.core;

import javax.swing.*;
import java.util.Random;

public class Cell extends JButton {
    public int countOfBombs; //number of bombs around cell
    private boolean hasFlag = false; //if cell hasn't flag -> false, else -> true
    private boolean hasBomb = false; //if cell hasn't bomb -> false, else -> true
    private boolean isOpen = false;  //if cell isn't open  -> false, else -> true

    /**
     * Here we know the number of bombs around the cell (this method is called when a field is generated)
     */
    public void countOfBombs(int numberOfBombs) {
        countOfBombs = numberOfBombs;
    }

    public boolean isHasFlag() {
        return hasFlag;
    }

    public boolean isHasBomb() {
        return hasBomb;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    /**
     * When Bot will open first cell, this cell wont't has a bomb.
     */
    public void firstButtonHasntBomb() {
        hasBomb = false;
    }

    /**
     * We use variable "chanceOfBombs" from Field. The higher this variable, the more chance of bombs in this cell.
     */
    public void chanceOfBomb(Field field, Cell cell) {
        Random rnd = new Random();
        cell.hasBomb = rnd.nextInt(100) <= field.chanceOfBombs;
    }
}
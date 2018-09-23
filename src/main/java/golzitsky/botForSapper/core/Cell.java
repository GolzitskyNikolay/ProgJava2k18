package golzitsky.botForSapper.core;

import javax.swing.*;
import java.util.Random;

public class Cell extends JButton {
    public boolean hasFlag = false;
    public boolean hasBomb = false;
    public boolean isOpen = false;

    public int countOfBombs;


    public void countOfBombs(int numberOfBombs) {
        countOfBombs = numberOfBombs;
    }

    public boolean isHasFlag() {
        return hasFlag;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isHasBomb() {
        return hasBomb;
    }

    public void firstButtonHasntBomb() {
        hasBomb = false;
    }

    public void chanceOfBomb(Field field, Cell cell) {
        Random rnd = new Random();
        cell.hasBomb = rnd.nextInt(100) <= field.chanceOfBombs;
    }
}
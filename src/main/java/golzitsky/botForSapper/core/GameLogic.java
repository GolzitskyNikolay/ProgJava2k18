package golzitsky.botForSapper.core;

public class GameLogic {

    public Boolean isLose(Cell cell) {
        return cell.isHasBomb() && cell.isOpen();
    }

    public Boolean isWin(Field field) {
        return field.quantityOfOpenButtons + field.allBombs == field.mapSize * field.mapSize;
    }

    public int countBombsAroundCell(Cell[] fieldButtons, int i, int mapSize) {
        int numberOfBombs = 0;
        if (i % mapSize != 0 && i >= mapSize + 1 && fieldButtons[i - (mapSize + 1)].isHasBomb()) numberOfBombs++;

        if ((i >= mapSize) && fieldButtons[i - mapSize].isHasBomb()) numberOfBombs++;

        if ((i >= mapSize - 1 && i % mapSize != mapSize - 1) &&
                fieldButtons[i - (mapSize - 1)].isHasBomb()) numberOfBombs++;

        if (i % mapSize != 0 && fieldButtons[i - 1].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize && i % mapSize != mapSize - 1 &&
                fieldButtons[i + 1].isHasBomb()) numberOfBombs++;

        if (i % mapSize != 0 && i <= mapSize * mapSize - mapSize &&
                fieldButtons[i + mapSize - 1].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize - mapSize && fieldButtons[i + mapSize].isHasBomb()) numberOfBombs++;

        if (i < mapSize * mapSize - mapSize && i % mapSize != mapSize - 1 &&
                fieldButtons[i + mapSize + 1].isHasBomb()) numberOfBombs++;
        return numberOfBombs;
    }
}

package golzitsky.task3.core;

public class GameLogic {

    public Boolean isLose(Cell cell) {
        return cell.hasBomb && cell.isOpen;
    }

    public Boolean isWin(Field field) {
        return field.quantityOfOpenButtons + field.allBombs == field.mapSize * field.mapSize;
    }

    public void generateFieldByFirstClick(int i, Field classField, int mapSize) {
        GameLogic gameLogic = new GameLogic();
        Cell[] fieldButtons = classField.buttons;
        fieldButtons[i].firstButtonHasntBomb();
        for (int j = 0; j < mapSize * mapSize; j++) {
            if (j != i) {
                fieldButtons[j].chanceOfBomb(classField, fieldButtons[j]);
                if (fieldButtons[j].isHasBomb()) {
                    classField.allBombs++;
                    classField.numbersOfBombs.add(j);
                }
            }
        }
        for (int j = 0; j < mapSize * mapSize; j++) {
            int numberOfBombs = -1;
            if (!fieldButtons[j].isHasBomb()) {
                numberOfBombs = gameLogic.countNumberOfBombsAroundCell(fieldButtons, j, mapSize);
            }
            if (numberOfBombs == 0) classField.numbersOfEmptyButtons.add(j);
            fieldButtons[j].countOfBombs(numberOfBombs);
        }
    }

    public int countNumberOfBombsAroundCell(Cell[] fieldButtons, int i, int mapSize) {
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

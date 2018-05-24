package golzitsky.task3.GUI;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTests {

    @Test
    void countOfBombsAroundCell() {
        List<Integer> mapSizes = new ArrayList<>(Arrays.asList(5, 6, 7, 8, 9, 10));
        List<HashSet> setsOfBombs = new ArrayList<>(Arrays.asList(
                new HashSet<>(Arrays.asList(0, 4, 6, 8, 12, 16, 18, 20, 24)),
                new HashSet<>(Arrays.asList(0, 1, 2, 6, 8, 12, 14, 18, 20, 23, 28, 29, 33, 34, 35)),
                new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 13, 14, 20, 21, 27, 28, 34, 35,
                        41, 42, 43, 44, 45, 46, 47, 48)),
                new HashSet<>(Arrays.asList(9, 11, 12, 14, 17, 19, 20, 22, 25, 27, 28, 30, 33, 35,
                        36, 38, 41, 43, 44, 46, 49, 51, 52, 54, 57, 58, 59, 60, 61, 62)),
                new HashSet<>(Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28,
                        30, 32, 34, 36, 38, 40, 42, 44, 46, 48, 50, 52, 54, 56, 58, 60, 62, 64, 66,
                        68, 70, 72, 74, 76, 78, 80)),
                new HashSet<>(Arrays.asList(11, 14, 15, 18, 22, 27, 33, 34, 35, 36, 41, 43, 46, 48,
                        51, 53, 56, 58, 63, 64, 65, 66, 72, 77, 81, 84, 85, 88))
        ));
        List<List<Integer>> expectedResults = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(2, 2, 2, 2, 3, 2, 2, 3, 3, 2, 2, 3, 2, 2, 2, 2)),
                new ArrayList<>(Arrays.asList(2, 0, 0, 7, 3, 0, 0, 6, 3, 1, 1, 4, 3, 3, 1, 2, 2, 4, 0, 0, 1)),
                new ArrayList<>(Arrays.asList(5, 3, 3, 3, 5, 3, 0, 0, 0, 3, 3, 0, 0, 0, 3, 3, 0, 0, 0, 3, 5,
                        3, 3, 3, 5)),
                new ArrayList<>(Arrays.asList(1, 1, 2, 2, 2, 2, 1, 1, 2, 4, 4, 2, 3, 6, 6, 3, 3, 6, 6, 3, 3,
                        6, 6, 3, 3, 6, 6, 3, 3, 7, 7, 3, 2, 2)),
                new ArrayList<>(Arrays.asList(3, 3, 3, 3, 3, 4, 4, 4, 3, 4, 4, 4, 4, 3, 4, 4, 4, 3, 4, 4, 4,
                        4, 3, 4, 4, 4, 3, 4, 4, 4, 4, 3, 4, 4, 4, 3, 3, 3, 3, 3)),
                new ArrayList<>(Arrays.asList(1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 2, 4, 5, 5,
                        4, 2, 1, 1, 2, 4, 4, 2, 1, 2, 5, 5, 5, 5, 2, 2, 5, 5, 5, 5, 2, 1, 2, 4, 4, 2, 1, 1,
                        2, 4, 5, 5, 4, 2, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1))
        ));
        RedrawCell[] testField;
        GenerateField object;
        for (int mapSize : mapSizes) {
            SapperLauncher.mapSize = mapSize;
            testField = new RedrawCell[mapSize * mapSize];
            for (int i = 0; i < mapSize * mapSize; i++) {
                testField[i] = new RedrawCell();
                if (setsOfBombs.get(mapSize - 5).contains(i)) testField[i].hasBomb = true;
            }
            object = new GenerateField();
            List<Integer> result = new ArrayList<>();
            for (int i = 0; i < mapSize * mapSize; i++) {
                if (!setsOfBombs.get(mapSize - 5).contains(i)) result.add(object.countNumberOfBombs(testField, i));
            }
            assertEquals(result, expectedResults.get(mapSize - 5));
        }
    }
}
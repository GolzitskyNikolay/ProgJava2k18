package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BotMovies {
    private GameLogic gameLogic;
    private BotLogic botLogic = new BotLogic();
    private RedrawCell redrawCell = new RedrawCell();
    private int numberOfOpenButton;
    private Cell[] buttons;
    private Field field;
    private int mapSize;

    private Timer timer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repeatCode(buttons, field, mapSize);
        }
    });

    void begin(Field field, int firstOpenButton, GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        numberOfOpenButton = firstOpenButton;
        buttons = field.buttons;
        this.field = field;
        mapSize = field.mapSize;
        timer.setRepeats(true);
        timer.start();

    }

    private void repeatCode(Cell[] buttons, Field field, int mapSize) {
        redrawCell.openButton(buttons, field, numberOfOpenButton,
                mapSize, field.numbersOfButtonsAroundEmptyButton, gameLogic);

        if (gameLogic.isLose(buttons[numberOfOpenButton])) {
            endGame("Lose!!!");
            PlaySound.playSound("src\\main\\resources\\sounds\\boom.wav");
        }

        if (gameLogic.isWin(field)) {
            endGame("Win!!!");
            PlaySound.playSound("src\\main\\resources\\sounds\\win.wav");
        }

        while (!field.numbersOfButtonsAroundEmptyButton.isEmpty()) {
            redrawCell.openButton(buttons, field, field.numbersOfButtonsAroundEmptyButton.poll(),
                    mapSize, field.numbersOfButtonsAroundEmptyButton, gameLogic);
        }

        for (int element : field.numbersOfOpenCellsWithDigit) {
            if (buttons[element].isOpen())
                botLogic.maybeAroundCellOnlyBombs(element, buttons, mapSize, field);
        }

        if (!field.knownNumbersOfBombs.isEmpty()) {
            for (int button : field.knownNumbersOfBombs) {
                redrawCell.makeFlag(buttons, button);
            }
            field.knownNumbersOfBombs.clear();
        }

        botLogic.knowButtonsWithoutBombs(buttons, mapSize, field);

        numberOfOpenButton = botLogic.numberOfNextOpenButton(numberOfOpenButton,
                buttons, mapSize, field);
    }

    private void endGame(String message) {
        System.out.println(message);
        timer.stop();
    }
}

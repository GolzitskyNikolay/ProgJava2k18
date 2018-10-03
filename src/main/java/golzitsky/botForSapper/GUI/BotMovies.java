package golzitsky.botForSapper.GUI;

import golzitsky.botForSapper.core.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BotMovies {
    private RedrawCell redrawCell = new RedrawCell();
    private BotLogic botLogic = new BotLogic();
    private int numberOfNextOpenButton;
    private GameLogic gameLogic;
    private Cell[] buttons;
    private Field field;
    private int mapSize;

    private Timer timer = new Timer(150, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repeatCode(buttons, field, mapSize);
        }
    });

    Timer getTimer() {
        return timer;
    }

    void begin(Field field, int firstOpenButton, GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        numberOfNextOpenButton = firstOpenButton;
        buttons = field.buttons;
        this.field = field;
        mapSize = field.mapSize;
        timer.setRepeats(true);
        timer.start();

    }

    /**
     * This method always repeat in the Timer.
     */
    private void repeatCode(Cell[] buttons, Field field, int mapSize) {
        redrawCell.openButton(buttons, field, numberOfNextOpenButton,             //open cell
                mapSize, field.numbersOfButtonsAroundEmptyButton, gameLogic);

        if (gameLogic.isLose(buttons[numberOfNextOpenButton])) {
            System.out.println("Lose!!!");
            timer.stop();
            PlaySound.playSound("src\\main\\resources\\sounds\\boom.wav");
        } else if (gameLogic.isWin(field)) {
            System.out.println("Win!!!");
            timer.stop();
            PlaySound.playSound("src\\main\\resources\\sounds\\win.wav");
        } else {
            while (!field.numbersOfButtonsAroundEmptyButton.isEmpty()) {          //open all cells around empty cell
                redrawCell.openButton(buttons, field,
                        field.numbersOfButtonsAroundEmptyButton.poll(), mapSize,
                        field.numbersOfButtonsAroundEmptyButton, gameLogic);
            }

            for (int element : field.numbersOfOpenCellsWithDigit) {                //write known bombs
                if (buttons[element].isOpen())
                    botLogic.maybeAroundCellOnlyBombs(element, buttons, mapSize, field);
            }

            if (!field.knownNumbersOfBombs.isEmpty()) {
                for (int button : field.knownNumbersOfBombs) {                     //make flags for known bombs
                    redrawCell.makeFlag(buttons, button, field);
                }
                field.knownNumbersOfBombs.clear();
            }

            botLogic.knowButtonsWithoutBombs(buttons, mapSize, field);

            numberOfNextOpenButton = botLogic.numberOfNextOpenButton(buttons, mapSize, field);
        }
    }
}

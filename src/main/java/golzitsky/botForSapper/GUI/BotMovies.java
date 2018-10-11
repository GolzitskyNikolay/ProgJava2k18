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
    private Boolean pause = false; //if Timer is going -> false, else -> true.
    private Timer timer = new Timer(150, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            repeatCode(buttons, field, mapSize);
        }
    });

    Boolean getPause() {
        return pause;
    }

    void setPause(Boolean pause) {
        this.pause = pause;
    }

    Timer getTimer() {
        return timer;
    }

    /**
     * It starts Timer.
     */
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
     * This method is repeated in the Timer.
     */
    private void repeatCode(Cell[] buttons, Field field, int mapSize) {
        redrawCell.openButton(buttons, field, numberOfNextOpenButton,             //open cell
                mapSize, field.numbersOfButtonsAroundEmptyButton, gameLogic);
        if (gameLogic.isLose(buttons[numberOfNextOpenButton])) {
            timer.stop();
            PlaySound.playSound("src\\main\\resources\\sounds\\boom.wav");
        } else if (gameLogic.isWin(field)) {
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

            botLogic.knowButtonsWithoutBombs(buttons, mapSize, field);             //find cells without bombs

            numberOfNextOpenButton = botLogic.numberOfNextOpenButton(buttons, mapSize, field);  //choose next
                                                                                                // cell to open it
        }
    }
}

package golzitsky.Sapper.GUI;

import golzitsky.Sapper.core.Field;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Menu {

    static void createMenu(Field classField, JFrame jFrame, JPanel panel) throws IOException {
        changeIcon(jFrame);
        JMenuBar menuBar = new JMenuBar();

        JMenu game = new JMenu("Game");
        addNewGame(game, classField, jFrame, panel);
        addExit(game);

        JMenu settings = new JMenu("Settings");
        addSizeOfMap(settings, classField, jFrame, panel);
        addDifficult(settings, classField, jFrame, panel);
        addSound(settings);

        menuBar.add(game);
        menuBar.add(settings);
        jFrame.add(menuBar);
        jFrame.setJMenuBar(menuBar);
    }

    private static void addNewGame(JMenu game, Field classField, JFrame jFrame, JPanel panel) {
        ImageIcon flag1 = new ImageIcon("src\\main\\resources\\images\\reload.png");
        JMenuItem newGame = new JMenuItem("Start New Game", flag1);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SapperLauncher.startGame(classField, jFrame, panel);
            }
        });
        game.add(newGame);
    }

    private static void addDifficult(JMenu settings, Field classField, JFrame jFrame, JPanel panel) {
        ImageIcon flag2 = new ImageIcon("src\\main\\resources\\images\\settings.png");
        JMenuItem difficult = new JMenuItem("Difficult", flag2);
        difficult.setToolTipText("You can choose number of chanceOfBombs");
        difficult.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forDifficultAndMap(true, classField, jFrame, panel);
            }
        });
        settings.add(difficult);
    }

    private static void addSizeOfMap(JMenu settings, Field classField, JFrame jFrame, JPanel panel) {
        ImageIcon flag3 = new ImageIcon("src\\main\\resources\\images\\field.png");
        JMenuItem sizeOfMap = new JMenuItem("Size of map", flag3);
        sizeOfMap.setToolTipText("You can choose map size of the playing field");
        sizeOfMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                forDifficultAndMap(false, classField, jFrame, panel);
            }
        });
        settings.add(sizeOfMap);
    }

    private static void forDifficultAndMap(Boolean isDifficult, Field classField, JFrame jFrame, JPanel panel) {
        JDialog jDialog = new JDialog();
        jDialog.setModal(true);
        JPanel jPanel = new JPanel();
        JSlider slider;
        if (!isDifficult) {
            slider = new JSlider(5, 15, classField.mapSize);
            slider.setMajorTickSpacing(1);
        } else {
            slider = new JSlider(10, 90, classField.chanceOfBombs);
            slider.setMajorTickSpacing(10);
        }
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setPaintLabels(true);

        JButton button = new JButton("Save changes");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isDifficult) classField.chanceOfBombs = slider.getValue();
                else {
                    int result = JOptionPane.showConfirmDialog(null,
                            "The current game will be over." + "\n" +
                                    "Do you want to start a new game?");
                    if (result == JOptionPane.YES_OPTION) {
                        classField.mapSize = slider.getValue();
                        SapperLauncher.startGame(classField, jFrame, panel);
                    }
                }
                jDialog.dispose();
            }
        });
        jPanel.add(slider);
        jPanel.add(button);

        jDialog.add(jPanel);
        jDialog.setBounds(465, 300, 450, 100);
        jDialog.setResizable(false);

        jDialog.setVisible(true);
    }

    private static void addExit(JMenu game) {
        ImageIcon flag4 = new ImageIcon("src\\main\\resources\\images\\exit.png");
        JMenuItem exit = new JMenuItem("Exit", flag4);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        game.add(exit);
    }

    private static void addSound(JMenu settings) {

        ImageIcon flag5 = new ImageIcon("src\\main\\resources\\images\\volume.png");

        JMenuItem sound = new JMenuItem("turn on/off Sound", flag5);

        PlaySound playSoundObject = new PlaySound();

        if (playSoundObject.playSound) sound.setToolTipText("Sound on");

        sound.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundObject.playSound = !playSoundObject.playSound;
                if (playSoundObject.playSound) sound.setToolTipText("Sound on");
                else sound.setToolTipText("Sound off");
            }
        });
        settings.add(sound);
    }

    private static void changeIcon(JFrame jFrame) throws IOException {
        Image icon = ImageIO.read(new java.io.File("src\\main\\resources\\images\\bomb1.png"));
        jFrame.setIconImage(icon);
    }

}

package ui;

import model.*;
import persistence.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * connects all GameHandler functionality to GameFrame class
 */
public class ConsoleHandlerGui {
    private static final String JSON_STORE = "./data/myFile.json";

    private boolean isAttacking;
    private GameHandler gameHandler;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private GameFrame gameFrame;

    public ConsoleHandlerGui() throws FileNotFoundException {
        gameFrame = new GameFrame();

        isAttacking = false;
        setUpButtons();
        setUpButtons2();

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //EFFECTS: calls exitApplication when gui window closes
        gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                exitApplication();
            }
        });
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    //EFFECTS: initializes a new game with given turns
    private void setUpGame(int turns) {
        gameFrame.getActionPerformed().setText("Setting up new game!");

        gameHandler = new GameHandler(turns);
        gameHandler.setUpGame();
        displayBoard();
    }

    //EFFECTS: connects left buttons with actionListener
    private void setUpButtons() {
        gameFrame.getLoadButton().addActionListener(new LeftButtonHandler());
        gameFrame.getSaveButton().addActionListener(new LeftButtonHandler());
        gameFrame.getInputEntry().addActionListener(new LeftButtonHandler());
        gameFrame.getQuitButton().addActionListener(new LeftButtonHandler());
    }

    //EFFECTS: connects right buttons with actionListener
    private void setUpButtons2() {
        RightButtonListener listener = new RightButtonListener();
        gameFrame.getUpButton().addActionListener(listener);
        gameFrame.getDownButton().addActionListener(listener);
        gameFrame.getLeftButton().addActionListener(listener);
        gameFrame.getRightButton().addActionListener(listener);
        gameFrame.getAttackButton().addActionListener(listener);
        disableButtons();
    }

    //EFFECTS: enables right buttons with actionListener
    private void enableButtons() {
        gameFrame.getUpButton().setEnabled(true);
        gameFrame.getDownButton().setEnabled(true);
        gameFrame.getLeftButton().setEnabled(true);
        gameFrame.getRightButton().setEnabled(true);
        gameFrame.getAttackButton().setEnabled(true);
    }

    //EFFECTS: disables right buttons with actionListener
    private void disableButtons() {
        gameFrame.getUpButton().setEnabled(false);
        gameFrame.getDownButton().setEnabled(false);
        gameFrame.getLeftButton().setEnabled(false);
        gameFrame.getRightButton().setEnabled(false);
        gameFrame.getAttackButton().setEnabled(false);
    }

    //EFFECTS: displays current game board
    private void displayBoard() {
        String toDisplay = "";

        for (int i = 0; i < gameHandler.getGrid().length; i++) {
            for (int j = 0; j < gameHandler.getGrid().length; j++) {
                toDisplay += gameHandler.getGrid()[i][j] + "  ";
            }
            toDisplay += "\n";
        }
        gameFrame.getRemainingTurns().setText("Remaining turns: " + gameHandler.getUser().getTurns());
        gameFrame.getScore().setText("Current score: " + gameHandler.getScore());
        gameFrame.getTextSide().setText(toDisplay);
    }

    //EFFECTS: loads and in input from user and sets up a new game
    private void getInput() {
        try {
            int turns = Integer.parseInt(gameFrame.getInputTurns().getText());
            setUpGame(turns);
            gameFrame.getInputEntry().setEnabled(false);
            enableButtons();
        } catch (Exception exception) {
            gameFrame.getActionPerformed().setText("Bleh... wrong input");
        }
    }

    //EFFECTS: loads and displays game
    private void loadGame() {
        gameFrame.getActionPerformed().setText("Attempting to load game...");
        try {
            setGameHandler(jsonReader.readToGameHandler());
            gameFrame.getActionPerformed().setText("Load successful!");
            enableButtons();
            displayBoard();
        } catch (IOException e) {
            gameFrame.getActionPerformed().setText("Game unable to load!");
        }
    }

    //EFFECTS: saves game
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.writeToJson(gameHandler);
            jsonWriter.closePrinter();
            gameFrame.getActionPerformed().setText("Game saved!");
        } catch (FileNotFoundException exception) {
            gameFrame.getActionPerformed().setText("Unable to save");
        }
    }

    //EFFECTS: checks if game can keep going, if not will disable all buttons and display final score
    private void checkGame() {
        if (gameHandler.checkTurns()) {
            gameFrame.getActionPerformed().setText("Thanks for playing!\nYour score is: " + gameHandler.getScore());
            disableButtons();
            gameFrame.getInputEntry().setEnabled(true);
        }
    }

    //EFFECTS: prints out event log and terminates the JVM
    private void exitApplication() {
        ConsoleEventLogHandler lp = new ConsoleEventLogHandler();
        System.out.println(lp.getLoggedEvents(EventLog.getInstance()));
        System.exit(0);
    }

    //EFFECTS: passes in direction of the arrow and resolve attack
    private void handleAttack(ActionEvent e) {
        if (e.getSource().equals(gameFrame.getUpButton())) {
            attack(ConsoleHandler.UP_PHRASE);
        } else if (e.getSource().equals(gameFrame.getDownButton())) {
            attack(ConsoleHandler.DOWN_PHRASE);
        } else if (e.getSource().equals(gameFrame.getLeftButton())) {
            attack(ConsoleHandler.LEFT_PHRASE);
        } else if (e.getSource().equals(gameFrame.getRightButton())) {
            attack(ConsoleHandler.RIGHT_PHRASE);
        }
        isAttacking = false;
        gameFrame.getGif().setVisible(false);
        gameFrame.getAttackButton().setEnabled(true);
        displayBoard();
    }

    //EFFECTS: gets corresponding Wraiths and passes the argument into Player.attack()
    private void attack(String cmd) {
        List<Monster> targets = gameHandler.selectAttackDirection(cmd);
        gameHandler.getUser().attack(targets);
        gameHandler.removeMonsters();
    }

    //EFFECTS: disables attack button and makes attacking gif visible
    private void prepAttack() {
        isAttacking = true;
        gameFrame.getAttackButton().setEnabled(false);
        gameFrame.getGif().setVisible(true);
        gameFrame.getActionPerformed().setText(GameFrame.ACTION_PERFORMED + "Is attacking!");
    }

    private class RightButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isAttacking) {
                handleAttack(e);
                gameFrame.getActionPerformed().setText(GameFrame.ACTION_PERFORMED + "Attacked!");
                return;
            }

            if (e.getSource().equals(gameFrame.getUpButton())) {
                gameHandler.moveUser(ConsoleHandler.UP_PHRASE);
                gameFrame.getActionPerformed().setText(GameFrame.ACTION_PERFORMED + "Moved up!");
            } else if (e.getSource().equals(gameFrame.getDownButton())) {
                gameHandler.moveUser(ConsoleHandler.DOWN_PHRASE);
                gameFrame.getActionPerformed().setText(GameFrame.ACTION_PERFORMED + "Moved down!");
            } else if (e.getSource().equals(gameFrame.getLeftButton())) {
                gameHandler.moveUser(ConsoleHandler.LEFT_PHRASE);
                gameFrame.getActionPerformed().setText(GameFrame.ACTION_PERFORMED + "Moved left!");
            } else if (e.getSource().equals(gameFrame.getRightButton())) {
                gameHandler.moveUser(ConsoleHandler.RIGHT_PHRASE);
                gameFrame.getActionPerformed().setText(GameFrame.ACTION_PERFORMED + "Moved right!");
            } else if (e.getSource().equals(gameFrame.getAttackButton())) {
                prepAttack();
            }

            checkGame();
            displayBoard();
        }
    }

    private class LeftButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(gameFrame.getSaveButton())) {
                saveGame();
            } else if (e.getSource().equals(gameFrame.getLoadButton())) {
                loadGame();
            } else if (e.getSource().equals(gameFrame.getInputEntry())) {
                getInput();
            } else if (e.getSource().equals(gameFrame.getQuitButton())) {
                exitApplication();
            }
        }
    }


}

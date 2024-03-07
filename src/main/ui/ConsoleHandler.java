package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * represents a printer for the game
 */

public class ConsoleHandler {
    public static final String UP_PHRASE = "up";
    public static final String DOWN_PHRASE = "down";
    public static final String LEFT_PHRASE = "left";
    public static final String RIGHT_PHRASE = "right";
    public static final String ATTACK_PHRASE = "attack";
    public static final String SAVE_PHRASE = "save";
    public static final String LOAD_PHRASE = "load";
    public static final String QUIT_PHRASE = "quit";

    private static final String JSON_STORE = "./data/myFile.json";
    private static final Scanner INPUT = new Scanner(System.in);

    private GameHandler gameHandler;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;


    public ConsoleHandler() throws FileNotFoundException {
        gameHandler = new GameHandler(promptTurn());
        gameHandler.setUpGame();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
    }

    public void setGameHandler(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    // EFFECTS: prompt user input for number of turns needed
    public int promptTurn() {
        System.out.println("Type in the number of turns you want... preferably more than 15 turns ... ");
        int turns = INPUT.nextInt();
        INPUT.nextLine();

        return turns;
    }

    public void pauseGame() {
        try {
            Thread.sleep(800);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //EFFECTS: Prints the gameHandler.getGrid() out
    public void printGrid() {
        for (int i = 0; i < gameHandler.getGrid().length; i++) {
            for (int j = 0; j < gameHandler.getGrid().length; j++) {
                System.out.print(gameHandler.getGrid()[i][j] + "  ");
            }
            System.out.println();
        }
    }

    //EFFECTS: gets corresponding Wraiths and passes the argument into Player.attack()
    public void handleAttack(String cmd) {
        List<Monster> targets = gameHandler.selectAttackDirection(cmd);
        gameHandler.getUser().attack(targets);
        gameHandler.removeMonsters();
    }

    // EFFECTS: gets input from user and returns the result
    public String getInput() {
        String prompt = "Turns left for this session: " + gameHandler.getUser().getTurns();
        prompt = prompt + "\nCurrent score: " + gameHandler.getScore() + "\nAvailable commands:";
        prompt = prompt + "\n1. left 2. right 3. up 4. down 5. attack 6. save 7. load";
        prompt = prompt + "\nWhat would you like to do?\n";
        System.out.println(prompt);
        String command = INPUT.nextLine();

        command.trim();
        System.out.println("You selected: " + command);
        return command;
    }

    // EFFECTS: gets attack direction from user and returns the result
    public String getDirection() {
        System.out.println("Which direction would you like to attack?");
        System.out.println("Available commands: left, right, up, down");
        String direction = INPUT.nextLine();
        direction.trim();
        return direction;
    }

    // EFFECTS: matches given string with the corresponding method call
    public void useMenu(String command) {
        if (command.equalsIgnoreCase(UP_PHRASE)) {
            gameHandler.moveUser(UP_PHRASE);
        } else if (command.equalsIgnoreCase(DOWN_PHRASE)) {
            gameHandler.moveUser(DOWN_PHRASE);
        } else if (command.equalsIgnoreCase(LEFT_PHRASE)) {
            gameHandler.moveUser(LEFT_PHRASE);
        } else if (command.equalsIgnoreCase(RIGHT_PHRASE)) {
            gameHandler.moveUser(RIGHT_PHRASE);
        } else if (command.equalsIgnoreCase(ATTACK_PHRASE)) {
            String direction = getDirection();
            handleAttack(direction);
        } else if (command.equalsIgnoreCase(SAVE_PHRASE)) {
            saveGame();
        } else if (command.equalsIgnoreCase(LOAD_PHRASE)) {
            loadGame();
        } else if (command.equalsIgnoreCase(QUIT_PHRASE)) {
            //:)
        } else {
            System.out.println("Invalid command! Try again");
        }
    }

    // EFFECTS: initiates game mechanics according to user inputs
    public void launchGame() {
        printGrid();
        while (true) {
            String command = getInput();
            useMenu(command);
            if (gameHandler.checkTurns() || command.equalsIgnoreCase(QUIT_PHRASE)) {
                printGrid();
                gameHandler.prepTurnChange();
                System.out.println("Thanks for playing! Your score is: " + gameHandler.getScore());
                return;
            }
            gameHandler.prepTurnChange();
            pauseGame();
            printGrid();
        }
    }

    // EFFECTS: saves the current game
    private void saveGame() {
        System.out.println("Attempting to save game... ");
        try {
            jsonWriter.open();
            jsonWriter.writeToJson(gameHandler);
            jsonWriter.closePrinter();
            System.out.println("Saving successful!");
            System.out.println("Game saved to " + JSON_STORE + "!");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads game onto consoleHandler
    private void loadGame() {
        System.out.println("Attempting to load game...");
        try {
            setGameHandler(jsonReader.readToGameHandler());
            //creates a new GameHandler here from previously stored fields
            System.out.println("Load successful!");
        } catch (IOException e) {
            System.out.println("Game unable to load from: " + JSON_STORE);
        }
    }
}

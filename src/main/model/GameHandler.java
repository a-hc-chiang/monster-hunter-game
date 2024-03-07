package model;

import org.json.*;
import persistence.JsonWritable;
import java.util.*;

/**
 * represents a game board 
 */

public class GameHandler implements JsonWritable {
    public static final int GAME_SIZE = 20;
    public static final char BLANK_SPACE = '_';
    public static final char VERTICAL_BORDER = '|';
    public static final char HORIZONTAL_BORDER = '=';
    public static final int NUM_OF_NEW_MONSTERS = 5;

    private Player user;
    private List<Monster> monsters;
    private int score;
    private char [][] grid;
    private int turnChange;

    // EFFECTS: constructs a GameHandler with the given number of turns for a game
    public GameHandler(int turns) {
        grid = new char[GAME_SIZE][GAME_SIZE];
        user = new Player(GAME_SIZE / 2, GAME_SIZE / 2, turns);
        monsters = new ArrayList<Monster>();
        score = 0;
        turnChange = 1;
    }

    //EFFECTS: constructs a GameHandler object with previously saved fields
    public GameHandler(Player user, List<Monster> monsters, int score, char [][] grid, int turnChange) {
        this.user = user;
        this.monsters = monsters;
        this.score = score;
        this.grid = grid;
        this.turnChange = turnChange;
    }

    // EFFECTS: converts the current GameHandler to a JSON String
    @Override
    public JSONObject writeToJson() {
        JSONObject gameHandlerJson = new JSONObject();

        gameHandlerJson.put("user", user.writeToJson());
        gameHandlerJson.put("monsters", monstersToJson());
        gameHandlerJson.put("score", score);
        gameHandlerJson.put("turnChange", turnChange);

        return gameHandlerJson;
    }

    // EFFECTS: converts the current List of monsters into a JSONObject
    private JSONArray monstersToJson() {
        JSONArray monstersJson = new JSONArray();

        for (Monster monster : monsters) {
            monstersJson.put(monster.writeToJson());
        }
        return monstersJson;
    }

    public Player getUser() {
        return user;
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public int getScore() {
        return score;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    public char[][] getGrid() {
        return grid;
    }

    // EFFECTS: calls methods to help set up game
    public void setUpGame() {
        genGrid();
        spawnMonsters(NUM_OF_NEW_MONSTERS * 2);
        addMonstersOnGrid();
        addUserOnGrid();
    }

    //EFFECTS: Generates a clean game grid
    public void genGrid() {
        for (int i = 0; i < GAME_SIZE; i++) {
            for (int j = 0; j < GAME_SIZE; j++) {
                if (i == 0 || i == (GAME_SIZE - 1)) {
                    grid[i][j] = HORIZONTAL_BORDER;
                } else if (j == 0 || j == (GAME_SIZE - 1)) {
                    grid[i][j] = VERTICAL_BORDER;
                } else {
                    grid[i][j] = BLANK_SPACE;
                }
            }
        }
    }

    // EFFECTS: adds the user char according to pX and pY on the grid
    // MODIFIES: this
    public void addUserOnGrid() {
        grid[user.getPy()][user.getPx()] = Player.ICON;
    }

    // EFFECTS: adds the wraith char according to dX and dY on the grid
    // MODIFIES: this
    public void addMonstersOnGrid() {
        for (Monster monster : monsters) {
            grid[monster.getPy()][monster.getPx()] = Monster.ICON;
        }
    }

    // EFFECTS: randomly generates coordinates for new wraiths to be made
    // MODIFIES: this
    public void spawnMonsters(int amount) {
        for (int i = 0; i < amount; i++) {
            int x = (int)(Math.random() * (GAME_SIZE - 2)) + 1;
            int y = (int)(Math.random() * (GAME_SIZE - 2)) + 1;

            Monster toAdd = new Monster(x, y);
            monsters.add(toAdd);
        }
        EventLog.getInstance().logEvent(new Event("Added  " + amount + " Monsters to GameHandler monsters!"));
    }

    //EFFECTS: iterates through the list of wraiths to remove wraiths that are already dead and updates score
    public void removeMonsters() {
        int amount = 0;
        for (int i = 0; i < monsters.size(); i++) {
            if (!(monsters.get(i).isAlive())) {

                grid[monsters.get(i).getPy()][monsters.get(i).getPx()] = BLANK_SPACE;
                monsters.remove(i);
                i--;
                score += Monster.POINT_VAL;
                amount++;
            }
        }
        EventLog.getInstance().logEvent(new Event("Removed  " + amount + " Monsters from GameHandler monsters!"));
    }

    //EFFECTS: updates the location of the user on the grid according to the passed string
    // if "left" decrement x, "right" increment x, "up" decrement y, "down" increment y
    //REQUIRES: cmd equals to "left", "right", "up", "down"
    public void moveUser(String cmd) {
        int px = user.getPx();
        int py = user.getPy();
        user.useTurn();
        grid[py][px] = BLANK_SPACE;

        if (cmd.equalsIgnoreCase("left")) {
            px--;
            user.setPx(px);
            EventLog.getInstance().logEvent(new Event("User moved left!"));
        } else if (cmd.equalsIgnoreCase("right")) {
            px++;
            user.setPx(px);
            EventLog.getInstance().logEvent(new Event("User moved right!"));
        } else if (cmd.equalsIgnoreCase("up")) {
            py--;
            user.setPy(py);
            EventLog.getInstance().logEvent(new Event("User moved up!"));
        } else if (cmd.equalsIgnoreCase("down")) {
            py++;
            user.setPy(py);
            EventLog.getInstance().logEvent(new Event("User moved down!"));
        }
        grid[py][px] = Player.ICON;
        prepTurnChange();
    }

    //EFFECTS: calls the corresponding method with the given integer and returns the result
    //REQUIRES: 0 <= direction <= 3
    public List<Monster> selectAttackDirection(String cmd) {
        if (cmd.equalsIgnoreCase("left")) {
            return getLeftTargets();
        } else if (cmd.equalsIgnoreCase("right")) {
            return getRightTargets();
        } else if (cmd.equalsIgnoreCase("up")) {
            return getUpTargets();
        } else if (cmd.equalsIgnoreCase("down")) {
            return getDownTargets();
        }
        prepTurnChange();
        return new ArrayList<Monster>();
    }

    // EFFECTS: gets wraiths in the same row and left as the player
    public List<Monster> getLeftTargets() {
        List<Monster> toReturn = new ArrayList<Monster>();
        for (Monster monster : monsters) {
            if (monster.getPx() < user.getPx() && monster.getPy() == user.getPy()) {
                toReturn.add(monster);
            }
        }
        return toReturn;
    }

    // EFFECTS: gets wraiths in the same row and right as the player
    public List<Monster> getRightTargets() {
        List<Monster> toReturn = new ArrayList<Monster>();
        for (Monster monster : monsters) {
            if (monster.getPx() > user.getPx() && monster.getPy() == user.getPy()) {
                toReturn.add(monster);
            }
        }
        return toReturn;
    }

    // EFFECTS: gets wraiths in the same column and up as the player
    public List<Monster> getUpTargets() {
        List<Monster> toReturn = new ArrayList<Monster>();
        for (Monster monster : monsters) {
            if (monster.getPy() < user.getPy() && monster.getPx() == user.getPx()) {
                toReturn.add(monster);
            }
        }
        return toReturn;
    }

    // EFFECTS: gets wraiths in the same column and down as the player
    public List<Monster> getDownTargets() {
        List<Monster> toReturn = new ArrayList<Monster>();
        for (Monster monster : monsters) {
            if (monster.getPy() > user.getPy() && monster.getPx() == user.getPx()) {
                toReturn.add(monster);
            }
        }
        return toReturn;
    }

    // EFFECTS: checks if the player still has turns
    public boolean checkTurns() {
        return user.getTurns() == 0;
    }

    //EFFECTS: increments turnChange counter, when turn change == 3,
    // more Wraiths will be spawned and turn change will be reset
    public void prepTurnChange() {
        turnChange++;
        if (turnChange == 3) {
            spawnMonsters(NUM_OF_NEW_MONSTERS);
            addMonstersOnGrid();
            turnChange = 1;
            EventLog.getInstance().logEvent(new Event("Turns active! Adding new monsters onto the grid."));
        }
    }
}
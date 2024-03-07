package model;

import org.json.JSONObject;
import persistence.JsonWritable;

import java.util.*;

/**
 * Represents a player
 */
public class Player implements JsonWritable {
    public static final char ICON = 'P';

    private int px;
    private int py;
    private int turns;

    // EFFECTS: constructs a player with a given x and y coordinates and number of turns
    public Player(int px, int py, int turns) {
        this.px = px;
        this.py = py;
        this.turns = turns;
    }

    // EFFECTS: converts the current Player to a JSON String
    @Override
    public JSONObject writeToJson() {
        JSONObject jsonPlayer = new JSONObject();

        jsonPlayer.put("px", px);
        jsonPlayer.put("py", py);
        jsonPlayer.put("turns", turns);

        return jsonPlayer;
    } //tested

    // EFFECTS: uses up 1 turn, and then iterates through the list of Wraiths reducing the hp
    public void attack(List<Monster> targets) {
        useTurn();
        for (int i = 0; i < targets.size(); i++) {
            targets.get(i).reduceHP();
        }
    }

    // EFFECTS: reduces the number of turns the Player has
    public void useTurn() {
        --turns;
    }

    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }

    public int getTurns() {
        return turns;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }
}


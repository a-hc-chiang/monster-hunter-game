package model;

import org.json.JSONObject;
import persistence.JsonWritable;

/**
 * represents a Monster (target to get points)
 */

public class Monster implements JsonWritable {
    public static final int HP = 3;
    public static final char ICON = 'M';
    public static final int POINT_VAL = 1;

    private int hp;
    private int px;
    private int py;
    private boolean isAlive;
    private boolean isHit;

    //Constructs a Monster with a given x and y positions
    public Monster(int px, int py) {
        this.hp = HP;
        this.px = px;
        this.py = py;
        this.isAlive = true;
        isHit = false;
    }

    //Constructs a Monster with all specified fields
    public Monster(int hp, int px, int py) {
        this.hp = hp;
        this.px = px;
        this.py = py;
        this.isAlive = true;
        this.isHit = false;
    }

    // EFFECTS: converts the current monster into a JSONObject
    @Override
    public JSONObject writeToJson() {
        JSONObject monsterJson = new JSONObject();

        monsterJson.put("hp", hp);
        monsterJson.put("px", px);
        monsterJson.put("py", py);

        return monsterJson;
    }

    // EFFECTS: reduces Monster HP, if HP == 0 then monster dies
    public void reduceHP() {
        this.hp--;
        if (this.hp == 0) {
            isAlive = false;
        }
    }

    public int getPx() {
        return px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public void setPx(int px) {
        this.px = px;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getHp() {
        return hp;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        this.isHit = hit;
    }
}

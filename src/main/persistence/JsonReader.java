package persistence;

import model.*;
import java.io.IOException;
import org.json.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a JsonReader
 * that converts a given JSONObject to a GameHandler objects
 */

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    // CITATION: method format taken from JsonSerializationDemo-master
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a saved GameHandler from the source file and returns it,
    // throws an IOException if an error occurs reading data from file
    // CITATION: method format taken from JsonSerializationDemo-master
    public GameHandler readToGameHandler() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonToGameHandler(jsonObject);
    }

    // EFFECTS: reads a source file as a String and returns it
    // CITATION: method format taken from JsonSerializationDemo-master
    private String readFile(String source) throws IOException {
        StringBuilder dataBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> dataBuilder.append(s));
        }

        return dataBuilder.toString();
    }

    // EFFECTS: converts from JSON object to GameHandler object and returns it
    public GameHandler jsonToGameHandler(JSONObject jsonObject) {
        Player user = jsonToPlayer(jsonObject.getJSONObject("user"));
        List<Monster> monsters = jsonToMonsters(jsonObject.getJSONArray("monsters"));
        int score = jsonObject.getInt("score");
        char [][] grid = new char[GameHandler.GAME_SIZE][GameHandler.GAME_SIZE];
        int turnChange = jsonObject.getInt("turnChange");

        GameHandler toReturn = new GameHandler(user, monsters, score, grid, turnChange);
        toReturn.genGrid();
        toReturn.addMonstersOnGrid();
        toReturn.addUserOnGrid();

        return toReturn;
    }

    // EFFECTS: converts JsonObject object into a Player object
    public Player jsonToPlayer(JSONObject jsonObject) {
        int px = jsonObject.getInt("px");
        int py = jsonObject.getInt("py");
        int turns = jsonObject.getInt("turns");

        return new Player(px, py, turns);
    }

    // EFFECTS: converts the given JsonObject to create a monster
    public Monster jsonToMonster(JSONObject monster) {
        return new Monster(monster.getInt("hp"), monster.getInt("px"),  monster.getInt("py"));
    }

    // EFFECTS: converts a JSONArray to a list of monsters for GameHandler object
    private List<Monster> jsonToMonsters(JSONArray jsonArray) {
        List<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject monster =  (JSONObject) jsonArray.get(i);
            monsters.add(jsonToMonster(monster));
        }
        return monsters;
    }
}
package persistence;

import java.io.*;
import model.GameHandler;
import org.json.*;

/**
 * Represents a JsonWriter
 * to write the data of a GameHandler to a JSONObject
 * CITATION: code format and methods taken from JsonSerializationDemo-master
 */
public class JsonWriter {
    private static final int TAB_SIZE = 4;

    private String fileName;
    private PrintWriter pr;

    // EFFECTS: constructs JSON writer to write to destination file
    public JsonWriter(String fileName) {
        this.fileName = fileName;
    }

    // EFFECTS: to prevent data leakage, closes the PrintWriter
    // MODIFIES: this
    public void closePrinter() {
        pr.close();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if destination file
    // is an invalid file path
    public void open() throws FileNotFoundException {
        pr = new PrintWriter(fileName);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of GameHandler to file
    public void writeToJson(GameHandler toSave) {
        JSONObject json = toSave.writeToJson();
        saveToFile(json.toString(TAB_SIZE));
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        pr.print(json);
    }
}
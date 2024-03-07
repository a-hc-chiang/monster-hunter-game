import model.*;
import org.junit.jupiter.api.BeforeEach;
import persistence.*;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private JsonReader reader;
    private JsonWriter writer;
    private GameHandler gameObject;
    private String source;

    @BeforeEach
    public void doBefore() {
        gameObject = new GameHandler(5);
        gameObject.setUpGame();
        source = "./data/myFile.json";
    }

    @Test
    public void testToGameHandler() {
        writer = new JsonWriter(source);
        reader = new JsonReader(source);

        try {
            writer.open();
            writer.writeToJson(gameObject);
            writer.closePrinter();
            GameHandler tester = reader.readToGameHandler();
            for (int row = 0; row < GameHandler.GAME_SIZE; row++) {
                for (int col = 0; col < GameHandler.GAME_SIZE; col++) {
                    assertEquals(gameObject.getGrid()[row][col], tester.getGrid()[row][col]);
                }
            }

            for (int i = 0; i < gameObject.getMonsters().size(); i++) {
                assertEquals(gameObject.getMonsters().get(i).getHp(), tester.getMonsters().get(i).getHp());
            }

            assertEquals(gameObject.getScore(), tester.getScore());
            assertEquals(gameObject.getScore(), tester.getScore());

        } catch (IOException e) {
            fail("Why is there an exception here?");
        }
    }

    @Test
    public void testFileWriting() {
        try {
            writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();

            fail("Summer, where are my exceptions?");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.setFileName("./data/myFile.json");

        try {
            writer.open();
        } catch (FileNotFoundException e) {
            fail("Summer, why are there exceptions?");
        }
    }
}

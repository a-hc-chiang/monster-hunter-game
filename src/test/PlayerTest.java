import model.Monster;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import model.Player;
import persistence.JsonReader;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    List<Monster> testers;
    Player test;
    Monster w1;
    Monster w2;
    Monster w3;

    @BeforeEach
    public void doThis() {
        testers = new ArrayList<Monster>();
        test = new Player(1, 2, 3);
        w1 = new Monster(1, 3);
        w2 = new Monster(0, 2);
        w3 = new Monster(2, 1);
        testers.add(w1);
        testers.add(w2);
        testers.add(w3);
    }

    @Test
    public void testToJson() {
        JsonReader jr = new JsonReader(":)");

        JSONObject jsonPlayer = test.writeToJson();
        Player newPlayer = jr.jsonToPlayer(jsonPlayer);

        assertEquals(test.getPx(), newPlayer.getPx());
        assertEquals(test.getPy(), newPlayer.getPy());
        assertEquals(test.getTurns(), newPlayer.getTurns());
    }

    @Test
    public void testAttack() {
        test.attack(testers);
        for (int i = 0; i < testers.size(); i++) {
            assertEquals(2, testers.get(i).getHp());
        }
        test.attack(testers);
        for (int i = 0; i < testers.size(); i++) {
            assertEquals(1, testers.get(i).getHp());
        }
        test.attack(testers);
        for (int i = 0; i < testers.size(); i++) {
            assertEquals(0, testers.get(i).getHp());
        }

        for (int i = 0; i < testers.size(); i++) {
            assertFalse(testers.get(i).isAlive());
        }

        assertEquals(0, test.getTurns());
    }

    @Test
    public void testUseTurn() {
        test.useTurn();
        assertEquals(2, test.getTurns());
    }

    @Test
    public void testGettersAndSetters() {
        int px = 3;
        int py = 4;
        int turn = 12;

        test.setPx(px);
        test.setPy(py);
        test.setTurns(turn);

        assertEquals(3, test.getPx());
        assertEquals(4, test.getPy());
        assertEquals(3, test.getTurns());
        assertEquals(turn, test.getTurns());
    }
}

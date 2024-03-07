import model.Monster;
import org.json.JSONObject;
import persistence.JsonReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonsterTest {
    Monster test;

    @BeforeEach
    public void doBefore() {
        test = new Monster(1,3);
    }

    @Test
    public void testToJson(){
        JsonReader jr = new JsonReader(":)");
        JSONObject monsterToJson = test.writeToJson();

        Monster jsonToMonster = jr.jsonToMonster(monsterToJson);
        assertEquals(test.getHp(), jsonToMonster.getHp());
        assertEquals(test.getPx(), jsonToMonster.getPx());
        assertEquals(test.getPy(), jsonToMonster.getPy());
    }

    @Test
    public void testReduceHP() {
        assertEquals(3, test.getHp());
        test.reduceHP();
        assertEquals(2, test.getHp());
        assertTrue(test.isAlive());
        test.reduceHP();
        assertEquals(1, test.getHp());
        assertTrue(test.isAlive());
        test.reduceHP();
        assertEquals(0, test.getHp());
        assertFalse(test.isAlive());
    }

    @Test
    public void testSettersAndGetters(){
       int newX = 10;
       int newY = 15;

       int hp = test.getHp();
       assertEquals(Monster.HP, hp);
       assertEquals(1, test.getPx());
       assertEquals(3, test.getPy());

       test.setPx(newX);
       test.setPy(newY);

        assertEquals(newX, test.getPx());
        assertEquals(newY, test.getPy());

        assertTrue(test.isAlive());
        test.setAlive(true);
        assertTrue(test.isAlive());
        test.setAlive(false);
        assertFalse(test.isAlive());

        assertFalse(test.isHit());
        test.setHit(true);
        assertTrue(test.isHit());

        test.setPx(20);
        test.setPy(20);
        assertEquals(20, test.getPx());
        assertEquals(20, test.getPy());
    }
}

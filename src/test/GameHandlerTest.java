import model.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class GameHandlerTest {
    GameHandler gb;
    List<Monster> testerMonsters;
    Monster w1;
    Monster w2;
    Monster w3;
    Monster w4;
    char [][] board;

    @BeforeEach
    public void doBefore(){
        gb = new GameHandler(5);
        testerMonsters = new ArrayList<Monster>();
        w1 = new Monster(1, 10);
        w2 = new Monster(10, 5);
        w3 = new Monster(16, 10);
        w4 = new Monster(10, 15);

        testerMonsters.add(w1);
        testerMonsters.add(w2);
        testerMonsters.add(w3);
        testerMonsters.add(w4);

        board = new char[GameHandler.GAME_SIZE][GameHandler.GAME_SIZE];
        for (int i = 0; i < GameHandler.GAME_SIZE; i++) {
            for (int j = 0; j < GameHandler.GAME_SIZE; j++) {
                if (i == 0 || i == (GameHandler.GAME_SIZE - 1)) {
                    board[i][j] = GameHandler.HORIZONTAL_BORDER;
                } else if (j == 0 || j == (GameHandler.GAME_SIZE - 1)) {
                    board[i][j] = GameHandler.VERTICAL_BORDER;
                } else {
                    board[i][j] = GameHandler.BLANK_SPACE;
                }
            }
        }
    }

    @Test
    public void testToJson() {
        JsonReader jr = new JsonReader("./data/myFile.json");

        gb.setUpGame();
        JSONObject gameHandlerJson = gb.writeToJson();
        GameHandler tester = jr.jsonToGameHandler(gameHandlerJson);

        for (int row = 0; row < GameHandler.GAME_SIZE; row++) {
            for (int col = 0; col < GameHandler.GAME_SIZE; col++) {
                assertEquals(gb.getGrid()[row][col], tester.getGrid()[row][col]);
            }
        }

        for (int i = 0; i < gb.getMonsters().size(); i++) {
            assertEquals(gb.getMonsters().get(i).getHp(), tester.getMonsters().get(i).getHp());
        }

        assertEquals(gb.getScore(), tester.getScore());
        assertEquals(gb.getScore(), tester.getScore());
    }

    @Test
    public void testPrepTurnChange() {
        gb.setUpGame();
        assertEquals(GameHandler.NUM_OF_NEW_MONSTERS * 2, gb.getMonsters().size());
        gb.prepTurnChange();
        gb.prepTurnChange();
        gb.prepTurnChange();
        assertEquals(GameHandler.NUM_OF_NEW_MONSTERS * 3, gb.getMonsters().size());
    }

    @Test
    public void testSetUpGame() {
        assertEquals(0, gb.getMonsters().size());
        gb.setUpGame();

        //test to see if boarders don't have anything
        for (int row = 0; row < GameHandler.GAME_SIZE; row++) {
           for (int col = 0; col < GameHandler.GAME_SIZE; col++) {
               if (row == 0 || row == GameHandler.GAME_SIZE - 1) {
                   assertEquals(board[row][col], gb.getGrid()[row][col]);
               } else if (col == 0 || col == GameHandler.GAME_SIZE - 1) {
                   if (row != 0 && row != GameHandler.GAME_SIZE - 1) {
                       assertEquals(board[row][col], gb.getGrid()[row][col]);
                   }
               }
           }
        }
        assertEquals(10, gb.getMonsters().size());
        assertEquals(GameHandler.GAME_SIZE / 2,gb.getUser().getPy());
        assertEquals(GameHandler.GAME_SIZE / 2,gb.getUser().getPx());

        assertEquals('P', gb.getGrid()[10][10]);
    }

    @Test
    public void testGenGrid() {
        gb.genGrid();

        for (int i = 0; i < GameHandler.GAME_SIZE; i++) {
            for (int j = 0; j < GameHandler.GAME_SIZE; j++) {
                assertEquals(board[i][j], gb.getGrid()[i][j]);
            }
        }
    }

    @Test
    public void testSpawnMonster() {
        gb.spawnMonsters(10);

        for (int i = 0; i < gb.getMonsters().size(); i++) {
            assertTrue(gb.getMonsters().get(i).getPx() > 0);
            assertTrue(gb.getMonsters().get(i).getPy() > 0);
            assertTrue(gb.getMonsters().get(i).getPx() < (GameHandler.GAME_SIZE - 1));
            assertTrue(gb.getMonsters().get(i).getPy() < (GameHandler.GAME_SIZE - 1));
        }
    }

    @Test
    public void testMoverUser(){
        int x = gb.getUser().getPx();
        int y = gb.getUser().getPy();

        gb.addUserOnGrid();
        assertEquals(Player.ICON, gb.getGrid()[y][x]);
        gb.moveUser("left");
        assertEquals(GameHandler.BLANK_SPACE, gb.getGrid()[y][x]);
        int leftX = x - 1;
        assertEquals(leftX, gb.getUser().getPx());
        assertEquals(4, gb.getUser().getTurns());

        gb.moveUser("right");
        assertEquals(Player.ICON, gb.getGrid()[y][x]);
        assertEquals(GameHandler.BLANK_SPACE, gb.getGrid()[y][leftX]);
        assertEquals(x, gb.getUser().getPx());
        assertEquals(3, gb.getUser().getTurns());

        int upY = y - 1;
        gb.moveUser("up");
        assertEquals(Player.ICON, gb.getGrid()[upY][x]);
        assertEquals(GameHandler.BLANK_SPACE, gb.getGrid()[y][x]);
        assertEquals(upY, gb.getUser().getPy());
        assertEquals(2, gb.getUser().getTurns());

        gb.moveUser("down");
        assertEquals(Player.ICON, gb.getGrid()[y][x]);
        assertEquals(GameHandler.BLANK_SPACE, gb.getGrid()[upY][x]);
        assertEquals(y, gb.getUser().getPy());
        assertEquals(1, gb.getUser().getTurns());
    }

    @Test
    public void testMoverUser2() {
        int x = gb.getUser().getPx();
        int y = gb.getUser().getPy();

        gb.addUserOnGrid();
        assertEquals(Player.ICON, gb.getGrid()[y][x]);
        int upY = y - 1;
        gb.moveUser("up");
        assertEquals(Player.ICON, gb.getGrid()[upY][x]);
        assertEquals(GameHandler.BLANK_SPACE, gb.getGrid()[y][x]);
        assertEquals(upY, gb.getUser().getPy());
        assertEquals(4, gb.getUser().getTurns());

        upY--;
        gb.moveUser("up");
        assertEquals(Player.ICON, gb.getGrid()[upY][x]);
        assertEquals(GameHandler.BLANK_SPACE, gb.getGrid()[y][x]);
        assertEquals(upY, gb.getUser().getPy());
        assertEquals(3, gb.getUser().getTurns());

        upY++;
        gb.moveUser("down");
        assertEquals(Player.ICON, gb.getGrid()[upY][x]);
        assertEquals(GameHandler.BLANK_SPACE, gb.getGrid()[y][x]);
        assertEquals(upY, gb.getUser().getPy());
        assertEquals(2, gb.getUser().getTurns());

        upY++;
        gb.moveUser("down");
        assertEquals(Player.ICON, gb.getGrid()[upY][x]);
        //assertEquals(GameHandler.BLANK_SPACE, gb.getGrid()[y][x]);
        assertEquals(upY, gb.getUser().getPy());
        assertEquals(1, gb.getUser().getTurns());

    }

    @Test
    public void testRemoveWraiths(){
        gb.setUpGame();
        List<Monster> toAttack = gb.getMonsters();

        for (int i = 0; i < Monster.HP; i++) {
            gb.getUser().attack(toAttack);
        }
        gb.removeMonsters();

        board[gb.getUser().getPy()][gb.getUser().getPy()] = Player.ICON;
        for (int i = 0; i < GameHandler.GAME_SIZE; i++) {
            for (int j = 0; j < GameHandler.GAME_SIZE; j++) {
                if (i != gb.getUser().getPy() && j != gb.getUser().getPx()) {
                    assertEquals(board[i][j], gb.getGrid()[i][j]);
                }
            }
        }
        assertEquals(0, gb.getMonsters().size());
        assertEquals(10 * Monster.POINT_VAL, gb.getScore());
    }

    @Test
    public void testCheckTurns(){
        List<Monster> dummyMonsters = new ArrayList<Monster>();
        int turns = gb.getUser().getTurns();
        for (int i = 0; i < turns - 1; i++) {
            gb.getUser().attack(dummyMonsters);
            assertFalse(gb.checkTurns());
        }

        gb.getUser().attack(dummyMonsters);
        assertTrue(gb.checkTurns());
    }

    @Test
    public void testSelectAttackDirection() {
        Monster m1 = new Monster(10, 10);

        List<Monster> left = new ArrayList<Monster>();
        left.add(w1);
        List<Monster> right = new ArrayList<Monster>();
        right.add(w3);
        List<Monster> up = new ArrayList<Monster>();
        up.add(w2);
        List<Monster> down = new ArrayList<Monster>();
        down.add(w4);

        testerMonsters.add(m1);
        gb.setMonsters(testerMonsters);

        assertEquals(left, gb.selectAttackDirection("left"));
        assertEquals(right, gb.selectAttackDirection("right"));
        assertEquals(up, gb.selectAttackDirection("up"));
        assertEquals(down, gb.selectAttackDirection("down"));

        assertEquals(0, gb.selectAttackDirection("bleh").size());
    }

}

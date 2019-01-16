import org.junit.Test;
import static org.junit.Assert.*;


public class PlayerTest {
    @Test
    public void testDefaultConstruction() {
        Player testPlayer = new Player(0, 8);

        // kazan should be empty at beginning
        Hole testPlayerKazan = testPlayer.getKazan();
        assertEquals(testPlayerKazan.getNumberOfKorgools(), 0);

        // tuzIndex should be -1 aka null
        assertEquals(testPlayer.getTuzIndex(), -1);

        assertEquals(testPlayer.getLastHoleIndex(), 8);
    }


    @Test
    public void testCustomConstructor() {
        Player testPlayer = new Player(0, 8, 50, 2);

        Hole testPlayerKazan = testPlayer.getKazan();
        assertEquals(testPlayerKazan.getNumberOfKorgools(), 50);
        assertEquals(testPlayer.getTuzIndex(), 2);
        assertEquals(testPlayer.getLastHoleIndex(), 8);
    }


    @Test
    public void testSetTuzIndex() {
        Player testPlayer = new Player(0, 8);
        assertEquals(testPlayer.getTuzIndex(), -1);

        testPlayer.setTuzIndex(3);
        assertEquals(testPlayer.getTuzIndex(), 3);
    }


    @Test
    public void testIsInMySide() {
        Player testPlayer = new Player(0, 8);

        // test correct index
        assertTrue(testPlayer.isInMySide(2));

        // test when index is not in player's side
        assertFalse(testPlayer.isInMySide(10));
    }


    @Test
    public void testEquals() {
        Player testPlayer = new Player(0, 8);
        Player testPlayer2 = new Player(0, 8);
        Player testPlayer3 = new Player(9,17);
        assertTrue(testPlayer.equals(testPlayer2));
        assertFalse(testPlayer.equals(testPlayer3));

    }
}
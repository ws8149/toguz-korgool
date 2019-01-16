import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class HoleTest {
    @Test
    public void testGetKorgoolsNumber() {
        Hole hole = new Hole(9);
        assertEquals(hole.getNumberOfKorgools(), 9);
    }


    @Test
    public void testAddOneKorgools() {
        Hole hole = new Hole(9);
        hole.addOneKorgool();
        assertEquals(hole.getNumberOfKorgools(),10);
    }


    @Test
    public void testAddKorgools() {
        Hole hole = new Hole(9);
        hole.addKorgools(4);
        assertEquals(hole.getNumberOfKorgools(), 13);
    }


    @Test
    public void testRemoveAllKorgools() {
        Hole hole = new Hole(9);
        int numberOfRemovedKorgools = hole.removeAllKorgools();
        assertEquals(hole.getNumberOfKorgools(), 0);
        assertEquals(numberOfRemovedKorgools, 9);
    }


    @Test
    public void testRemoveOneKorgools() {
        Hole hole = new Hole(9);
        int numberOfRemovedKorgools = hole.removeOneKorgool();
        assertEquals(hole.getNumberOfKorgools(), 8);
        assertEquals(numberOfRemovedKorgools, 1);
    }
}

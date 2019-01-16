import org.junit.Test;
import static org.junit.Assert.*;


public class TurnTest {

    //TODO: remove failing test case?
    @Test
    public void testDefaultConstructor() {
        GameBoard gameBoard = new GameBoard();
        Player player = new Player(0, 8);
        Opponent opponent = new Opponent(9, 17);

        Turn turn = new Turn(player, opponent, gameBoard);

        assertTrue(turn.getCurrentPlayer().equals(player));
        assertTrue(turn.getCurrentOpponent().equals(opponent));
    }


    @Test
    public void testClickFirstHoleWithMultipleKorgools(){
        GameBoard gameBoard = new GameBoard();
        Turn turn = gameBoard.getCurrentTurn();

        // Click player's first hole, which has no effect on opponent's board
        turn.clickHole(0);
        int holeOpponentClicked = getIndexOfHoleOpponentClicked(gameBoard);

        // when it's 10, first player hole becomes 2 and is captured
        // any time it isn't 9, it will increment first player hole by 1, so it becomes 2

        // When opponent's first hole clicked, player's side unaffected
        if (holeOpponentClicked == 9) {
            assertEquals(1, gameBoard.getHole(0).getNumberOfKorgools());
            // rest of affected holes should have 10 korgools
            for (int i = 1; i < 9; i++){
                assertEquals(10, gameBoard.getHole(i).getNumberOfKorgools());
            }
        // When opponent's second hole clicked,
            // player's first hole becomes a tuz and thus zero
            // all holes after that become 10
        } else if (holeOpponentClicked == 10) {
            // opponents last korgools lands on first hole of player, making it 2, so it gets captured
            assertEquals(0, gameBoard.getHole(0).getNumberOfKorgools());
            for (int i = 1; i < 9; i++){
                assertEquals(10, gameBoard.getHole(i).getNumberOfKorgools());
            }
        // Any other hole clicked,
            // Player's first hole not affected
            // all other holes after are 11
        } else {
            assertEquals(2, gameBoard.getHole(0).getNumberOfKorgools());
            for (int i = 1; i < (holeOpponentClicked+8)%9; i++){
                assertEquals(11, gameBoard.getHole(i).getNumberOfKorgools());
            }
        }
    }


    @Test
    public void testClickSecondHoleWithMultipleKorgools(){
        GameBoard gameBoard = new GameBoard();
        Turn turn = gameBoard.getCurrentTurn();

        // Click player's second hole
        turn.clickHole(1);

        int holeOpponentClicked = getIndexOfHoleOpponentClicked(gameBoard);

        // All holes on the opponent's side,
        // and after the hole the opponent clicked should have 10 korgools
        for (int i = holeOpponentClicked + 1; i <= 17; i++){
            assertEquals(10, gameBoard.getHole(i).getNumberOfKorgools());
        }

        // the first hole of the player's side should be 10
        assertEquals(10, gameBoard.getHole(0).getNumberOfKorgools());

        // all holes after the hole the player clicked
        // and up until the last hole of the opponent's move
        // should be 11
        for (int i = 2; i < (holeOpponentClicked+8)%9; i++){
            assertEquals(11, gameBoard.getHole(i).getNumberOfKorgools());
        }

        // first hole on the opponent's side should be zero
        // as that was captured by the player in their original move
        assertEquals(0, gameBoard.getHole(9).getNumberOfKorgools());
    }


    @Test
    public void testClickHoleWithOneKorgool(){
        // some dummy data
        int[] korgoolCounts = {
                1, 9, 9, 9, 9, 9, 9, 9, 9,
                9, 9, 9, 9, 9, 9, 9, 9, 9,
        };

        //setup
        GameBoard gameBoard = new GameBoard(korgoolCounts,
                0, 8,
                -1, -1);
        Turn turn = gameBoard.getCurrentTurn();

        // check right starting conditions
        assertEquals(1, gameBoard.getHole(0).getNumberOfKorgools());

        turn.clickHole(0);

        int holeOpponentClicked = getIndexOfHoleOpponentClicked(gameBoard);
        assertEquals(1, gameBoard.getHole(holeOpponentClicked).getNumberOfKorgools());

        for (int i = holeOpponentClicked + 1; i <= 17; i++){
            assertEquals(10, gameBoard.getHole(i).getNumberOfKorgools());
        }


        //TODO: more comments
        if (holeOpponentClicked == 9){
            // hole player clicked should be 0, hole after it 10, and every hole after that 9
            assertEquals(0, gameBoard.getHole(0).getNumberOfKorgools());
            assertEquals(10, gameBoard.getHole(1).getNumberOfKorgools());
            for (int i = 2; i <= (holeOpponentClicked+8)%9; i++){
                assertEquals(9, gameBoard.getHole(i).getNumberOfKorgools());
            }
        } else if (holeOpponentClicked == 10) {
            assertEquals(1, gameBoard.getHole(0).getNumberOfKorgools());
            assertEquals(10, gameBoard.getHole(1).getNumberOfKorgools());
            for (int i = 2; i <= (holeOpponentClicked+8)%9; i++){
                assertEquals(9, gameBoard.getHole(i).getNumberOfKorgools());
            }

        } else {
            // opponent's turn is going into player's side
            // hole player clicked should be 1, hole after should be 11, and every hole after that until last hole of opponen'ts move should be 10
            assertEquals(1, gameBoard.getHole(0).getNumberOfKorgools());
            assertEquals(11, gameBoard.getHole(1).getNumberOfKorgools());
            for (int i = 2; i <= (holeOpponentClicked+8)%9 - 1; i++){
                System.out.println(i);
                assertEquals(10, gameBoard.getHole(i).getNumberOfKorgools());
            }
        }

        //TODO: make lambda function for gameboard.gethole.getnumberofkorgools? repeated a lot
        for (int i = (holeOpponentClicked+8)%9 + 2; i <= 8; i++){
            System.out.println(i);
            assertEquals(9, gameBoard.getHole(i).getNumberOfKorgools());
        }
    }


    @Test
    public void testClickHoleAllTuzConditionsMet() {

        // some dummy data
        // hole with two should become a tuz in this test
        int[] korgoolCounts = {
                9, 9, 9, 9, 9, 9, 9, 9, 9,
                9, 9, 9, 9, 9, 9, 2, 9, 9,
        };

        //setup
        // player does not already have tuz
        GameBoard gameBoard = new GameBoard(korgoolCounts,
                0, 7,
                -1, -1);
        Turn turn = gameBoard.getCurrentTurn();

        turn.clickHole(7);
        assertEquals(0, gameBoard.getHole(15).getNumberOfKorgools());
        assertEquals(15, turn.getCurrentPlayer().getTuzIndex());

        int holeOpponentClicked = getIndexOfHoleOpponentClicked(gameBoard);
        System.out.println(holeOpponentClicked);

        if (holeOpponentClicked == 0) {
            // have some asserts
            return;
        } else if (holeOpponentClicked < 7){
           assertEquals(4, turn.getCurrentPlayer().getKazan().getNumberOfKorgools());
        }
    }


    //TODO: then test for each condition being false, where it should not happen
    @Test
    public void testGetCurrentPlayer(){
        GameBoard gameBoard = new GameBoard();
        Turn turn = gameBoard.getCurrentTurn();
        Player player = new Player(0,8);
        Player player2 = new Player(9,17);
        assertTrue(turn.getCurrentPlayer().equals(player));
        assertFalse(turn.getCurrentPlayer().equals(player2));
    }


    @Test
    public void testGetCurrentOpponent(){
        GameBoard gameBoard = new GameBoard();

        Turn turn = gameBoard.getCurrentTurn();
        Player player = new Player(0,8);
        Player player2 = new Player(9,17);
        assertFalse(turn.getCurrentOpponent().equals(player));
        assertTrue(turn.getCurrentOpponent().equals(player2));

    }

    /**
     * Determine which hole the opponent clicked.
     *
     * This only works for when the opponent is making their first move.
     * @param gameBoard gameBoard object of current game
     * @return the index of the hole the opponent clicked
     */
    private int getIndexOfHoleOpponentClicked(GameBoard gameBoard){
        int holeOpponentClicked = 0;
        for (int i = 9; i <= 17; i++){
            if (gameBoard.getHole(i).getNumberOfKorgools() == 1){
                holeOpponentClicked = i;
                break;
            }
        }
        if (holeOpponentClicked == 0) {
            System.out.println(" ZERO");
        }
        return holeOpponentClicked;
    }
}
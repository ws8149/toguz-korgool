import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GameBoardTest{
    @Test
    public void testDefaultConstructor(){
        GameBoard gameBoard = new GameBoard();

        // check holes array
        assertEquals(gameBoard.getBoardLength(), 18);
        for (int i = 0; i < 18; i++){
            assertEquals(gameBoard.getHole(i).getNumberOfKorgools(), 9);
        }

        int whitePlayerKazan = gameBoard.getCurrentTurn().getCurrentPlayer().getKazan().getNumberOfKorgools();
        assertEquals(whitePlayerKazan, 0);

        int blackPlayerKazan = gameBoard.getCurrentTurn().getCurrentOpponent().getKazan().getNumberOfKorgools();
        assertEquals(blackPlayerKazan, 0);
        //TODO: more tests for constructor
    }


    @Test
    public void testCustomConstructor(){
        //TODO: more to test

        // some dummy data
        int[] korgoolCounts = {
            9, 9, 9, 6, 6, 6,
            10, 10, 10, 10, 12, 12,
            9, 9, 9, 7, 1, 7
        };

        GameBoard gameBoard = new GameBoard(korgoolCounts, 8,
                        3, 3, -1);

        assertEquals(gameBoard.getBoardLength(), 18);


        // check total number of korgools equals 162 after construction
        int sum = 0;
        for (int i = 0; i < gameBoard.getBoardLength(); i++){
            sum += gameBoard.getHole(i).getNumberOfKorgools();
        }


        //NOTE: the below code smells
        int whitePlayerKazan = gameBoard.getCurrentTurn().getCurrentPlayer().getKazan().getNumberOfKorgools();
        int blackPlayerKazan = gameBoard.getCurrentTurn().getCurrentOpponent().getKazan().getNumberOfKorgools();
        assertEquals(sum + whitePlayerKazan + blackPlayerKazan, 162);
    }


    //TODO: test does pass. fix it.
    @Test
    public void testNextTurn(){
        GameBoard gameBoard = new GameBoard();
        Player initialPlayer = gameBoard.getCurrentTurn().getCurrentPlayer();
        Player initialOpponent = gameBoard.getCurrentTurn().getCurrentOpponent();

        // check that a new turn swaps players
        // setting the previous player to the current opponent
        // and the previous opponent to the current player
        gameBoard.nextTurn(initialPlayer, initialOpponent);
        // calling next turn from the player, will make the opponent immeidately play
        // which in turn will call nextTurn.
        assertEquals(gameBoard.getCurrentTurn().getCurrentPlayer(), initialPlayer);
        assertEquals(gameBoard.getCurrentTurn().getCurrentOpponent(), initialOpponent);

        //TODO: test when AI makes a move

    }

    //TODO: test endGame()
    //TODO: test addOneKorgool()
    @Test
    public void testAddOneKorgool(){

        // some dummer data
        int[] korgoolCounts = {
                9, 9, 9, 6, 6, 6,
                10, 10, 10, 10, 12, 12,
                9, 9, 9, 7, 1, 7
        };

        GameBoard gameBoard = new GameBoard(korgoolCounts, 12, 6, 3, -1);
        Player initialPlayer = gameBoard.getCurrentTurn().getCurrentPlayer();
        Player initialOpponent = gameBoard.getCurrentTurn().getCurrentOpponent();


        gameBoard.addOneKorgool(2, initialPlayer, initialOpponent);
        assertEquals(gameBoard.getHole(2).getNumberOfKorgools(),10);


        gameBoard.addOneKorgool(7, initialPlayer, initialOpponent);
        assertEquals(gameBoard.getHole(7).getNumberOfKorgools(),11);

        gameBoard.addOneKorgool(16, initialPlayer, initialOpponent);
        assertEquals(gameBoard.getHole(16).getNumberOfKorgools(),2);


    }


    @Test
    public void testRemoveOneKorgool() {
        GameBoard gameBoard = new GameBoard();
        gameBoard.removeOneKorgool(2);
        assertEquals(gameBoard.getHole(2).getNumberOfKorgools(),8);
        gameBoard.removeOneKorgool(2);
        assertEquals(gameBoard.getHole(2).getNumberOfKorgools(),7);

    }

    //TODO: test getters


    @Test
    public void testGetHole(){
        int[] korgoolCounts = {
                9, 9, 9, 6, 6, 6, 10, 10, 10, 10, 12, 12, 9, 9, 9, 7, 1, 7
        };

        GameBoard gameBoard = new GameBoard(korgoolCounts,
                12, 6,
                -1, -1);
        //TODO: swap argumnets for assertEQuals
        assertEquals(gameBoard.getHole(2).getNumberOfKorgools(),9);
        assertEquals(gameBoard.getHole(3).getNumberOfKorgools(),6);
        assertEquals(gameBoard.getHole(6).getNumberOfKorgools(),10);
    }


    @Test
    public void testGetBoardLength(){
        GameBoard gameBoard = new GameBoard();
        assertEquals(gameBoard.getBoardLength(),18);

    }


    @Test
    public  void testGetCurrentTurn(){
        GameBoard gameBoard = new GameBoard();
        Player initialPlayer = gameBoard.getCurrentTurn().getCurrentPlayer();
        Player initialOpponent = gameBoard.getCurrentTurn().getCurrentOpponent();

        assertEquals(gameBoard.getCurrentTurn().getCurrentPlayer(), initialPlayer);
        assertEquals(gameBoard.getCurrentTurn().getCurrentOpponent(), initialOpponent);
    }
}
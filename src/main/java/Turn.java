/**
 * A single turn of the game
 */
public class Turn {
    private Player currentPlayer;
    private Player currentOpponent;
    private GameBoard gameBoard;


    /**
     * Setup a turn
     * 
     * @param currentPlayer Player whose turn it is
     * @param currentOpponent Player whose turn it isn't
     * @param gameBoard gameBoard this turn is playing on
     */
    public Turn(Player currentPlayer, Player currentOpponent, GameBoard gameBoard) {
        this.currentPlayer = currentPlayer;
        this.currentOpponent = currentOpponent;
        this.gameBoard = gameBoard; 

        // If there are no more korgools left on player's side, end game.
        boolean allEmpty = true;
        for (int i = currentPlayer.firstHoleIndex; i < currentPlayer.getFirstHoleIndex()+9; i++) {
            if(gameBoard.getHole(i).getNumberOfKorgools() > 0){
                allEmpty = false;
                break;
            }
        }
        if(allEmpty){

            // Move all of opponent's korgools into opponent's kazan for counting
            for (int j = currentOpponent.firstHoleIndex; j < currentOpponent.getFirstHoleIndex()+9; j++) {
                currentOpponent.getKazan().addKorgools(gameBoard.getHole(j).removeAllKorgools());
            }
            gameBoard.endGame();
        }
    }


    /**
    * Handles game logic, and delegates executing game usecases
     *
     * @param holeIndex index of hole that was clicked
    */
    public void clickHole(int holeIndex) {

        // hole has one korgool
        if (gameBoard.getHole(holeIndex).getNumberOfKorgools() == 1) {
            int holeToAddIndex = (holeIndex + 1) % gameBoard.getBoardLength();
            moveOneKorgool(holeIndex, holeToAddIndex);

        // hole has more than one korgool
        } else {
            distributeKorgools(holeIndex);
        }

        if (currentPlayer.getKazan().getNumberOfKorgools() >= 82){
            gameBoard.endGame();
            return;
        }

        gameBoard.nextTurn(currentPlayer, currentOpponent);
    }


    /**
     * Move one Korgool to another hole
     * 
     * @param holeIndex index of hole where a Korgool will be removed
     * @param holeToAddIndex index of hole a Korgool will be added
     */
    private void moveOneKorgool(int holeIndex, int holeToAddIndex) {
        gameBoard.addOneKorgool(holeToAddIndex, currentOpponent, currentPlayer);
        gameBoard.removeOneKorgool(holeIndex);
    }


    /**
     * Distribute Korgools in each subsequent hole after index
     * Handle final Korgool logic.
     * 
     * @param startingHoleIndex - index of starting hole for this move
     */
    private void distributeKorgools(int startingHoleIndex) {

        int limit = gameBoard.getHole(startingHoleIndex).getNumberOfKorgools();

        // get the index of the last hole for that move
        int indexOfLastHoleInMove = (startingHoleIndex + limit - 1) % gameBoard.getBoardLength();

        // i being set to 1 skips the first hole, as there should be one korgool left
        for (int i = 1; i < limit; i++) {
            int holeToAddIndex = (startingHoleIndex + i) % gameBoard.getBoardLength();
            moveOneKorgool(startingHoleIndex, holeToAddIndex);
        }

        // Check if move ends in opponent's side
        // Usecase Move.4.b
        if (isInOpponentsSide(indexOfLastHoleInMove)) {

            // even number of Korgools, player captures them
            // Usecase Move.4.b.i
            if (gameBoard.getHole(indexOfLastHoleInMove).getNumberOfKorgools() % 2 == 0) {
                currentPlayer.getKazan().addKorgools(
                    gameBoard.getHole(indexOfLastHoleInMove).removeAllKorgools()
                );

            // Tuz conditions, declare Tuz
            // Usecase Move.4.b.ii
            } else if (gameBoard.getHole(indexOfLastHoleInMove).getNumberOfKorgools() == 3 // 3 korgools in hole
                        && currentPlayer.getTuzIndex() == -1 // player has not already declared Tuz
                        && currentOpponent.getLastHoleIndex() != indexOfLastHoleInMove // is not last hole of opponent
                        && (indexOfLastHoleInMove < 9 ? // is not in opponent's equivalent hole
                                currentOpponent.getTuzIndex() != indexOfLastHoleInMove % 9 + 9 :
                                currentOpponent.getTuzIndex() != indexOfLastHoleInMove % 9)) {
                currentPlayer.setTuzIndex(indexOfLastHoleInMove);
                currentPlayer.getKazan().addKorgools(
                    gameBoard.getHole(indexOfLastHoleInMove).removeAllKorgools()
                );
            }
        }
    }


    /**
     * Check if a hole is in the opponent's side
     *
     * @param holeIndex index of hole to check
     * @return true if hole is in opponent's side, false otherwise
     */
    private boolean isInOpponentsSide(int holeIndex) {
        return currentOpponent.isInMySide(holeIndex);
    }


    /**
     * Get the current player
     *
     * @return player object representing the current player of the game
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }


    /**
     * Get the current opponent
     *
     * @return player object representing the current opponent of the game
     */
    public Player getCurrentOpponent(){
        return currentOpponent;
    }
}
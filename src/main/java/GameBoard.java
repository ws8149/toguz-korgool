/**
 * Represents a game board
 */
public class GameBoard {
    // array of holes
    private Hole[] holes;

    // game players
    private Player whitePlayer;
    private Opponent blackPlayer;

    // object of the current Turn in play
    private Turn currentTurn;

    // number of holes in board
    private int boardLength;

    //Continue means that the game has not ended and is still continuing
    public enum GameState { WHITEWINS , BLACKWINS, DRAW, CONTINUE}

    /**
     * Default Constructor
     */
    public GameBoard() {
        // populate holes with 9 Korgools
        this.holes = new Hole[18];
        for (int i = 0; i < holes.length; i++) {
            holes[i] = new Hole(9);
        }

        this.boardLength = this.holes.length;
        int sideLength = (boardLength / 2) - 1;
        this.whitePlayer = new Player(0, 0 + sideLength);
        this.blackPlayer = new Opponent(9, 9 + sideLength);
        this.currentTurn = new Turn(whitePlayer, blackPlayer, this);
    }


    /**
     * Custom constructor for practise mode
     *
     * @param korgoolCounts numberOfKorgools in each hole
     * @param whitePlayerKazanKorgools number of korgools in white Kazan
     * @param blackPlayerKazanKorgools number of korgools in black Kazan
     * @param whitePlayerTuzIndex index of white player's Tuz
     * @param blackPlayerTuzIndex index of black player's Tuz
     */
    public GameBoard(
            int[] korgoolCounts,
            int whitePlayerKazanKorgools, int blackPlayerKazanKorgools,
            int whitePlayerTuzIndex, int blackPlayerTuzIndex) {

        // populate holes with 9 Korgools
        this.holes = new Hole[18];
        for (int i = 0; i < holes.length; i++) {
            holes[i] = new Hole(korgoolCounts[i]);
        }

        this.boardLength = this.holes.length;
        int sideLength = (boardLength / 2) - 1;

        this.whitePlayer = new Player(0, 0 + sideLength,
                whitePlayerKazanKorgools, whitePlayerTuzIndex);
        this.blackPlayer = new Opponent(9, 9 + sideLength,
                blackPlayerKazanKorgools, blackPlayerTuzIndex);

        // If tuzes were set on holes that still contain korgools, empty those holes into Kazans
        if (whitePlayerTuzIndex != -1) {
            whitePlayer.getKazan().addKorgools(getHole(whitePlayerTuzIndex).removeAllKorgools());
        }
        if (blackPlayerTuzIndex != -1) {
            blackPlayer.getKazan().addKorgools(getHole(blackPlayerTuzIndex).removeAllKorgools());
        }

        // Initiate first turn
        this.currentTurn = new Turn(whitePlayer, blackPlayer, this);
    }


    /**
     * Change turn to next player.
     *
     * @param currentPlayer player whose turn it was
     * @param currentOpponent player whose turn it wasn't
     */
    public void nextTurn(Player currentPlayer, Player currentOpponent){

        // Create new turn, where previous turn's opponent is now player
        this.currentTurn = new Turn(currentOpponent, currentPlayer, this);

        // If it is the opponent's turn, activate stupid AI logic
        if (currentOpponent.equals(blackPlayer)) {
            int index = blackPlayer.decideMove();

            // Make sure opponent hasn't chosen a hole with no korgools.
            while(getHole(index).getNumberOfKorgools() == 0) {
                index = blackPlayer.decideMove();
            }

            // Play opponent's move
            currentTurn.clickHole(index);
        }
    }


    /**
     * Check for ending conditions of came
     *
     * @return state of the game
     */
    public GameState endGame() {
        int whitePlayerKorgools = whitePlayer.getKazan().getNumberOfKorgools();
        int blackPlayerKorgools = blackPlayer.getKazan().getNumberOfKorgools();

        if (whitePlayerKorgools == 81 && blackPlayerKorgools == 81){

            return GameState.DRAW;

        } else if (whitePlayerKorgools > 81){

            return GameState.WHITEWINS;

        } else if (blackPlayerKorgools > 81) {

            return GameState.BLACKWINS;

        } else {
            return GameState.CONTINUE;
        }
    }


    /**
     * Add one Korgool to hole at given index.
     * If hole is a Tuz, send korgool to appropriate kazan
     *
     * @param index the index of hole to add to
     * @param currentOpponent the current opponent of the game
     * @param currentPlayer the current player of the game
     */
    public void addOneKorgool(int index, Player currentOpponent, Player currentPlayer) {
        holes[index].addOneKorgool();

        // If the korgool was added to a Tuz hole, send it to the appropriate kazan
        if (index == currentOpponent.getTuzIndex()) {
            currentOpponent.getKazan().addKorgools(removeOneKorgool(index));
        } else if (index == currentPlayer.getTuzIndex()) {
            currentPlayer.getKazan().addKorgools(removeOneKorgool(index));
        }
    }


    /**
     * Remove a single Korgool from a hole
     *
     * @param holeIndex index of the hole to remove
     * @return number of korgools removed. Always one in this case.
     */
    public int removeOneKorgool(int holeIndex){
        return holes[holeIndex].removeOneKorgool();
    }


    /**
     * Get the Hole object at given index
     *
     * @param holeIndex index of Hole object in board
     * @return Hole Object
     */
    public Hole getHole(int holeIndex){
        return holes[holeIndex];
    }


    /**
     * Get length of board in number of holes
     */
    public int getBoardLength(){
        return holes.length;
    }


    /**
     * Get the current turn of the game.
     */
    public Turn getCurrentTurn(){
        return currentTurn;
    }


    /**
     * Get the white player. White player is always the human player.
     */
    public Player getWhitePlayer() {return  whitePlayer;}


    /**
     * Get black player. Black player is always the AI player.
     */
    public Player getBlackPlayer() {return  blackPlayer;}


    /**
     * Get the array of Hole objects, representing the holes in the game
     */
    public Hole[] getHoles() {
        return holes;
    }
}
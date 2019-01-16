/**
 * A layer of the game
 */
public class Player {

    // index of first hole on player's side
    protected int firstHoleIndex;

    // index of last hole on player's side
    protected int lastHoleIndex;

    // index of players tuz, defaults to -1
    private int tuzIndex;
    private Hole kazan;


    /**
     * Default constructor for standard game configuration
     *  
     * @param firstHoleIndex starting index of player's side
     * @param lastHoleIndex last index of player's side
     */
    public Player(int firstHoleIndex, int lastHoleIndex) {
        this(firstHoleIndex, lastHoleIndex, 0, -1);
    }


    /**
     * Custom constructor for custom game mode.
     * 
     * @param firstHoleIndex see above
     * @param lastHoleIndex see above
     * @param numberOfKorgoolsInkazan the number of korgools in the kazan that the player will start with
     * @param tuzIndex the tuz index the player will start with. -1 if no tuz.
     */
    public Player(int firstHoleIndex, int lastHoleIndex,
                  int numberOfKorgoolsInkazan, int tuzIndex) {
        this.firstHoleIndex = firstHoleIndex;
        this.lastHoleIndex = lastHoleIndex;
        this.kazan = new Hole(numberOfKorgoolsInkazan);
        this.tuzIndex = tuzIndex;
    }


    /**
     * @return the kazan
     */
    public Hole getKazan() {
        return kazan;
    }


    /**
     * @return the tuzIndex
     */
    public int getTuzIndex() {
        return tuzIndex;
    }


    /**
     * Set Tuz index
     *
     * @param tuzIndex the index to set tuz to
     */
    public void setTuzIndex(int tuzIndex) {
        this.tuzIndex = tuzIndex;
    }


    /**
     * get the index of the first hole on player's side
     * @return index of first hole
     */
    public int getFirstHoleIndex() {
        return this.firstHoleIndex;
    }


    /**
     * get the index of the last hole on player's side
     * @return index of last hole
     */
    public int getLastHoleIndex(){
        return this.lastHoleIndex;
    }


    /**
     * Check if a given hole is in this player object's side
     *
     * @param holeIndex index of hole to check for inclusion
     * @return true if hole is in player's side, false otherwise
     */
    public boolean isInMySide(int holeIndex) {
        return (holeIndex >= firstHoleIndex && holeIndex <= lastHoleIndex);
    }


    /**
     * Check equality of another player object
     * @param other player object to compare equality to
     * @return true if equal, false otherwise
     */
    public boolean equals(Player other) {
        return  firstHoleIndex == other.getFirstHoleIndex() &&
                lastHoleIndex == other.getLastHoleIndex() &&
                tuzIndex == other.getTuzIndex();
    }
}
import java.util.Random;

/**
 * Represents the opponent of the human player of the game.
 *
 * Allows for integrating any degree of intelligence for deciding a move.
 * For now, just a random algorithm.
 */
public class Opponent extends Player {
    Random rand;


    /**
     * Constuctor for normal mode.
     *
     * Indexes are zero based
     * @param firstHoleIndex index of the player's first hole
     * @param lastHoleIndex index of the player's last hole
     */
    public Opponent(int firstHoleIndex, int lastHoleIndex) {
        super(firstHoleIndex, lastHoleIndex);
        rand = new Random();
    }


    /**
     * Constuctor for custom game mode.
     *
     * Indexes are zero based
     * @param firstHoleIndex index of the player's first hole
     * @param lastHoleIndex index of the player's last hole
     * @param numberOfKorgoolsInkazan the number of korgools in the kazan that the player will start with
     * @param tuzIndex the tuz index the player will start with. -1 if no tuz.
     */
    public Opponent(int firstHoleIndex, int lastHoleIndex,
                  int numberOfKorgoolsInkazan, int tuzIndex) {
        super(firstHoleIndex, lastHoleIndex, numberOfKorgoolsInkazan, tuzIndex);
        rand = new Random();
    }


    /**
     * Determine the index of the hole the opponent will click during their turn.
     *
     * @return index of button to be clicked by the opponent.
     */
    public int decideMove() {
        int upperbound = super.lastHoleIndex;
        int lowerbound = super.firstHoleIndex;

        // plus one as nextInt gets number from zero to bound - 1
        int index = rand.nextInt(upperbound-lowerbound + 1) + lowerbound;
        return index;
    }
}
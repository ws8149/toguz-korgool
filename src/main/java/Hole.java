/**
 * Hole class representing a single hole on the game board
 */
public class Hole{
    // Number of Korgools in this hole
    private int numberOfKorgools;


    /**
     * Construct a hole
     * 
     * @param number the number of korgools in this hole
     */
    public Hole(int number){
        this.numberOfKorgools = number;
    }


    /**
     * Get the number of korgools in this hole
     */
    public int getNumberOfKorgools() {
        return numberOfKorgools;
    }


    //TODO: this might only be for kazan. Make subclass for kazan?
    /**
     * Add some number of korgools to this hole.
     *
     * @param numberOfKorgools number of korgools to add
     */
    public void addKorgools(int numberOfKorgools){
        this.numberOfKorgools += numberOfKorgools;
    }


    /**
     * Add one korgool to this hole.
     */
    public void addOneKorgool() {
        this.numberOfKorgools += 1;
    }


    /**
     * Remove all korgools from this hole
     * 
     * @return the number of removed korgools
     */
    public int removeAllKorgools(){
        int temp = this.numberOfKorgools;
        this.numberOfKorgools = 0;
        return temp;
    }


    /**
     * Remove a single korgool from this hole.
     *
     * @return the number of removed korgools - always one
     */
    public int removeOneKorgool(){
        this.numberOfKorgools -= 1;
        return 1;
    }
}

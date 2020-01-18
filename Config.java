public class Config {

    /**
     * A set of caves to search through.  The first one is provided.
     * TODO add at least 2 more caves.
     */
	public static final char[][][] CAVES= new char[][][] {
	    { 
    		{ ' ', ' ', ' ', ' ', ' ' }, 
    		{ ' ', 'b', ' ', 'p', ' ' }, 
    		{ ' ', 'p', 'w', ' ', ' ' },
    		{ ' ', ' ', 'b', ' ', ' ' }, 
    		{ ' ', ' ', ' ', 'b', 'p' } 
    	},
	    
	  { { ' ', ' ', 'b', 'p', ' ' }, 
		{ 'p', ' ', ' ', ' ', ' ' }, 
		{ ' ', ' ', 'p', 'b', ' ' },
		{ ' ', 'b', ' ', ' ', 'w' }, 
		{ ' ', ' ', ' ', ' ', ' ' } 
	    },
	    {
    	{ ' ', ' ', 'p', ' ', 'p' }, 
		{ 'b', ' ', ' ', ' ', ' ' }, 
		{ ' ', ' ', 'b', ' ', ' ' },
		{ ' ', 'w', ' ', 'b', ' ' }, 
		{ 'p', ' ', ' ', ' ', ' ' } }
	};

	/**
	 * The methods in WumpusCaves should refer to these constants,
	 * e.g., Config.PIT, and not the literals themselves.
	 */

	public static final char PIT = 'p';
	public static final char BAT = 'b';
	public static final char WUMPUS = 'w';
	
	/**
	 * The number of perceptions and the indexes in a perceptions
	 * array.
	 */
	public static final int numPerceptions = 3;
	public static final int PerceivePIT = 0;
	public static final int PerceiveBAT = 1;
	public static final int PerceiveWUMPUS = 2;
	
	/**
	 * indices for location arrays
	 */
	public static final int ROW = 0;
	public static final int COLUMN = 1;
	
    /**
     * Random number generator SEED. Passed to the random number generator
     * to get repeatable random numbers which can aid with debugging.
     */
    public static final int SEED = 123;
}

package de.unituebingen.sfs.npuzzle;

// some Java libraries to consider
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Iterator;
import java.util.Comparator;

/**
 * Hello NN Puzzle world!
 *
 */
public class NNPuzzle 
{
    private int[] tiles;
    
    // constructor taking N

    /**
     * Question!!!
     * Are the numbers on tiles always continuous integers from 1 to (N*N - 1), or they can be any random integers?
     */
    public NNPuzzle( int N ) throws Exception{
        //throw Exception if N is not accepted
        if((N < 3)||(N > 6))
        {
            throw new Exception("Not a solvable puzzle");
        }
        int size = N*N;
        int[] puzzle = new int[size];
        //fillling the array
        for(int i = 1; i < N; i++)
        {
            puzzle[i] = i;
        }
        puzzle[N] = 0;

        //initialize instance variable

        /**
         * tiles = puzzle;
         */
        for(int i = 0; i < N; i++)
        {
            tiles[i] = puzzle[i];
        }


    }

    // constructor, using array of ints
    //pretty usure
    public NNPuzzle( int[] newTiles ) {
        int N = newTiles.length;
        for(int i = 0; i < N; i++)
        {
            tiles[i] = newTiles[i];
        }
    }

    //accessor: return instance variable

    public int[] getTiles()
    {
        return tiles;
    }

    @Override
    public boolean equals(Object obj) {

	// given
        if (this == obj) { return true;  }
	if (obj == null) { return false; }
	
        if (getClass() != obj.getClass()) {
            return false;
        }

        NNPuzzle pobj = (NNPuzzle) obj;
			
        // two puzzles are equal when they have their tiles positioned equally.
	// YOUR CODE COMES HERE
        //type casting
        NNPuzzle o = (NNPuzzle)obj;
        //check the length first
        if(o.getTiles().length == this.tiles.length)
        {
            int N = tiles.length;
            for(int i = 0; i < N; i++)
            {
                if(o.getTiles()[i] != tiles[i])
                    return false;
            }
            return true;
        }
        else return false;
    }


    // given, do not change or delete.
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Return all possible direct successor states
     * @return all possble direct successor states
     */
    public List<NNPuzzle> successors() {

	    // The following is not binding (but the code compiles ;-)
	    List<NNPuzzle> successorList = new ArrayList<NNPuzzle>();
	    //locate blank
        int N2 = tiles.length;
        int locate = 0;
        for(int i = 0; i < N2; i++)
        {
            if(tiles[i] == 0)
                locate = i;
        }
        int N = (int)Math.sqrt(N2);
        //check if blank can move up

        //check if blk can move down
        //check if blk can move left
        //check if blk can move right
	return successorList;
    }

    public void easyShuffle( int numberOfMoves ) {
    }

    public void knuthShuffle() {
    }

    public boolean isSolvable() {
	return false;
    }

    public boolean isSolved() {
	return false;
    }

    public void createStartState() {
    }

    public int hamming() {
	return 0;
    }

    public int manhattan() {
	return 0;
    }

    public static void blindSearch( NNPuzzle startState){
    }
    
    public static void heuristicSearch( NNPuzzle startState ){
    }
    
    private void printState(){
    }
    
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello N*N-Puzzle World!" );

	try {

	} catch (Exception e) {
            System.out.printf("Exception!");
        }
    }
}

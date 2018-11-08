package de.unituebingen.sfs.npuzzle;

// some Java libraries to consider
import java.sql.SQLSyntaxErrorException;
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

    public NNPuzzle(int N) throws Exception{
        //throw Exception if N is not accepted
        if((N < 3)||(N > 6))
        {
            throw new Exception("Not a solvable puzzle");
        }
        int length = N*N;

        tiles = new int[length];

        for(int i = 1; i < length; i++)
        {
            tiles[i-1] = i;
        }

    }

    // constructor, using array of ints
    //pretty usure
    public NNPuzzle( int[] newTiles ) {
        int N = newTiles.length;
        tiles = new int[N];
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

    //given, do not change or delete
    @Override
    /**
     * will be amended later, new instance variable to be added
     * wait for double check
     */
    public boolean equals(Object obj) {

        if (this == obj) { return true;  }

	    if (obj == null) { return false; }

        if (getClass() != obj.getClass()) {
            return false;
        }

        NNPuzzle pobj = (NNPuzzle) obj;

        // two puzzles are equal when they have their tiles positioned equally.

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
        int NN = tiles.length;
        int position = 0;
        for(int i = 0; i < NN; i++)
        {
            if(tiles[i] == 0)
            {
                position = i;
                //end the loop here
                break;
            }
        }
        int N = (int)Math.sqrt(NN);

        int above = (position - N);

        //check if blank can move up
        if(position >= N)
        {
            NNPuzzle duplicate = clone(this);
            int[] tmpa = duplicate.getTiles();
            //exch
            int tmp = tmpa[position];
            tmpa[position] = tmpa[above];
            tmpa[above] = tmp;
            NNPuzzle suc = new NNPuzzle(tmpa);
            //add succesor state to list
            successorList.add(suc);
        }
        //check if blk can move down
        if(position < (NN - N))
        {
            NNPuzzle duplicate = clone(this);
            int[] tmpa = duplicate.getTiles();
            //exch
            int tmp = tmpa[position];
            tmpa[position] = tmpa[position + N];
            tmpa[position + N] = tmp;
            NNPuzzle suc = new NNPuzzle(tmpa);
            //add succesor state to list
            successorList.add(suc);
        }
        //check if blk can move left
        if((position % N ) != 0)
        {
            NNPuzzle duplicate = clone(this);
            int[] tmpa = duplicate.getTiles();
            //exch
            int tmp = tmpa[position];
            tmpa[position] = tmpa[position - 1];
            tmpa[position - 1] = tmp;
            NNPuzzle suc = new NNPuzzle(tmpa);
            //add succesor state to list
            successorList.add(suc);
        }
        //check if blk can move right
        if((position % N) != (N - 1))
        {
            NNPuzzle duplicate = clone(this);
            int[] tmpa = duplicate.getTiles();
            //exch
            int tmp = tmpa[position];
            tmpa[position] = tmpa[position + 1];
            tmpa[position + 1] = tmp;
            NNPuzzle suc = new NNPuzzle(tmpa);
            //add succesor state to list
            successorList.add(suc);
        }
	return successorList;
    }

    /**
     * generate a random array by getting the successor state numberOfMoves times
     * @param numberOfMoves
     */
    public void easyShuffle( int numberOfMoves ) {
        for(int i = 0; i < numberOfMoves; i++)
        {
            int size = this.successors().size();
            return;
        }
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

    /**
     * clone method to make a copy of a given board
     * @param NNPuzzle to be cloned
     * @return duplicated NNPuzzle
     */
    public NNPuzzle clone(NNPuzzle board)
    {
        NNPuzzle duplicate = new NNPuzzle(board.getTiles());
        return duplicate;
    }

    /**
     * print the board
     */
    public void printer()
    {
        int NN = tiles.length;
        int N = (int)Math.sqrt(NN);

        for(int i = 0; i < NN; i++)
        {
            System.out.print(tiles[i] + " ");
            if((i % N) == (N - 1))
                System.out.println();

        }
    }

    
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello N*N-Puzzle World!" );

        System.out.println();

        /*
        test for method successors
         */
            int[] testBoard = {1,2,4,6,0,8,7,5,6};
            NNPuzzle test = new NNPuzzle(testBoard);
            test.printer();
            System.out.println();
            List<NNPuzzle> l = test.successors();
            for(int i = 0; i < 4; i++)
            {
                l.get(i).printer();
                System.out.println();
            }




        }


}

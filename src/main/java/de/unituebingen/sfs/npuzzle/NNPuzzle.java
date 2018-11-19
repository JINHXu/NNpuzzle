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


    /*
    accessor
     */

    public int[] getTiles()
    {
        return tiles;
    }
    /*
    mutator
     */
    public void setTiles(int[] newTiles)
    {
        if(newTiles.length != tiles.length)
        {
            System.out.println("error");
            System.exit(0);
        }

        for(int i = 0; i < newTiles.length; i++)
        {
            tiles[i] = newTiles[i];
        }
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
    public void easyShuffle( int numberOfMoves ) throws Exception
    {
        //ileegal para
        if(numberOfMoves < 1)
        {
            throw new Exception("ILLEGAL NUMBER OF MOVES");
        }
        //base case
        else if(numberOfMoves == 1)
        {
            int numOfSucs = this.successors().size();
            //generate random number between zero(inclusive) and size(exclusive)
            int random = (int)(Math.random()*numOfSucs);
            NNPuzzle randomSuc = this.successors().get(random);
            this.setTiles(randomSuc.getTiles());
        }
        //recursive case
        else
        {
            int numOfSucs = this.successors().size();
            //generate random number between zero(inclusive) and size(exclusive)
            int random = (int)(Math.random()*numOfSucs);
            NNPuzzle randomSuc = this.successors().get(random);
            this.setTiles(randomSuc.getTiles());
            easyShuffle(numberOfMoves-1);

        }
    }

    /**
     * Knuth Shuffle the board, leaving the blank in its home position
     */
    public void knuthShuffle() {
        int N = tiles.length - 1;
        for(int i = 0; i < N; i++)
        {
            Random rand = new Random();
            int r = rand.nextInt(i + 1);
            exch(tiles, i, r);
        }
    }

    /**
     * helper method exch for knuthShuffle
     * @param t, int i, int j
     */
    public void exch(int[] t, int a, int b)
    {
        int tmp = t[a];
        t[a] = t[b];
        t[b] = tmp;
    }

    /**
     * return true if the NNPuzzle is solvable(a puzzle is solvable iff it has even number of inversions)
     * @return true if solvable, false otherwise
     */
    public boolean isSolvable() {

        if((inversionCounter() % 2) == 0)
            return true;
        else
            return false;
    }

    /**
     * count the number of inversions
     * @return number of inversions
     */
    public int inversionCounter()
    {
        int inversionCounter = 0;
        int N = tiles.length;
        for(int i = 0; i < N; i++)
        {
            if(tiles[i] == 0)
            {
                continue;
            }

            for(int j = i+1; j < N; j++)
            {
                if((tiles[j] < tiles[i])&&(tiles[j] != 0))
                    inversionCounter++;
            }
        }
        return inversionCounter;
    }

    /**
     * checks whether a given puzzle is in a solved state.
     * @return true if the NNPuzzle is solved, false otherwise
     */
    public boolean isSolved() {
        for(int i = 0; i < (tiles.length-1); i++)
        {
            if(tiles[i] != (i+1))
            {
                return false;
            }
        }
        if(tiles[tiles.length - 1] != 0)
        {
            return false;
        }
	    return true;
    }

    /**
     * initialize the problem state with a solvable Knuth randomization
     */
    public void createStartState() {
        knuthShuffle();
        while(!isSolvable())
        {
            knuthShuffle();
        }
        return;
    }

    public int hamming() {
	return 0;
    }

    public int manhattan() {
	return 0;
    }

    /**
     * a method that solves the N puzzle for a given N
     * @param startState the start state that resulted from a good shuffle
     */
    public static void blindSearch( NNPuzzle startState){
        //create open list and closed
        Stack<NNPuzzle> openList = new Stack<NNPuzzle>();
        Stack<NNPuzzle> closedList = new Stack<NNPuzzle>();
        //innitialize open list
        openList.push(startState);

        while(true)
        {
            //take the first element from open list
            NNPuzzle s = openList.pop();
            //if the state is goal state, terminate
            if(s.isSolved())
            {
                System.out.println("the given board is solved");
                return;
            }
            //add the state to the closed list of seen states
            closedList.push(s);
            //call successors to get the successor states for the examined state
            List<NNPuzzle> l = s.successors();
            for(int i = 0; i < l.size(); i++)
            {
                NNPuzzle sta = l.get(i);
                //check if sta is not in open list nor closed list with depth-first search
                if(sta.notInStack(openList)&&sta.notInStack(closedList))
                {
                    openList.add(sta);
                }
            }

        }
    }

    /**
     * helper method for blind search
     * @return true if state is not in parameter s
     * @param s stack of states
     */
    public boolean notInStack(Stack<NNPuzzle> s)
    {
        while(!s.isEmpty())
        {
            if(equals(s.pop()))
                return false;
        }
        return true;
    }
    
    public static void heuristicSearch( NNPuzzle startState ){
    }

    /**
     * prints all states
     */
    private void printState(){
        int NN = tiles.length;
        int N = (int)Math.sqrt(NN);

        for(int i = 0; i < NN; i++)
        {
            System.out.print(tiles[i] + " ");
            if((i % N) == (N - 1))
                System.out.println();

        }
    }

    /**
     * clone method to make a copy of a given board
     * @param board to be cloned
     * @return duplicated NNPuzzle
     */
    public NNPuzzle clone(NNPuzzle board)
    {
        NNPuzzle duplicate = new NNPuzzle(board.getTiles());
        return duplicate;
    }

    /**
     * print the board, which is printState
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

        try {

        /*
        test for method successors
         */
            int[] testBoard = {1, 2, 4, 6, 0, 8, 7, 5, 6};
            NNPuzzle test = new NNPuzzle(testBoard);
            test.printer();
            System.out.println();
            List<NNPuzzle> l = test.successors();
            for (int i = 0; i < 4; i++) {
                l.get(i).printer();
                System.out.println();
            }

            /*
            test for mutator
             */

            int[] tobeSet = {1, 2, 3, 4, 5, 6, 7, 8, 0};
            test.setTiles(tobeSet);
            test.printer();
            System.out.println();

            /*
            test easyShuffle
             */
            test.easyShuffle(340);
            test.printer();
            System.out.println();
            /*
            second test easyShuffle
             */
            NNPuzzle test2 = new NNPuzzle(4);
            test2.easyShuffle(416);
            test2.printer();
            System.out.println();

            /*
            third test easyShuffle
             */
            NNPuzzle test3 = new NNPuzzle(6);
            test3.easyShuffle(999);
            test3.printer();
            System.out.println();

            /*
            test for knuth shuffle
             */
            NNPuzzle testk1 = new NNPuzzle(3);
            testk1.knuthShuffle();
            testk1.printer();
            System.out.println();

            /*
            second test for knuth shuffle
             */
            NNPuzzle testk2 = new NNPuzzle(5);
            testk2.knuthShuffle();
            testk2.printer();
            System.out.println();

            /*
            third test for knuth shuffle
             */
            NNPuzzle testk3 = new NNPuzzle(6);
            testk3.knuthShuffle();
            testk3.printer();
            System.out.println();

            /*
            test for method inversionCounter(isSolvable)
             */
            NNPuzzle testi = new NNPuzzle(4);
            System.out.println(testi.isSolvable());
            System.out.println(testi.inversionCounter());

            /*
            second test for method inversionCounter
             */
            int[] array4i = {3,2,1,8,4,5,6,7,0};
            NNPuzzle testi2 = new NNPuzzle(array4i);
            System.out.println(testi2.inversionCounter());
            System.out.println(testi2.isSolvable());

            /*
            third test for method inversionCounter
             */
            int[] array4i2 = {9,2,11,12,15,3,6,8,7,13,0,1,4,5,10,14};
            NNPuzzle testi3 = new NNPuzzle(array4i2);
            System.out.println(testi3.inversionCounter());
            System.out.println(testi3.isSolvable());
            System.out.println();

            /*
            test for method createStartState
             */

            NNPuzzle t0 = new NNPuzzle(3);
            NNPuzzle t1 = new NNPuzzle(4);
            NNPuzzle t2 = new NNPuzzle(5);
            NNPuzzle t3 = new NNPuzzle(6);


            t0.createStartState();
            t1.createStartState();
            t2.createStartState();
            t3.createStartState();


            t0.printer();
            t1.printer();
            t2.printer();
            t3.printer();

            System.out.println(t0.isSolvable());
            System.out.println(t1.isSolvable());
            System.out.println(t2.isSolvable());
            System.out.println(t3.isSolvable());

            System.out.println();

            /*
            test method isSolved
             */
            System.out.println(t0.isSolved());
            System.out.println(t1.isSolved());
            System.out.println(t2.isSolved());
            System.out.println(t3.isSolved());


            /*
            test blind search
             */

            System.out.println("BLIND SEARCH SATRTS, 1: 09AM");

            NNPuzzle bs = new NNPuzzle(3);
            bs.createStartState();
            bs.printer();
            NNPuzzle.blindSearch(bs);
            System.out.println("mark");
            bs.printer();

        }

        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        }


}

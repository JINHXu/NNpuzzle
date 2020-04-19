
//Jinghau Xu
// An NNPuzzle solver(N = 3,4,5,6).

package de.unituebingen.sfs.npuzzle;

import java.util.*;
import java.util.stream.IntStream;


/**
 * Class represents NNpuzzle.
 */
public class NNPuzzle implements Comparable {

    private int[] tiles;
    private int boardSize;
    private int goalDistance;


    //constructor taking N
    public NNPuzzle(int N) throws IllegalArgumentException {
        //param check
        if ((N < 3) || (N > 6)) {
            throw new IllegalArgumentException("Not a solvable puzzle, N can only be 3,4,5,6.");
        }

        this.boardSize = N;
        this.goalDistance = 0;

        this.tiles = new int[N * N];
        for (int i = 0; i < tiles.length - 1; i++) {
            tiles[i] = i + 1;
        }

    }

    //constructor taking tiles array(of ints)
    public NNPuzzle(int[] newTiles) {
        int N = (int) Math.sqrt(newTiles.length);

        // check there is a blank space
        if (Arrays.stream(newTiles).noneMatch(i -> i == 0))
            throw new IllegalArgumentException("tiles must contain a blank space (0)!");
        // check that tiles are unique
        if (Arrays.stream(newTiles).distinct().count() != newTiles.length)
            throw new IllegalArgumentException("tiles must be unique!");
        // check the board shape
        if (newTiles.length % N != 0) throw new IllegalArgumentException("board must be a square!");


        // check board dimensions
        if (N < 3 || N > 6)
            throw new IllegalArgumentException(String.format("board size must be 3, 4, 5, or 6! (provided board size was %d)" , N));

        int[] tilestmp = newTiles.clone();
        Arrays.sort(tilestmp);
        if (!Arrays.equals(tilestmp , IntStream.range(0 , N * N).toArray()))
            throw new IllegalArgumentException("Board must have only tiles 0-" + N * N);

        this.tiles = newTiles.clone();
        this.boardSize = N;
    }

    //tiles getter
    public int[] getTiles() {
        return tiles;
    }

    //tiles setter
    public void setTiles(int[] newTiles) {
        if (newTiles.length != tiles.length) {
            System.out.println("error");
            System.exit(0);
        }

        for (int i = 0; i < newTiles.length; i++) {
            tiles[i] = newTiles[i];
        }
    }

    //goalDistance setter
    private void setDistance(String distanceMeasure) {
        switch (distanceMeasure.toLowerCase()) {
            case "hamming":
                this.goalDistance = this.hamming();
                break;
            case "manhattan":
                this.goalDistance = this.manhattan();
                break;
            default:
                throw new IllegalArgumentException("Unknown distance measure");
        }
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        NNPuzzle otherPuzzle = (NNPuzzle) other;

        // two puzzles are equal if they have their tiles positioned equally
        return Arrays.equals(this.tiles , otherPuzzle.tiles);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(tiles);
    }

    @Override
    public int compareTo(Object o) {
        NNPuzzle board = (NNPuzzle) o;
        if (this.hamming() > board.hamming())
            return 1;
        else if (this.hamming() < board.hamming())
            return -1;
        else
            return 0;
    }


    /**
     * print the board, which is printState
     */
    public void printer() {
        int N = (int) Math.sqrt(tiles.length);

        for (int i = 0; i < tiles.length; i++) {
            System.out.print(tiles[i] + " ");
            if ((i % N) == (N - 1))
                System.out.println();

        }

        System.out.println();
    }

    /**
     * clone method to make a copy of a given board
     *
     * @param board to be cloned
     * @return duplicated NNPuzzle
     */
    public NNPuzzle clone(NNPuzzle board) {
        NNPuzzle duplicate = new NNPuzzle(board.getTiles());
        return duplicate;
    }


    /**
     * Return all possible direct successor states
     *
     * @return all possble direct successor states
     */
    public List<NNPuzzle> successors() {

        // The following is not binding (but the code compiles ;-)
        List<NNPuzzle> successorList = new ArrayList<NNPuzzle>();
        //locate blank
        int position = 0;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == 0) {
                position = i;
                //end the loop here
                break;
            }
        }
        int N = boardSize;

        int above = (position - N);

        //check if blank can move up
        if (position >= N) {
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
        if (position < (tiles.length - N)) {
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
        if ((position % N) != 0) {
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
        if ((position % N) != (N - 1)) {
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
     *
     * @param numberOfMoves
     */
    public void easyShuffle(int numberOfMoves) throws Exception {
        //para check
        if (numberOfMoves < 1) {
            throw new Exception("ILLEGAL NUMBER OF MOVES");
        }

        Random r = new Random();

        //base case
        if (numberOfMoves == 1) {
            int numOfSucs = this.successors().size();
            //generate random number between zero(inclusive) and size(exclusive)
            int random = r.nextInt(numOfSucs);
            NNPuzzle randomSuc = this.successors().get(random);
            this.setTiles(randomSuc.getTiles());
        }
        //recursive case
        else {
            int numOfSucs = this.successors().size();
            //generate random number between zero(inclusive) and size(exclusive)
            int random = r.nextInt(numOfSucs);
            NNPuzzle randomSuc = this.successors().get(random);
            this.setTiles(randomSuc.getTiles());
            easyShuffle(numberOfMoves - 1);

        }
    }

    /**
     * Knuth Shuffle the board, leaving the blank in its home position.
     */
    public void knuthShuffle() {

        Random rand = new Random();

        for (int i = 0; i < tiles.length - 1; i++) {
            int r = rand.nextInt(i + 1);
            exch(tiles , i , r);
        }
    }

    /**
     * helper method exch for knuthShuffle
     *
     * @param t, int i, int j
     */
    public void exch(int[] t , int a , int b) {
        int tmp = t[a];
        t[a] = t[b];
        t[b] = tmp;
    }

    /**
     * return true if the NNPuzzle is solvable(a puzzle is solvable iff it has even number of inversions)
     *
     * @return true if solvable, false otherwise
     */
    public boolean isSolvable() {
        if (tiles[tiles.length - 1] != 0) return false;
        return (inversionCounter() % 2 == 0);
    }

    /**
     * count the number of inversions
     *
     * @return number of inversions
     */
    public int inversionCounter() {
        int inversionCounter = 0;
        for (int i = 0; i < tiles.length - 1; i++) {
            for (int j = i + 1; j < tiles.length - 1; j++) {
                if (tiles[i] > tiles[j]) {
                    inversionCounter++;
                }
            }
        }
        return inversionCounter;
    }

    /**
     * checks whether a given puzzle is in a solved state.
     *
     * @return true if the NNPuzzle is solved, false otherwise
     */
    public boolean isSolved() {
        if (tiles[tiles.length - 1] != 0) {
            return false;
        }
        for (int i = 0; i < (tiles.length - 1); i++) {
            if (tiles[i] != (i + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * initialize the problem state with a solvable Knuth randomization
     */
    public void createStartState() {
        knuthShuffle();
        while (!isSolvable()) {
            knuthShuffle();
        }
    }

    /**
     * counts the number of misplaced tiles.
     *
     * @return the number of misplaced tiles.
     */
    public int hamming() {
        int count = 0;
        for (int i = 0; i < (tiles.length - 1); i++) {
            if (tiles[i] != (i + 1)) {
                count++;
            }
        }
        if (tiles[tiles.length - 1] != 0)
            count++;
        return count;
    }

    /**
     * counts for each tile its distance from its home location.
     *
     * @return the sum of each tile its distance from its home position
     */
    public int manhattan() {
        int count = 0;
        int N = (int) Math.sqrt((double) tiles.length);
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] != 0) {
                int x = (tiles[i] - 1) % N - (i % N);
                int y = (tiles[i] - 1) / N - (i / N);
                count = count + Math.abs(x) + Math.abs(y);
            } else {
                int x = Math.abs((i % N) - (N - 1));
                int y = Math.abs((i / N) - (N - 1));
                count = count + x + y;
            }
        }
        return count;
    }

    /**
     * Blind search: solves the N puzzle.
     *
     * @param p an NNpuzzle
     */
    public static void blindSearch(NNPuzzle p) {
        //param checks
        if (p == null) throw new IllegalArgumentException("NNPuzzle cannot be null!");

        if (!p.isSolvable()) {
            System.out.println("Puzzle has no solution.");
            return;
        }
        //create open list and closed
        Stack<NNPuzzle> openList = new Stack<NNPuzzle>();
        Set<NNPuzzle> closedList = new HashSet<>();

        openList.push(p);

        while (!openList.isEmpty()) {
            //take the first element from open list
            NNPuzzle s = openList.pop();
            //if the state is goal state, terminate
            if (s.isSolved()) {
                System.out.println("the given board is solved\n");
                p.setTiles(s.getTiles());
                break;
            }

            //add the state to the closed list of seen states
            closedList.add(s);

            for (NNPuzzle sta : s.successors()) {
                if (!closedList.contains(sta)) {
                    openList.push(sta);
                }
            }

        }
        System.out.println("number of examined states: " + closedList.size());
    }

    /**
     * Heuristic search: a method that solves the N puzzle for a given N
     *
     * @param p               a puzzle resulted from a good shuffle
     * @param distanceMeasure decide which distance measure to use, hamming or manhattan.
     */
    public static void heuristicSearch(NNPuzzle p , String distanceMeasure) {
        //param checks
        if (p == null) throw new IllegalArgumentException("NNPuzzle cannot be null");
        if (!distanceMeasure.matches("[Hh]amming|[Mm]anhattan"))
            throw new IllegalArgumentException("Unknown distance measure!");
        if (!p.isSolvable()) {
            System.out.println("Puzzle has no solution.");
            return;
        }

        //create open list and closed list
        PriorityQueue<NNPuzzle> openPQ = new PriorityQueue<NNPuzzle>();
        Set<NNPuzzle> closedList = new HashSet<>();

        //initialize the openlist
        openPQ.add(p);

        int count = 0;

        while (!openPQ.isEmpty()) {

            //take the first element from the open list
            //when you add a state to the priority queue, compute its goal distance value first.
            NNPuzzle s = openPQ.poll();

            if (s.isSolved()) {
                System.out.println("the given board is solved\n");
                p.setTiles(s.getTiles());
                break;
            }
            count++;
            //when you add a state to the priority queue, compute its goal distance value first.
            closedList.add(s);

            List<NNPuzzle> l = s.successors();

            for (NNPuzzle sta : s.successors()) {
                if (!closedList.contains(sta)) {
                    s.setDistance(distanceMeasure);
                    openPQ.offer(sta);
                }
            }

        }
        System.out.println("number of examined states: " + count);
    }


    //a series of tests

    public static void main(String[] args) throws Exception {
        System.out.println("Hello N*N-Puzzle World!");

        System.out.println();

        try {

        /*
        test for method successors
         */
            int[] testBoard = {1 , 2 , 4 , 3 , 0 , 8 , 7 , 5 , 6};
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

            int[] tobeSet = {1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 0};
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
            int[] array4i = {3 , 2 , 1 , 8 , 4 , 5 , 6 , 7 , 0};
            NNPuzzle testi2 = new NNPuzzle(array4i);
            System.out.println(testi2.inversionCounter());
            System.out.println(testi2.isSolvable());

            /*
            third test for method inversionCounter
             */
            int[] array4i2 = {9 , 2 , 11 , 12 , 15 , 3 , 6 , 8 , 7 , 13 , 0 , 1 , 4 , 5 , 10 , 14};
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

            System.out.println();


            /*
            test method equal
             */

            NNPuzzle e0 = new NNPuzzle(3);
            System.out.println(e0.equals(t0));

            NNPuzzle e0i = new NNPuzzle(3);
            System.out.println(e0.equals(e0i));


            /*
            test blind search
             */

            /*
            NNPuzzle bs = new NNPuzzle(4);
            bs.createStartState();
            bs.printer();
            NNPuzzle.blindSearch(bs);
            System.out.println("mark");
            bs.printer();
            System.out.println();
            */


            /*
            test for method for hamming and manhattan
             */
            NNPuzzle hnm3 = new NNPuzzle(3);
            NNPuzzle hnm4 = new NNPuzzle(4);
            NNPuzzle hnm5 = new NNPuzzle(5);
            NNPuzzle hnm6 = new NNPuzzle(6);

            hnm3.createStartState();
            hnm4.createStartState();
            hnm5.createStartState();
            hnm6.createStartState();

            hnm3.printer();
            hnm4.printer();
            hnm5.printer();
            hnm6.printer();

            System.out.println(hnm3.hamming() + " " + hnm3.manhattan());
            System.out.println(hnm4.hamming() + " " + hnm4.manhattan());
            System.out.println(hnm5.hamming() + " " + hnm5.manhattan());
            System.out.println(hnm6.hamming() + " " + hnm6.manhattan());



            /*
            test for heuristics search
             */

            System.out.println("test for heuristics search");
            NNPuzzle hs3 = new NNPuzzle(3);
            hs3.createStartState();
            hs3.printer();
            NNPuzzle.heuristicSearch(hs3 , "hamming");
            System.out.println("solution by heuristics search");
            hs3.printer();

            //performance analysis

            System.out.println("Analysis starts here:");


            System.out.println("informed search(hamming): \n");

            for (int i = 3; i < 7; i++) {
                NNPuzzle anlysis_hhl = new NNPuzzle(i);
                anlysis_hhl.createStartState();
                anlysis_hhl.printer();
                StopWatch stopWatch = new StopWatch();
                NNPuzzle.heuristicSearch(anlysis_hhl , "hamming");
                Double time = stopWatch.elapsedTime();
                anlysis_hhl.printer();
                System.out.println("time consuming by informed search(hamming) for " + i + "*" + i + " puzzle is " + time + "\n" + "\n");
            }


            /*
            informed search(manhattan) requires replacing all invocations of hamming()
            with manhattan() in method compareTo at the end of NNPuzzle class
            the same codes in main as informed search(hamming)
             */

            System.out.println("Blind Search:" + "\n");

            for (int i = 3; i < 7; i++) {
                NNPuzzle anlysis_bl = new NNPuzzle(i);
                anlysis_bl.createStartState();
                anlysis_bl.printer();
                StopWatch stopWatch = new StopWatch();
                NNPuzzle.blindSearch(anlysis_bl);
                Double time = stopWatch.elapsedTime();
                anlysis_bl.printer();
                System.out.println("time consuming by blind search for " + i + "*" + i + " puzzle is " + time + "\n" + "\n");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }

}

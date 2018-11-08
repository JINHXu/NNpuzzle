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

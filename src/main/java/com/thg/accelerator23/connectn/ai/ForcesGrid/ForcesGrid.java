package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;

import java.util.*;


public class ForcesGrid extends Player {
  public int depth;



  public ForcesGrid(Counter counter, int depth) {
    super(counter, ForcesGrid.class.getName());
    this.depth = depth;
  }

  public ForcesGrid(Counter counter) {
    //TODO: fill in your name here
    this(counter, 0);
  }

  @Override
  public int makeMove(Board board) {
    long tzero = System.currentTimeMillis();
//    int result = test.recurring(new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9)));
    ArrayList<Integer> result = BFSearch.getMove(board,0,3,true, getCounter());
    System.out.println("call count: "+BFSearch.count);
//    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    System.out.println("move: " + result.get(0) + "  TimeElapsed: " + (System.currentTimeMillis() - tzero) + "ms");
    return result.get(0);
  }
}
package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;


public class ForcesGrid extends Player {
  public ForcesGrid (Counter counter) {
    //TODO: fill in your name here
    super(counter, ForcesGrid.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    //TODO: some crazy analysis
    //TODO: make sure said analysis uses less than 2G of heap and returns within 10 seconds on whichever machine is running it
    return 4;
  }
}

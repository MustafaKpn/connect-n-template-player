package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class BFSearch {
    public static int count = 0;

    private static ArrayList<Integer> terminalScore(Board board, Counter counter, Position position){
        if (Analysis.gameOver(board, counter)) {
            if (Analysis.isWin(board, counter)) {
                return new ArrayList<>(Arrays.asList(null, 9999999));   //win scenario
            } else if (Analysis.isWin(board, counter.getOther())) {
                return new ArrayList<>(Arrays.asList(null, -9999999));  //lose scenario
            } else {
                return new ArrayList<>(Arrays.asList(null, 0));     //draw scenario
            }
        } else {
            int score =  forScoring.scorePosition(board, position, counter);
            return new ArrayList<>(Arrays.asList(null, score));
        }
    }

    public static ArrayList<Integer> getMove(Board board, int curDepth, int maxDepth, boolean maximise, Counter counter, Position position) {
        count++;
        ArrayList<Position> positions = Analysis.getValidLocations(board);
        boolean gameOver = Analysis.gameOver(board, counter);

        Random rand = new Random();
        int move = rand.nextInt(board.getConfig().getWidth());
        int score = 0;

        if (curDepth == maxDepth || gameOver) {
            return terminalScore(board, counter, position);
        } else if (maximise) {
            try {
                score = -9999999;
                for (Position p : positions) {
                    Board boardCopy = new Board(board, p.getX(), counter);
                    int newScore = terminalScore(boardCopy, counter, p).get(1);
                    System.out.println("depth: " + curDepth + "; counter: " + counter + "; column: " + p.getX() + "; score: " + newScore);
                    System.out.println("simulated board:");
                    forPrinting.printBoard(boardCopy);

                    if (newScore > score) {
                        score = newScore;
                        move = p.getX();
                    }
                }
                //break clause
                System.out.println("score: " + score + ", move: " + move);


                for (Position p : positions) {
                    Board boardCopy = new Board(board, p.getX(), counter);
                    int newScore = getMove(boardCopy, curDepth + 1, maxDepth,false,counter,p).get(1);
                    if (newScore > score) {
                        score = newScore;
                        move = p.getX();
                    }
                }
                return new ArrayList<>(Arrays.asList(move, score));
            } catch (InvalidMoveException e) {
            System.out.println("oops");
            }
        } else {
            try {
                score = 9999999;
                for (Position p : positions) {
                    Board boardCopy = new Board(board, p.getX(), counter.getOther());
                    int newScore = terminalScore(boardCopy, counter, p).get(1);
                    System.out.println("depth: " + curDepth + "; counter: " + counter.getOther() + "; column: " + p.getX() + "; score: " + newScore);
                    System.out.println("simulated board:");
                    forPrinting.printBoard(boardCopy);

                    if (newScore < score) {
                        score = newScore;
                        move = p.getX();
                    }

                }
                //break clause
                for (Position p : positions) {
                    Board boardCopy = new Board(board, p.getX(), counter.getOther());
                    int newScore = getMove(boardCopy, curDepth + 1, maxDepth,true,counter,p).get(1);
                    if (newScore < score) {
                        score = newScore;
                        move = p.getX();
                    }
                }
                return new ArrayList<>(Arrays.asList(move, score));
            } catch (InvalidMoveException e) {
                System.out.println("oops");
            }
        }
        return new ArrayList<>(Arrays.asList(move, score));
    }


    //overload
    public static ArrayList<Integer> getMove(Board board, int depth, int maxDepth, boolean maximise, Counter counter) {
        return getMove(board, depth, maxDepth, maximise, counter, null);
    }
}



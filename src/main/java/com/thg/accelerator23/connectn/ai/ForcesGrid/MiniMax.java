package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MiniMax {
    public static ArrayList<Integer> getMove(Board board, int depth, int alpha, int beta, boolean maximise, Counter counter, Position position) {
//        System.out.println("depth: "+depth+", alpha: "+alpha+", beta: "+beta+", maximise: "+maximise);

        ArrayList<Position> positions = Analysis.getValidLocations(board);
        boolean gameOver = Analysis.gameOver(board, counter);
        Random rand = new Random();
        int move = rand.nextInt(board.getConfig().getWidth());
        int score;

        if (depth == 0 || gameOver) {
            if (gameOver) {
                if (Analysis.isWin(board, counter)) {
                    return new ArrayList<>(Arrays.asList(null, 9999999));   //win scenario
                } else if (Analysis.isWin(board, counter.getOther())) {
                    return new ArrayList<>(Arrays.asList(null, -9999999));  //lose scenario
                } else {
                    return new ArrayList<>(Arrays.asList(null, 0));     //draw scenario
                }
            } else {
                score =  forScoring.scorePosition(board, position, counter);
                return new ArrayList<>(Arrays.asList(null, score));
            }
        }

        if (maximise) {
            score = -9999999;
            try {
                for (Position pos : positions) {
                    Board boardCopy = new Board(board, pos.getX(), counter);
                    int new_score = getMove(boardCopy, depth - 1, alpha, beta, false, counter, pos).get(1);

                    System.out.println("depth: "+depth+"; counter: "+ counter.getStringRepresentation() + "; column: "+pos.getX() + "; score: "+ new_score);
                    System.out.println("simulated board:");
                    forPrinting.printBoard(boardCopy);

                    if (new_score > score) {
                        score = new_score;
                        move = pos.getX();
                    }
                    alpha = Math.max(alpha, score);
                    if (alpha >= beta) {
                        break;
                    }
                }
            } catch (InvalidMoveException e) {
                System.out.println("oops");
            }
        } else {
            score = 9999999;
            try {
                for (Position pos : positions) {
                    Board boardCopy = new Board(board, pos.getX(), counter.getOther());
                    int new_score = getMove(boardCopy, depth - 1, alpha, beta, true, counter, pos).get(1);

                    System.out.println("depth: "+depth+"; counter: "+ counter.getOther().getStringRepresentation() + "; column: "+pos.getX() + "; score: "+ new_score);
                    System.out.println("simulated board:");
                    forPrinting.printBoard(boardCopy);

                    if (new_score < score) {
                        score = new_score;
                        move = pos.getX();
                    }
                    beta = Math.min(beta, score);
                    if (alpha >= beta) {
                        break;
                    }
                }
            } catch (InvalidMoveException e) {
                System.out.println("oops");
            }
        }
        return new ArrayList<>(Arrays.asList(move, score));
    }

    //overload
    public static ArrayList<Integer> getMove(Board board, int depth, int alpha, int beta, boolean maximise, Counter counter) {
        return getMove(board, depth, alpha, beta, maximise, counter, null);
    }
}

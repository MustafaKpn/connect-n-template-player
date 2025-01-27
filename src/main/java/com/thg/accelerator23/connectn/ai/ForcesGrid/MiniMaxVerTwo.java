package com.thg.accelerator23.connectn.ai.ForcesGrid;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.InvalidMoveException;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MiniMaxVerTwo {
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

    public static ArrayList<Integer> getMove(Board board, int depth, int alpha, int beta, boolean maximise, Counter counter, Position position) {
        count++;
        ArrayList<Position> positions = Analysis.getValidLocations(board);
        boolean gameOver = Analysis.gameOver(board, counter);

        Random rand = new Random();
        int move = rand.nextInt(board.getConfig().getWidth());
        int score;

        if (depth == 0 || gameOver) {
            return terminalScore(board, counter, position);
        }

        if (maximise) {
            score = -9999999;
            try {
                for (Position p : positions) {
                    Board boardCopy = new Board(board, p.getX(), counter);
                    int newScore = terminalScore(boardCopy, counter, p).get(1);
                    System.out.println("depth: "+depth+"; counter: "+ counter + "; column: "+ p.getX()+"; score: "+ newScore);
                    System.out.println("simulated board:");
                    forPrinting.printBoard(boardCopy);

                    if (newScore > score) {
                        score = newScore;
                        move = p.getX();
                    }

                }
                alpha = Math.max(alpha, score);
                System.out.println("alpha: "+alpha+"; beta: " + beta);
                if (alpha >= beta) {
                    return new ArrayList<>(Arrays.asList(move, score));
                }
                System.out.println("score: "+score+", move: "+move);

                for (Position pos : positions) {
                    Board boardCopy = new Board(board, pos.getX(), counter);
                    if (Analysis.gameOver(boardCopy, counter)) {
                        return new ArrayList<>(Arrays.asList(pos.getX(), score));
                    }
//                    System.out.println("depth: "+depth+"; counter: "+ counter + "; column: "+pos.getX());
                    int new_score = getMove(boardCopy, depth - 1, alpha, beta, false, counter, pos).get(1);

//                    System.out.println("[loop] score: "+ new_score + "\nsimulated board:");
//                    forPrinting.printBoard(boardCopy);
//                    System.out.println("\n\n");

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
                for (Position p : positions) {
                    Board boardCopy = new Board(board, p.getX(), counter.getOther());
                    int newScore = terminalScore(boardCopy, counter, p).get(1);
                    System.out.println("depth: "+depth+"; counter: "+ counter.getOther() + "; column: "+ p.getX()+"; score: "+ newScore);
                    System.out.println("simulated board:");
                    forPrinting.printBoard(boardCopy);

                    if (newScore < score) {
                        score = newScore;
                        move = p.getX();
                    }

                }
                beta = Math.min(beta, score);
                System.out.println("alpha: "+alpha+"; beta: " + beta);
                if (alpha >= beta) {
                    return new ArrayList<>(Arrays.asList(move, score));
                }
                for (Position pos : positions) {
                    Board boardCopy = new Board(board, pos.getX(), counter.getOther());
                    if (Analysis.gameOver(boardCopy, counter)) {
                        return new ArrayList<>(Arrays.asList(pos.getX(), score));
                    }
//                    System.out.println("depth: "+depth+"; counter: "+ counter.getOther().getStringRepresentation() + "; column: "+pos.getX() );

                    int new_score = getMove(boardCopy, depth - 1, alpha, beta, true, counter, pos).get(1);

//                    System.out.println("[loop] score: "+ new_score + "\nsimulated board:");
//                    forPrinting.printBoard(boardCopy);
//                    System.out.println("\n\n");

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

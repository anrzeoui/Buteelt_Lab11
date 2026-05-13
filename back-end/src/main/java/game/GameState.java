package game;

import java.util.Arrays;

public class GameState {

    private final Cell[] cells;
    private final String instructions;
    private final String nextPlayer;
    private final String winner;
    private final boolean gameOver;

    private GameState(Cell[] cells, String instructions, String nextPlayer, String winner, boolean gameOver) {
        this.cells = cells;
        this.instructions = instructions;
        this.nextPlayer = nextPlayer;
        this.winner = winner;
        this.gameOver = gameOver;
    }

    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        Player winner = game.getWinner();
        
        boolean gameOver = winner != null || game.isDraw();
        String winnerText = winner == null ? "" : textForPlayer(winner);
        String instructions = getInstructions(game, winner);
        return new GameState(cells, instructions, textForPlayer(game.getPlayer()), winnerText, gameOver);
    }

    public Cell[] getCells() {
        return this.cells;
    }

    /**
     * toString() of GameState will return the string representing
     * the GameState in JSON format.
     */
    @Override
    public String toString() {
        return """
                {
                    "cells": %s,
                    "instructions": "%s",
                    "nextPlayer": "%s",
                    "winner": "%s",
                    "gameOver": %b
                }
                """.formatted(
                Arrays.toString(this.cells),
                escapeJson(this.instructions),
                this.nextPlayer,
                this.winner,
                this.gameOver);
    }

    private static Cell[] getCells(Game game) {
        Cell cells[] = new Cell[9];
        Board board = game.getBoard();
        boolean gameOver = game.getWinner() != null || game.isDraw();
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                String text = "";
                boolean playable = false;
                Player player = board.getCell(x, y);
                if (player == Player.PLAYER0)
                    text = "X";
                else if (player == Player.PLAYER1)
                    text = "O";
                else if (player == null && !gameOver) {
                    playable = true;
                }
                cells[3 * y + x] = new Cell(x, y, text, playable);
            }
        }
        return cells;
    }

    private static String getInstructions(Game game, Player winner) {
        if (winner != null)
            return textForPlayer(winner) + " wins!";
        if (game.isDraw())
            return "Draw game!";
        return textForPlayer(game.getPlayer()) + "'s turn";
    }

    private static String textForPlayer(Player player) {
        if (player == Player.PLAYER0)
            return "X";
        return "O";
    }

    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

class Cell {
    private final int x;
    private final int y;
    private final String text;
    private final boolean playable;

    Cell(int x, int y, String text, boolean playable) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.playable = playable;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getText() {
        return this.text;
    }

    public boolean isPlayable() {
        return this.playable;
    }

    @Override
    public String toString() {
        return """
                {
                    "text": "%s",
                    "playable": %b,
                    "x": %d,
                    "y": %d
                }
                """.formatted(this.text, this.playable, this.x, this.y);
    }
}

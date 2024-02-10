package org.vtorbin.util;

import org.vtorbin.exception.PuzzleNotFoundException;

public class Parser {
    private static final int FEN_POSITION = 1;
    private static final int MOVES_POSITION = 2;
    private final String line;

    public Parser (String line) {
        if (line == null) {
            throw new PuzzleNotFoundException("No puzzle found to parse");
        }

        this.line = line;
    }

    public String parseFen() {
        return line.split(",")[FEN_POSITION];
    }

    public String parseColor() {
        return parseFen().split(" ")[1].equals("w") ? "white" : "black";
    }

    public String parseSolution() {
        return line.split(",")[MOVES_POSITION];
    }
}

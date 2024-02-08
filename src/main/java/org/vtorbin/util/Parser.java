package org.vtorbin.util;

public class Parser {
    private static final int FEN_POSITION = 0;
    private static final int MOVES_POSITION = 1;
    private static final int URL_POSITION = 4;
    public String parseFen(String line) {
        return line.split(",")[FEN_POSITION];
    }

    public String parseColor(String line) {
        return parseFen(line).split(" ")[1] .equals("w") ? "White" : "Black";
    }

    public String parseSolution(String line) {
        return line.split(",")[MOVES_POSITION].substring(4);
    }
}

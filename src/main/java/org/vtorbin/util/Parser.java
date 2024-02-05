package org.vtorbin.util;

public class Parser {
    private static final int FEN_POSITION = 1;
    private static final int MOVES_POSITION = 2;
    private static final int URL_POSITION = 8;
    public String parseFen(String line) {
        return line.split(",")[FEN_POSITION];
    }

    public String parseColor(String line) {
        return Integer.parseInt(line.split(",")[URL_POSITION]
                .split("#")[1].substring(5, 7)) % 2 == 1 ? "White to move" : "Black to move";
    }

    public String parseSolution(String line) {
        return line.split(",")[MOVES_POSITION].substring(4);
    }
}

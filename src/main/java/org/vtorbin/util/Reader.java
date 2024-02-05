package org.vtorbin.util;

import java.io.*;

public class Reader {
    private static final String path = "resources/lichess_db_puzzle.csv";
    private static final int RATING_POS = 3;
    private static final int DEVIATION_POS = 4;

    public String findByDifficulty(int elo) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] tokens = line.split(",");
                int rating = Integer.parseInt(tokens[RATING_POS]);
                int deviation = Integer.parseInt(tokens[DEVIATION_POS]);
                if (rating - deviation <= elo && elo <= rating + deviation) {
                    return line;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found on path" + path);
        } catch (IOException e) {
            System.out.println("Error while reading from file" + e);
        }
        return null;
    }
}

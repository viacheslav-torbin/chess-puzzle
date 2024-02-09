package org.vtorbin.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reader {
    private static final String path = "src/main/resources/lichess_db_puzzle.csv";
    private static final int RATING_POS = 3;
    private static final int DEVIATION_POS = 4;
    //TODO: Use a DB xD
    public String findByDifficulty(int elo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<String> puzzles = new ArrayList<>();
            String line = reader.readLine();
            for (line = reader.readLine(); line != null; line = reader.readLine()) {
                String[] tokens = line.split(",");
                int rating = Integer.parseInt(tokens[RATING_POS]);
                int deviation = Integer.parseInt(tokens[DEVIATION_POS]);
                if (rating - deviation <= elo && elo <= rating + deviation) {
                    puzzles.add(tokens[0]);
                }
            }
            if (puzzles.isEmpty()) {
                return null;
            } else if (puzzles.size() == 1) {
                return puzzles.get(0);
            } else {
                Random random = new Random();
                return findById(random.nextInt(puzzles.size()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found on path" + path);
        } catch (IOException e) {
            System.out.println("Error while reading from file" + e);
        }
        return null;
    }

    private String findById(int id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.startsWith(String.valueOf(id))) {
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

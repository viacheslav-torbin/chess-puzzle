package org.vtorbin.service;

import org.vtorbin.model.Puzzle;

public interface PuzzleService {
    Puzzle add(Puzzle puzzle);
    Puzzle getById(int id);
    Puzzle getRandom(int elo);
    void delete(int id);

}

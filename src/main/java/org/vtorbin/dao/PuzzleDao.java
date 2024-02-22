package org.vtorbin.dao;

import org.vtorbin.model.Puzzle;

import java.util.Optional;

public interface PuzzleDao {
    Puzzle add(Puzzle puzzle);
    Optional<Puzzle> getById(int id);
    Optional<Puzzle> getRandom(int elo);
    void delete(Puzzle puzzle);

}

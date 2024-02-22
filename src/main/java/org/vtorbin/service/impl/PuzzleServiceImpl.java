package org.vtorbin.service.impl;

import org.vtorbin.dao.PuzzleDao;
import org.vtorbin.dao.impl.PuzzleDaoImpl;
import org.vtorbin.exception.PuzzleNotFoundException;
import org.vtorbin.model.Puzzle;
import org.vtorbin.service.PuzzleService;

public class PuzzleServiceImpl implements PuzzleService {
    private final PuzzleDao puzzleDao = new PuzzleDaoImpl();
    @Override
    public Puzzle add(Puzzle puzzle) {
        return puzzleDao.add(puzzle);
    }

    @Override
    public Puzzle getById(int id) {
        return puzzleDao.getById(id)
                .orElseThrow(() ->
                        new PuzzleNotFoundException("Puzzle with id " + id + " don't exist"));
    }

    @Override
    public Puzzle getRandom(int elo) {
        return puzzleDao.getRandom(elo)
                .orElseThrow(() ->
                        new PuzzleNotFoundException("Puzzle with elo " + elo + " don't exist"));
    }

    @Override
    public void delete(int id) {
        Puzzle puzzle = puzzleDao.getById(id)
                .orElseThrow(() ->
                        new PuzzleNotFoundException("Puzzle already deleted/doesn't exist"));
        puzzleDao.delete(puzzle);
    }
}

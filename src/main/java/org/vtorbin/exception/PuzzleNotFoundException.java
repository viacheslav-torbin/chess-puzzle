package org.vtorbin.exception;

public class PuzzleNotFoundException extends RuntimeException {
    public PuzzleNotFoundException(String message) {
        super(message);
    }
}

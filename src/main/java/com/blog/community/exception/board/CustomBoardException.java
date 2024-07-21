package com.blog.community.exception.board;

import org.springframework.http.HttpStatus;

public class CustomBoardException extends RuntimeException{
    private final BoardException boardException;

    public CustomBoardException(BoardException boardException) {
        super(boardException.getMessage());
        this.boardException = boardException;
    }

    public CustomBoardException(BoardException boardException, String message) {
        super(boardException.getMessage() + " - " + message);
        this.boardException = boardException;
    }

    public HttpStatus getStatus() {
        return boardException.getStatus();
    }

    public String getMessage() {
        return boardException.getMessage();
    }
}

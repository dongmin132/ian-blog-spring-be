package com.blog.community.exception.file;

import com.blog.community.exception.board.CustomBoardException;
import org.springframework.http.HttpStatus;

public class CustomFileException extends RuntimeException {
    private final FileException fileException;

    public CustomFileException(FileException fileException, String message) {
        super(fileException.getMessage() + " - " + message);
        this.fileException = fileException;
    }

    public CustomFileException(FileException fileException) {
        super(fileException.getMessage());
        this.fileException = fileException;
    }

    public HttpStatus getStatus() {
        return fileException.getStatus();
    }

    public String getMessage() {
        return fileException.getMessage();
    }
}

package com.blog.community.exception.comment;


import org.springframework.http.HttpStatus;

public class CustomCommentException extends RuntimeException {
    private final CommentException commentException;
    public CustomCommentException(CommentException commentException) {
        super(commentException.getMessage());
        this.commentException = commentException;
    }
    public CustomCommentException(CommentException commentException, String message) {
        super(commentException.getMessage() + " - " + message);
        this.commentException = commentException;
    }

    public HttpStatus getStatus() {
        return commentException.getStatus();
    }

    public String getMessage() {
        return commentException.getMessage();
    }
}

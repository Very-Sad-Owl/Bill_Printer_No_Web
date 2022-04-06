package ru.clevertec.tasks.olga.exception.repoexc;

public class WritingException extends RepositoryException {
    public WritingException() {
        super();
    }

    public WritingException(String message) {
        super(message);
    }

    public WritingException(String message, Throwable cause) {
        super(message, cause);
    }

    public WritingException(Throwable cause) {
        super(cause);
    }
}

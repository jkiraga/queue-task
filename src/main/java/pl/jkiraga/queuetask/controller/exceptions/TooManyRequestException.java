package pl.jkiraga.queuetask.controller.exceptions;

public class TooManyRequestException extends RuntimeException {

    public TooManyRequestException(String message) {
        super(message);
    }
}

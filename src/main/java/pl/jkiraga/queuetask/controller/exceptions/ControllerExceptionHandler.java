package pl.jkiraga.queuetask.controller.exceptions;

import org.openapitools.model.DisplayableErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice(annotations = RestController.class)
public class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    protected DisplayableErrorDTO handleIllegalArgumentException(Exception exception) {
        return new DisplayableErrorDTO()
                .message(exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(TooManyRequestException.class)
    protected DisplayableErrorDTO handleUnavailableException(Exception exception) {
        return new DisplayableErrorDTO()
                .message(exception.getMessage());
    }
}

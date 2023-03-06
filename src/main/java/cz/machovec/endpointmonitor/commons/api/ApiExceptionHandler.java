package cz.machovec.endpointmonitor.commons.api;

import cz.machovec.endpointmonitor.commons.ErrorTo;
import cz.machovec.endpointmonitor.exceptions.InvalidUsernameOrPasswordException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static cz.machovec.endpointmonitor.commons.api.HttpResponses.unauthorized;
import static cz.machovec.endpointmonitor.commons.api.HttpResponses.badRequest;

@ControllerAdvice(basePackages =  {"cz.machovec.endpointmonitor.monitoring", "cz.machovec.endpointmonitor.security"})
public class ApiExceptionHandler {

    private final Log logger = LogFactory.getLog(getClass());
    
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public ResponseEntity<ErrorTo> handleInvalidEmailOrPasswordException(InvalidUsernameOrPasswordException exc) {
        logger.warn(exc.getMessage());
        return unauthorized(exc.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        // Extract the validation errors from the exception
        BindingResult result = ex.getBindingResult();

        // Create a list of error messages
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : result.getFieldErrors()) {
            errorMessages.add(error.getDefaultMessage());
        }

        // Return an error response with the list of error messages
        return badRequest(errorMessages);
    }

}

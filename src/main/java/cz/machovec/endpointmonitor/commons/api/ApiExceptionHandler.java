package cz.machovec.endpointmonitor.commons.api;

import cz.machovec.endpointmonitor.commons.ErrorTo;
import cz.machovec.endpointmonitor.exceptions.InvalidUsernameOrPasswordException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static cz.machovec.endpointmonitor.commons.api.HttpResponses.unauthorized;

@ControllerAdvice(basePackages =  {"cz.machovec.endpointmonitor.monitoring", "cz.machovec.endpointmonitor.security"})
public class ApiExceptionHandler {

    private final Log logger = LogFactory.getLog(getClass());
    
    @ExceptionHandler(InvalidUsernameOrPasswordException.class)
    public ResponseEntity<ErrorTo> handleInvalidEmailOrPasswordException(InvalidUsernameOrPasswordException exc) {
        logger.warn(exc.getMessage());
        return unauthorized(exc.getMessage());
    }
}

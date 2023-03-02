package cz.machovec.endpointmonitor.exceptions;

import static cz.machovec.endpointmonitor.commons.MessageKeys.EXCEPTION_LOGIN_INVALID_USERNAME_OR_PASSWORD;

public class InvalidUsernameOrPasswordException extends RuntimeException {

    public InvalidUsernameOrPasswordException() {
        super(EXCEPTION_LOGIN_INVALID_USERNAME_OR_PASSWORD);
    }

    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }

}

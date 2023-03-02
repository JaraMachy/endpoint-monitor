package cz.machovec.endpointmonitor.commons.api;

import cz.machovec.endpointmonitor.commons.ErrorTo;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.*;

public class HttpResponses {

    public static <T> ResponseEntity<T> ok() { return new ResponseEntity<>(OK); }

    public static <T> ResponseEntity<T> ok(T to) { return new ResponseEntity<>(to, OK); }

    public static <T> ResponseEntity<T> created() { return new ResponseEntity<>(CREATED); }

    public static <T> ResponseEntity<T> unauthorized() { return new ResponseEntity<>(UNAUTHORIZED); }

    public static ResponseEntity<ErrorTo> unauthorized(String message) {
        ErrorTo to = new ErrorTo(UNAUTHORIZED, message);
        return new ResponseEntity<>(to, UNAUTHORIZED);
    }

    public static <T> ResponseEntity<T> forbidden() {
        return new ResponseEntity<>(FORBIDDEN);
    }

    public static <T> ResponseEntity<T> badRequest() { return new ResponseEntity<>(BAD_REQUEST);
    }

}

package cz.machovec.endpointmonitor.commons.api;

import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.*;

public class HttpResponses {

    public static <T> ResponseEntity<T> ok() { return new ResponseEntity<>(OK); }

    public static <T> ResponseEntity<T> ok(T to) {
        return new ResponseEntity<>(to, OK);
    }

    public static <T> ResponseEntity<T> created() { return new ResponseEntity<>(CREATED); }

    public static <T> ResponseEntity<T> unauthorized() { return new ResponseEntity<>(UNAUTHORIZED); }

    public static <T> ResponseEntity<T> forbidden() {
        return new ResponseEntity<>(FORBIDDEN);
    }

    public static <T> ResponseEntity<T> badRequest() { return new ResponseEntity<>(BAD_REQUEST);
    }

}

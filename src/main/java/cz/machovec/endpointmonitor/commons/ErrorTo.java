package cz.machovec.endpointmonitor.commons;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.Date;

import static cz.machovec.endpointmonitor.commons.MessageKeys.ERRORTO_HTTP_STATUS_NULL;

public class ErrorTo {

    public Date timestamp;
    public int status;
    public String error;
    public String message;

    public ErrorTo(HttpStatus httpStatus, String message) {
        Assert.notNull(httpStatus, ERRORTO_HTTP_STATUS_NULL);

        this.timestamp = new Date();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
    }
}

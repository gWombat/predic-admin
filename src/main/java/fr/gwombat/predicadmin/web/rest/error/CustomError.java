package fr.gwombat.predicadmin.web.rest.error;

import org.springframework.http.HttpStatus;

public class CustomError {

    private HttpStatus status;
    private String     message;

    public CustomError(final HttpStatus status, final String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

package fr.gwombat.predicadmin.exception;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by gWombat.
 *
 * @since 02/04/2017
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4378970760572617910L;

    private String requestedId;
    private String uri;

    public ResourceNotFoundException(final String requestedId, final String uri) {
        this.requestedId = requestedId;
        this.uri = uri;
    }

    @Override
    public String getMessage() {
        final String superMessage = super.getMessage();
        final StringBuilder stringBuilder = new StringBuilder();
        if(!StringUtils.isBlank(superMessage)) {
            stringBuilder.append(superMessage);
            stringBuilder.append(": ");
        }
        stringBuilder.append(String.format("The resource with id [%s] for the path [%s] does not exists", requestedId, uri));
        return stringBuilder.toString();
    }

    public void setRequestedId(String requestedId) {
        this.requestedId = requestedId;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}

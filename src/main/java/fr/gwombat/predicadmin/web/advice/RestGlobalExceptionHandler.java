package fr.gwombat.predicadmin.web.advice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.gwombat.predicadmin.exception.upload.UploadDataException;
import fr.gwombat.predicadmin.web.rest.error.CustomError;

@ControllerAdvice(basePackages = "fr.gwombat.predicadmin.web.rest")
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestGlobalExceptionHandler.class);
    
    private MessageSource messageSource;

    @ResponseBody
    @ExceptionHandler(UploadDataException.class)
    public ResponseEntity<CustomError> handleException(final HttpServletRequest request, final UploadDataException e) {
        final String message = messageSource.getMessage(e.getMessageCode(), e.getMessageArguments(), LocaleContextHolder.getLocale());
        final HttpStatus status = getStatus(request);
        final CustomError error = new CustomError(status, message);
        error.setId(e.getId());

        logger.info("[{}] An error occured when trying to read file: {}", e.getId(), e.getMessage());
        
        return new ResponseEntity<CustomError>(error, status);
    }

    private static HttpStatus getStatus(HttpServletRequest request) {
        final Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null)
            return HttpStatus.BAD_REQUEST;
        return HttpStatus.valueOf(statusCode);
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}

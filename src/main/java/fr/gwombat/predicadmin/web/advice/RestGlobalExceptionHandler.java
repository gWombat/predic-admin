package fr.gwombat.predicadmin.web.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "fr.gwombat.predicadmin.web.rest")
public class RestGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    public ResponseEntity<?> handleException(){
        return null;
    }
    
}

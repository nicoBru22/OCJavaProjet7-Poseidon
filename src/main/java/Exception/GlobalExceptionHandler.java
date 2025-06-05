package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Erreur interne : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("Erreur interne : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
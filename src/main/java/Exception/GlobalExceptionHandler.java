package Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Gestionnaire global des exceptions pour toute l'application Spring Boot.
 * 
 * Cette classe intercepte les exceptions non gérées et renvoie une réponse HTTP appropriée.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Gère toutes les exceptions génériques de type {@link Exception}.
     * 
     * @param ex l'exception levée
     * @return une réponse HTTP avec un message d'erreur et le statut 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Erreur interne : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Gère les exceptions de type {@link RuntimeException}.
     * 
     * @param ex l'exception levée
     * @return une réponse HTTP avec un message d'erreur et le statut 500 (Internal Server Error)
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleGenericRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>("Erreur interne : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

package Exception;

/**
 * Exception levée lorsqu'un utilisateur existe déjà dans le système.
 * 
 * Cette exception étend {@link RuntimeException} et permet de signaler
 * un conflit lors de la création ou l'ajout d'un utilisateur déjà existant.
 */
public class UserExistingException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    /**
     * Constructeur avec un message d'erreur personnalisé.
     * 
     * @param message le message décrivant la cause de l'exception
     */
    public UserExistingException(String message) {
        super(message);
    }

}

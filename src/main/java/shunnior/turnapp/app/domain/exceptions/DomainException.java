package shunnior.turnapp.app.domain.exceptions;

public abstract class DomainException extends RuntimeException {
    
    protected DomainException(String message) {
        super(message);
    }
    
    public abstract int getHttpStatusCode();
}
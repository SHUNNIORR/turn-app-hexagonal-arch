package shunnior.turnapp.app.domain.exceptions;

public class UserAlreadyExistsException extends DomainException {
    public UserAlreadyExistsException(String email) {
        super("El usuario con email " + email + " ya existe");
    }

    @Override
    public int getHttpStatusCode() {
        return 409;
    }
}
package shunnior.turnapp.app.domain.exceptions;

public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException() {
        super("Email o contraseña inválidos");
    }

    @Override
    public int getHttpStatusCode() {
        return 401;
    }
}
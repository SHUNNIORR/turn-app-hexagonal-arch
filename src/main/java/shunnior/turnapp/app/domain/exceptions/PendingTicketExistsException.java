package shunnior.turnapp.app.domain.exceptions;

public class PendingTicketExistsException extends DomainException {
    public PendingTicketExistsException() {
        super("Ya existe un ticket pendiente creado por este usuario.");
    }

    @Override
    public int getHttpStatusCode() {
        return 409;
    }
}
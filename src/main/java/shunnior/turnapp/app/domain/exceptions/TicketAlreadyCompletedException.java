package shunnior.turnapp.app.domain.exceptions;

public class TicketAlreadyCompletedException extends DomainException {
    public TicketAlreadyCompletedException(Integer ticketId) {
        super("El ticket con id: " + ticketId + " ya está finalizado.");
    }

    @Override
    public int getHttpStatusCode() {
        return 400;
    }
}
package shunnior.turnapp.app.domain.exceptions;

public class TicketNotFoundException extends DomainException {
    public TicketNotFoundException(Integer ticketId) {
        super("Ticket con ID " + ticketId + " no encontrado");
    }

    @Override
    public int getHttpStatusCode() {
        return 404;
    }
}
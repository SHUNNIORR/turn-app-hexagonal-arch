package shunnior.turnapp.app.domain.exceptions;

public class TicketAlreadyCompletedException extends RuntimeException {
    public TicketAlreadyCompletedException(Integer ticketId) {
        super("El ticket con id: " + ticketId + " ya está finalizado.");
    }
}


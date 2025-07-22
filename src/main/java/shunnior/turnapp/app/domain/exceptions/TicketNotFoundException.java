package shunnior.turnapp.app.domain.exceptions;

import org.springframework.http.HttpStatus;
import shunnior.turnapp.app.infraestructure.globalExceptionHandler.ApiException;

public class TicketNotFoundException extends ApiException {
    public TicketNotFoundException(Integer ticketId) {
        super("Ticket con ID " + ticketId + " no encontrado", HttpStatus.NOT_FOUND);
    }
}

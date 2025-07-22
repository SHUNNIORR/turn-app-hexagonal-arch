package shunnior.turnapp.app.domain.exceptions;

import org.springframework.http.HttpStatus;
import shunnior.turnapp.app.infraestructure.globalExceptionHandler.ApiException;

public class PendingTicketExistsException extends ApiException {
    public PendingTicketExistsException() {
        super("Ya existe un ticket pendiente creado por este usuario.", HttpStatus.CONFLICT);
    }
}

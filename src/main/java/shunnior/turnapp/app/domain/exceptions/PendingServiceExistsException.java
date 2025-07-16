package shunnior.turnapp.app.domain.exceptions;

import org.springframework.http.HttpStatus;
import shunnior.turnapp.app.infraestructure.globalExceptionHandler.ApiException;

public class PendingServiceExistsException extends ApiException {
    public PendingServiceExistsException() {
        super("Ya existe un servicio pendiente creado por este usuario.", HttpStatus.CONFLICT);
    }
}

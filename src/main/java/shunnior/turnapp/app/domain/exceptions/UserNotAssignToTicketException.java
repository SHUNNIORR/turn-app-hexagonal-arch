package shunnior.turnapp.app.domain.exceptions;

import org.springframework.http.HttpStatus;
import shunnior.turnapp.app.infraestructure.globalExceptionHandler.ApiException;

public class UserNotAssignToTicketException extends ApiException {
    public UserNotAssignToTicketException(String userEmail) {
        super("El usuario: "+ userEmail +" no está autorizado para finalizar el ticket", HttpStatus.FORBIDDEN);
    }
}
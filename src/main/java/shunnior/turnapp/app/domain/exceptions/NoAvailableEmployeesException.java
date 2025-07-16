package shunnior.turnapp.app.domain.exceptions;

import org.springframework.http.HttpStatus;
import shunnior.turnapp.app.infraestructure.globalExceptionHandler.ApiException;

public class NoAvailableEmployeesException extends ApiException {
    public NoAvailableEmployeesException() {
        super("No hay empleados disponibles actualmente", HttpStatus.NOT_FOUND);
    }
}

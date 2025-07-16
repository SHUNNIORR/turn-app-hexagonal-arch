package shunnior.turnapp.app.domain.exceptions;

import org.springframework.http.HttpStatus;
import shunnior.turnapp.app.infraestructure.globalExceptionHandler.ApiException;

public class ServiceNotFoundException extends ApiException {
    public ServiceNotFoundException(Integer serviceId) {
        super("Servicio con ID " + serviceId + " no encontrado", HttpStatus.NOT_FOUND);
    }
}

package shunnior.turnapp.app.domain.exceptions;

public class ServiceAlreadyCompletedException extends RuntimeException {
    public ServiceAlreadyCompletedException(Integer serviceId) {
        super("El servicio con ID " + serviceId + " ya está finalizado.");
    }
}


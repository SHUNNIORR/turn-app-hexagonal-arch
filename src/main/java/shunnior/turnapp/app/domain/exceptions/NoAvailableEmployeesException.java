package shunnior.turnapp.app.domain.exceptions;

public class NoAvailableEmployeesException extends DomainException {
    public NoAvailableEmployeesException() {
        super("No hay empleados disponibles actualmente");
    }

    @Override
    public int getHttpStatusCode() {
        return 404;
    }
}
package shunnior.turnapp.app.domain.exceptions;

public class UserNotAssignToTicketException extends DomainException {
    public UserNotAssignToTicketException(String userEmail) {
        super("El usuario: "+ userEmail +" no está autorizado para finalizar el ticket");
    }

    @Override
    public int getHttpStatusCode() {
        return 403;
    }
}
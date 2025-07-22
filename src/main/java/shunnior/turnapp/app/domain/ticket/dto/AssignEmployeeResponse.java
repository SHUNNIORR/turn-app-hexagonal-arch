package shunnior.turnapp.app.domain.ticket.dto;

public record AssignEmployeeResponse(
        String message,
        String employee,
        Integer ticketId
) {
}

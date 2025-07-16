package shunnior.turnapp.app.domain.services.dto;

public record AssignEmployeeResponse(
        String message,
        String employee,
        Integer serviceId
) {
}

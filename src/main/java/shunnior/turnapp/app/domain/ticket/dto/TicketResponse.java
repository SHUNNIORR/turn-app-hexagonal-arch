package shunnior.turnapp.app.domain.ticket.dto;

public record TicketResponse(
        Integer id,
        String description,
        String createdByEmail,
        String status
) {}

package shunnior.turnapp.app.domain.services.dto;

public record ServiceResponse(
        Integer id,
        String description,
        String createdByEmail,
        String status
) {}

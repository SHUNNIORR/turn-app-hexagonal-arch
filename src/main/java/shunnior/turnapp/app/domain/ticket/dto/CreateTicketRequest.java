package shunnior.turnapp.app.domain.ticket.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTicketRequest(
        @NotBlank(message = "La descripción es obligatoria")
        @Size(min = 3, max = 500, message = "La descripción debe tener entre 3 y 500 caracteres")
        String description
) {
}
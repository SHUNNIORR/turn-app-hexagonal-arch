package shunnior.turnapp.app.infraestructure.in.web.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email debe tener un formato válido")
        String email,
        
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {
}
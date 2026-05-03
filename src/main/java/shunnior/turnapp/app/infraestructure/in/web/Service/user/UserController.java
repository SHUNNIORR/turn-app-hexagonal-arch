package shunnior.turnapp.app.infraestructure.in.web.Service.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.in.UserUseCase;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class UserController {

    private final UserUseCase userUseCase;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar todos los usuarios",
            description = "Retorna todos los usuarios (paginado). Requiere rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuarios"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado")
            }
    )
    public List<UserResponse> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.debug("Fetching users page={}, size={}", page, size);
        return userUseCase.getAllUsersPaged(page, size).stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar usuarios disponibles",
            description = "Retorna usuarios disponibles (paginado). Requiere rol ADMIN",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuarios disponibles"),
                    @ApiResponse(responseCode = "403", description = "Acceso denegado")
            }
    )
    public List<UserResponse> getAvailableUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.debug("Fetching available users page={}, size={}", page, size);
        return userUseCase.findAvailableEmployeesPaged(page, size).stream()
                .map(this::toResponse)
                .toList();
    }

    private UserResponse toResponse(UserH user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isBusy()
        );
    }

    public record UserResponse(
            @Schema(example = "1") Integer id,
            @Schema(example = "jorge admin") String name,
            @Schema(example = "admin@email.com") String email,
            @Schema(example = "false") boolean isBusy
    ) {}
}
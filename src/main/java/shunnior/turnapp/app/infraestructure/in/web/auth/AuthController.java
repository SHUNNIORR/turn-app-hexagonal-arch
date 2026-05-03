package shunnior.turnapp.app.infraestructure.in.web.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shunnior.turnapp.app.domain.auth.dto.AuthenticateCommand;
import shunnior.turnapp.app.domain.auth.dto.AuthTokenResponse;
import shunnior.turnapp.app.domain.auth.dto.RegisterUserCommand;
import shunnior.turnapp.app.domain.auth.in.AuthUseCase;
import shunnior.turnapp.app.infraestructure.in.web.auth.dto.AuthRequest;
import shunnior.turnapp.app.infraestructure.in.web.auth.dto.RegisterRequest;
import shunnior.turnapp.app.infraestructure.in.web.auth.dto.TokenResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Autenticación", description = "Endpoints para registro y autenticación de usuarios")
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    @Operation(
            summary = "Registrar nuevo usuario",
            description = "Registra un nuevo usuario en el sistema",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente",
                            content = @Content(schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Error de validación en los datos ingresados"),
                    @ApiResponse(responseCode = "409", description = "El usuario ya existe")
            }
    )
    public ResponseEntity<TokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.debug("Register request received for: {}", request.email());
        AuthTokenResponse response = authUseCase.register(
                new RegisterUserCommand(request.name(), request.email(), request.password()),
                false
        );
        return ResponseEntity.ok(toTokenResponse(response));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Autentica un usuario y retorna tokens JWT",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticación exitosa",
                            content = @Content(schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas"),
                    @ApiResponse(responseCode = "400", description = "Error de validación")
            }
    )
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody AuthRequest request) {
        log.debug("Login request received for: {}", request.email());
        AuthTokenResponse response = authUseCase.authenticate(
                new AuthenticateCommand(request.email(), request.password())
        );
        return ResponseEntity.ok(toTokenResponse(response));
    }

    @PostMapping("/refresh")
    @Operation(
            summary = "Refrescar token",
            description = "Refresca el token de acceso usando el refresh token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token refrescado exitosamente",
                            content = @Content(schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Token inválido o expirado")
            }
    )
    public ResponseEntity<TokenResponse> refresh(@RequestHeader("Authorization") String authorization) {
        AuthTokenResponse response = authUseCase.refreshToken(authorization);
        return ResponseEntity.ok(toTokenResponse(response));
    }

    private TokenResponse toTokenResponse(AuthTokenResponse authResponse) {
        return new TokenResponse(
                authResponse.accessToken(),
                authResponse.refreshToken(),
                authResponse.userName(),
                authResponse.email()
        );
    }
}
package shunnior.turnapp.app.application.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shunnior.turnapp.app.domain.auth.dto.AuthenticateCommand;
import shunnior.turnapp.app.domain.auth.dto.AuthTokenResponse;
import shunnior.turnapp.app.domain.auth.dto.RegisterUserCommand;
import shunnior.turnapp.app.domain.auth.in.AuthUseCase;
import shunnior.turnapp.app.domain.auth.in.JwtPort;
import shunnior.turnapp.app.domain.auth.out.PasswordEncoderPort;
import shunnior.turnapp.app.domain.auth.out.TokenRepositoryPort;
import shunnior.turnapp.app.domain.exceptions.InvalidCredentialsException;
import shunnior.turnapp.app.domain.exceptions.UserAlreadyExistsException;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.in.UserUseCase;
import shunnior.turnapp.app.domain.user.enums.Role;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthUseCaseImpl implements AuthUseCase {

    private final UserUseCase userUseCase;
    private final JwtPort jwtPort;
    private final TokenRepositoryPort tokenRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public AuthTokenResponse register(RegisterUserCommand command, boolean isAdmin) {
        log.info("Intentando registrar usuario: {}", command.email());
        
        if (userUseCase.findByEmail(command.email()).isPresent()) {
            log.warn("Usuario ya existe: {}", command.email());
            throw new UserAlreadyExistsException(command.email());
        }

        final UserH user = UserH.builder()
                .name(command.name())
                .email(command.email())
                .roles(Set.of(isAdmin ? Role.ROLE_ADMIN : Role.ROLE_USER))
                .password(passwordEncoderPort.encode(command.password()))
                .build();

        final UserH savedUser = userUseCase.saveUser(user);
        final String jwtToken = jwtPort.generateToken(savedUser);
        final String refreshToken = jwtPort.generateRefreshToken(savedUser);

        tokenRepositoryPort.saveToken(savedUser.getId(), jwtToken);
        log.info("Usuario registrado exitosamente: {}", savedUser.getEmail());
        return new AuthTokenResponse(jwtToken, refreshToken, savedUser.getName(), savedUser.getEmail());
    }

    @Override
    public AuthTokenResponse authenticate(AuthenticateCommand command) {
        log.info("Intento de autenticación para: {}", command.email());
        
        final UserH user = userUseCase.findByEmail(command.email())
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado: {}", command.email());
                    return new InvalidCredentialsException();
                });

        if (!passwordEncoderPort.matches(command.password(), user.getPassword())) {
            log.warn("Credenciales inválidas para: {}", command.email());
            throw new InvalidCredentialsException();
        }

        final String accessToken = jwtPort.generateToken(user);
        final String refreshToken = jwtPort.generateRefreshToken(user);

        tokenRepositoryPort.revokeAllUserTokens(user.getId());
        tokenRepositoryPort.saveToken(user.getId(), accessToken);
        
        log.info("Usuario autenticado exitosamente: {}", user.getEmail());
        return new AuthTokenResponse(accessToken, refreshToken, user.getName(), user.getEmail());
    }

    @Override
    public AuthTokenResponse refreshToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid auth header");
        }
        final String refreshToken = bearerToken.substring(7);

        final String userEmail = jwtPort.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new IllegalArgumentException("Token inválido");
        }
        final UserH user = userUseCase.findByEmail(userEmail).orElseThrow();

        final boolean isTokenValid = jwtPort.isTokenValid(refreshToken, user);
        if (!isTokenValid) {
            throw new IllegalArgumentException("Token expirado");
        }

        final String accessToken = jwtPort.generateToken(user);
        tokenRepositoryPort.revokeAllUserTokens(user.getId());
        tokenRepositoryPort.saveToken(user.getId(), accessToken);

        return new AuthTokenResponse(accessToken, refreshToken, user.getName(), user.getEmail());
    }
}
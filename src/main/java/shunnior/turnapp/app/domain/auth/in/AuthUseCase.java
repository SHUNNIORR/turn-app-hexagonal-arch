package shunnior.turnapp.app.domain.auth.in;

import shunnior.turnapp.app.domain.auth.dto.AuthenticateCommand;
import shunnior.turnapp.app.domain.auth.dto.AuthTokenResponse;
import shunnior.turnapp.app.domain.auth.dto.RegisterUserCommand;

public interface AuthUseCase {
    AuthTokenResponse register(RegisterUserCommand command, boolean isAdmin);
    AuthTokenResponse authenticate(AuthenticateCommand command);
    AuthTokenResponse refreshToken(String bearerToken);
}
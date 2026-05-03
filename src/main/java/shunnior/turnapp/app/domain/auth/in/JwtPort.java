package shunnior.turnapp.app.domain.auth.in;

import shunnior.turnapp.app.domain.user.UserH;

public interface JwtPort {
    String extractUsername(String token);
    String generateToken(UserH user);
    String generateRefreshToken(UserH user);
    boolean isTokenValid(String token, UserH user);
}
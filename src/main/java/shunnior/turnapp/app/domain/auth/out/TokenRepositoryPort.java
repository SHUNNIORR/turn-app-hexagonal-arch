package shunnior.turnapp.app.domain.auth.out;

public interface TokenRepositoryPort {
    void saveToken(Integer userId, String token);
    boolean isTokenValid(String token);
    void revokeAllUserTokens(Integer userId);
    void revokeToken(String token);
}
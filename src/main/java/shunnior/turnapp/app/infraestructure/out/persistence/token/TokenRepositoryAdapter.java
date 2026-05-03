package shunnior.turnapp.app.infraestructure.out.persistence.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shunnior.turnapp.app.domain.auth.out.TokenRepositoryPort;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserJpaRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenRepositoryAdapter implements TokenRepositoryPort {

    private final TokenJpaRepository tokenJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public void saveToken(Integer userId, String token) {
        UserEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        TokenEntity tokenEntity = TokenEntity.builder()
                .user(user)
                .token(token)
                .tokenType(TokenEntity.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenJpaRepository.save(tokenEntity);
    }

    @Override
    public boolean isTokenValid(String token) {
        return tokenJpaRepository.findByToken(token)
                .map(t -> !t.getIsExpired() && !t.getIsRevoked())
                .orElse(false);
    }

    @Override
    public void revokeAllUserTokens(Integer userId) {
        List<TokenEntity> validUserTokens = tokenJpaRepository.findAllValidTokenByUser(userId);
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setIsExpired(true);
                token.setIsRevoked(true);
            });
            tokenJpaRepository.saveAll(validUserTokens);
        }
    }

    @Override
    public void revokeToken(String token) {
        tokenJpaRepository.findByToken(token).ifPresent(t -> {
            t.setIsExpired(true);
            t.setIsRevoked(true);
            tokenJpaRepository.save(t);
        });
    }
}
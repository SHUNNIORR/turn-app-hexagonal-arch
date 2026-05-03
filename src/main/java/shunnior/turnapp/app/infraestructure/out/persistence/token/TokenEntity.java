package shunnior.turnapp.app.infraestructure.out.persistence.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TokenEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TokenType tokenType = TokenType.BEARER;

    @Column(nullable = false)
    private Boolean isRevoked;

    @Column(nullable = false)
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public enum TokenType {
        BEARER
    }
}
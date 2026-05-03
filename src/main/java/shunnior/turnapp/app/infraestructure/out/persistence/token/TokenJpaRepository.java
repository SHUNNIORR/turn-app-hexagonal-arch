package shunnior.turnapp.app.infraestructure.out.persistence.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, Integer> {

    @Query(value = """
            select t from TokenEntity t inner join UserEntity u
            on t.user.id = u.id
            where u.id = :id and (t.isExpired = false or t.isRevoked = false)
            """)
    List<TokenEntity> findAllValidTokenByUser(Integer id);

    Optional<TokenEntity> findByToken(String token);
}
package shunnior.turnapp.app.infraestructure.out.persistance.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u FROM UserEntity u JOIN u.roles r WHERE r = 'ROLE_USER' AND u.isBusy = false")
    List<UserEntity> findAvailableEmployees();
}

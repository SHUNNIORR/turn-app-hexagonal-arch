package shunnior.turnapp.app.domain.user.out;

import shunnior.turnapp.app.domain.user.UserH;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    Optional<UserH> findByEmail(String email);
    List<UserH> getAllUsers();
    List<UserH> findAvailableEmployees();
    UserH saveUser(UserH user);
    Optional<UserH> findById(Integer userId);
}

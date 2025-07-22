package shunnior.turnapp.app.domain.user.in;


import shunnior.turnapp.app.domain.user.UserH;

import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    Optional<UserH> findByEmail(String email);

    List<UserH> getAllUsers();

    List<UserH> findAvailableEmployees();

    UserH saveUser(UserH user);

    Optional<UserH> findById(Integer userId);
}

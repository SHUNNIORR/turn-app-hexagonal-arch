package shunnior.turnapp.app.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.in.UserUseCase;
import shunnior.turnapp.app.domain.user.out.UserRepositoryPort;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public Optional<UserH> findByEmail(String email) {
        return userRepositoryPort.findByEmail(email);
    }

    @Override
    public List<UserH> getAllUsers() {
        return userRepositoryPort.getAllUsers();
    }

    @Override
    public List<UserH> findAvailableEmployees() {
        return userRepositoryPort.findAvailableEmployees();
    }

    @Override
    public UserH saveUser(UserH user) {
        return userRepositoryPort.saveUser(user);
    }

    @Override
    public Optional<UserH> findById(Integer userId) {
        return userRepositoryPort.findById(userId);
    }

    @Override
    public List<UserH> getAllUsersPaged(int page, int size) {
        return userRepositoryPort.getAllUsersPaged(page, size);
    }

    @Override
    public List<UserH> findAvailableEmployeesPaged(int page, int size) {
        return userRepositoryPort.findAvailableEmployeesPaged(page, size);
    }
}
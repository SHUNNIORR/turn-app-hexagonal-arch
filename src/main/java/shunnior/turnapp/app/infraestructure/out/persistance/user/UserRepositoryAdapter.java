package shunnior.turnapp.app.infraestructure.out.persistance.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.out.UserRepositoryPort;
import shunnior.turnapp.app.infraestructure.out.persistance.user.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;

    @Override
    public Optional<UserH> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public List<UserH> getAllUsers() {
        return jpaRepository.findAll().stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public List<UserH> findAvailableEmployees() {
        return jpaRepository.findAvailableEmployees().stream()
                .map(UserMapper::toDomain)
                .toList();
    }

    @Override
    public UserH saveUser(UserH user) {
        UserEntity entity = UserMapper.toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public Optional<UserH> findById(Integer userId) {
        return jpaRepository.findById(userId).map(UserMapper::toDomain);
    }
}

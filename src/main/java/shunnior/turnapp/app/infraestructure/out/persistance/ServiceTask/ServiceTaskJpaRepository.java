package shunnior.turnapp.app.infraestructure.out.persistance.ServiceTask;

import org.springframework.data.jpa.repository.JpaRepository;
import shunnior.turnapp.app.domain.services.enums.ServiceStatus;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

public interface ServiceTaskJpaRepository extends JpaRepository<ServiceTaskEntity, Integer> {
    boolean existsByCreatedByAndStatus(UserEntity createdBy, ServiceStatus status);
}

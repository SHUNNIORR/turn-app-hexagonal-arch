package shunnior.turnapp.app.domain.services.out;

import shunnior.turnapp.app.domain.services.ServiceTask;

import java.util.Optional;

public interface ServiceTaskRepositoryPort {
    ServiceTask save(ServiceTask serviceTask);
    boolean existsByCreatedByAndStatus(Integer userId, String status);
    Optional<ServiceTask> findById(Integer id);
}

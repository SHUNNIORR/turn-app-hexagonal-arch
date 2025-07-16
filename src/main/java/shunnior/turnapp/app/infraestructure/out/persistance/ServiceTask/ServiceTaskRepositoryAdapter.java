package shunnior.turnapp.app.infraestructure.out.persistance.ServiceTask;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shunnior.turnapp.app.domain.services.ServiceTask;
import shunnior.turnapp.app.domain.services.enums.ServiceStatus;
import shunnior.turnapp.app.domain.services.out.ServiceTaskRepositoryPort;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.infraestructure.out.persistance.ServiceTask.mapper.ServiceTaskMapper;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ServiceTaskRepositoryAdapter implements ServiceTaskRepositoryPort {

    private final ServiceTaskJpaRepository jpaRepository;
    private final UserJpaRepository userJpaRepository;
    @Override
    public ServiceTask save(ServiceTask serviceTask) {
        var createdBy = userJpaRepository.findById(serviceTask.getCreatedByUserId()).orElseThrow();
        var assignedTo = serviceTask.getAssignedToUserId() != null
                ? userJpaRepository.findById(serviceTask.getAssignedToUserId()).orElse(null)
                : null;

        var saved = jpaRepository.save(ServiceTaskMapper.toEntity(serviceTask, createdBy, assignedTo));
        return ServiceTaskMapper.toDomain(saved);
    }

    @Override
    public boolean existsByCreatedByAndStatus(Integer userId, String status) {
        var user = userJpaRepository.findById(userId).orElseThrow();
        return jpaRepository.existsByCreatedByAndStatus(user, ServiceStatus.valueOf(status));
    }

    @Override
    public Optional<ServiceTask> findById(Integer id) {
        return jpaRepository.findById(id).map(ServiceTaskMapper::toDomain);
    }

}

package shunnior.turnapp.app.infraestructure.out.persistance.ServiceHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shunnior.turnapp.app.domain.history.ServiceHistory;
import shunnior.turnapp.app.domain.history.out.ServiceHistoryRepositoryPort;
import shunnior.turnapp.app.infraestructure.out.persistance.ServiceHistory.mapper.ServiceHistoryMapper;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserJpaRepository;
import shunnior.turnapp.app.infraestructure.out.persistance.user.mapper.UserMapper;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ServiceHistoryAdapter implements ServiceHistoryRepositoryPort {
    private final ServiceHistoryRepository serviceHistoryRepository;
    private final UserJpaRepository UserJpaRepository;

    @Override
    public ServiceHistory save(ServiceHistory serviceHistory) {

        UserEntity createdBy = UserJpaRepository.findById(serviceHistory.getService().getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UserEntity assignTo = UserJpaRepository.findById(serviceHistory.getService().getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));;

        ServiceHistoryEntity entityServiceHistory = ServiceHistoryMapper.toEntity(serviceHistory, createdBy, assignTo);

        ServiceHistoryEntity serviceHistoryEntity = new ServiceHistoryEntity();
        serviceHistoryEntity.setService(entityServiceHistory.getService());
        serviceHistoryEntity.setEmployee(UserMapper.toEntity(serviceHistory.getEmployee()));
        serviceHistoryEntity.setAssignedAt(serviceHistory.getAssignedAt());

        ServiceHistoryEntity saved = serviceHistoryRepository.save(serviceHistoryEntity);

        ServiceHistory serviceHistoryDomain = ServiceHistoryMapper.toDomain(saved);
        return  new ServiceHistory(
                saved.getId(),
                serviceHistoryDomain.getService(),
                UserMapper.toDomain(saved.getEmployee()),
                saved.getAssignedAt()
        );
    }
}

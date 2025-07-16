package shunnior.turnapp.app.infraestructure.out.persistance.ServiceHistory.mapper;

import shunnior.turnapp.app.domain.history.ServiceHistory;
import shunnior.turnapp.app.infraestructure.out.persistance.ServiceHistory.ServiceHistoryEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.ServiceTask.ServiceTaskEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.ServiceTask.mapper.ServiceTaskMapper;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.mapper.UserMapper;

public class ServiceHistoryMapper {

    public static ServiceHistoryEntity toEntity(
            ServiceHistory domain,
            UserEntity createdBy,
            UserEntity assignedTo
    ) {
        ServiceTaskEntity serviceEntity = ServiceTaskMapper.toEntity(domain.getService(), createdBy, assignedTo);

        return ServiceHistoryEntity.builder()
                .id(domain.getId())
                .service(serviceEntity)
                .employee(assignedTo)
                .assignedAt(domain.getAssignedAt())
                .build();
    }

    public static ServiceHistory toDomain(ServiceHistoryEntity entity) {
        return new ServiceHistory(
                entity.getId(),
                ServiceTaskMapper.toDomain(entity.getService()),
                UserMapper.toDomain(entity.getEmployee()),
                entity.getAssignedAt()
        );
    }
}

package shunnior.turnapp.app.infraestructure.out.persistance.ServiceTask.mapper;

import shunnior.turnapp.app.domain.services.ServiceTask;
import shunnior.turnapp.app.domain.services.enums.ServiceStatus;
import shunnior.turnapp.app.infraestructure.out.persistance.ServiceTask.ServiceTaskEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

public class ServiceTaskMapper {
    public static ServiceTaskEntity toEntity(ServiceTask domain, UserEntity createdBy, UserEntity assignedTo) {
        return ServiceTaskEntity.builder()
                .id(domain.getId())
                .description(domain.getDescription())
                .createdBy(createdBy)
                .assignedTo(assignedTo)
                .status(ServiceStatus.valueOf(domain.getStatus()))
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public static ServiceTask toDomain(ServiceTaskEntity entity) {
        return ServiceTask.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .createdByUserId(entity.getCreatedBy().getId())
                .assignedToUserId(entity.getAssignedTo() != null ? entity.getAssignedTo().getId() : null)
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
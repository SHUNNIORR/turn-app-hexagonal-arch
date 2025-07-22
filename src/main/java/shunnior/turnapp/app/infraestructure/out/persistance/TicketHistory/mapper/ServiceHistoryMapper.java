package shunnior.turnapp.app.infraestructure.out.persistance.TicketHistory.mapper;

import shunnior.turnapp.app.domain.history.TicketHistory;
import shunnior.turnapp.app.infraestructure.out.persistance.TicketHistory.TicketHistoryEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.ticket.TicketEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.ticket.mapper.TicketMapper;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.mapper.UserMapper;

public class ServiceHistoryMapper {

    public static TicketHistoryEntity toEntity(
            TicketHistory domain,
            UserEntity createdBy,
            UserEntity assignedTo
    ) {
        TicketEntity serviceEntity = TicketMapper.toEntity(domain.getTicket(), createdBy, assignedTo);

        return TicketHistoryEntity.builder()
                .id(domain.getId())
                .ticket(serviceEntity)
                .employee(assignedTo)
                .assignedAt(domain.getAssignedAt())
                .build();
    }

    public static TicketHistory toDomain(TicketHistoryEntity entity) {
        return new TicketHistory(
                entity.getId(),
                TicketMapper.toDomain(entity.getTicket()),
                UserMapper.toDomain(entity.getEmployee()),
                entity.getAssignedAt()
        );
    }
}

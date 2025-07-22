package shunnior.turnapp.app.infraestructure.out.persistance.ticket.mapper;

import shunnior.turnapp.app.domain.ticket.Ticket;
import shunnior.turnapp.app.domain.ticket.enums.TicketStatus;
import shunnior.turnapp.app.infraestructure.out.persistance.ticket.TicketEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

public class TicketMapper {
    public static TicketEntity toEntity(Ticket domain, UserEntity createdBy, UserEntity assignedTo) {
        return TicketEntity.builder()
                .id(domain.getId())
                .description(domain.getDescription())
                .createdBy(createdBy)
                .assignedTo(assignedTo)
                .status(TicketStatus.valueOf(domain.getStatus()))
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public static Ticket toDomain(TicketEntity entity) {
        return Ticket.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .createdByUserId(entity.getCreatedBy().getId())
                .assignedToUserId(entity.getAssignedTo() != null ? entity.getAssignedTo().getId() : null)
                .status(entity.getStatus().name())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
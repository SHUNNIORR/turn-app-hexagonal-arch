package shunnior.turnapp.app.infraestructure.out.persistance.user.mapper;

import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.infraestructure.out.persistance.ticket.TicketEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

import java.util.List;

public class UserMapper {

    public static UserEntity toEntity(UserH domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .roles(domain.getRoles()) // ya es Set<Role>
                .isBusy(domain.isBusy())
                .build();
    }

    public static UserH toDomain(UserEntity entity) {
        return UserH.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(entity.getRoles()) // también es Set<Role>
                .isBusy(entity.isBusy())
                .assignedTicketIds(
                        entity.getAssignedTickets() != null
                                ? entity.getAssignedTickets().stream()
                                .map(TicketEntity::getId)
                                .toList()
                                : List.of()
                )
                .build();
    }
}


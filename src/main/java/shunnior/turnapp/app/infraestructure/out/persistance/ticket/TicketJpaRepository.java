package shunnior.turnapp.app.infraestructure.out.persistance.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import shunnior.turnapp.app.domain.ticket.enums.TicketStatus;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

import java.util.List;

public interface TicketJpaRepository extends JpaRepository<TicketEntity, Integer> {
    boolean existsByCreatedByAndStatus(UserEntity createdBy, TicketStatus status);
    List<TicketEntity> findByAssignedToIsNull();
}

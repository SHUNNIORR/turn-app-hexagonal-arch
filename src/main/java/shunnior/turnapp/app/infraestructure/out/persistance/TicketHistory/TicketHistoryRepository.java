package shunnior.turnapp.app.infraestructure.out.persistance.TicketHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketHistoryRepository extends JpaRepository<TicketHistoryEntity, Integer> {
}


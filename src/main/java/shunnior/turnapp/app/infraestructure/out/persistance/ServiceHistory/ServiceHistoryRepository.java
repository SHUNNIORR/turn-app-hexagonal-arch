package shunnior.turnapp.app.infraestructure.out.persistance.ServiceHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceHistoryRepository extends JpaRepository<ServiceHistoryEntity, Integer> {
}


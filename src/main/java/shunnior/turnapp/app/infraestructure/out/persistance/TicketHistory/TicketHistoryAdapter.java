package shunnior.turnapp.app.infraestructure.out.persistance.TicketHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shunnior.turnapp.app.domain.history.TicketHistory;
import shunnior.turnapp.app.domain.history.out.TicketHistoryRepositoryPort;
import shunnior.turnapp.app.infraestructure.out.persistance.TicketHistory.mapper.ServiceHistoryMapper;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserJpaRepository;
import shunnior.turnapp.app.infraestructure.out.persistance.user.mapper.UserMapper;

@Repository
@RequiredArgsConstructor
public class TicketHistoryAdapter implements TicketHistoryRepositoryPort {
    private final TicketHistoryRepository ticketHistoryRepository;
    private final UserJpaRepository UserJpaRepository;

    @Override
    public TicketHistory save(TicketHistory ticketHistory) {

        UserEntity createdBy = UserJpaRepository.findById(ticketHistory.getTicket().getCreatedByUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        UserEntity assignTo = UserJpaRepository.findById(ticketHistory.getTicket().getAssignedToUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));;

        TicketHistoryEntity entityServiceHistory = ServiceHistoryMapper.toEntity(ticketHistory, createdBy, assignTo);

        TicketHistoryEntity ticketHistoryEntity = new TicketHistoryEntity();
        ticketHistoryEntity.setTicket(entityServiceHistory.getTicket());
        ticketHistoryEntity.setEmployee(UserMapper.toEntity(ticketHistory.getEmployee()));
        ticketHistoryEntity.setAssignedAt(ticketHistory.getAssignedAt());

        TicketHistoryEntity saved = ticketHistoryRepository.save(ticketHistoryEntity);

        TicketHistory ticketHistoryDomain = ServiceHistoryMapper.toDomain(saved);
        return  new TicketHistory(
                saved.getId(),
                ticketHistoryDomain.getTicket(),
                UserMapper.toDomain(saved.getEmployee()),
                saved.getAssignedAt()
        );
    }
}

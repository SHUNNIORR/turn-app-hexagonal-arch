package shunnior.turnapp.app.infraestructure.out.persistance.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shunnior.turnapp.app.domain.ticket.Ticket;
import shunnior.turnapp.app.domain.ticket.enums.TicketStatus;
import shunnior.turnapp.app.domain.ticket.out.TicketRepositoryPort;
import shunnior.turnapp.app.infraestructure.out.persistance.ticket.mapper.TicketMapper;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserJpaRepository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TicketRepositoryAdapter implements TicketRepositoryPort {

    private final TicketJpaRepository jpaRepository;
    private final UserJpaRepository userJpaRepository;
    @Override
    public Ticket save(Ticket ticket) {
        var createdBy = userJpaRepository.findById(ticket.getCreatedByUserId()).orElseThrow();
        var assignedTo = ticket.getAssignedToUserId() != null
                ? userJpaRepository.findById(ticket.getAssignedToUserId()).orElse(null)
                : null;

        var saved = jpaRepository.save(TicketMapper.toEntity(ticket, createdBy, assignedTo));
        return TicketMapper.toDomain(saved);
    }

    @Override
    public boolean existsByCreatedByAndStatus(Integer userId, String status) {
        var user = userJpaRepository.findById(userId).orElseThrow();
        return jpaRepository.existsByCreatedByAndStatus(user, TicketStatus.valueOf(status));
    }

    @Override
    public Optional<Ticket> findById(Integer id) {
        return jpaRepository.findById(id).map(TicketMapper::toDomain);
    }

}

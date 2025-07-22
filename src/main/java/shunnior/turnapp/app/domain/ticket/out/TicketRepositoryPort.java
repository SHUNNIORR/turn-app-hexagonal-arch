package shunnior.turnapp.app.domain.ticket.out;

import shunnior.turnapp.app.domain.ticket.Ticket;

import java.util.Optional;

public interface TicketRepositoryPort {
    Ticket save(Ticket ticket);
    boolean existsByCreatedByAndStatus(Integer userId, String status);
    Optional<Ticket> findById(Integer id);
}

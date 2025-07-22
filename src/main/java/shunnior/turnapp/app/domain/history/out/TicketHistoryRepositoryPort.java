package shunnior.turnapp.app.domain.history.out;

import shunnior.turnapp.app.domain.history.TicketHistory;

public interface TicketHistoryRepositoryPort {
    TicketHistory save(TicketHistory ticketHistory);
}

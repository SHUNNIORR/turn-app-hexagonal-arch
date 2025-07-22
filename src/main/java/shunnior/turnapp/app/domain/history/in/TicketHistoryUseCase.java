package shunnior.turnapp.app.domain.history.in;

import shunnior.turnapp.app.domain.history.TicketHistory;

public interface TicketHistoryUseCase {
    TicketHistory registerHistory(TicketHistory ticketHistory);
}

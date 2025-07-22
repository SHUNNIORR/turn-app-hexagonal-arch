package shunnior.turnapp.app.application.TicketHistory;

import org.springframework.stereotype.Service;
import shunnior.turnapp.app.domain.history.TicketHistory;
import shunnior.turnapp.app.domain.history.in.TicketHistoryUseCase;
import shunnior.turnapp.app.domain.history.out.TicketHistoryRepositoryPort;
@Service
public class TicketHistoryService implements TicketHistoryUseCase {
    private final TicketHistoryRepositoryPort ticketHistoryRepositoryPort;

    public TicketHistoryService(TicketHistoryRepositoryPort ticketHistoryRepositoryPort) {
        this.ticketHistoryRepositoryPort = ticketHistoryRepositoryPort;
    }

    @Override
    public TicketHistory registerHistory(TicketHistory ticketHistory) {
        return ticketHistoryRepositoryPort.save(ticketHistory);
    }
}

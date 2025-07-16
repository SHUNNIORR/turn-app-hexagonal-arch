package shunnior.turnapp.app.domain.history.in;

import shunnior.turnapp.app.domain.history.ServiceHistory;

public interface ServiceHistoryUseCase {
    ServiceHistory registerHistory(ServiceHistory serviceHistory);
}

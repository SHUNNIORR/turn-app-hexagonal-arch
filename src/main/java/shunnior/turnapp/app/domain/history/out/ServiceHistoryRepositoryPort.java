package shunnior.turnapp.app.domain.history.out;

import shunnior.turnapp.app.domain.history.ServiceHistory;

public interface ServiceHistoryRepositoryPort {
    ServiceHistory save(ServiceHistory serviceHistory);
}

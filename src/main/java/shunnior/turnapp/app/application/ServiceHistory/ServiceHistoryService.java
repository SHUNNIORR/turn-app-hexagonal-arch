package shunnior.turnapp.app.application.ServiceHistory;

import org.springframework.stereotype.Service;
import shunnior.turnapp.app.domain.history.ServiceHistory;
import shunnior.turnapp.app.domain.history.in.ServiceHistoryUseCase;
import shunnior.turnapp.app.domain.history.out.ServiceHistoryRepositoryPort;
@Service
public class ServiceHistoryService implements ServiceHistoryUseCase {
    private final ServiceHistoryRepositoryPort serviceHistoryRepositoryPort;

    public ServiceHistoryService(ServiceHistoryRepositoryPort serviceHistoryRepositoryPort) {
        this.serviceHistoryRepositoryPort = serviceHistoryRepositoryPort;
    }

    @Override
    public ServiceHistory registerHistory(ServiceHistory serviceHistory) {
        return serviceHistoryRepositoryPort.save(serviceHistory);
    }
}

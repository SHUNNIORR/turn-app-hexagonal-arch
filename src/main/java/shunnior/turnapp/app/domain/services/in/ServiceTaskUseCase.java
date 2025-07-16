package shunnior.turnapp.app.domain.services.in;

import shunnior.turnapp.app.domain.services.ServiceTask;
import shunnior.turnapp.app.domain.services.dto.AssignEmployeeResponse;
import shunnior.turnapp.app.domain.services.dto.CreateServiceRequest;

public interface ServiceTaskUseCase {
    ServiceTask createService(CreateServiceRequest serviceRequest, Integer createdBy);
    AssignEmployeeResponse assignRandomEmployee(Integer serviceId);
    String closeService(Integer serviceId, Integer loggedUserId, String loggedEmail);
}

package shunnior.turnapp.app.application.ServiceTask;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shunnior.turnapp.app.domain.exceptions.*;
import shunnior.turnapp.app.domain.history.ServiceHistory;
import shunnior.turnapp.app.domain.history.in.ServiceHistoryUseCase;
import shunnior.turnapp.app.domain.services.ServiceTask;
import shunnior.turnapp.app.domain.services.dto.AssignEmployeeResponse;
import shunnior.turnapp.app.domain.services.dto.CreateServiceRequest;
import shunnior.turnapp.app.domain.services.enums.ServiceStatus;
import shunnior.turnapp.app.domain.services.in.ServiceTaskUseCase;
import shunnior.turnapp.app.domain.services.out.ServiceTaskRepositoryPort;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.in.UserUseCase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ServiceTaskUseCaseImpl implements ServiceTaskUseCase {

    private final ServiceTaskRepositoryPort serviceTaskRepository;
    private final UserUseCase userUseCase;
    private final ServiceHistoryUseCase serviceHistoryUseCase;

    @Override
    public ServiceTask createService(CreateServiceRequest serviceRequest, Integer createdBy) {

        boolean existsPending = serviceTaskRepository.existsByCreatedByAndStatus(
                createdBy, ServiceStatus.PENDING.name()
        );

        if (existsPending) {
            throw new PendingServiceExistsException();
        }

        ServiceTask newService = ServiceTask.builder()
                .description(serviceRequest.description())
                .createdAt(LocalDateTime.now())
                .status(ServiceStatus.PENDING.name())
                .createdByUserId(createdBy)
                .assignedToUserId(null)
                .build();

        return serviceTaskRepository.save(newService);
    }

    @Override
    @Transactional
    public AssignEmployeeResponse assignRandomEmployee(Integer serviceId) {
        List<UserH> availableUsers = userUseCase.findAvailableEmployees();
        if (availableUsers.isEmpty()) {
            throw new NoAvailableEmployeesException();
        }

        UserH selectedUser = availableUsers.get(new Random().nextInt(availableUsers.size()));
        selectedUser.setBusy(true);
        userUseCase.saveUser(selectedUser);

        ServiceTask task = serviceTaskRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceNotFoundException(serviceId));

        task.setAssignedToUserId(selectedUser.getId());
        serviceTaskRepository.save(task);

        serviceHistoryUseCase.registerHistory(new ServiceHistory(
                task.getId(), task, selectedUser, LocalDateTime.now()
        ));

        return new AssignEmployeeResponse(
                "Empleado asignado correctamente",
                selectedUser.getEmail(),
                task.getId()
        );
    }

    @Override
    @Transactional
    public String closeService(Integer serviceId, Integer loggedUserId, String loggedEmail) {
        ServiceTask task = serviceTaskRepository.findById(serviceId)
                .orElseThrow(() -> new ServiceNotFoundException(serviceId));

        if (task.getAssignedToUserId() == null || !task.getAssignedToUserId().equals(loggedUserId)) {
            throw new UserNotAssignToServiceException(loggedEmail);
        }

        if ("COMPLETED".equals(task.getStatus())) {
            throw new ServiceAlreadyCompletedException(serviceId);
        }

        task.setStatus("COMPLETED");
        serviceTaskRepository.save(task);

        UserH assignedUser = userUseCase.findById(loggedUserId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        assignedUser.setBusy(false);
        userUseCase.saveUser(assignedUser);

        return "Servicio finalizado con éxito.";
    }
}

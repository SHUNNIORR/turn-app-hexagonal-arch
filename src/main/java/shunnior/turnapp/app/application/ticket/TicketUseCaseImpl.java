package shunnior.turnapp.app.application.ticket;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shunnior.turnapp.app.domain.exceptions.*;
import shunnior.turnapp.app.domain.history.TicketHistory;
import shunnior.turnapp.app.domain.history.in.TicketHistoryUseCase;
import shunnior.turnapp.app.domain.ticket.Ticket;
import shunnior.turnapp.app.domain.ticket.dto.AssignEmployeeResponse;
import shunnior.turnapp.app.domain.ticket.dto.CreateTicketRequest;
import shunnior.turnapp.app.domain.ticket.enums.TicketStatus;
import shunnior.turnapp.app.domain.ticket.in.TicketUseCase;
import shunnior.turnapp.app.domain.ticket.out.TicketRepositoryPort;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.in.UserUseCase;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TicketUseCaseImpl implements TicketUseCase {

    private final TicketRepositoryPort serviceTaskRepository;
    private final UserUseCase userUseCase;
    private final TicketHistoryUseCase ticketHistoryUseCase;

    @Override
    public Ticket createTicket(CreateTicketRequest serviceRequest, Integer createdBy) {
        boolean existsPending = serviceTaskRepository.existsByCreatedByAndStatus(
                createdBy, TicketStatus.PENDING.name()
        );

        if(existsPending){
            throw new PendingTicketExistsException();
        }

        Ticket newService = Ticket.builder()
                .description(serviceRequest.description())
                .createdAt(LocalDateTime.now())
                .status(TicketStatus.PENDING.name())
                .createdByUserId(createdBy)
                .assignedToUserId(null)
                .build();

        return serviceTaskRepository.save(newService);
    }

    @Override
    @Transactional
    public AssignEmployeeResponse assignRandomEmployee(Integer ticketId) {
        log.info("Assigning random employee to ticket: {}", ticketId);
        List<UserH> availableUsers = userUseCase.findAvailableEmployees();
        if (availableUsers.isEmpty()) {
            log.warn("No available employees for ticket: {}", ticketId);
            throw new NoAvailableEmployeesException();
        }

        UserH selectedUser = availableUsers.get((int) (Math.random() * availableUsers.size()));
        selectedUser.setBusy(true);
        userUseCase.saveUser(selectedUser);

        Ticket task = serviceTaskRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        task.setAssignedToUserId(selectedUser.getId());
        task.setStatus(TicketStatus.ASSIGNED.name());
        serviceTaskRepository.save(task);

        ticketHistoryUseCase.registerHistory(new TicketHistory(
                task.getId(), task, selectedUser, LocalDateTime.now()
        ));

        log.info("Employee {} assigned to ticket {}", selectedUser.getEmail(), ticketId);
        return new AssignEmployeeResponse(
                "Empleado asignado correctamente",
                selectedUser.getEmail(),
                task.getId()
        );
    }

    @Override
    @Transactional
    public String closeTicket(Integer ticketId, Integer loggedUserId, String loggedEmail) {
        log.info("Closing ticket: {} by user: {}", ticketId, loggedEmail);
        Ticket task = serviceTaskRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));

        if (task.getAssignedToUserId() == null || !task.getAssignedToUserId().equals(loggedUserId)) {
            throw new UserNotAssignToTicketException(loggedEmail);
        }

        if ("COMPLETED".equals(task.getStatus())) {
            throw new TicketAlreadyCompletedException(ticketId);
        }

        task.setStatus("COMPLETED");
        serviceTaskRepository.save(task);

        UserH assignedUser = userUseCase.findById(loggedUserId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        assignedUser.setBusy(false);
        userUseCase.saveUser(assignedUser);

        log.info("Ticket {} closed successfully", ticketId);
        return "Servicio finalizado con éxito.";
    }

    @Override
    public List<Ticket> getUnassignedTickets() {
        return serviceTaskRepository.findByAssignedToIsNull();
    }

    @Override
    public List<Ticket> getUnassignedTicketsPaged(int page, int size) {
        log.debug("Fetching unassigned tickets page={}, size={}", page, size);
        return serviceTaskRepository.findByAssignedToIsNullPaged(page, size);
    }
}
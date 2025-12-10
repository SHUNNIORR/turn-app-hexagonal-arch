package shunnior.turnapp.app.domain.ticket.in;

import shunnior.turnapp.app.domain.ticket.Ticket;
import shunnior.turnapp.app.domain.ticket.dto.AssignEmployeeResponse;
import shunnior.turnapp.app.domain.ticket.dto.CreateTicketRequest;

import java.util.List;

public interface TicketUseCase {
    Ticket createTicket(CreateTicketRequest serviceRequest, Integer createdBy);
    AssignEmployeeResponse assignRandomEmployee(Integer ticketId);
    String closeTicket(Integer ticketId, Integer loggedUserId, String loggedEmail);
    List<Ticket> getUnassignedTickets();
}

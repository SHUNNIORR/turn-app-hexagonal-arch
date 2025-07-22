package shunnior.turnapp.app.infraestructure.in.web.Service.ServiceTask;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shunnior.turnapp.app.domain.ticket.Ticket;
import shunnior.turnapp.app.domain.ticket.dto.AssignEmployeeResponse;
import shunnior.turnapp.app.domain.ticket.dto.CreateTicketRequest;
import shunnior.turnapp.app.domain.ticket.dto.TicketResponse;
import shunnior.turnapp.app.domain.ticket.in.TicketUseCase;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

@RestController
@RequestMapping("/api/admin/ticket")
@RequiredArgsConstructor
public class TicketController {
    private final TicketUseCase ticketUseCase;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @RequestBody CreateTicketRequest request,
            @AuthenticationPrincipal UserEntity userEntity
    ) {

        Ticket created = ticketUseCase.createTicket(request, userEntity.getId());

        return ResponseEntity.ok(new TicketResponse(
                created.getId(),
                created.getDescription(),
                userEntity.getEmail(),
                created.getStatus()
        ));
    }

    @PostMapping("/assign/{ticketId}")
    public ResponseEntity<AssignEmployeeResponse> assignRandomEmployee(@PathVariable Integer ticketId) {
        AssignEmployeeResponse response = ticketUseCase.assignRandomEmployee(ticketId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/close/{ticketId}")
    public ResponseEntity<String> closeTicket(
            @PathVariable Integer ticketId,
            @AuthenticationPrincipal UserEntity userEntity
    ) {
        return ResponseEntity.ok(
                ticketUseCase.closeTicket(
                        ticketId,
                        userEntity.getId(),
                        userEntity.getEmail()
                )
        );
    }
}

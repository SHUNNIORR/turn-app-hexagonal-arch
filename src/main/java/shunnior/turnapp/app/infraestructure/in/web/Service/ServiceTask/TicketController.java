package shunnior.turnapp.app.infraestructure.in.web.Service.ServiceTask;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import shunnior.turnapp.app.domain.ticket.Ticket;
import shunnior.turnapp.app.domain.ticket.dto.AssignEmployeeResponse;
import shunnior.turnapp.app.domain.ticket.dto.CreateTicketRequest;
import shunnior.turnapp.app.domain.ticket.dto.TicketResponse;
import shunnior.turnapp.app.domain.ticket.in.TicketUseCase;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.in.UserUseCase;
import shunnior.turnapp.config.SecurityUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ticket")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tickets", description = "Gestión de tickets del salón")
public class TicketController {
    private final TicketUseCase ticketUseCase;
    private final UserUseCase userUseCase;

    @PostMapping
    @Operation(
            summary = "Crear ticket",
            description = "Crea un nuevo ticket (solo usuarios autenticados)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket creado exitosamente",
                            content = @Content(schema = @Schema(implementation = TicketResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Error de validación"),
                    @ApiResponse(responseCode = "409", description = "Ya existe un ticket pendiente para este usuario")
            }
    )
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody CreateTicketRequest request,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        Ticket created = ticketUseCase.createTicket(request, userDetails.getId());

        return ResponseEntity.ok(new TicketResponse(
                created.getId(),
                created.getDescription(),
                userDetails.getEmail(),
                null,
                created.getStatus()
        ));
    }

    @PostMapping("/assign/{ticketId}")
    @Operation(
            summary = "Asignar empleado",
            description = "Asigna un empleado aleatorio disponible al ticket",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Empleado asignado"),
                    @ApiResponse(responseCode = "404", description = "No hay empleados disponibles")
            }
    )
    public ResponseEntity<AssignEmployeeResponse> assignRandomEmployee(@PathVariable Integer ticketId) {
        log.info("Assigning random employee to ticket: {}", ticketId);
        AssignEmployeeResponse response = ticketUseCase.assignRandomEmployee(ticketId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/close/{ticketId}")
    @Operation(
            summary = "Cerrar ticket",
            description = "Cierra el ticket (solo el empleado asignado puede cerrarlo)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ticket cerrado"),
                    @ApiResponse(responseCode = "403", description = "No tienes permisos para cerrar este ticket"),
                    @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
            }
    )
    public ResponseEntity<String> closeTicket(
            @PathVariable Integer ticketId,
            @AuthenticationPrincipal SecurityUserDetails userDetails
    ) {
        return ResponseEntity.ok(
                ticketUseCase.closeTicket(
                        ticketId,
                        userDetails.getId(),
                        userDetails.getEmail()
                )
        );
    }

    @GetMapping("/unassigned")
    @Operation(
            summary = "Listar tickets sin asignar",
            description = "Retorna tickets sin empleado asignado (paginado)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de tickets")
            }
    )
    public ResponseEntity<List<TicketResponse>> getUnassignedTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        log.debug("Fetching unassigned tickets page={}, size={}", page, size);
        List<TicketResponse> unassignedTickets = ticketUseCase.getUnassignedTicketsPaged(page, size)
                .stream()
                .map(ticket -> {
                    String createdByEmail = userUseCase.findById(ticket.getCreatedByUserId())
                            .map(UserH::getEmail)
                            .orElse("");
                    String assignedToEmail = ticket.getAssignedToUserId() != null
                            ? userUseCase.findById(ticket.getAssignedToUserId())
                                    .map(UserH::getEmail)
                                    .orElse("")
                            : "";
                    return new TicketResponse(
                            ticket.getId(),
                            ticket.getDescription(),
                            createdByEmail,
                            assignedToEmail,
                            ticket.getStatus()
                    );
                })
                .toList();

        return ResponseEntity.ok(unassignedTickets);
    }
}
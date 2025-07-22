package shunnior.turnapp.app.infraestructure.out.persistance.TicketHistory;


import jakarta.persistence.*;
import lombok.*;
import shunnior.turnapp.app.infraestructure.out.persistance.ticket.TicketEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_history")
public class TicketHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketEntity ticket;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private UserEntity employee;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();
}


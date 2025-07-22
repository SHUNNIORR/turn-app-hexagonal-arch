package shunnior.turnapp.app.infraestructure.out.persistance.ticket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shunnior.turnapp.app.domain.ticket.enums.TicketStatus;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private UserEntity assignedTo;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.PENDING;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}

package shunnior.turnapp.app.infraestructure.out.persistance.ServiceHistory;


import jakarta.persistence.*;
import lombok.*;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.infraestructure.out.persistance.ServiceTask.ServiceTaskEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.UserEntity;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service_history")
public class ServiceHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceTaskEntity service;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private UserEntity employee;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime assignedAt = LocalDateTime.now();
}


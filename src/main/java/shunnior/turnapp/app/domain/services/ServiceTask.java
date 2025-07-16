package shunnior.turnapp.app.domain.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceTask {

    private Integer id;

    private String description;

    private Integer createdByUserId;
    private Integer assignedToUserId;

    private String status;
    private LocalDateTime createdAt;
}
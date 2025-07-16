package shunnior.turnapp.app.domain.history;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shunnior.turnapp.app.domain.services.ServiceTask;
import shunnior.turnapp.app.domain.user.UserH;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServiceHistory {
    private Integer id;

    private ServiceTask service;

    private UserH employee;

    private LocalDateTime assignedAt = LocalDateTime.now();
}

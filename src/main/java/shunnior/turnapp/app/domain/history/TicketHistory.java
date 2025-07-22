package shunnior.turnapp.app.domain.history;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shunnior.turnapp.app.domain.ticket.Ticket;
import shunnior.turnapp.app.domain.user.UserH;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketHistory {
    private Integer id;

    private Ticket ticket;

    private UserH employee;

    private LocalDateTime assignedAt = LocalDateTime.now();
}

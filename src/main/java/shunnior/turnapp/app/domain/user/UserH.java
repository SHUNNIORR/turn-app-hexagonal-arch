package shunnior.turnapp.app.domain.user;

import lombok.*;
import shunnior.turnapp.app.domain.user.enums.Role;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserH {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Set<Role> roles;
    private boolean isBusy;
    private List<Integer> assignedTicketIds;
}
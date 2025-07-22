package shunnior.turnapp.app.infraestructure.out.persistance.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shunnior.turnapp.app.infraestructure.out.persistance.ticket.TicketEntity;
import shunnior.turnapp.app.infraestructure.out.persistance.user.roles.Role;
import shunnior.turnapp.auth.repository.Token;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("Roles asignados en getAuthorities()xxxx: " + roles);
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Builder.Default
    @Column(nullable = false)
    private boolean isBusy = false;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.EAGER)
    private List<TicketEntity> assignedTickets;
}

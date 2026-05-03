package shunnior.turnapp.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import shunnior.turnapp.app.domain.user.UserH;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class SecurityUserDetails implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean isBusy;

    public SecurityUserDetails(UserH user) {
        this.id = user.getId();
        this.username = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
        this.isBusy = user.isBusy();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
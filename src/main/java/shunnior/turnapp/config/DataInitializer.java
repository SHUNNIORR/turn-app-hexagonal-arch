package shunnior.turnapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.in.UserUseCase;
import shunnior.turnapp.app.domain.user.enums.Role;

import java.util.Set;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserUseCase userUseCase;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_PASSWORD = "password123";

    @Override
    public void run(String... args) {
        initAdminUser();
        initStandardUser();
    }

    private void initAdminUser() {
        String email = "admin@email.com";
        if (userUseCase.findByEmail(email).isEmpty()) {
            UserH admin = UserH.builder()
                    .name("jorge admin")
                    .email(email)
                    .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .roles(Set.of(Role.ROLE_ADMIN))
                    .build();
            userUseCase.saveUser(admin);
            System.out.println("[DataInitializer] Admin user created: " + email);
        }
    }

    private void initStandardUser() {
        String email = "user@email.com";
        if (userUseCase.findByEmail(email).isEmpty()) {
            UserH user = UserH.builder()
                    .name("jorge user")
                    .email(email)
                    .password(passwordEncoder.encode(DEFAULT_PASSWORD))
                    .roles(Set.of(Role.ROLE_USER))
                    .build();
            userUseCase.saveUser(user);
            System.out.println("[DataInitializer] Standard user created: " + email);
        }
    }
}
package shunnior.turnapp.app.infraestructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shunnior.turnapp.app.domain.auth.out.PasswordEncoderPort;

@Component
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements PasswordEncoderPort {

    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
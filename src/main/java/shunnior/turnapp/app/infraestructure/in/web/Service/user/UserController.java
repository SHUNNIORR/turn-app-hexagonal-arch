package shunnior.turnapp.app.infraestructure.in.web.Service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import shunnior.turnapp.app.domain.user.UserH;
import shunnior.turnapp.app.domain.user.in.UserUseCase;
import shunnior.turnapp.auth.controller.RegisterRequest;
import shunnior.turnapp.auth.controller.TokenResponse;
import shunnior.turnapp.auth.service.AuthService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;
    private final UserUseCase userUseCase;

    @GetMapping
    public List<UserH> getAllUsers() {
        return userUseCase.getAllUsers();
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TokenResponse> registerAdmin(@RequestBody RegisterRequest request) {
        final TokenResponse response = authService.register(request,true);
        return ResponseEntity.ok(response);
    }
}
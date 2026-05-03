package shunnior.turnapp.app.domain.auth.dto;

public record RegisterUserCommand(String name, String email, String password) {}
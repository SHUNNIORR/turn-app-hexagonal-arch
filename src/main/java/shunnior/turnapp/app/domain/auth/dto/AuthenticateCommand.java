package shunnior.turnapp.app.domain.auth.dto;

public record AuthenticateCommand(String email, String password) {}
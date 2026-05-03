package shunnior.turnapp.app.domain.auth.dto;

public record AuthTokenResponse(String accessToken, String refreshToken, String userName, String email) {}
package com.lagoinha.connect.model.user;

public record RegisterDTO(String name, String login, String password, UserRole role) {
}

package com.lagoinha.connect.model.user;

public record UserResponseDTO(String id, String login, UserRole role) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getLogin(), user.getRole());
    }
}

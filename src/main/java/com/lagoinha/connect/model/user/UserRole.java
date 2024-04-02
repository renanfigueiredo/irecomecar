package com.lagoinha.connect.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user");

    private String role;
}

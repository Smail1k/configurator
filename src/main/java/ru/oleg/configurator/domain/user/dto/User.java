package ru.oleg.configurator.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String email;
    private String name;
    private String password;
    private String role;

}

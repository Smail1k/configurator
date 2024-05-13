package ru.oleg.configurator.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String username;
    private String fullName;
    private boolean autoIn;
    private String password;
    private boolean role;

}

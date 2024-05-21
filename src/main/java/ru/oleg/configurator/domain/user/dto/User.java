package ru.oleg.configurator.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String fullName;
    private String password;
    private boolean autoIn;
    private boolean role;

}

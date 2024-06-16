package ru.oleg.configurator.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOut {
    private long id;
    private String username;
    private String fullName;
    private String password;
    private boolean isAutoIn;
    private Role role;

}

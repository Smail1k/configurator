package ru.oleg.configurator.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private long id;
    private String username;
    private String fullName;
    private String password;
    private boolean isAutoIn;
    private Set<Role> role;

}

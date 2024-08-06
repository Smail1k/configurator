package ru.oleg.configurator.service.user.dto;

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
    private Set<Role> roles;

    public User(UserIn updateUserIn) {
        this.username = updateUserIn.username();
        this.fullName = updateUserIn.fullName();
        this.password = updateUserIn.password();
        this.isAutoIn = updateUserIn.isAutoIn();
        this.roles = updateUserIn.roles();
    }
}

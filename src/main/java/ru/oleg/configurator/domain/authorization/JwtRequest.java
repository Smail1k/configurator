package ru.oleg.configurator.domain.authorization;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {

    private String login;
    private String password;

}

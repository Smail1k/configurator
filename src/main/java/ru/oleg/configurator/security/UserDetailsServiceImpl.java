package ru.oleg.configurator.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.oleg.configurator.exception.NotFoundException;
import ru.oleg.configurator.service.user.UserService;
import ru.oleg.configurator.service.user.dto.User;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new NotFoundException("Пользователь не найден");
        }

        return new UserDetailsImpl(user);
    }
}
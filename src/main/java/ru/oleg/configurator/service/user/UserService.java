package ru.oleg.configurator.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.exception.ForbiddenException;
import ru.oleg.configurator.repositories.user.UserRepository;
import ru.oleg.configurator.security.JwtAuthentication;
import ru.oleg.configurator.service.user.dto.Role;
import ru.oleg.configurator.service.user.dto.UserIn;
import ru.oleg.configurator.service.user.dto.User;
import ru.oleg.configurator.service.utils.InteractionSystem;
import ru.oleg.configurator.service.utils.Parser;


import java.util.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Parser parser;
    private final InteractionSystem interactionSystem;

    public List<User> getAllUsers() {
//        String usersString = interactionSystem.executeCommand("getent passwd | awk -F: '$3 >= 1000 && $3 < 1100" +
//                " { print $1 }'");
//
//        if (usersString == null) {
//            return new ArrayList<>();
//        }

//        String[] usersArray = usersString.split("\n");
        List<User> users = new ArrayList<>();

//        for (String username: usersArray) {
//            users.add(getUserByUsername(username));
//        }
        for (int i = 0; i < 3; i++) {
            users.add(getUser("oleg"));
        }

        return users;
    }

    public User getUser(final String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("Oleg");
        user.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)));
        user.setId(1);
        user.setAutoIn(true);
        return user;
    }

    public User getUserByUsername(final String username) {
        boolean checkExistsUser = Boolean.parseBoolean(Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"", username))).trim());

        if (!checkExistsUser) {
            return null;
        }

        return userRepository.getUserByUsername(username);
    }

    public User updateUser(final UserIn userIn, final JwtAuthentication authentication) {
        checkAdminPrivileges(authentication);
        return userRepository.updateUser(userIn);
    }

    private void checkAdminPrivileges(final JwtAuthentication authentication) {
        if (authentication == null || !authentication.getAuthorities().contains(Role.ADMIN)) {
            throw new ForbiddenException("Недостаточно прав");
        }
    }

    public User createUser(final UserIn userIn) {
        final User user = new User(userIn);
        user.setId(userRepository.createUser(userIn));
        return user;
    }

    public void deleteUser(final long id, final JwtAuthentication authentication) {
        checkAdminPrivileges(authentication);
        userRepository.deleteUser(id);
    }

}

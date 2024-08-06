package ru.oleg.configurator.repositories.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.oleg.configurator.service.user.dto.Role;
import ru.oleg.configurator.service.user.dto.User;
import ru.oleg.configurator.service.user.dto.UserIn;
import ru.oleg.configurator.service.utils.InteractionSystem;
import ru.oleg.configurator.service.utils.Parser;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

@Component
@AllArgsConstructor
public class UserRepository {
    private final Parser parser;
    private final InteractionSystem interactionSystem;

    public User getUserByUsername(final String username) {
        User user = new User();
        user.setUsername(username);

        long id = Long.parseLong(Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("getent passwd %s | cut -d: -f5", username))).trim());
        user.setId(id);

        String fullName = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("getent passwd %s | cut -d: -f5", username))).trim();
        user.setFullName(parser.extractData(fullName, "^[\\p{L}0-9 ]+"));

        String role = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"", username))).trim();
        if (Boolean.parseBoolean(role)) {
            user.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.USER)));
        } else {
            user.setRoles(Collections.singleton(Role.USER));
        }

        String password = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("sudo grep '^%s:' /etc/shadow | awk -F: '{print $2}'",
                        username))).trim();
        user.setPassword(password.equals("*") ? null : password);

        String autoLogin = interactionSystem.executeCommand(
                String.format("grep -i 'AutomaticLogin' /etc/gdm3/custom.conf | grep -q 'AutomaticLogin=%s'" +
                        " && echo \"True\" || echo \"False\"", username).trim());
        user.setAutoIn(Boolean.parseBoolean(autoLogin));

        return user;
    }

    public User updateUser(final UserIn userIn) {
        final User user = getUserByUsername(userIn.username());

        interactionSystem.executeCommand(
                String.format("sudo chfn -f \"%s\" %s", userIn.fullName(), userIn.username()));
        user.setFullName(userIn.fullName());

        // TODO добавить изменение пароля

        String roleValueBoolean = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"",
                        userIn.username()))).trim();
        if (!Collections.singleton(Boolean.parseBoolean(roleValueBoolean)
                ? Role.ADMIN : Role.USER).equals(userIn.roles())){
            if (!userIn.roles().contains(Role.ADMIN)) {
                interactionSystem.executeCommand(String.format("sudo usermod -aG sudo %s", userIn.username()));
            } else {
                interactionSystem.executeCommand(String.format("sudo deluser %s sudo", userIn.username()));
            }
        }
        user.setRoles(userIn.roles());

        String autoLogin = interactionSystem.executeCommand(
                String.format("grep -i 'AutomaticLogin' /etc/gdm3/custom.conf | grep -q 'AutomaticLogin=%s'" +
                        " && echo \"True\" || echo \"False\"", userIn.username()).trim());
        if (Boolean.parseBoolean(autoLogin) != userIn.isAutoIn()) {
            interactionSystem.changeAutoLogin(userIn.username(), userIn.isAutoIn());
        }
        user.setAutoIn(userIn.isAutoIn());

        return user;
    }

    public long createUser(final UserIn userIn) {
        if (!userIn.password().isEmpty()) {
            interactionSystem.executeCommand(String.format(
                    "sudo adduser --gecos \"%s\" --disabled-password %s && echo \"%s:%s\" | sudo chpasswd",
                    userIn.fullName(), userIn.username(), userIn.username() , userIn.password()));
        } else {
            interactionSystem.executeCommand(String.format(
                    "sudo adduser --gecos \"%s\" --disabled-password %s",
                    userIn.fullName(), userIn.username()));
        }
        if (!userIn.roles().contains(Role.ADMIN)) {
            interactionSystem.executeCommand(String.format("sudo usermod -aG sudo %s", userIn.username()));
        }

        String id = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("getent passwd %s | awk -F: '{ print $3 }'", userIn.username()))).trim();
        return Long.parseLong(id);
    }

    public void deleteUser(final long id) {
        interactionSystem.executeCommand(
                String.format("sudo userdel -r $(getent passwd | awk -F: '$3 == %s { print $1 }')", id));
    }
}

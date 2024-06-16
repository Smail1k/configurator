package ru.oleg.configurator.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.user.dto.Role;
import ru.oleg.configurator.domain.user.dto.UserOut;
import ru.oleg.configurator.domain.utils.InteractionSystem;
import ru.oleg.configurator.domain.utils.Parser;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final Parser parser;
    private final InteractionSystem interactionSystem;

    public List<UserOut> getAllUsers() {
        String usersString;
        usersString = interactionSystem.executeCommand("getent passwd | awk -F: '$3 >= 1000 && $3 < 1100" +
                " { print $1, $3 }'");

        if (usersString == null) {
            return new ArrayList<>();
        }

        String[] usersArray = usersString.split("\n");
        List<UserOut> users = new ArrayList<>();

        for (String userline: usersArray) {
            users.add(getUserByUsername(userline.split(" ")[0]));
        }

        return users;
    }

    public UserOut getUserByUsername(String username) {
        boolean checkExistsUser = Boolean.parseBoolean(Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"", username))).trim());

        if (!checkExistsUser) {
            return null;
        }

        UserOut user = new UserOut();
        user.setUsername(username);

        long id = Long.parseLong(Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("getent passwd %s | cut -d: -f5", username))).trim());
        user.setId(id);

        String fullName = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("getent passwd %s | cut -d: -f5", username))).trim();
        user.setFullName(parser.extractData(fullName, "^[\\p{L}0-9 ]+"));

        String role = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"", username))).trim();
        user.setRole(Boolean.parseBoolean(role) ? Role.ADMIN: Role.USER);

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

    public UserOut updateMe(UserOut user) {
        return new UserOut();
    }

    public UserOut updateUser(long id, UserOut user) {
        interactionSystem.executeCommand(String.format("sudo chfn -f \"%s\" %s", user.getFullName(), user.getUsername()));

        String role = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"",
                        user.getUsername()))).trim();
        if (Role.parseValue(role) != user.getRole()){
            if (Role.ADMIN == user.getRole()) {
                interactionSystem.executeCommand(String.format("sudo usermod -aG sudo %s", user.getUsername()));
            } else {
                interactionSystem.executeCommand(String.format("sudo deluser %s sudo", user.getUsername()));
            }
        }

        String autoLogin = interactionSystem.executeCommand(
                String.format("grep -i 'AutomaticLogin' /etc/gdm3/custom.conf | grep -q 'AutomaticLogin=%s'" +
                        " && echo \"True\" || echo \"False\"", user.getUsername()).trim());
        if (Boolean.parseBoolean(autoLogin) != user.isAutoIn()) {
            interactionSystem.changeAutoLogin(user.getUsername(), user.isAutoIn());
        }

        return user;
    }

    public UserOut createUser(UserOut user) {
        if (!user.getPassword().isEmpty()) {
            interactionSystem.executeCommand(String.format(
                            "sudo adduser --gecos \"%s\" --disabled-password %s && echo \"%s:%s\" | sudo chpasswd",
                            user.getFullName(), user.getUsername(), user.getUsername() , user.getPassword()));
        } else {
            interactionSystem.executeCommand(String.format(
                    "sudo adduser --gecos \"%s\" --disabled-password %s",
                    user.getFullName(), user.getUsername()));
        }

        if (Role.ADMIN == user.getRole()) {
            interactionSystem.executeCommand(String.format("sudo usermod -aG sudo %s",user.getUsername()));
        }

        String id = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("getent passwd %s | awk -F: '{ print $3 }'", user.getUsername()))).trim();
        user.setId(Long.parseLong(id));

        return user;
    }

    public void deleteUserByUsername(Long id) {
        interactionSystem.executeCommand(String.format("sudo userdel -r $(getent passwd | awk -F: '$3 == %s { print $1 }')", id));

    }

}

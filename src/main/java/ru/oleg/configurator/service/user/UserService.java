package ru.oleg.configurator.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.service.user.dto.Role;
import ru.oleg.configurator.service.user.dto.User;
import ru.oleg.configurator.service.utils.InteractionSystem;
import ru.oleg.configurator.service.utils.Parser;


import java.util.*;

@Service
@AllArgsConstructor
public class UserService {
    private final Parser parser;
    private final InteractionSystem interactionSystem;

    public List<User> getAllUsers() {
        String usersString;
        usersString = interactionSystem.executeCommand("getent passwd | awk -F: '$3 >= 1000 && $3 < 1100" +
                " { print $1 }'");

        if (usersString == null) {
            return new ArrayList<>();
        }

        String[] usersArray = usersString.split("\n");
        List<User> users = new ArrayList<>();

        for (String username: usersArray) {
            users.add(getUserByUsername(username));
        }

        return users;
    }

    public User getUserByUsername(final String username) {
        boolean checkExistsUser = Boolean.parseBoolean(Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"", username))).trim());

        if (!checkExistsUser) {
            return null;
        }

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

    public User updateMe(User user) {
        return new User();
    }

    public User updateUser(long id, User user) {
        interactionSystem.executeCommand(String.format("sudo chfn -f \"%s\" %s", user.getFullName(), user.getUsername()));

        String roleValueBoolean = Objects.requireNonNull(interactionSystem.executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"",
                        user.getUsername()))).trim();

        if (!Collections.singleton(Boolean.parseBoolean(roleValueBoolean)
                ? Role.ADMIN : Role.USER).equals(user.getRoles())){
            if (!user.getRoles().contains(Role.ADMIN)) {
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

    public User createUser(User user) {
        if (!user.getPassword().isEmpty()) {
            interactionSystem.executeCommand(String.format(
                            "sudo adduser --gecos \"%s\" --disabled-password %s && echo \"%s:%s\" | sudo chpasswd",
                            user.getFullName(), user.getUsername(), user.getUsername() , user.getPassword()));
        } else {
            interactionSystem.executeCommand(String.format(
                    "sudo adduser --gecos \"%s\" --disabled-password %s",
                    user.getFullName(), user.getUsername()));
        }
        if (!user.getRoles().contains(Role.ADMIN)) {
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

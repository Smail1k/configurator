package ru.oleg.configurator.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.user.dto.User;

import static ru.oleg.configurator.domain.utils.AutoLoginConfigurator.changeAutoLogin;
import static ru.oleg.configurator.domain.utils.ExecuteCommand.executeCommand;
import static ru.oleg.configurator.domain.utils.ExecuteCommand.extractData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    public List<User> getAllUsers() {
        String usersString;
        usersString = executeCommand("getent passwd | awk -F: '$3 >= 1000 && $3 < 1100 { print $1 }'");

        if (usersString == null) {
            return new ArrayList<>();
        }

        String[] usersArray = usersString.split("\n");
        List<User> users = new ArrayList<>();

        for (String username : usersArray) {
            User userObj = new User();

            userObj.setUsername(username);

            String fullName = Objects.requireNonNull(executeCommand(
                        String.format("getent passwd %s | cut -d: -f5", username))).trim();
            System.out.println(fullName);
            userObj.setFullName(extractData(fullName,"^[a-zA-Z0-9 ]+"));


            String role = Objects.requireNonNull(executeCommand(
                    String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"", username))).trim();
            userObj.setRole(Boolean.parseBoolean(role));

            String password = Objects.requireNonNull(executeCommand(
                    String.format("sudo grep '^%s:' /etc/shadow | awk -F: '{print $2}'", username))).trim();
            userObj.setPassword(password.isEmpty() ? null : password);

            String autoLogin = executeCommand(
                    String.format("grep -i 'AutomaticLogin' /etc/gdm3/custom.conf | grep -q 'AutomaticLogin=%s'" +
                            " && echo \"True\" || echo \"False\"", username).trim());
            userObj.setAutoIn(Boolean.parseBoolean(autoLogin));

            users.add(userObj);
        }
        return users;
    }


    public User updateMe(User user) {
        return new User();
    }

    public User updateUserByUsername(String username, User user) {
        executeCommand(String.format("sudo chfn -f \"%s\" %s", user.getFullName(), user.getUsername()));

        String role = Objects.requireNonNull(executeCommand(
                String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"",
                        user.getUsername()))).trim();
        if (Boolean.parseBoolean(role) != user.isRole()){
            if (user.isRole()) {
                executeCommand(String.format("sudo usermod -aG sudo %s", user.getUsername()));
            } else {
                executeCommand(String.format("sudo deluser %s sudo", user.getUsername()));
            }
        }

        changeAutoLogin(user.getUsername(), user.isAutoIn());
        executeCommand("sudo systemctl restart gdm3");

        return user;
    }

    public User createUser(User user) {
        return new User();
    }

    public void deleteUserByUsername(String username) {

    }

}

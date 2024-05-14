package ru.oleg.configurator.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.user.dto.User;

import static ru.oleg.configurator.domain.utils.AutoLoginConfigurator.changeAutoLogin;
import static ru.oleg.configurator.domain.utils.ExecuteCommand.executeCommand;
import static ru.oleg.configurator.domain.utils.ExecuteCommand.extractData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {

    public List<User> getAllUsers() {
        String usersString;
        usersString = executeCommand("getent passwd | awk -F: '$3 >= 1000 && $3 < 1100 { print $1, $3 }'");

        if (usersString == null) {
            return new ArrayList<>();
        }

        String[] usersArray = usersString.split("\n");
        List<User> users = new ArrayList<>();

        for (String userline : usersArray) {
            User userObj = new User();

            userObj.setUsername(userline.split(" ")[0]);
            userObj.setId(Long.parseLong(userline.split(" ")[1]));

            String fullName = Objects.requireNonNull(executeCommand(
                        String.format("getent passwd %s | cut -d: -f5", userObj.getUsername()))).trim();
            userObj.setFullName(extractData(fullName, "^[\\p{L}0-9 ]+"));

            String role = Objects.requireNonNull(executeCommand(
                    String.format("id -nG %s | grep -qw sudo && echo \"True\" || echo \"False\"",
                            userObj.getUsername()))).trim();
            userObj.setRole(Boolean.parseBoolean(role));

            String password = Objects.requireNonNull(executeCommand(
                    String.format("sudo grep '^%s:' /etc/shadow | awk -F: '{print $2}'",
                            userObj.getUsername()))).trim();
            userObj.setPassword(password.equals("*") ? null : password);

            String autoLogin = executeCommand(
                    String.format("grep -i 'AutomaticLogin' /etc/gdm3/custom.conf | grep -q 'AutomaticLogin=%s'" +
                            " && echo \"True\" || echo \"False\"", userObj.getUsername()).trim());
            userObj.setAutoIn(Boolean.parseBoolean(autoLogin));

            users.add(userObj);
        }
        return users;
    }

    public User updateMe(User user) {
        return new User();
    }

    public User updateUser(Long id, User user) {
        System.out.println("OKEY");
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

        String autoLogin = executeCommand(
                String.format("grep -i 'AutomaticLogin' /etc/gdm3/custom.conf | grep -q 'AutomaticLogin=%s'" +
                        " && echo \"True\" || echo \"False\"", user.getUsername()).trim());
        if (Boolean.parseBoolean(autoLogin) != user.isAutoIn()) {
            changeAutoLogin(user.getUsername(), user.isAutoIn());
        }

        return user;
    }

    public User createUser(User user) {
        if (!user.getPassword().isEmpty()) {
            executeCommand(String.format(
                            "sudo adduser --gecos \"%s\" --disabled-password %s && echo \"%s:%s\" | sudo chpasswd",
                            user.getFullName(), user.getUsername(), user.getUsername() , user.getPassword()));
        } else {
            executeCommand(String.format(
                    "sudo adduser --gecos \"%s\" --disabled-password %s",
                    user.getFullName(), user.getUsername()));
        }

        if (user.isRole()) {
            executeCommand(String.format("sudo usermod -aG sudo %s",user.getUsername()));
        }

        String id = Objects.requireNonNull(executeCommand(
                String.format("getent passwd %s | awk -F: '{ print $3 }'", user.getUsername()))).trim();
        user.setId(Long.parseLong(id));

        return user;
    }

    public void deleteUserByUsername(Long id) {
        executeCommand(String.format("sudo userdel -r $(getent passwd | awk -F: '$3 == %s { print $1 }')", id));

    }

}

package ru.oleg.configurator.domain.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.user.dto.User;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    public List<User> getAllUser() {
        return new ArrayList<User>();
    }

    public User updateMe(User user) {
        return new User();
    }

    public User updateUserByUsername(String username, User user) {
        return new User();
    }
}

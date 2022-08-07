package pl.cyryl.finalproject.users.user;

import pl.cyryl.finalproject.users.user.User;

public interface UserService {
    User findByUserName(String name);
    void saveUser(User user);
}

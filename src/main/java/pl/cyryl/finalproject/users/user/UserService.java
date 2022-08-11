package pl.cyryl.finalproject.users.user;

import pl.cyryl.finalproject.users.user.exception.EmailAlreadyRegisteredException;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(String name);
    Optional<User> findById(Long id);
    void registerNewUser(User user) throws EmailAlreadyRegisteredException;
}

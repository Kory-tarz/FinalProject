package pl.cyryl.finalproject.users.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.users.role.Role;
import pl.cyryl.finalproject.users.role.RoleRepository;
import pl.cyryl.finalproject.users.user.exception.EmailAlreadyRegisteredException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void registerNewUser(User user) throws EmailAlreadyRegisteredException {
        if(emailExist(user.getEmail())){
            throw new EmailAlreadyRegisteredException("There is already an account with email address: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    private boolean emailExist(String email){
        return userRepository.findByEmail(email).isPresent();
    }
}

package pl.cyryl.finalproject.users.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.users.role.Role;
import pl.cyryl.finalproject.users.role.RoleRepository;
import pl.cyryl.finalproject.users.user.exception.EmailAlreadyRegisteredException;
import pl.cyryl.finalproject.users.authentication.verification.VerificationToken;
import pl.cyryl.finalproject.users.authentication.verification.VerificationTokenRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder, VerificationTokenRepository tokenRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
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
    public User registerNewUser(User user) throws EmailAlreadyRegisteredException {
        if (emailExist(user.getEmail())) {
            throw new EmailAlreadyRegisteredException("There is already an account with email address: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = new VerificationToken(user, token);
        Optional<VerificationToken> oldToken = tokenRepository.findByUser(user);
        oldToken.ifPresent(tokenRepository::delete);
        tokenRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public User processOAuthLogin(User user) {
        Optional<User> userDbData = userRepository.findByEmail(user.getEmail());
        if (userDbData.isEmpty()) {
            try {
                return registerNewUser(user);
            } catch (EmailAlreadyRegisteredException e) {
                System.out.println("should be impossible to get here");
                throw new RuntimeException("should be impossible to get here");
            }
        } else {
            return userDbData.get();
        }
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

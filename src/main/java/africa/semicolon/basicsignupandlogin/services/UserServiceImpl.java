package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.data.repositories.UserRepository;
import africa.semicolon.basicsignupandlogin.exceptions.UserDoesNotExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserDoesNotExistException("User with id: " + id + " does not exist");
        }
        userRepository.deleteById(id);

        return "user deleted";
    }
}

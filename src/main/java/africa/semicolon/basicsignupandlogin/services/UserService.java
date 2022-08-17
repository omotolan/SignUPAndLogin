package africa.semicolon.basicsignupandlogin.services;

import africa.semicolon.basicsignupandlogin.data.models.User;
import africa.semicolon.basicsignupandlogin.exceptions.UserDoesNotExistException;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    String deleteUser(Long id) throws UserDoesNotExistException;
}

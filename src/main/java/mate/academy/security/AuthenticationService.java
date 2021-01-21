package mate.academy.security;

import mate.academy.exception.AuthenticationException;
import mate.academy.model.Driver;

public interface AuthenticationService {
    Driver login(String login, String password) throws AuthenticationException;
}

package academy.mindswap.flight.services;

import academy.mindswap.flight.commands.LoginRequest;
import academy.mindswap.flight.persistence.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private UserService userService;

    public User login(LoginRequest loginRequest) {
        return userService.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
    }

    public User validate(String email) {
        return userService.validate(email);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}

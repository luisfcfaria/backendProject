package academy.mindswap.lms.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import academy.mindswap.lms.commands.LoginRequest;
import academy.mindswap.lms.converters.FlightConverter;
import academy.mindswap.lms.converters.UserConverter;
import academy.mindswap.lms.persistence.models.User;
import academy.mindswap.lms.persistence.repositories.FlightRepository;
import academy.mindswap.lms.persistence.repositories.RoleRepository;
import academy.mindswap.lms.persistence.repositories.UserRepository;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class, UserService.class, UserConverter.class, ModelMapper.class,
        FlightConverter.class, RoleService.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private FlightRepository flightRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testLogin() {
        // Arrange
        User user = new User();
        user.setAge(1);
        user.setAvatar_url("https://example.org/example");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setFlights(new HashSet<>());
        user.setIdNumber(1L);
        user.setLastName("Doe");
        user.setName("Name");
        user.setPassword("lovey");
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByEmailAndPassword((String) any(), (String) any())).thenReturn(ofResult);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("jane.doe@example.org");
        loginRequest.setPassword("lovey");

        // Act and Assert
        assertSame(user, this.authenticationService.login(loginRequest));
        verify(this.userRepository).findByEmailAndPassword((String) any(), (String) any());
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testLogin2() {

        // Arrange
        when(this.userRepository.findByEmailAndPassword((String) any(), (String) any())).thenReturn(Optional.empty());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("jane.doe@example.org");
        loginRequest.setPassword("iloveyou");

        // Act
        this.authenticationService.login(loginRequest);
    }

    @Test
    void testValidate() {

        // Arrange
        User user = new User();
        user.setAge(1);
        user.setAvatar_url("https://example.org/example");
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setFlights(new HashSet<>());
        user.setIdNumber(1L);
        user.setLastName("Doe");
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        Optional<User> ofResult = Optional.of(user);
        when(this.userRepository.findByEmail((String) any())).thenReturn(ofResult);

        // Act and Assert
        assertSame(user, this.authenticationService.validate("jane.doe@example.org"));
        verify(this.userRepository).findByEmail((String) any());
    }

    @Test
    @Disabled("TODO: Complete this test")
    void testValidate2() {

        // Arrange
        when(this.userRepository.findByEmail((String) any())).thenReturn(Optional.empty());

        // Act
        this.authenticationService.validate("jane.doe@example.org");
    }


}


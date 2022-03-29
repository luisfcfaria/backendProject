package academy.mindswap.lms.services;

import academy.mindswap.lms.annotations.MindswapAnnotation;
import academy.mindswap.lms.commands.UserDto;
import academy.mindswap.lms.converters.FlightConverter;
import academy.mindswap.lms.converters.UserConverter;
import academy.mindswap.lms.exceptions.InvalidUserId;
import academy.mindswap.lms.exceptions.UserNotFoundException;
import academy.mindswap.lms.persistence.models.Role;
import academy.mindswap.lms.persistence.models.User;
import academy.mindswap.lms.persistence.repositories.FlightRepository;
import academy.mindswap.lms.persistence.repositories.RoleRepository;
import academy.mindswap.lms.persistence.repositories.UserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private FlightConverter flightConverter;

    public List<UserDto> getUserByName(String name) {
        LOGGER.log(Level.INFO, "getUserByName: " + name);
        List<User> users = userRepository.findByName(name);

        return users.stream()
                .map(userConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public void addRoleToUser(String email, String roleName) {
        LOGGER.info("Adding role {} to user{}", roleName, email);
        User user = userRepository.findByEmail(email).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

//    public UserDto bookFlight(String email, String flightId) {
//
//        User user = userRepository.findByEmail(email).get();
//        Flight flight = flightRepository.findByFlightNumber(flightId);
//
//        if (user != null && flight != null) {
//            user.getFlights().add(flight);
//            return userConverter.convertToDto(userRepository.save(user));
//        }
//        return null;
//    }

//    public List<UserDto> getUserByOther(String name) {
//        return userRepository.findByOtherNameThatIWant(name)
//                .stream()
//                .map(userConverter::convertToDto).collect(Collectors.toList());
//    }

    public UserDto save(UserDto userDto) {
        return userConverter
                .convertToDto(
                        userRepository.save(
                                userConverter.convertToEntity(userDto)
                        ));
    }

    public User login(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

    @MindswapAnnotation
    public List<UserDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userConverter::convertToDto).collect(Collectors.toList());
    }


    public Optional<UserDto> getUserById(long id) {

        if (id < 0) {
            LOGGER.log(Level.WARN, "Users are trying to break our site: " + id);
            throw new InvalidUserId(Long.toString(id));
        }
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException(Long.toString(id));
        }
        return user.map(userConverter::convertToDto);
    }

    public User validate(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password).orElseThrow(() -> new UserNotFoundException(email));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }
}

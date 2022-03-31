package academy.mindswap.lms.services;

import academy.mindswap.lms.annotations.MindswapAnnotation;
import academy.mindswap.lms.commands.UserDto;
import academy.mindswap.lms.converters.FlightConverter;
import academy.mindswap.lms.converters.UserConverter;
import academy.mindswap.lms.exceptions.InvalidUserId;
import academy.mindswap.lms.exceptions.UserNotFoundException;
import academy.mindswap.lms.exceptions.RoleNotFoundException;
import academy.mindswap.lms.persistence.models.Flight;
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
    @Autowired
    private RoleService roleService;

    public List<UserDto> getUserByName(String name) {
        LOGGER.log(Level.INFO, "getUserByName: " + name);
        List<User> users = userRepository.findByName(name);

        return users.stream()
                .map(userConverter::convertToDto)
                .collect(Collectors.toList());
    }

    public void addRoleToUser(Long id, String roleName) {
        if (roleService.validateRole(roleName) == null) {
            LOGGER.warn("Role {} is not valid", roleName);
            throw new RoleNotFoundException(roleName);
        }

        LOGGER.info("Adding role {} to user with id = {}", roleName, id);
        User user = userRepository.findById(id).get();

        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            Role newRole = new Role();
            newRole.setName(roleName);
            roleRepository.save(newRole);
            user.getRoles().add(newRole);
            userRepository.save(user);
            return;
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }

//    public void addRoleToUser(Long id, String roleName) {
//        LOGGER.info("Adding role {} to user with id = {}", roleName, id);
//        User user = userRepository.findById(id).get();
//
//        Role role = roleRepository.findByName(roleName);
//        if(role == null){
//            Role newRole = new Role();
//            newRole.setName(roleName);
//            roleRepository.save(newRole);
//            user.getRoles().add(newRole);
//            userRepository.save(user);
//            return;
//        }
//        user.getRoles().add(role);
//        userRepository.save(user);
//    }

    public UserDto bookFlight(String email, String flightId) {

        Optional<User> user = userRepository.findByEmail(email);
        Flight flight = flightRepository.findByFlightNumber(flightId);

        if (user.isPresent() && flight != null) {
            user.get().getFlights().add(flight);
            return userConverter.convertToDto(userRepository.save(user.get()));
        }
        return null;
    }

    public  UserDto cancelFlight(String email, String flightId) {
        Optional<User> user = userRepository.findByEmail(email);
        Flight flight = flightRepository.findByFlightNumber(flightId);
        if (user.isPresent() && flight != null) {
            user.get().getFlights().remove(flight);
            return userConverter.convertToDto(userRepository.save(user.get()));
        }
        return null;
    }


    //CHECK DIFFERENCE BETWEEN THIS AND GET USER BY ID METHOD
    public UserDto findById(Long id) {
        return userConverter.convertToDto(userRepository.findById(id).get());
    }

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

    public User SignIn (UserDto userDto) {
    	User user = userConverter.convertToEntity(userDto);
    	return userRepository.save(user);
    }
}

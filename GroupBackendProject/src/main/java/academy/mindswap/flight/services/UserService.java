package academy.mindswap.flight.services;

import academy.mindswap.flight.commands.BookFlightDto;
import academy.mindswap.flight.commands.FlightDTO;
import academy.mindswap.flight.commands.InsertUserDto;
import academy.mindswap.flight.commands.UserDto;
import academy.mindswap.flight.converters.FlightConverter;
import academy.mindswap.flight.converters.UserConverter;
import academy.mindswap.flight.exceptions.InvalidUserId;
import academy.mindswap.flight.exceptions.UserNotFoundException;
import academy.mindswap.flight.exceptions.RoleNotFoundException;
import academy.mindswap.flight.persistence.models.Flight;
import academy.mindswap.flight.persistence.models.Role;
import academy.mindswap.flight.persistence.models.User;
import academy.mindswap.flight.persistence.repositories.FlightRepository;
import academy.mindswap.flight.persistence.repositories.RoleRepository;
import academy.mindswap.flight.persistence.repositories.UserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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

    public void addRoleToUser(String email, String roleName) {
        if (roleService.validateRole(roleName) == null) {
            LOGGER.warn("Role {} is not valid", roleName);
            throw new RoleNotFoundException(roleName);
        }

        LOGGER.info("Adding role {} to user with id = {}", roleName, email);
        User user = userRepository.findByEmail(email).get();

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

    public UserDto bookFlight(BookFlightDto flightDTO) {

        Optional<User> user = userRepository.findById(flightDTO.getPassengerId());
        FlightDTO flight = flightConverter.convertToDTO(flightRepository.findByFlightNumber(flightDTO.getFlightNumber()));

        if (user.isPresent() && flight != null) {
            user.get().getFlights().add(flightConverter.convertToEntity(flight));
            return userConverter.convertToDto(userRepository.save(user.get()));
        }
        return null;
    }

    public UserDto cancelFlight(BookFlightDto bookFlightDto) {
        Optional<User> user = userRepository.findById(bookFlightDto.getPassengerId());
        Flight flight = flightRepository.findByFlightNumber(bookFlightDto.getFlightNumber());

        if (user.isPresent() && flight != null) {
            user.get().getFlights().remove(flight);
            return userConverter.convertToDto(userRepository.save(user.get()));
        }
        return null;
    }

    public UserDto save(UserDto userDto) {
        return userConverter
                .convertToDto(userRepository
                        .save(userConverter.convertToEntity(userDto)));
    }

    public UserDto save(InsertUserDto userDto) {
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.ROLE_USER));

        User user = userConverter.convertToEntity(userDto);
        user.setRoles(roles);

        return userConverter.convertToDto(
                userRepository
                        .save(user));
    }

    public User login(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);
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

    public User SignIn(InsertUserDto userDto) {
        User user = userConverter.convertToEntity(userDto);
        return userRepository.save(user);
    }
}

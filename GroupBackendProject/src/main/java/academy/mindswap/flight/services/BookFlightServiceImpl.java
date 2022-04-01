package academy.mindswap.flight.services;

import academy.mindswap.flight.commands.BookFlightDto;
import academy.mindswap.flight.commands.FlightDTO;
import academy.mindswap.flight.commands.UserDto;
import academy.mindswap.flight.converters.BookFlightConverter;
import academy.mindswap.flight.converters.FlightConverter;
import academy.mindswap.flight.converters.UserConverter;
import academy.mindswap.flight.persistence.models.Flight;
import academy.mindswap.flight.persistence.models.User;
import academy.mindswap.flight.persistence.repositories.FlightRepository;
import academy.mindswap.flight.persistence.repositories.RoleRepository;
import academy.mindswap.flight.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookFlightServiceImpl {
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
    private BookFlightConverter bookFlightConverter;
    @Autowired
    private RoleService roleService;

    public UserDto bookFlight(BookFlightDto flightDTO) {

        Optional<User> user = userRepository.findById(flightDTO.getPassengerId());
        BookFlightDto flight = bookFlightConverter.convertToDTO(flightRepository.findByFlightNumber(flightDTO.getFlightNumber()));

        if (user.isPresent() && flight != null) {
            user.get().getFlights().add(bookFlightConverter.convertToEntity(flight));
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
}


package academy.mindswap.lms.services;

import academy.mindswap.lms.commands.FlightDTO;
import academy.mindswap.lms.converters.FlightConverter;
import academy.mindswap.lms.persistence.models.Flight;
import academy.mindswap.lms.persistence.repositories.FlightRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

    private static final Logger LOGGER = LogManager.getLogger(FlightService.class);

    @Autowired
    private FlightConverter flightConverter;

    @Autowired
    private FlightRepository flightRepository;

    public FlightDTO getFlightByNumber(Integer flightNumber) {

        return flightConverter.convertToDTO(flightRepository.findByFlightNumber(String.valueOf(flightNumber)));
    }

    public FlightDTO addFlight(FlightDTO flightDTO) {

        if (flightDTO.getFlightNumber() == null) {
            LOGGER.log(Level.INFO, "Adding flight: " + flightDTO.getFlightNumber());
            return flightConverter.convertToDTO(flightRepository
                    .save(flightConverter
                            .convertToEntity(flightDTO)));
        }
        return null;
    }

    public List<FlightDTO> getFlightByArrival(String arrival) {

        LOGGER.log(Level.INFO, "Getting flight by arrival: " + arrival);
        return (flightRepository.findAll()
                .stream()
                .filter(i -> i.getArrivalAirport().equals(arrival))
                .map(flightConverter::convertToDTO)
                .collect(Collectors.toList()));

    }

    public List<FlightDTO> getFlightByArrival(String arrival, String date){

        return (flightRepository.findAll()
        .stream()
        .filter(i -> i.getArrivalAirport().equals(arrival) && i.getArrivalDate().equals(date))
        .map(flightConverter::convertToDTO)
        .collect(Collectors.toList()));
    }

    public List<FlightDTO> getFlightByDeparture(String departure) {

        LOGGER.log(Level.INFO, "Getting flight by departure: " + departure);
        return (flightRepository.findAll()
                .stream()
                .filter(i -> i.getDepartureAirport().equals(departure))
                .map(flightConverter::convertToDTO)
                .collect(Collectors.toList()));

    }

    public List<FlightDTO> getFlightByDeparture(String arrival, String date){

        return (flightRepository.findAll()
        .stream()
        .filter(i -> i.getDepartureAirport().equals(arrival) && i.getDepartureDate().equals(date))
        .map(flightConverter::convertToDTO)
        .collect(Collectors.toList()));
    }

    public List<FlightDTO> getAllFlights() {

        LOGGER.log(Level.INFO, "Getting all flights");

        return (flightRepository.findAll()
                .stream()
                .map(flightConverter::convertToDTO)
                .collect(Collectors.toList()));

    }

    public FlightDTO updateFlight(FlightDTO flightDTO) {

        Optional<Flight> flight = Optional.ofNullable(flightRepository
                .findByFlightNumber(flightConverter.convertToEntity(flightDTO).getFlightNumber()));

        if (flight.isPresent()) {
            LOGGER.log(Level.INFO, "Updating flight: " + flightDTO.getFlightNumber());
            FlightDTO updatedFlight = flightConverter.convertToDTO(flightRepository
                    .save(flightConverter
                            .convertToEntity(flightDTO)));
            return updatedFlight;
        } else {
            return null;
        }
    }

    public FlightDTO deleteFlight(String flightNumber) {
        LOGGER.log(Level.INFO, "Deleting flight: " + flightNumber);
        flightRepository.deleteByFlightNumber(flightNumber);
        return null;
    }
}

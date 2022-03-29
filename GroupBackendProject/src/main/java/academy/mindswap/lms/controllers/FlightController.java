package academy.mindswap.lms.controllers;

import academy.mindswap.lms.commands.FlightDTO;
import academy.mindswap.lms.persistence.models.Flight;
import academy.mindswap.lms.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        if (flights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(flights);
    }

    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable String id) {
        Optional<FlightDTO> flight = Optional.ofNullable(flightService.getFlightByNumber(id));

        return flight.map(flightDTO -> ResponseEntity.ok().body(flightDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/flights/origin/{origin}")
    public ResponseEntity<List<FlightDTO>> getFlightByOrigin(@PathVariable String origin) {
        List<FlightDTO> flights = flightService.getFlightByDeparture(origin);
        if (flights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(flights);
    }


    @GetMapping("/flights/destination/{destination}")
    public ResponseEntity<List<FlightDTO>> getFlightByDestination(@PathVariable String destination) {
        List<FlightDTO> flights = flightService.getFlightByArrival(destination);
        if (flights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(flights);
    }
}


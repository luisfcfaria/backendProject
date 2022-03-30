package academy.mindswap.lms.controllers;

import academy.mindswap.lms.commands.FlightDTO;
import academy.mindswap.lms.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<FlightDTO> getFlight(@PathVariable Integer id) {
        Optional<FlightDTO> flight = Optional.ofNullable(flightService.getFlightByNumber(id));

        return flight.map(flightDTO -> ResponseEntity.ok().body(flightDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/flights/origin/{departureAirport}")
    public ResponseEntity<List<FlightDTO>> getFlightByOrigin(@PathVariable String departureAirport) {
        List<FlightDTO> flights = flightService.getFlightByDeparture(departureAirport);
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

    @PutMapping("/admin/updateflight")
    public ResponseEntity<FlightDTO> updateFlight(@RequestBody FlightDTO flightDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        FlightDTO flight = flightService.updateFlight(flightDTO);
        if(flight == null) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @PostMapping("/admin/addflight")
    public ResponseEntity<FlightDTO> addFlight(@RequestBody FlightDTO flightDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(null);
        }
        FlightDTO flight = flightService.addFlight(flightDTO);
        if(flight == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(flight, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/deleteflight/{id}")
    public ResponseEntity<FlightDTO> deleteFlight(@PathVariable String id) {
        FlightDTO flight = flightService.deleteFlight(id);
        if(flight == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }
}


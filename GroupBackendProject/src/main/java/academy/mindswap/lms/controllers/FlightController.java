package academy.mindswap.lms.controllers;

import academy.mindswap.lms.commands.BookFlightDto;
import academy.mindswap.lms.commands.FlightDTO;
import academy.mindswap.lms.commands.UserDto;
import academy.mindswap.lms.persistence.models.User;
import academy.mindswap.lms.services.FlightService;
import academy.mindswap.lms.services.UserService;
import org.hibernate.validator.constraints.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FlightController {

    @Autowired
    private FlightService flightService;
    @Autowired
    private UserService userService;


    @GetMapping("/flights")
    public ResponseEntity<List<FlightDTO>> getFlights() {
        List<FlightDTO> flights = flightService.getAllFlights();
        if (flights.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(flights);
    }

    @GetMapping("/flights/{flightNumber}")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable String flightNumber) {
        Optional<FlightDTO> flight = Optional.ofNullable(flightService.getFlightByNumber(flightNumber));

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



   // @PutMapping("/user/{id}/bookflight/{flightNumber}")
    @PostMapping("/flights/bookflight/")
    //@PreAuthorize("hasRole('ROLE_BATATSUSER')")   //
    @PreAuthorize("principal.idNumber == #bookFlightDto.passengerId")
    public ResponseEntity<UserDto> bookFlight(@RequestBody BookFlightDto flightDTO, Principal principal) {

        System.out.println( principal.getName() + "nnnnnnnnnnnnnnn");
        return ResponseEntity.ok().body(userService.bookFlight(flightDTO));
    }

    @DeleteMapping("flights/bookflight/")
    @PreAuthorize("principal.idNumber == #bookFlightDto.passengerId")
    public ResponseEntity<UserDto> cancelFlight(@RequestBody BookFlightDto bookFlightDto, Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println( ((User) auth.getPrincipal()).getIdNumber());

        return ResponseEntity.ok().body(userService.cancelFlight(bookFlightDto));
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

    @GetMapping("/admin/passengers-per-flight/{flightNumber}")
    public ResponseEntity<List<UserDto>> passengerList(@PathVariable String flightNumber) {
        List<UserDto> passengers = flightService.getPassengersPerFlight(flightNumber);
        if(passengers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        if(flightService.getFlightByNumber(flightNumber) == null){
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok().body(passengers);
    }


}


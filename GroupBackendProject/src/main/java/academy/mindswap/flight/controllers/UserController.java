package academy.mindswap.flight.controllers;

import academy.mindswap.flight.commands.InsertUserDto;
import academy.mindswap.flight.commands.UserDto;
//import academy.mindswap.lms.services.GitHubLookupService;
import academy.mindswap.flight.services.FlightService;
import academy.mindswap.flight.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    //  private static final Logger LOGGER = LogManager.getLogger(UserController.class);


    @Autowired
    private final UserService userService;


    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        Optional<UserDto> user = userService.getUserById(id);
        return user.map(userDto -> ResponseEntity.ok().body(userDto)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error: " + bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(null);

        }
        UserDto createdUserDto = userService.save(userDto);
        if (createdUserDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody InsertUserDto userDto) throws RoleNotFoundException {
        userService.save(userDto);
        return ResponseEntity.ok().build();
    }

}
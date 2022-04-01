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

    @Autowired
    private final FlightService flightService;

//   // @Autowired
//    private final GitHubLookupService gitHubLookupService;

  /*  public UserController(UserService userService) {
        this.userService = userService;
    }*/


    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
        Optional<UserDto> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        }
        return ResponseEntity.notFound().build();
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

//
//    @GetMapping("/search")
//    public List<UserDto> getUserByName(@RequestParam(value = "name", defaultValue = "World") String name) {
//        log.info( "getUserByName: " + name);
//        return  userService.getUserByName(name);
//    }
//
//    @GetMapping("/find")
//    public List<UserDto> getUserByNameIWant(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return  userService.getUserByOther(name);
//    }

//    @GetMapping("/users")
//    @MindswapAnnotation
//    public List<UserDto> getAllUsers() {
//
//        return  userService.getAllUsers();
//    }

    @PostMapping("/admin/changeUserRole/{userId}")
    public ResponseEntity<?> saveRole(@PathVariable String userEmail, @RequestBody String roleName) throws RoleNotFoundException {
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        userService.addRoleToUser(userEmail, roleName);
        return ResponseEntity.ok().build();
    }

  /*  @PostMapping("/user/{userid}/bookflight")
    public ResponseEntity<?> bookflight(@PathVariable Long userid, @RequestBody String flightNumber) throws RoleNotFoundException {
        String userEmail = userService.findById(userid).getEmail();
        userService.bookFlight(userEmail, flightNumber);
        return ResponseEntity.ok().build();
    }*/

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody InsertUserDto userDto) throws RoleNotFoundException {
        userService.save(userDto);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/user/github/{githubId}")
//    public User getGitHubUser(@PathVariable String githubId) throws ExecutionException, InterruptedException {
//        System.out.println("before Github Id: " + githubId);
//        CompletableFuture<User> user = gitHubLookupService.findUser(githubId);
//        System.out.println("after Github Id: " + githubId);
//        User userGit = user.get();
//        System.out.println("end Github Id: " + githubId);
//        return userGit;
//       // return  gitHubLookupService.findUser(githubId);
//    }
//
//    @GetMapping("/user/github/testing")
//    public List<User> getGitHubTesting() throws ExecutionException, InterruptedException {
//        long start = System.currentTimeMillis();
//
//        // Kick of multiple, asynchronous lookups
//        CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
//        CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
//        CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");
//
//        // Wait for the lookups to complete
//        CompletableFuture.allOf(page1, page2, page3).join();
//
//        System.out.println(("Elapsed Time: " + (System.currentTimeMillis() - start)));
//
//
//        List<User> users = List.of(page1.get(), page2.get(), page3.get());
//
//        return users;
//        // return  gitHubLookupService.findUser(githubId);
//    }
/*
    @GetMapping("/user")
    public User  getUser() {
     return   User.builder()
                .email("j@jsd.com")
                .name("j")
                .build();

     //   return new User("John", "Doe");
    }
    @GetMapping("/search")
    public User getUserByName(@RequestParam(value = "name", defaultValue = "World") String name,
                              @RequestParam(value = "lastname", defaultValue = "World") String lastname,
                              @RequestParam(value = "email", defaultValue = "d@d.com") String email,
                              @RequestParam(value = "name", defaultValue = "1") String id
                              ) {
        return  User.builder()
                .id(1)
                .email(email)
                .name(name + " " + lastname + " " + id)
                .build();
        //   return new User("John", "Doe");
    }


    @PostMapping("/users")
    public  List<User> newUsers( @RequestBody List<User> users) {
       // user.setName(user.getName() + " Resposta" );

        //Create a new users list

        return users;
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        // delete user
    }


    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        user.setName(user.getName() + " Resposta" + id);
        return user;
    }

*/
}
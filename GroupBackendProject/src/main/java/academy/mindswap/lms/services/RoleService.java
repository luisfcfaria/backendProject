//package academy.mindswap.lms.services;
//
//import academy.mindswap.lms.commands.UserDto;
//import academy.mindswap.lms.persistence.models.Role;
//import academy.mindswap.lms.persistence.repositories.RoleRepository;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import javax.validation.Valid;
//
//
//@Service
//public class RoleService {
//
//    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    public void addRole(String roleName) {
//        LOGGER.info("Adding role {} to user{}", roleName);
//        Role role = roleRepository.findByName(roleName);
//        userRepository.save(user);
//    }
//
//
//    @PostMapping("/user")
//    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult) {
//
//        if(bindingResult.hasErrors()) {
//            System.out.println("Error: " + bindingResult.getAllErrors());
//            return ResponseEntity.badRequest().body(null);
//
//        }
//        UserDto  createdUserDto = userService.save(userDto);
//        if (createdUserDto == null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
//    }
//}

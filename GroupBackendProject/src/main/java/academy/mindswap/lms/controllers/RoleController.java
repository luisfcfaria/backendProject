//package academy.mindswap.lms.controllers;
//
//import academy.mindswap.lms.commands.UserDto;
//import academy.mindswap.lms.persistence.models.Role;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//@Slf4j
//public class RoleController {
//
//    @PostMapping("/role")
//    public ResponseEntity<UserDto> createRole(@Valid @RequestBody Role role, BindingResult bindingResult) {
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

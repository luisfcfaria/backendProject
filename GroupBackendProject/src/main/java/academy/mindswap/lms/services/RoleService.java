package academy.mindswap.lms.services;
import academy.mindswap.lms.commands.UserDto;
import academy.mindswap.lms.converters.UserConverter;
import academy.mindswap.lms.persistence.models.Role;
import academy.mindswap.lms.persistence.models.User;
import academy.mindswap.lms.persistence.repositories.RoleRepository;
import academy.mindswap.lms.persistence.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;

    public Role validateRole(String roleName) {

        List<Role> roles = roleRepository.findAll();
//        System.out.println("------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>" + roles.get(0).getName());
        return roles.stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't add random roles!"));
    }
}

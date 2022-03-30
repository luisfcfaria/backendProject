package academy.mindswap.lms.services;
import academy.mindswap.lms.persistence.models.Role;
import academy.mindswap.lms.persistence.repositories.RoleRepository;
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


    public Role validateRole(String roleName) {

        List<Role> roles = roleRepository.findAll();

        System.out.println("------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>" + roles.get(0).getName());

        return roles.stream()
                .filter(role -> role.getName().equalsIgnoreCase(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't add random roles!"));
    }
}

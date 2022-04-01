package academy.mindswap.flight.config.loader;

import academy.mindswap.flight.persistence.models.Role;
import academy.mindswap.flight.persistence.models.User;
import academy.mindswap.flight.persistence.repositories.RoleRepository;
import academy.mindswap.flight.persistence.repositories.UserRepository;
import org.aspectj.weaver.bcel.BcelAccessForInlineMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

@Component
public class UserDataLoader {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void loadData() {

        Role roleUser = roleRepository.findByName("USER");
        Role roleAdmin = roleRepository.findByName("ADMIN");

        Set<Role> rolesSimple = new HashSet<>();
        Set<Role> rolesAdmins = new HashSet<>();
        rolesSimple.add(roleUser);
        rolesAdmins.add(roleUser);
        rolesAdmins.add(roleAdmin);


        userRepository.save(User.builder().idNumber(1009474392L).name("John Smith").email("jonh@email").password(passwordEncoder.encode("pass1")).age(50)
                .roles(rolesSimple).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Alice Smith").email("alice@email").password(passwordEncoder.encode("randompass")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Bob Smith").email("bob@email").password(passwordEncoder.encode( "lotsOfChars")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Charlie Smith").email("charlie@email").password(passwordEncoder.encode("yesPlease")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("David Smith").email("david@email").password(passwordEncoder.encode("NoWay")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Eve Smith").email("Eve@email").password(passwordEncoder.encode("22345")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Frank Smith").email("frank@email").password(passwordEncoder.encode("tinnez_18")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("George Smith").email("george@email").password(passwordEncoder.encode("alheiras")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Harry Potter").email("harry@email").password(passwordEncoder.encode("uihdwon")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Ivan Ivanov").email("ivan@email").password(passwordEncoder.encode("oiwoqn")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("John Doe").email("jonhD@email").password(passwordEncoder.encode("asyuwosj")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Kate Smith").email("kate@email").password(passwordEncoder.encode("tamosaqui")).age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Linda Smith").email("Linda@email").password(passwordEncoder.encode("toucheiodisto")).age(46).build());


    }

}



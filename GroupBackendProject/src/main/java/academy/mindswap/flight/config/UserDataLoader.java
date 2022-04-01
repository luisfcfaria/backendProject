package academy.mindswap.flight.config;

import academy.mindswap.flight.persistence.models.Role;
import academy.mindswap.flight.persistence.models.User;
import academy.mindswap.flight.persistence.repositories.RoleRepository;
import academy.mindswap.flight.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;

@Component
public class UserDataLoader {

    @Autowired
    private UserRepository userRepository;

    public void loadData() {

        userRepository.save(User.builder().idNumber(1009474392L).name("John Smith").email("jonh@email").password("pass1").age(50).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Alice Smith").email("alice@email").password("randompass").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Bob Smith").email("bob@email").password("lotsOfChars").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Charlie Smith").email("charlie@email").password("yesPlease").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("David Smith").email("david@email").password("NoWay").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Eve Smith").email("Eve@email").password("22345").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Frank Smith").email("frank@email").password("tinnez_18").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("George Smith").email("george@email").password("alheiras").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Harry Potter").email("harry@email").password("uihdwon").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Ivan Ivanov").email("ivan@email").password("oiwoqn").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("John Doe").email("jonhD@email").password("asyuwosj").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Kate Smith").email("kate@email").password("tamosaqui").age(46).build());
        userRepository.save(User.builder().idNumber(548393092L).name("Linda Smith").email("Linda@email").password("toucheiodisto").age(46).build());


    }

}



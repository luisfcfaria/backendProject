package academy.mindswap.lms.config;

import academy.mindswap.lms.persistence.models.User;
import academy.mindswap.lms.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Component
public class UserDataLoader {

    @Autowired
    private UserRepository userRepository;

    public void loadData() {
       /* userRepository.deleteAll();
        for (int i = 1; i <= 10; i++) {
            User user = User.builder().id(i).name("user"+i).email("user" + i + "@email").password(i+"pass"+i).build();
            createIfNotFound(i, user);
        }*/

        LongConsumer operation = i ->{
            User user = User.builder().idNumber(i).firstName("user"+i).email("user" + i + "@email.com").password(i+"pass"+i).build();
            createIfNotFound(i, user);
        };

        reloadData(operation,20);

        LongConsumer operation2 = i ->{
            User user = User.builder().idNumber(i).firstName("whatever"+i).email("whatever" + i + "@email.com").password(i+"whatever"+i).build();
            createIfNotFound(i, user);
        };

        reloadData(operation2,10);

    }

    public void reloadData(LongConsumer operation, Integer end) {

        LongStream.range(1, end).forEach(operation);
    }

    public void createIfNotFound(Long id, User userToSave) {
        Optional<User> user = userRepository.findById(id);
        user.ifPresentOrElse(
                u -> {
                    System.out.println("User already exists");
                },
                () -> {
                    System.out.println("User not found, creating...");

                    userRepository.save(userToSave);
                }
        );
    }

}

package academy.mindswap.lms.mockdata;

import academy.mindswap.lms.commands.UserDto;
import academy.mindswap.lms.persistence.models.User;


public class MockData {

    public static User getUser() {
        return User.builder()
                .idNumber(3L)
                .firstName("Tiago")
                .lastName("Correia")
                .email("tiago@email.com")
                .password("password")
                .age(20)
                .build();
    }

    public static UserDto getUserDto(User user) {
        return UserDto.builder()
                .idNumber(user.getIdNumber())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();

    }
}

package academy.mindswap.flight.mockdata;

import academy.mindswap.flight.commands.UserDto;
import academy.mindswap.flight.persistence.models.User;


public class MockData {

    public static User getUser() {
        return User.builder()
                .idNumber(3L)
                .name("Tiago")
                .email("tiago@email.com")
                .password("password")
                .age(20)
                .build();
    }

    public static UserDto getUserDto(User user) {
        return UserDto.builder()
                .idNumber(user.getIdNumber())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .build();

    }
}

package academy.mindswap.lms.acceptance;

import academy.mindswap.lms.commands.UserDto;
import academy.mindswap.lms.mockdata.MockData;
import academy.mindswap.lms.persistence.models.User;
import academy.mindswap.lms.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_getUserById_shouldReturn200() {
        //GIVEN
        User user = MockData.getUser();
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        String path = "/users/3";

        //WHEN
        ResponseEntity<UserDto> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                UserDto.class
        );

        //THEN
      //  verify(userRepository, times(1)).findById(anyLong());
        UserDto expected = MockData.getUserDto(user);
        assertEquals(expected, response.getBody());

    }
}


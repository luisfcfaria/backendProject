package academy.mindswap.lms.commands;

import academy.mindswap.lms.persistence.models.Role;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {


    private Long idNumber;
    @NotBlank( message= "Name cannot be empty")
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email address")
    @NotBlank( message= "Email cannot be empty")
    private String email;
    private Integer age;
    private Set<Role> roles;
    private Set<FlightDTO> flights;

}

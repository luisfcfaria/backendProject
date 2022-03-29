package academy.mindswap.lms.commands;

import lombok.*;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {


    private Integer idNumber;
    @NotBlank( message= "Name cannot be empty")
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email address")
    @NotBlank( message= "Email cannot be empty")
    private String email;
    private Integer age;


}

package academy.mindswap.flight.commands;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsertUserDto {
    @NotBlank( message= "Name cannot be empty")
    private String name;
    @Email(message = "Invalid email address")
    @NotBlank( message= "Email cannot be empty")
    private String email;
    private Integer age;
    private String password;
    private Long identificationNumber;
}

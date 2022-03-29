package academy.mindswap.lms.persistence.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long idNumber;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String name = firstName.concat(" " + lastName);
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Integer age;

//    @Column
//    private String avatar_url;
//    @Column
//    private String location;

    @JsonIgnore
    @ManyToMany//(fetch = FetchType.EAGER)
    @JoinTable(name = "User_Roles", joinColumns =@JoinColumn(name = "userId"/*, referencedColumnName = "userId"*/),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private Collection<Role> roles;


//   @JsonIgnore
//    @ManyToMany//(fetch = FetchType.EAGER)
//    @JoinTable(name = "User_Flights", joinColumns = @JoinColumn(name = "userId"), //referencedColumnName = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "flight_number"))
//    private Collection<Flight> flights;

}


package academy.mindswap.lms.persistence.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

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
    private String name = Objects.isNull(firstName) ? "" : firstName.concat(" " + lastName);
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Integer age;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "roles", joinColumns =@JoinColumn(name = "userId"/*, referencedColumnName = "userId"*/),
//            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
//    private Collection<Role> roles;
//
//
//   @JsonIgnore
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "User_Flights", joinColumns = @JoinColumn(name = "userId"), //referencedColumnName = "userId"),
//            inverseJoinColumns = @JoinColumn(name = "flight_number"))
//    private Collection<Flight> flights;

}


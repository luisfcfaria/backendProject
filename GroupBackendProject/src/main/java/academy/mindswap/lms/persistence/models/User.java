package academy.mindswap.lms.persistence.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long idNumber;
    @Column
    private String firstName;
    @Column
    private String lastName;
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
    @ManyToMany
    @JoinTable(name = "Passenger_Flights", joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")}, inverseJoinColumns = {@JoinColumn(name = "flight_number", referencedColumnName = "flight_number")})
    private Collection<Flight> flights;

}


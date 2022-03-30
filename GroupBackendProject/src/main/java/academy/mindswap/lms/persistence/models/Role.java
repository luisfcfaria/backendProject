package academy.mindswap.lms.persistence.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    private static final String GUEST = "GUEST";
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnore
//    private Collection<User> users;

}
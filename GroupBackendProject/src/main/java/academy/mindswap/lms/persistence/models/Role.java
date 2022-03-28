package academy.mindswap.lms.persistence.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Role implements Serializable {

    private static final String GUEST = "GUEST";
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";

    @Serial
    private static final long serialVersionUIS = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users;

}
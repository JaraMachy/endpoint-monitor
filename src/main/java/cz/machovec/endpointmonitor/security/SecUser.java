package cz.machovec.endpointmonitor.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class SecUser {

    @Id
    @GeneratedValue(generator="idgen_sec_user", strategy= GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1, name = "idgen_sec_user", sequenceName = "seq_sec_user")
    private Long id;
    private String username;
    private String password;
    private String email;

}

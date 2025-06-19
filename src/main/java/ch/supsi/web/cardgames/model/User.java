package ch.supsi.web.cardgames.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String username;
    private String name;
    private String surname;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String username, String name, String surname, UserRole userRole, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = userRole;
        this.password = password;
    }

}

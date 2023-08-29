package project.lagalt.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import project.lagalt.utilites.enums.Skills;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    @ElementCollection(targetClass = Skills.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_skills")
    @Column(name = "skills")
    private Set<Skills> skills;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", skills=" + skills +
                '}';
    }
}

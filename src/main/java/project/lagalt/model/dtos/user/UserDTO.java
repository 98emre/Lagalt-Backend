package project.lagalt.model.dtos.user;

import lombok.Data;
import project.lagalt.utilites.enums.Skills;

import java.util.Set;


@Data
public class UserDTO {

    private int id;
    private String username;
    private String password;

    private Set<Skills> skills;

}

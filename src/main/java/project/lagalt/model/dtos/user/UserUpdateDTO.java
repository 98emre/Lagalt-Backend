package project.lagalt.model.dtos.user;


import lombok.Data;
import project.lagalt.utilites.enums.Skills;

import java.util.Set;

@Data
public class UserUpdateDTO {
    private int id;
    private String username;
    private String password;
    private Set<Skills> skills;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Skills> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skills> skills) {
        this.skills = skills;
    }
}

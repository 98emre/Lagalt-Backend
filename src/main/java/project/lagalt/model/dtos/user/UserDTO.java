package project.lagalt.model.dtos.user;

import lombok.Data;
import project.lagalt.utilites.enums.Skills;

import java.util.Set;


@Data
public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private Set<Skills> skills;
    private Set<Integer> projectIds;
    private Set<Integer> collaboratorIds;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<Skills> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skills> skills) {
        this.skills = skills;
    }

    public Set<Integer> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(Set<Integer> projectIds) {
        this.projectIds = projectIds;
    }

    public Set<Integer> getCollaboratorIds() {
        return collaboratorIds;
    }

    public void setCollaboratorIds(Set<Integer> collaboratorIds) {
        this.collaboratorIds = collaboratorIds;
    }
}

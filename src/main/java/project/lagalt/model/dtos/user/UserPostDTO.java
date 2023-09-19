package project.lagalt.model.dtos.user;

import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.Data;
import project.lagalt.utilites.enums.ProfileVisibility;
import project.lagalt.utilites.enums.Skills;

@Data
public class UserPostDTO {

    private int id;

    @Size(max = 50, message = "Username is Max Length")
    private String username;

    @Size(max = 100, message = "Email is Max Length")
    private String email;

    @Size(max = 200, message = "Description is Max Length")
    private String description;

    @Size(max = 50, message = "Fullname is Max Length")
    private String fullname;

    private Set<Skills> skills;
    private ProfileVisibility profileVisibility;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ProfileVisibility getProfileVisibility() {
        return profileVisibility;
    }

    public void setProfileVisibility(ProfileVisibility profileVisibility) {
        this.profileVisibility = profileVisibility;
    }
}

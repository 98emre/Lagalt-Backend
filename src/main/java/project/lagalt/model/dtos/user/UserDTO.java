package project.lagalt.model.dtos.user;

import jakarta.validation.constraints.*;
import lombok.Data;
import project.lagalt.utilites.enums.ProfileVisibility;
import project.lagalt.utilites.enums.Skills;

import java.util.Set;

@Data
public class UserDTO {
    private int id;

    @Size(max = 50, message = "Username is Max Length")
    @NotBlank(message = "No Blank Username")
    private String username;

    @Size(max = 100, message = "Email is Max Length")
    private String email;

    @Size(max = 200, message = "Description is Max Length")
    private String description;

    @Size(max = 50, message = "Fullname is Max Length")
    private String fullname;

    private Set<Skills> skills;
    private ProfileVisibility profileVisibility;
    private Set<Integer> projectIds;
    private Set<Integer> collaboratorIds;
    private Set<Integer> commentIds;
    private Set<Integer> receivedMessageIds;
    private Set<Integer> sentMessageIds;

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

    public Set<Integer> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(Set<Integer> commentIds) {
        this.commentIds = commentIds;
    }

    public Set<Integer> getReceivedMessageIds() {
        return receivedMessageIds;
    }

    public void setReceivedMessageIds(Set<Integer> receivedMessageIds) {
        this.receivedMessageIds = receivedMessageIds;
    }

    public Set<Integer> getSentMessageIds() {
        return sentMessageIds;
    }

    public void setSentMessageIds(Set<Integer> sentMessageIds) {
        this.sentMessageIds = sentMessageIds;
    }
}

package project.lagalt.model.dtos.user;

import lombok.Data;
import project.lagalt.utilites.enums.Skills;

import java.util.Set;


@Data
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private String description;
    private String fullname;
    private Set<Skills> skills;
    private Set<Integer> projectIds;
    private Set<Integer> collaboratorIds;
    private Set<Integer> commentIds;

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

    public void setDescription(String description){
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
}

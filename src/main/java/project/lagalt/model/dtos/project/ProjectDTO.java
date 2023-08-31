package project.lagalt.model.dtos.project;

import lombok.Data;
import project.lagalt.utilites.enums.Category;
import project.lagalt.utilites.enums.Status;

import java.util.Set;


@Data
public class ProjectDTO {

    private int id;
    private String title;
    private String descriptions;
    private String gitlink;
    private Category category;
    private Status status;
    private Set<Integer> commentIds;
    private Set<Integer> collaboratorIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getGitlink() {
        return gitlink;
    }

    public void setGitlink(String gitlink) {
        this.gitlink = gitlink;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Integer> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(Set<Integer> commentIds) {
        this.commentIds = commentIds;
    }

    public Set<Integer> getCollaboratorIds() {
        return collaboratorIds;
    }

    public void setCollaboratorIds(Set<Integer> collaboratorIds) {
        this.collaboratorIds = collaboratorIds;
    }
}

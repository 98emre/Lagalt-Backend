package project.lagalt.model.dtos.project;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import project.lagalt.utilites.enums.Category;
import project.lagalt.utilites.enums.Status;

import java.util.Set;


@Data
public class ProjectDTO {

    private int id;

    @Size(max = 100, message = "Title is Max Length")
    @NotNull
    private String title;

    @Size(max = 1000, message = "Descriptions is Max Length")
    @NotNull
    private String descriptions;

    @Size(max = 1000, message = "Git link is Max Length")
    @NotNull
    private String gitlink;

    @NotNull
    private Category category;

    @NotNull
    private Status status;



    private Integer userId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

package project.lagalt.model.dtos.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import project.lagalt.utilites.enums.Category;
import project.lagalt.utilites.enums.Status;


@Data
public class ProjectPostDTO {

    private int id;

    @Size(max = 100, message = "Title is Max Length")
    @NotNull
    @NotBlank
    private String title;

    @Size(max = 250, message = "Descriptions is Max Length")
    @NotNull
    @NotBlank
    private String descriptions;

    @Size(max = 1000, message = "Git link is Max Length")
    @NotNull
    @NotBlank
    private String gitlink;

    @NotNull
    private Category category;

    @NotNull
    private Status status;

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
}

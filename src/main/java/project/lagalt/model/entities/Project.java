package project.lagalt.model.entities;


import jakarta.persistence.*;
import project.lagalt.utilites.enums.Category;
import project.lagalt.utilites.enums.Status;

@Entity
@Table(name = "table")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;


    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", length = 1000, nullable = false)
    private String description;


    @Column(name = "gitlink", length = 1000, nullable = false)
    private String gitLink;


    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Status status;

    public Project() {

    }

    public Project(int id, String title, String description, String gitLink, Category category, Status status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.gitLink = gitLink;
        this.category = category;
        this.status = status;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGitLink() {
        return gitLink;
    }

    public void setGitLink(String gitLink) {
        this.gitLink = gitLink;
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

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", gitLink='" + gitLink + '\'' +
                ", category=" + category +
                ", status=" + status +
                '}';
    }
}
